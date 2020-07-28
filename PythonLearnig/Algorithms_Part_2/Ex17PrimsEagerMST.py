import math

from Ex13EdgeWeightedGraph import EdgeWeightedGraph


class IndexMinPQ:

    def __init__(self, v):
        self.keys = [None] * v
        self.pq = []
        self.qp = [-1] * v

    def insert(self, i, key):
        self.keys[i] = key
        self.qp[i] = self.size()
        self.pq.append(i)
        self.swim(self.size() - 1)

    def contains(self, i):
        return self.keys[i] is not None

    def key_of(self, i):
        return self.keys[i]

    def change_key(self, i, key):
        if not self.contains(i):
            self.insert(i, key)
            return
        cmp = self.keys[i].compare_to(key)
        if cmp < 0:
            self.increase_key(i, key)
        elif cmp > 0:
            self.decrease_key(i, key)

    def decrease_key(self, i, key):
        if not self.contains(i):
            self.insert(i, key)
            return
        self.keys[i] = key
        self.swim(self.qp[i])

    def increase_key(self, i, key):
        if not self.contains(i):
            self.insert(i, key)
            return
        self.keys[i] = key
        self.sink(self.qp[i])

    def min_key(self):
        if self.is_empty():
            return None
        return self.keys[self.pq[0]]

    def delete_min(self):
        if self.is_empty():
            return -1
        if self.size() > 1:
            self.exch(0, self.size() - 1)
        i = self.pq.pop()
        self.keys[i] = None
        self.qp[i] = -1
        self.sink(0)
        return i

    def size(self):
        return len(self.pq)

    def is_empty(self):
        return self.size() == 0

    def less(self, first, second):
        return self.keys[self.pq[first]].compare_to(self.keys[self.pq[second]]) < 0

    def exch(self, i, j):
        temp = self.qp[self.pq[i]]
        self.qp[self.pq[i]] = self.qp[self.pq[j]]
        self.qp[self.pq[j]] = temp

        temp = self.pq[i]
        self.pq[i] = self.pq[j]
        self.pq[j] = temp

    def sink(self, parent):
        child = (parent + 1) * 2 - 1
        if child >= self.size():
            return
        if child + 1 < self.size() and self.less(child + 1, child):
            child += 1
        if self.less(child, parent):
            self.exch(child, parent)
            self.sink(child)

    def swim(self, child):
        parent = int(math.floor((child + 1) / 2)) - 1
        if parent < 0:
            return
        if self.less(child, parent):
            self.exch(child, parent)
            self.swim(parent)


class PrimsEagerMST:

    def __init__(self, graph):
        self.all_edges = []
        self.total_weight = 0.0

        min_pq = IndexMinPQ(graph.get_v())
        marked = [False] * graph.get_v()

        def visit(v):
            marked[v] = True
            for edge in graph.get_adj(v):
                w = edge.other(v)
                if not marked[w]:
                    if min_pq.contains(w):
                        if edge.compare_to(min_pq.key_of(w)) < 0:
                            min_pq.decrease_key(w, edge)
                    else:
                        min_pq.insert(w, edge)

        def execute():
            visit(0)
            while not min_pq.is_empty():
                edge = min_pq.min_key()
                min_pq.delete_min()
                v = edge.either()
                w = edge.other(v)
                if marked[v] and marked[w]:
                    continue
                self.all_edges.append(edge)
                self.total_weight += edge.get_weight()
                if not marked[w]:
                    visit(w)
                else:
                    visit(v)

        execute()
        print(self.total_weight)

    def get_weight(self):
        return self.total_weight

    def edges(self):
        for edge in self.all_edges:
            yield edge


def test_prims_eager_mst():

    graph = EdgeWeightedGraph(0)
    graph.init_from('tinyEWG.txt')
    print()
    print(graph)

    print()
    mst = PrimsEagerMST(graph)
    print(f'weight: {mst.get_weight()} mst:')
    for edge in mst.edges():
        print(f'\t{edge}')


if __name__ == '__main__':
    test_prims_eager_mst()
