from Ex13EdgeWeightedGraph import EdgeWeightedGraph
from Ex15KruskalsMST import MinPQ


class PrimsLazyMST:

    def __init__(self, graph):
        self.all_edges = []
        self.total_weight = 0.0

        marked = [False] * graph.get_v()
        queue = MinPQ()

        def visit(vertex):
            marked[vertex] = True
            for edge in graph.get_adj(vertex):
                v = edge.either()
                w = edge.other(v)
                if marked[v] and marked[w]:
                    continue
                queue.put(edge)

        def execute():
            while not queue.is_empty():
                edge = queue.delete_min()
                v = edge.either()
                w = edge.other(v)
                if marked[v] and marked[w]:
                    continue
                self.all_edges.append(edge)
                self.total_weight += edge.get_weight()
                if marked[v]:
                    visit(w)
                else:
                    visit(v)

        visit(0)
        execute()

    def get_weight(self):
        return self.total_weight

    def edges(self):
        for edge in self.all_edges:
            yield edge


def test_prims_lazy_mst():

    graph = EdgeWeightedGraph(0)
    graph.init_from('tinyEWG.txt')
    print()
    print(graph)

    mst = PrimsLazyMST(graph)
    print(f'weight: {mst.get_weight()} mst:')
    for edge in mst.edges():
        print(f'\t{edge}')


if __name__ == '__main__':
    test_prims_lazy_mst()
