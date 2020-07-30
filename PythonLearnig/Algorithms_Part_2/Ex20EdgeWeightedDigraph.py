from Ex19DirectedEdge import DirectedEdge


class EdgeWeightedDigraph:

    def __init__(self, v):
        self.v = v
        self.e = 0
        self.adj = [[] for _ in range(self.v)]

    def init_from(self, file_name):

        with open(file_name) as f:
            self.v = int(f.readline())
            self.e = int(f.readline())
            self.adj = [[] for _ in range(self.v)]
            for _ in range(self.e):
                v, w, weight = f.readline().split()
                self.add_edge(int(v), int(w), float(weight))
                self.e -= 1

    # noinspection PyTypeChecker
    def add_edge(self, v, w, weight):
        self.adj[v].append(DirectedEdge(v, w, weight))
        self.e += 1

    def get_v(self):
        return self.v

    def get_e(self):
        return self.e

    def get_adj(self, v):
        for adj in self.adj[v]:
            yield adj

    def __str__(self):
        builder = f'Vertices: {self.get_v()} Edges: {self.get_e()}'
        for v in range(self.get_v()):
            builder += f'\n{v}:'
            for adj in self.get_adj(v):
                builder += f' {adj.to_v()},{adj.get_weight()} '
        return builder + '\n'


def test_edge_weighted_digraph():
    digraph = EdgeWeightedDigraph(0)
    digraph.init_from('tinyEWD.txt')
    print(digraph)


if __name__ == '__main__':
    test_edge_weighted_digraph()
