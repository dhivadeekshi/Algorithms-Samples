class Graph:

    def __init__(self, v):
        self.v = v
        self.e = 0
        self.adj = [[] for _ in range(v)]

    def add_edge(self, v, w):
        self.adj[v].append(w)
        self.adj[w].append(v)
        self.e += 1

    def get_v(self):
        return self.v

    def det_e(self):
        return self.e

    def get_adj(self, v):
        for adj_v in self.adj[v]:
            yield adj_v

    def __str__(self):
        builder = f'Vertices: {self.v} Edges: {self.e}'
        for v in range(self.v):
            builder += f'\n{v}:'
            for adj_v in self.adj[v]:
                builder += f' {adj_v}'
        return builder


def test_graph():
    graph = Graph(5)
    print(graph)


if __name__ == '__main__':
    test_graph()
