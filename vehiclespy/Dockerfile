FROM python:3-alpine
WORKDIR /app
RUN pip3 install flask
RUN pip3 install bson
RUN pip3 install flask_pymongo
RUN pip3 install gunicorn
RUN pip3 install requests
RUN pip3 install -U flask-cors
COPY ["main.py", "/app"]
EXPOSE 5001
CMD ["gunicorn", "-w 4", "-b", "0.0.0.0:5001", "main:app"]