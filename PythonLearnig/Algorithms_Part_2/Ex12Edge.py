class Edge:

    def __init__(self, v, w, weight):
        self.v = v
        self.w = w
        self.weight = weight

    def either(self):
        return self.v

    def other(self, v):
        if v == self.v:
            return self.w
        elif v == self.w:
            return self.v
        else:
            raise ValueError

    def get_weight(self):
        return self.weight

    def compare_to(self, other):
        return self.get_weight() - other.get_weight()

    def __str__(self):
        return f'{self.v} <-- {self.weight} --> {self.w}'


if __name__ == '__main__':
    edge = Edge(2, 7, 0.5)
    print(edge)
