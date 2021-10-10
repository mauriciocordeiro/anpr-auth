import cv2
import imagezmq
import imutils
from datetime import datetime
from cateye.source.source import Source


class ImageZMQSource(Source):

    def __init__(self, id, name, source='tcp://*:5555', scale=1.0, width=None, height=None, automaticReply=True):
        super().__init__(id, name, source, scale, width, height)
        self._imageHub = imagezmq.ImageHub(source)
        self._lastUpdate = {}
        self._frameDict = {}
        self._originalFrameDict = {}
        self._lastClientName = None
        self._automaticReply = automaticReply

    def more(self):
        return True

    def acquire(self):
        try:
            (clientName, frame) = self._imageHub.recv_image()

            if self._automaticReply:
                self._imageHub.send_reply(b'OK')

            if clientName not in self._lastUpdate.keys():
                print(f"[INFO] receiving data from {clientName}...")

            if self.width is not None:
                frame = imutils.resize(frame, width=self.width)

            self._lastUpdate[clientName] = datetime.now()
            self._frameDict[clientName] = frame
            self._originalFrameDict[clientName] = frame.copy()
            self._lastClientName = clientName
            self._frame = frame
        except Exception as err:
            self._imageHub.send_reply(b'Error')

    def send(self, message):
        self._imageHub.send_reply(message)

    def __next__(self):

        if self.more():
            self.acquire()

            if self.scale != 1.0:
                self._frame = self.rescale()

            self._next += 1
            self._originalFrame = self._frame.copy()
            return self._frame, self._lastClientName

        raise StopIteration


    def frame(self, clientName=None):
        return self._frame if clientName is None else self._frameDict[clientName]

    def original(self, clientName=None):
        return self._originalFrame if clientName is None else self._originalFrameDict[clientName]

