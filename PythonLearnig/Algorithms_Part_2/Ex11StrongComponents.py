from Ex6Digraph import Digraph


class StrongComponents:

    def __init__(self, digraph):
        self.counter = 0
        self.connected_to = [-1] * digraph.get_v()

        self.reverse_post_order = []
        self.marked = [False] * digraph.get_v()
        def rpo():

            def dfs(vertex):
                self.marked[vertex] = True
                for adj in digraph.get_adj(vertex):
                    if not self.marked[adj]:
                        dfs(adj)
                self.reverse_post_order.insert(0, vertex)

            for v in range(digraph.get_v()):
                if not self.marked[v]:
                    dfs(v)
        rpo()

        def find_connected(vertex):
            self.marked[vertex] = True
            self.connected_to[vertex] = self.counter
            for adj in digraph.get_adj(vertex):
                if not self.marked[adj]:
                    self.connected_to[adj] = self.counter
                    find_connected(adj)

        self.marked = [False] * digraph.get_v()
        for i in self.reverse_post_order:
            if not self.marked[i]:
                find_connected(i)
                self.counter += 1

    def connected(self, v, w):
        return self.id(v) == self.id(w)

    def count(self):
        return self.counter

    def id(self, v):
        return self.connected_to[v]


def test_strong_components():

    digraph = Digraph(0)
    digraph.init_from('digraph.txt')
    print()
    print(digraph)

    print()
    scc = StrongComponents(digraph)
    for i in range(digraph.get_v()):
        connected_to = []
        for j in range(digraph.get_v()):
            if i == j:
                continue
            if scc.connected(i, j):
                connected_to.append(j)
        print(f'{i} with id: {scc.id(i)} connected to: {connected_to}')
    print(f'Count: {scc.count()}')


if __name__ == '__main__':
    test_strong_components()
