FROM python:3.6
WORKDIR /app
COPY . /app
RUN pip install --upgrade pip
RUN pip install -r requirements.txt
EXPOSE 5000
WORKDIR /app/src
RUN ls -l
CMD ["gunicorn", "-w 4", "-b", "0.0.0.0:5000", "main:app"]
