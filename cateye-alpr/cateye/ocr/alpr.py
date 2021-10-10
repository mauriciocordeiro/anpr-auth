import os
import cv2
import numpy as np
import inspect
from datetime import datetime
from cateye.nn.pytorch.yolov4.yolov4 import Yolov4
from cateye.image.processing import save

class CatEyeALPR:

    def __init__(self,
                 configurationFile=None,
                 modelFile=None,
                 classFile=None,
                 enableCUDA=True):

        path = os.path.dirname(inspect.getfile(CatEyeALPR))
        configurationFile = path+'/models/alpr_yolov4_tiny.cfg' if configurationFile is None else configurationFile
        modelFile = path + '/models/alpr_yolov4_tiny.weights' if modelFile is None else modelFile
        classFile = path + '/models/alpr_yolov4_tiny.names' if classFile is None else classFile

        try:
            self.net = Yolov4(configurationFile=configurationFile,
                              modelFile=modelFile,
                              classFile=classFile,
                              width=160,
                              height=160,
                              threshold=0.4,
                              nmsThreshold=0.3,
                              enableCUDA=enableCUDA)
            self.net.init()
            self.warmUp()

            print("CatEyeALPR ready to predict")
        except Exception as e:
            print("Error initiating CatEyeALPR", e)

    def warmUp(self):
        print("CatEyeALPR warming up...")
        img = np.zeros([160, 160, 3], dtype=np.uint8)
        self.recognize(img)

    def storeToActiveLearning(self, img, bboxes, pathToStoreFails):
        annotation = ''
        for i in range(len(bboxes)):
            bbox = bboxes[i]
            w, h = bbox.w / 160, bbox.h / 160
            x1, y1 = (bbox.x + (bbox.w / 2)) / 160, (bbox.y + (bbox.h / 2)) / 160

            if i > 0:
                annotation += '\n'
            annotation += f'{bbox.classId} {x1} {y1} {w} {h}'

        save(img, pathToStoreFails, annotation)

    def recognize(self, img,
                  twoLines=False,
                  ignoreShortDetections=True,
                  storeFailsToActiveLearning=False,
                  pathToStoreFails=None):

        bboxes = self.net.detect(img=img)
        plate, conf = '', 0.0

        if ignoreShortDetections and len(bboxes) < 7:
            # stores short detections to retraing the model
            if storeFailsToActiveLearning and len(bboxes) > 1 and pathToStoreFails is not None:
                self.storeToActiveLearning(img, bboxes, pathToStoreFails)
            return plate, conf, bboxes

        if len(bboxes) > 0:
            try:
                if twoLines:
                    # line1 = sorted(bboxes, key=lambda b: b.classId, reverse=True)[:4]
                    line1 = [b for b in sorted(bboxes, key=lambda b: b.y) if b.classId > 9][:4]
                    topLeft = None
                    for b in sorted(line1, key=lambda b: b.x):
                        if topLeft is None or (b.x <= topLeft.x and b.y <= topLeft.y):
                            topLeft = b
                    line1 = [b for b in sorted(line1, key=lambda b: b.y) if b != topLeft][:2]
                    line1.append(topLeft)
                    line1.sort(key=lambda b: b.x)
                    line2 = [b for b in bboxes if b not in line1]
                    line2 = sorted(line2, key=lambda b: b.x)
                    line1.extend(line2)
                    plate = "".join(str(tb.className) for tb in line1)
                else:
                    plate = "".join(str(b.className) for b in sorted(bboxes, key=lambda b: b.x))

                conf = round(sum(b.confidence for b in bboxes) / 7, 2) #len(bboxes)
            except:
                pass

        return plate, conf, bboxes

    def alphaCorrection(self, str):
        map = {
            '0': 'O',
            '1': 'I',
            '2': 'Z',
            '3': 'B',
            '4': 'A',
            '5': 'S',
            '8': 'B'
        }

        for c, i in enumerate(str):
            if c in map:
                str[i] = map[c]

        return str