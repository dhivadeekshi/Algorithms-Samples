from Ex6Digraph import Digraph


class DepthFirstSearchDigraph:

    def __init__(self, digraph, source):

        def dfs(vertex):
            self.marked[vertex] = True
            for adj in digraph.get_adj(vertex):
                if not self.marked[adj]:
                    self.edge_to[adj] = vertex
                    dfs(adj)

        self.marked = [False] * digraph.get_v()
        self.edge_to = [-1] * digraph.get_v()
        if isinstance(source, list):
            for i in range(len(source)):
                if not self.marked[i]:
                    dfs(source[i])
        else:
            dfs(source)

    def has_path_to(self, v):
        return self.marked[v]

    def path_to(self, v):
        if self.edge_to[v] != -1:
            for path in self.path_to(self.edge_to[v]):
                yield path
        yield v


def test_depth_first_search_digraph():
    digraph = Digraph(0)
    digraph.init_from('digraph.txt')

    print()
    print(digraph)

    print()
    dfs = DepthFirstSearchDigraph(digraph, 0)
    for v in range(digraph.get_v()):
        message = f'Has Path to {v}? {dfs.has_path_to(v)}'
        if dfs.has_path_to(v):
            message += ' Path:'
            for p in dfs.path_to(v):
                message += f' {p}'
        print(message)


if __name__ == '__main__':
    test_depth_first_search_digraph()
