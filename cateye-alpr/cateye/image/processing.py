import numpy as np
import cv2
from datetime import datetime
from scipy.ndimage import interpolation as inter


def correctSkew(image, delta=1, limit=5):

    def determine_score(arr, angle):
        data = inter.rotate(arr, angle, reshape=False, order=0)
        histogram = np.sum(data, axis=1)
        score = np.sum((histogram[1:] - histogram[:-1]) ** 2)
        return histogram, score

    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    thresh = cv2.threshold(gray, 0, 255, cv2.THRESH_BINARY_INV + cv2.THRESH_OTSU)[1]

    scores = []
    angles = np.arange(-limit, limit + delta, delta)
    for angle in angles:
        histogram, score = determine_score(thresh, angle)
        scores.append(score)

    best_angle = angles[scores.index(max(scores))]

    (h, w) = image.shape[:2]
    center = (w // 2, h // 2)
    M = cv2.getRotationMatrix2D(center, best_angle, 1.0)
    rotated = cv2.warpAffine(image, M, (w, h), flags=cv2.INTER_CUBIC, \
                             borderMode=cv2.BORDER_REPLICATE)

    return best_angle, rotated


def resizePad(image, desired_size):

    old_size = image.shape[:2]  # old_size is in (height, width) format

    ratio = float(desired_size) / max(old_size)
    new_size = tuple([int(x * ratio) for x in old_size])

    # new_size should be in (width, height) format

    im = cv2.resize(image, (new_size[1], new_size[0]))

    delta_w = desired_size - new_size[1]
    delta_h = desired_size - new_size[0]
    top, bottom = delta_h // 2, delta_h - (delta_h // 2)
    left, right = delta_w // 2, delta_w - (delta_w // 2)

    color = [0, 0, 0]
    new_im = cv2.copyMakeBorder(im, top, bottom, left, right, cv2.BORDER_CONSTANT,
                                value=color)

    return new_im

def crop(img, roi, offset=10):
        x, y, w, h = roi[0], roi[1], roi[2], roi[3]

        height, width, _ = img.shape

        y1 = max(y - offset, 0)
        y2 = min(y + h + offset, height)
        x1 = max(x - offset, 0)
        x2 = min(x + w + offset, width)

        return img[y1:y2, x1:x2]

def save(img, path, annotation=None):
    fileName = f'{datetime.now().strftime("%Y%m%d_%H%M%S%f")}'

    cv2.imwrite(f'{path}/{fileName}.jpg', img)
    if annotation is not None:
        annotationFile = open(f'{path}/{fileName}.txt', 'w')
        annotationFile.write(annotation)
        annotationFile.close()

