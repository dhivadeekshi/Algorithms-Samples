from Ex1Graph import Graph


class ConnectedComponents:

    def __init__(self, graph):

        def bfs(source):

            queue = [source]
            self.marked[source] = True
            self.ids[source] = self.counter

            while len(queue) > 0:

                vertex = queue.pop(0)
                for adj in graph.get_adj(vertex):
                    if not self.marked[adj]:
                        queue.append(adj)
                        self.marked[adj] = True
                        self.ids[adj] = self.counter

        self.marked = [False] * graph.get_v()
        self.ids = [-1] * graph.get_v()
        self.counter = 0
        for i in range(graph.get_v()):
            if not self.marked[i]:
                bfs(i)
                self.counter += 1

    def connected(self, v, w):
        return self.ids[v] == self.ids[w]

    def connected_to(self, v):
        id_v = self.id(v)
        for i in range(len(self.ids)):
            if i != v and self.id(i) == id_v:
                yield i

    def count(self):
        return self.counter

    def id(self, v):
        return self.ids[v]


def test_connected_components():

    with open('tinyG.txt') as f:

        v = int(f.readline())

        # edges
        f.readline()

        graph = Graph(v)
        while True:
            try:
                line = f.readline()
                v, w = line.split(' ')
            except ValueError:
                break
            else:
                graph.add_edge(int(v), int(w))

        print()
        print(graph)

        print()
        cc = ConnectedComponents(graph)
        for i in range(graph.get_v()):
            print(f'{i} id: {cc.id(i)} connected to: {list(cc.connected_to(i))}')
        print(f'Count: {cc.count()} ')


if __name__ == '__main__':
    test_connected_components()
