import inspect
import os
import threading
import cv2
import numpy as np

from flask import Flask, Response, render_template, request


class EndpointAction(object):

    def __init__(self, action):
        self.action = action

    def __call__(self, *args):
        return self.action()


class CatEyeALPRApp(object):

    def __init__(self, name, detector):
        self.app = Flask(name)
        self.detector = detector
        self.appPath = os.path.dirname(inspect.getfile(CatEyeALPRApp))
        self.lock = threading.Lock()

    def run(self):
        self.initEndpoints()
        threading.Thread(target=self.startServer).start()

    def startServer(self):
        self.app.run(host="0.0.0.0")

    def addEndpoint(self, endpoint=None, endpoint_name=None, handler=None, methods=None):
        self.app.add_url_rule(endpoint, endpoint_name, EndpointAction(handler), methods=methods)

    def initEndpoints(self):
        self.addEndpoint(endpoint='/', endpoint_name='/', handler=self.index)
        self.addEndpoint(endpoint='/status', endpoint_name='status', handler=self.status)
        self.addEndpoint(endpoint='/upload', endpoint_name='upload', handler=self.upload)
        self.addEndpoint(endpoint='/alpr', endpoint_name='alpr', handler=self.alpr, methods=['POST', 'GET'])

    def index(self):
        return render_template("index.html")

    def upload(self):
        return render_template("upload.html")

    def alpr(self):
        if request.method == 'POST':
            f = request.files['file']

            img = cv2.imdecode(np.frombuffer(f.read(), np.uint8), -1)
            return Response(self.detector.detect(img), 200)

    def status(self):
        return Response('Running...<br/>Detector CatEye ALPR', 200)
