import cv2
import numpy as np


class Overlay:

    def __init__(self, source=None):
        self.source = source

    def drawFPS(self):
        cv2.putText(self.source.frame(), "{:.1f} FPS".format(self.source.fps()),
                    (5, 30), cv2.FONT_HERSHEY_SIMPLEX, 1,
                    (0, 0, 255), 1, cv2.LINE_AA)

    def drawBoundingBox(self, bbox, img=None, labelTemplate=None, color=None):

        if img is None and self.source is not None:
            img = self.source.frame()

        bboxThick = int(0.6 * (bbox.h + bbox.w) / 1000)
        if bboxThick < 1: bboxThick = 1
        fontScale = 0.75 * bboxThick

        color = bbox.color if color is None else color

        cv2.rectangle(img, (bbox.x, bbox.y), (bbox.x+bbox.w, bbox.y+bbox.h), color, bboxThick)

        if labelTemplate is None:
            label = f'{bbox.className.upper()}' \
                    f'{"("+bbox.id+")" if bbox.id is not None else ""} ' \
                    f'{int(bbox.confidence*100)}%'
        else:
            label = labelTemplate.format(**bbox.__dict__)

        (text_width, text_height), baseline = cv2.getTextSize(label, cv2.FONT_HERSHEY_COMPLEX_SMALL,
                                                              fontScale, thickness=bboxThick)
        cv2.rectangle(img, (bbox.x, bbox.y), (bbox.x + text_width, bbox.y - text_height - baseline), color,
                      thickness=cv2.FILLED)
        cv2.putText(img, label, (bbox.x, bbox.y-4), cv2.FONT_HERSHEY_PLAIN,
                        fontScale, (255, 255, 255), bboxThick, lineType=cv2.LINE_AA)

        return img

    def drawBoxes(self, bboxes, img=None, labelTemplate=None, color=None):
        for bbox in bboxes:
            self.drawBoundingBox(bbox, img, labelTemplate, color)

    def drawDetectionArea(self, detectionArea, color=(0, 255, 0), thickness=1):

        img = self.source.frame()

        thick = int(0.6 * (detectionArea.shape.h + detectionArea.shape.w) / 1000)
        if thick < 1: thick = 1
        fontScale = 0.75 * thick

        cv2.rectangle(img, (detectionArea.shape.x, detectionArea.shape.y),
                      (detectionArea.shape.x + detectionArea.shape.w, detectionArea.shape.y + detectionArea.shape.h),
                      color, thickness)
        (textWidth, textHeight), baseline = cv2.getTextSize(detectionArea.id, cv2.FONT_HERSHEY_COMPLEX_SMALL,
                                                              fontScale, thickness=thick)
        cv2.rectangle(img, (detectionArea.shape.x, detectionArea.shape.y),
                           (detectionArea.shape.x + textWidth, detectionArea.shape.y - textHeight - baseline),
                      color, thickness=cv2.FILLED)
        cv2.putText(img, detectionArea.id, (detectionArea.shape.x, detectionArea.shape.y - 4),
                    cv2.FONT_HERSHEY_PLAIN,
                    fontScale, (255, 255, 255), thick, lineType=cv2.LINE_AA)

    def drawTracklet(self, tracklet, drawTrajectory=False):

        img = self.source.frame()
        height, width, channels = img.shape


        if drawTrajectory:
            boxes = tracklet.bboxes[-8:]
            for i in range(len(boxes)):
                if i > 0:
                    lastPt1 = (int(boxes[i-1][0]*width), int(boxes[i-1][1]*height))
                    lastPt2 = (int(boxes[i-1][2]*width), int(boxes[i-1][3]*height))
                    lastPt = (lastPt1[0]+int((lastPt2[0]-lastPt1[0])/2), lastPt2[1])

                    pt1 = (int(boxes[i][0]*width), int(boxes[i][1]*height))
                    pt2 = (int(boxes[i][2]*width), int(boxes[i][3]*height))
                    pt = (pt1[0]+int((pt2[0]-pt1[0])/2), pt2[1])

                    # thickness = int(np.sqrt(len(boxes) / float(i + 1)) * 2.5)
                    thickness = i
                    cv2.line(img, lastPt, pt, tracklet.color, thickness)
                    # cv2.circle(img, lastPt, thickness+1, tracklet.color, thickness=-1)


        bbox = tracklet.getLastBoundingBox()
        x, y = int(bbox[0] * width), int(bbox[1] * height)
        w, h = int((bbox[2]-bbox[0]) * width), \
               int((bbox[3]-bbox[1]) * height)

        thick = int(0.6 * (h + w) / 1000)
        if thick < 1: thick = 1
        fontScale = 0.75 * thick

        cv2.rectangle(img, (x, y), (x+w, y+h), tracklet.color, thick)

        label = f'{tracklet.className.upper()} ' \
                f'{tracklet.id if tracklet.id is not None else ""}'

        (text_width, text_height), baseline = cv2.getTextSize(label, cv2.FONT_HERSHEY_COMPLEX_SMALL,
                                                              fontScale, thickness=thick)
        cv2.rectangle(img, (x, y), (x + text_width, y - text_height - baseline), tracklet.color,
                      thickness=cv2.FILLED)
        cv2.putText(img, label, (x, y-4), cv2.FONT_HERSHEY_PLAIN,
                        fontScale, (255, 255, 255), thick, lineType=cv2.LINE_AA)

        return img

    def drawTracker(self, tracker, key=None, drawTrajectory=False):

        tck = tracker if key is None else tracker.get(key)

        for tracklet in tck.activeTracklets:
            self.drawTracklet(tracklet, drawTrajectory)

    def drawImage(self, img, x=None, y=None):
        if x is None:
            x = self.source.frame().shape[1] - img.shape[1] - 30
        if y is None:
            y = 30

        x = int(x)
        y = int(y)

        self.source.frame()[y:y + img.shape[0], x:x + img.shape[1]] = img

    def drawText(self, text, uvTopLeft, color=(255, 255, 255), fontScale=0.5, thickness=1,
                 fontFace=cv2.FONT_HERSHEY_SIMPLEX, outlineColor=(0, 0, 0), lineSpacing=1.5):

        assert isinstance(text, str)

        uvTopLeft = np.array(uvTopLeft, dtype=float)
        assert uvTopLeft.shape == (2,)

        for line in text.splitlines():
            (w, h), _ = cv2.getTextSize(
                text=line,
                fontFace=fontFace,
                fontScale=fontScale,
                thickness=thickness,
            )
            uvBottomLeftI = uvTopLeft + [0, h]
            org = tuple(uvBottomLeftI.astype(int))

            if outlineColor is not None:
                cv2.putText(
                    self.source.frame(),
                    text=line,
                    org=org,
                    fontFace=fontFace,
                    fontScale=fontScale,
                    color=outlineColor,
                    thickness=thickness * 3,
                    lineType=cv2.LINE_AA,
                )
            cv2.putText(
                self.source.frame(),
                text=line,
                org=org,
                fontFace=fontFace,
                fontScale=fontScale,
                color=color,
                thickness=thickness,
                lineType=cv2.LINE_AA,
            )

            uvTopLeft += [0, h * lineSpacing]
