class DirectedEdge:

    def __init__(self, v, w, weight):
        self.v = v
        self.w = w
        self.weight = weight

    def from_v(self):
        return self.v

    def to_v(self):
        return self.w

    def get_weight(self):
        return self.weight

    def compare_to(self, that):
        return self.get_weight() - that.get_weight()

    def __str__(self):
        return f'{self.from_v()} -- {self.weight} --> {self.to_v()}'


def test_directed_graph():
    print(DirectedEdge(3, 7, 10.7))


if __name__ == '__main__':
    test_directed_graph()
