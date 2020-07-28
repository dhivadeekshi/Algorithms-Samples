from Ex12Edge import Edge


class EdgeWeightedGraph:

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

    # noinspection PyTypeChecker
    def add_edge(self, v, w, weight):
        edge = Edge(v, w, weight)
        self.adj[v].append(edge)
        self.adj[w].append(edge)

    def get_adj(self, v):
        for edge in self.adj[v]:
            yield edge

    def get_v(self):
        return self.v

    def get_e(self):
        return self.e

    def __str__(self):
        builder = f'Vertices: {self.v} Edges: {self.e}\n'
        for i in range(self.get_v()):
            builder += f'{i}:'
            for edge in self.get_adj(i):
                builder += f' {edge.other(i)},{edge.get_weight()} '
            builder += '\n'
        return builder


def test_edge_weighted_graph():
    graph = EdgeWeightedGraph(0)
    graph.init_from('tinyEWG.txt')
    print(graph)


if __name__ == '__main__':
    test_edge_weighted_graph()
