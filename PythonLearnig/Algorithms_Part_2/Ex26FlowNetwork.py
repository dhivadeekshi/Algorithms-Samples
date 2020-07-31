from Ex25FlowEdge import FlowEdge


class FlowNetwork:

    def __init__(self, v):
        self.v = v
        self.e = 0
        self.adj = [[] for _ in range(self.v)]

    def init_from(self, file_name):
        with open(file_name) as f:
            self.v = int(f.readline())
            self.e = int(f.readline())
            self.adj = [[] for _ in range(self.v)]
            for i in range(self.e):
                v, w, c = f.readline().split()
                self.add_edge(FlowEdge(int(v), int(w), float(c)))
                self.e -= 1

    def add_edge(self, edge):
        v = edge.from_v()
        w = edge.to_v()
        self.adj[v].append(edge)
        self.adj[w].append(edge)
        self.e += 1

    def get_adj(self, v):
        for edge in self.adj[v]:
            yield edge

    def __str__(self):
        builder = f'Vertices: {self.v} Edges: {self.e}'
        for v in range(self.v):
            builder += f'\n{v}:'
            for adj in self.get_adj(v):
                builder += f' {adj} '
        return builder


def test_flow_network():
    fn = FlowNetwork(0)
    fn.init_from('tinyFN.txt')
    print(fn)


if __name__ == '__main__':
    test_flow_network()
