FROM python:3.8-slim-buster

LABEL "br.com.cateye"="CatEye BR"
LABEL "author"="Leonardo Sapucaia"
LABEL "version"="1.0"
LABEL "description"="Automated License Plate Recognizer built with CatEye Technology"

EXPOSE 5000
EXPOSE 5556

ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1

COPY requirements.txt .
RUN python -m pip install -r requirements.txt

WORKDIR /app
COPY . /app

USER root
RUN apt-get update && apt-get install -y --no-install-recommends \
        ffmpeg \
        libsm6 \
        libxext6 \
    && rm -rf /var/lib/apt/lists/*

RUN useradd appuser && chown -R appuser /app
USER appuser

ENTRYPOINT python /app/server.py