from Ex20EdgeWeightedDigraph import EdgeWeightedDigraph
from Ex19DirectedEdge import DirectedEdge


class BellmanFordSortestPath:

    def __init__(self, digraph, source):
        self.dist_to = [float('inf')] * digraph.get_v()
        self.edge_to = [None] * digraph.get_v()

        self.dist_to[source] = 0.0
        for i in range(digraph.get_v()):
            for j in range(digraph.get_v()):
                for edge in digraph.get_adj(j):
                    self.relax(edge)

    def path_to(self, v):
        if self.edge_to[v] is not None:
            for edge in self.path_to(self.edge_to[v].from_v()):
                yield edge
            yield self.edge_to[v]

    def has_path_to(self, v):
        return self.edge_to[v] is not None

    def relax(self, edge):
        v = edge.from_v()
        w = edge.to_v()
        if self.dist_to[v] + edge.weight < self.dist_to[w]:
            self.dist_to[w] = self.dist_to[v] + edge.weight
            self.edge_to[w] = edge


def test_bellman_ford_sortest_path():

    digraph = EdgeWeightedDigraph(0)
    digraph.init_from('tinyEWD.txt')
    print()
    print(digraph)

    sp = BellmanFordSortestPath(digraph, 0)
    for v in range(digraph.get_v()):
        builder = f'Has path to {v}? {sp.has_path_to(v)}'
        if sp.has_path_to(v):
            builder += f' dist: {round(sp.dist_to[v], 2)} path:'
            for edge in sp.path_to(v):
                builder += f'\n\t{edge}'
        print(builder)


if __name__ == '__main__':
    test_bellman_ford_sortest_path()
