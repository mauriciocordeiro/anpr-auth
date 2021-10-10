import cv2
import imagezmq
import time
from app import CatEyeALPRApp
from cateye.detection.detectionarea import DetectionArea, PercentualShape
from cateye.drawing.overlay import Overlay
from cateye.image.processing import correctSkew, resizePad, crop
from cateye.nn.pytorch.yolov4.yolov4 import Yolov4
from cateye.ocr.alpr import CatEyeALPR
from cateye.source.zmqsource import ImageZMQSource

imageHub = imagezmq.ImageHub()


class Detector:
    def __init__(self):
        self.source = ImageZMQSource('ImageZMQServer', 'Servidor ZMQ', source='tcp://*:5556', width=416, automaticReply=False)
        self.yolov4 = Yolov4(configurationFile='assets/plates_mercosulbr_yolov4tiny_v1.cfg',
                             modelFile='assets/plates_mercosulbr_yolov4tiny_v1.weights',
                             classFile='assets/plates_mercosulbr_yolov4tiny_v1.names',
                             width=416,
                             height=416,
                             threshold=0.5,
                             nmsThreshold=0.3,
                             source=self.source, 
                             enableCUDA=False)
        self.yolov4.init()
        self.overlay = Overlay(self.source)
        self.detectionArea = None

        print('Loading CatEye ALPR...')
        self.cateyeALPR = CatEyeALPR(enableCUDA=False)

    def alpr(self, bbox, image):
        twoLines = bbox.classId > 1
        cropImg = crop(image, (bbox.x, bbox.y, bbox.w, bbox.h), offset=0)
        _, cropImg = correctSkew(cropImg)
        cropImg = resizePad(cropImg, 160)
        
        placa, conf, bbxs = self.cateyeALPR.recognize(cropImg, twoLines, storeFailsToActiveLearning=True, pathToStoreFails='./failsaves')    
        return placa, conf

    def detect(self, image=None):
        initial = time.time()

        if image is None and self.source is not None:
            image = self.source.frame()

        (h, w) = image.shape[:2]
        nSize = max(w, h)
        image = resizePad(image, nSize)
        (h, w) = image.shape[:2]

        self.detectionArea = DetectionArea('det', 'Area de Deteccao', PercentualShape(1, 1, 99, 99, w, h))
        bboxes = detector.yolov4.detect(detectionArea=detector.detectionArea, img=image)

        if len(bboxes) > 0:
            box = None
            for b in bboxes:
                if box is None or box.confidence < b.confidence:
                    box = b

            placa, conf = self.alpr(box, image)
            result = f'{{"type": "{box.className}", "number": "{placa}", "confidence": {str(conf)}, "roi": [{box.x}, {box.y}, {box.w}, {box.h}]'
        else:
            result = '{"type": "-", "number": "-------", "confidence": 0.0, "roi": [0, 0, 0, 0]'
        
        final = time.time()

        return result+f', "time": {final-initial:.2f}}}'

if __name__ == "__main__":
    print(f"[INFO] iniciando CatEye ALPR server...")
    detector = Detector()

    alprWebApp = CatEyeALPRApp("CatEyeALPRApp", detector)
    alprWebApp.run()

    for frame, clientName in detector.source:
        result = detector.detect()
        detector.source.send(result.encode())

