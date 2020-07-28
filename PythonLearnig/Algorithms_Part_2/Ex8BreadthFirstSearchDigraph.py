from Ex6Digraph import Digraph


class BreadthFirstSearchDigraph:

    def __init__(self, digraph, source):

        self.marked = [False] * digraph.get_v()
        self.edge_to = [-1] * digraph.get_v()

        queue = [source]
        self.marked[source] = True

        while len(queue) > 0:

            vertex = queue.pop(0)
            for adj in digraph.get_adj(vertex):
                if not self.marked[adj]:
                    self.marked[adj] = True
                    self.edge_to[adj] = vertex
                    queue.append(adj)

    def has_path_to(self, v):
        return self.marked[v]

    def path_to(self, v):
        if self.edge_to[v] != -1:
            for path in self.path_to(self.edge_to[v]):
                yield path
        yield v


def test_breadth_first_search():

    digraph = Digraph(0)
    digraph.init_from('digraph.txt')

    print()
    print(digraph)

    print()
    bfs = BreadthFirstSearchDigraph(digraph, 0)
    for v in range(digraph.get_v()):
        message = f'Has path to {v}? {bfs.has_path_to(v)}'
        if bfs.has_path_to(v):
            message += ' Path:'
            for adj in bfs.path_to(v):
                message += f' {adj}'
        print(message)


if __name__ == '__main__':
    test_breadth_first_search()
