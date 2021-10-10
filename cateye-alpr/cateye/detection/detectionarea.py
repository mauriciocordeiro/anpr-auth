
class Shape:
    def __init__(self, x, y, w, h):
        self.x = x
        self.y = y
        self.w = w
        self.h = h

    def getValues(self):
        return self.x, self.y, self.w, self.h

    def getPt1(self):
        return self.x, self.y

    def getPt2(self):
        return self.x+self.w, self.y+self.h

    def setPt1(self, pt1):
        self.x = pt1[0]
        self.y = pt1[1]

    def setPt2(self, pt2):
        self.w = pt2[0]-self.x
        self.h = pt2[1]-self.y

class PercentualShape(Shape):
    def __init__(self, pX1, pY1, pX2, pY2, width, height):
        self.x = int(pX1/100*width)
        self.y = int(pY1/100*height)
        self.w = int((pX2/100*width)-self.x)
        self.h = int((pY2/100*height)-self.y)

class DetectionArea:

    TYPE_LINE = 'line'
    TYPE_RECT = 'rect'
    TYPE_POLYGON = 'polygon'

    OVERLAP_BBOX_CENTER = 'center'
    OVERLAP_BBOX_TOPLEFT = 'topleft'

    def __init__(self, id, name, shape=Shape(0, 0, 0, 0), type=TYPE_RECT, callback=None):
        self.id = id
        self.name = name
        self.shape = shape
        self.type = type
        self.callback = callback
        # print(f'New DetectionArea [{id}, {name}, {shape.getValues()}, {type}] ')

    def setPoints(self, pt1, pt2):
        self.shape.setPt1(pt1)
        self.shape.setPt2(pt2)

    #only for rect now
    def detectOverlap(self, bbox, type=OVERLAP_BBOX_CENTER):


        if type == DetectionArea.OVERLAP_BBOX_CENTER:
            x = int(bbox.x+(bbox.w/2))
            y = int(bbox.y+(bbox.h/2))

        if x >= self.shape.x and x <= (self.shape.x + self.shape.w) and y >= self.shape.y and y <= (self.shape.y + self.shape.h):
            if self.callback:
                self.callback(self, bbox)
            return True
        else:
            return False

