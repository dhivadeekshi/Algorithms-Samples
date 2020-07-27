from Ex1Graph import Graph


class BreadthFirstSearchPaths:

    def __init__(self, graph, source):

        self.marked = [False] * graph.get_v()
        self.edge_to = [-1] * graph.get_v()

        queue = [source]
        self.marked[source] = True

        while len(queue) > 0:
            vertex = queue.pop(0)

            for adj in graph.get_adj(vertex):
                if not self.marked[adj]:
                    queue.append(adj)
                    self.marked[adj] = True
                    self.edge_to[adj] = vertex

    def has_path_to(self, v):
        return self.marked[v]

    def path_to(self, v):
        if self.edge_to[v] != -1:
            for path in self.path_to(self.edge_to[v]):
                yield path
        yield v


def test_breadth_first_search_paths():
    with open('tinyG.txt') as f:

        v = int(f.readline())

        # edges
        f.readline()

        graph = Graph(v)
        while True:
            try:
                line = f.readline()
                v, w = line.split()
            except ValueError:
                break
            else:
                graph.add_edge(int(v), int(w))

    print()
    print(graph)

    print()
    bfs = BreadthFirstSearchPaths(graph, 0)
    for v in range(graph.get_v()):
        message = f'Has path to {v}? {bfs.has_path_to(v)}'
        if bfs.has_path_to(v):
            message += ' Path:'
            for p in bfs.path_to(v):
                message += f' {p}'
        print(message)


if __name__ == '__main__':
    test_breadth_first_search_paths()
