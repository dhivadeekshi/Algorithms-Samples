from Ex19DirectedEdge import DirectedEdge


class TopologicalEWD:

    def __init__(self, digraph):
        self.data = []
        marked = [False] * digraph.get_v()

        def topological(vertex):
            marked[vertex] = True
            for edge in digraph.get_adj(vertex):
                if not marked[edge.to_v()]:
                    topological(edge.to_v())
            self.data.insert(0, vertex)

        for i in range(digraph.get_v()):
            if not marked[i]:
                topological(i)

    def order(self):
        for v in self.data:
            yield v
