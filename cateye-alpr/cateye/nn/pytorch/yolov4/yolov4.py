#Binding para rede neural convulacional YoloV4 usando framework PyTorch
import cv2
import numpy as np
import torch
import torch.backends.cudnn as cudnn

from cateye.nn.pytorch.yolov4.darknet2pytorch import Darknet
from cateye.drawing.boundingbox import BBox
from cateye.drawing.colors import Colors


class Yolov4:

    def __init__(self, configurationFile, modelFile, classFile, width, height, threshold, nmsThreshold, source=None, enableCUDA=True):
        self.configurationFile = configurationFile
        self.modelFile = modelFile
        self.classFile = classFile
        self.width = width
        self.height = height
        self.threshold = threshold
        self.nmsThreshold = nmsThreshold

        self.enableCUDA = enableCUDA

        self.classNames = []
        self.net = None
        self.colors = None

        self.source = source

    def init(self):
        if self.net is None:
            self.net = Darknet(self.configurationFile)

            if self.enableCUDA:
                cudnn.benchmark = True

            # self.net.print_network()
            self.net.load_weights(self.modelFile)
            print('Loading weights from %s...' % self.modelFile)

            if self.enableCUDA:
                self.net.cuda()

        with open(self.classFile, 'rt') as f:
            self.classNames = f.read().rstrip('\n').split('\n')

        self.colors = Colors(30000)

        self.net.eval()
        print('...Done!')

    def nms_cpu(self, boxes, confs, nms_thresh=0.5, min_mode=False):
        # print(boxes.shape)
        x1 = boxes[:, 0]
        y1 = boxes[:, 1]
        x2 = boxes[:, 2]
        y2 = boxes[:, 3]

        areas = (x2 - x1) * (y2 - y1)
        order = confs.argsort()[::-1]

        keep = []
        while order.size > 0:
            idx_self = order[0]
            idx_other = order[1:]

            keep.append(idx_self)

            xx1 = np.maximum(x1[idx_self], x1[idx_other])
            yy1 = np.maximum(y1[idx_self], y1[idx_other])
            xx2 = np.minimum(x2[idx_self], x2[idx_other])
            yy2 = np.minimum(y2[idx_self], y2[idx_other])

            w = np.maximum(0.0, xx2 - xx1)
            h = np.maximum(0.0, yy2 - yy1)
            inter = w * h

            if min_mode:
                over = inter / np.minimum(areas[order[0]], areas[order[1:]])
            else:
                over = inter / (areas[order[0]] + areas[order[1:]] - inter)

            inds = np.where(over <= nms_thresh)[0]
            order = order[inds + 1]

        return np.array(keep)

    def detect(self, img=None, detectionArea=None, filterClasses=None):
        if img is None and self.source is not None:
            img = self.source.frame()

        #preprocess
        sized = cv2.resize(img, (self.net.width, self.net.height))
        sized = cv2.cvtColor(sized, cv2.COLOR_BGR2RGB)
        #sized = cv2.cvtColor(sized, cv2.COLOR_BGR2GRAY)
        #cv2.imshow('yolo vision', sized)
        #cv2.waitKey(0)

        #detect
        # self.net.eval()

        if type(sized) == np.ndarray and len(sized.shape) == 3:  # cv2 image
            sized = torch.from_numpy(sized.transpose(2, 0, 1)).float().div(255.0).unsqueeze(0)
        elif type(sized) == np.ndarray and len(sized.shape) == 4:
            sized = torch.from_numpy(sized.transpose(0, 3, 1, 2)).float().div(255.0)
        else:
            print("unknow image type")
            exit(-1)

        if self.enableCUDA:
            sized = sized.cuda()
        sized = torch.autograd.Variable(sized)

        output = self.net(sized)

        #postprocess

        # [batch, num, 1, 4]
        box_array = output[0]

        # [batch, num, num_classes]
        confs = output[1]

        if type(box_array).__name__ != 'ndarray':
            box_array = box_array.cpu().detach().numpy()
            confs = confs.cpu().detach().numpy()

        num_classes = confs.shape[2]

        # [batch, num, 4]
        box_array = box_array[:, :, 0]

        # [batch, num, num_classes] --> [batch, num]
        max_conf = np.max(confs, axis=2)
        max_id = np.argmax(confs, axis=2)

        resultBBoxes = []
        for i in range(box_array.shape[0]):

            argwhere = max_conf[i] > self.threshold
            l_box_array = box_array[i, argwhere, :]
            l_max_conf = max_conf[i, argwhere]
            l_max_id = max_id[i, argwhere]

            hT, wT, cT = img.shape

            # nms for each class
            for j in range(num_classes):

                cls_argwhere = l_max_id == j
                ll_box_array = l_box_array[cls_argwhere, :]
                ll_max_conf = l_max_conf[cls_argwhere]
                ll_max_id = l_max_id[cls_argwhere]

                keep = self.nms_cpu(ll_box_array, ll_max_conf, self.nmsThreshold)

                if keep.size > 0:
                    ll_box_array = ll_box_array[keep, :]
                    ll_max_conf = ll_max_conf[keep]
                    ll_max_id = ll_max_id[keep]

                    for k in range(ll_box_array.shape[0]):

                        # filtering detections
                        if filterClasses is not None and self.classNames[ll_max_id[k]] not in filterClasses:
                            continue

                        x, y = int(ll_box_array[k, 0] * wT), int(ll_box_array[k, 1] * hT)
                        w, h = int((ll_box_array[k, 2] * wT) - x), int((ll_box_array[k, 3] * hT) - y)

                        bbox = BBox(classId=ll_max_id[k],
                                    className=self.classNames[ll_max_id[k]],
                                    confidence=ll_max_conf[k],
                                    x=x,
                                    y=y,
                                    w=w,
                                    h=h,
                                    color=self.colors.color(ll_max_id[k]))

                        #in case of detection area, only consider detections overlaping that area
                        if detectionArea is None or detectionArea.detectOverlap(bbox):
                            resultBBoxes.append(bbox)

        return resultBBoxes

