from Ex1Graph import Graph


class DepthFirstSearchPaths:

    def __init__(self, graph, source):

        def dfs(vertex):
            self.marked[vertex] = True
            for adj in graph.get_adj(vertex):
                if not self.marked[adj]:
                    self.edge_to[adj] = vertex
                    dfs(adj)

        self.marked = [False] * graph.get_v()
        self.edge_to = [-1] * graph.get_v()
        dfs(source)

    def has_path_to(self, v):
        return self.marked[v]

    def path_to(self, v):
        if self.edge_to[v] != -1:
            for path in self.path_to(self.edge_to[v]):
                yield path
        yield v


def test_depth_first_search_paths():
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
    dfs = DepthFirstSearchPaths(graph, 0)
    for i in range(graph.get_v()):
        message = f'Has path to {i}? {dfs.has_path_to(i)}'
        if dfs.has_path_to(i):
            message += ' Path:'
            for p in dfs.path_to(i):
                message += f' {p}'
        print(message)


if __name__ == '__main__':
    test_depth_first_search_paths()
