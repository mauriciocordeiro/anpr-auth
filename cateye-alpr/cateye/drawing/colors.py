import random
import colorsys


class Colors:

    def __init__(self, colorRange):
        self.colorRange = colorRange

        hsvTuples = [(1.0 * x / self.colorRange, 1., 1.) for x in range(self.colorRange)]
        colors = list(map(lambda x: colorsys.hsv_to_rgb(*x), hsvTuples))
        colors = list(map(lambda x: (int(x[0] * 255), int(x[1] * 255), int(x[2] * 255)), colors))
        random.seed(0)
        random.shuffle(colors)
        random.seed(None)

        self.colors = colors

    def color(self, index=None):

        if index is None:
            index = random.randint(0, len(self.colors))

        return self.colors[index]
