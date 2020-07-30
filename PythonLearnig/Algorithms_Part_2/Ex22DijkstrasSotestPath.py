from Ex20EdgeWeightedDigraph import EdgeWeightedDigraph
from Ex17PrimsEagerMST import IndexMinPQ


class Float(float):

    def __init__(self, x):
        super().__init__()

    def compare_to(self, that):
        """

        :type that: Float
        """
        return self - that


class DijkstrasSortestPath:

    def __init__(self, digraph, source):
        self.edge_to = [None] * digraph.get_v()
        self.dist_to = [float('inf') for _ in range(digraph.get_v())]

        marked = [False] * digraph.get_v()
        min_pq = IndexMinPQ(digraph.get_v())
        self.dist_to[source] = 0.0
        min_pq.insert(source, Float(0.0))

        while not min_pq.is_empty():

            vertex = min_pq.delete_min()
            marked[vertex] = True
            for edge in digraph.get_adj(vertex):
                w = edge.to_v()
                if not marked[w]:
                    self.relax(edge)
                    if not min_pq.contains(w):
                        min_pq.insert(w, Float(self.dist_to[w]))
                    elif self.dist_to[w] < min_pq.key_of(w):
                        min_pq.decrease_key(w, Float(self.dist_to[w]))

    def path_to(self, v):
        if self.edge_to[v] is not None:
            for path in self.path_to(self.edge_to[v].from_v()):
                yield path
            yield self.edge_to[v]

    def has_path_to(self, v):
        return self.edge_to[v] is not None

    def relax(self, edge):
        v = edge.from_v()
        w = edge.to_v()
        if self.dist_to[v] + edge.get_weight() < self.dist_to[w]:
            self.dist_to[w] = self.dist_to[v] + edge.get_weight()
            self.edge_to[w] = edge


def test_dijkstras_sortest_path():

    digraph = EdgeWeightedDigraph(0)
    digraph.init_from('tinyEWD.txt')
    print()
    print(digraph)

    sp = DijkstrasSortestPath(digraph, 0)
    print()
    for i in range(digraph.get_v()):
        builder = f'Has path to {i}? {sp.has_path_to(i)}'
        if sp.has_path_to(i):
            builder += f' dist_to {i}: {round(sp.dist_to[i], 2)} path: '
            for path in sp.path_to(i):
                builder += f'\n\t{path}'
        print(builder)


if __name__ == '__main__':
    test_dijkstras_sortest_path()
