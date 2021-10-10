
class BBox:

    def __init__(self, classId, className, confidence, x, y, w, h, color, id=None):
        self.classId = classId
        self.className = className
        self.confidence = confidence
        self.x = x
        self.y = y
        self.w = w
        self.h = h
        self.color = color
        self.id = id

    def coordsToX1X2(self, width, height):
        x1 = self.x / width
        y1 = self.y / height
        x2 = (self.x + self.w) / width
        y2 = (self.y + self.h) / height
        return [x1, y1, x2, y2]

    def __str__(self):
        return f'({self.classId}, {self.className}, {self.confidence}, {self.x}, {self.y}, {self.w}, {self.h})'



