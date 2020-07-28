class Digraph:

    def __init__(self, v):
        self.v = v
        self.e = 0
        self.adj = [[] for _ in range(v)]

    def init_from(self, file_name):
        with open(file_name) as f:
            self.v = int(f.readline())
            self.e = int(f.readline())

            self.adj = [[] for _ in range(self.v)]

            for i in range(self.e):
                v1, v2 = f.readline().split()
                self.add_edge(int(v1), int(v2))
                self.e -= 1

    def add_edge(self, v, w):
        self.adj[v].append(w)
        self.e += 1

    def get_adj(self, v):
        for adj in self.adj[v]:
            yield adj

    def get_v(self):
        return self.v

    def get_e(self):
        return self.e

    def __str__(self):
        builder = f'Vertices: {self.get_v()} Edges: {self.get_e()}'
        for i in range(self.v):
            builder += f'\n{i}: '
            for adj in self.adj[i]:
                builder += f' {adj}'
        return builder


def test_digraph():
    digraph = Digraph(0)
    digraph.init_from('digraph.txt')
    print(digraph)


if __name__ == '__main__':
    test_digraph()
