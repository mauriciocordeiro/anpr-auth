import cv2


class Source:

    def __init__(self, id, name, source, scale=1.0, width=None, height=None):
        self.id = id
        self.name = name
        self.source = source
        self.scale = scale
        self.width = width
        self.height = height

        self._next = 0

        self._originalFrame = None
        self._frame = None
        self._frameStartTime = 0

        self._videoOut = None

    def __repr__(self):
        return str(self.__class__)

    def __str__(self):
        return str(self.__class__) + ": " + str(self.__dict__)

    def __iter__(self):
        self._next = 0
        return self

    def __next__(self):

        if self.more():
            self.acquire()

            if self.scale != 1.0:
                self._frame = self.rescale()

            self._next += 1
            self._originalFrame = self._frame.copy()
            return self._frame

        raise StopIteration

    def more(self):
        return False

    def acquire(self):
        #self._frame =
        pass

    def rescale(self):
        return cv2.resize(self._frame, None, fx=self.scale, fy=self.scale)

    def set(self, frame):
        self._frame = frame
        self._originalFrame = frame.copy()
        self._next += 1

    def frame(self):
        return self._frame

    def original(self):
        return self._originalFrame

    def count(self):
        return self._next

    def crop(self, roi, offset=10, original=False):
        x, y, w, h = roi[0], roi[1], roi[2], roi[3]
        img = self._originalFrame if original else self._frame

        height, width, _ = img.shape

        y1 = max(y - offset, 0)
        y2 = min(y + h + offset, height)
        x1 = max(x - offset, 0)
        x2 = min(x + w + offset, width)

        return img[y1:y2, x1:x2]

    def save(self, pathName, original=False):
        img = self._frame if not original else self._originalFrame
        cv2.imwrite(f'{pathName}.png', img)

    def setRecorder(self, pathName, fps=20):
        height, width, channels = self._frame.shape
        self._videoOut = cv2.VideoWriter(pathName, cv2.VideoWriter_fourcc(*'XVID'), fps, (width, height))

    def record(self, original=False):
        img = self._frame if not original else self._originalFrame
        if self._videoOut is not None:
            self._videoOut.write(img)

    def releaseRecorder(self):
        if self._videoOut is not None:
            self._videoOut.release()
            self._videoOut = None

    def getSourceById(id, filePath):
        source = None
        with open(filePath) as jsonFile:
            data = json.load(jsonFile)
            for vs in data['sources']:
                if vs['id'] == id:
                    source = Source(vs['id'],
                                    vs['name'],
                                    vs['source'],
                                    vs['scale'] if 'scale' in vs else 1.0)
                    break
        return source


