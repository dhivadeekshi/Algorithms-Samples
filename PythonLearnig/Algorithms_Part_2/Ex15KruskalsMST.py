import math
from Ex13EdgeWeightedGraph import EdgeWeightedGraph


class MinPQ:

    def __init__(self):
        self.data = []

    def put(self, item):
        self.data.append(item)
        self.swim(self.size() - 1)

    def min(self):
        if self.is_empty():
            return None
        return self.data[0]

    def delete_min(self):
        if self.is_empty():
            return None
        if self.size() > 1:
            self.exch(0, self.size() - 1)
        item = self.data.pop()
        self.sink(0)
        return item

    def size(self):
        return len(self.data)

    def is_empty(self):
        return self.size() == 0

    def swim(self, child):
        parent = int(math.floor((child + 1) / 2)) - 1
        if parent < 0:
            return
        if self.less(child, parent):
            self.exch(child, parent)
            self.swim(parent)

    def sink(self, parent):
        child = (parent + 1) * 2 - 1
        if child >= self.size():
            return
        if child < self.size() - 1 and self.less(child + 1, child):
            child += 1
        if self.less(child, parent):
            self.exch(parent, child)
            self.sink(child)

    def exch(self, i, j):
        temp = self.data[i]
        self.data[i] = self.data[j]
        self.data[j] = temp

    def less(self, first, second):
        return self.data[first].compare_to(self.data[second]) < 0

    def __str__(self):

        def build(parent=0, tab=''):
            if parent >= self.size():
                return ''

            builder = tab + f'Parent: ({parent}, {self.data[parent]})'
            child = (parent + 1) * 2 - 1

            def add_child(child):
                if child < self.size():
                    return f' Child: ({child}, {self.data[child]})'
                return ''

            builder += add_child(child)
            builder += add_child(child + 1)
            tab += '\t'
            builder += '\n'
            builder += build(child, tab)
            builder += build(child + 1, tab)

            return builder

        return build()


class WeightedQuickUnion:

    def __init__(self, v):
        self.data = list(range(v))
        self.size = [0] * v

    def union(self, p, q):
        pid = self.root(p)
        qid = self.root(q)
        if pid == qid:
            return
        if self.size[pid] < self.size[qid]:
            self.data[pid] = qid
            self.size[pid] += self.size[qid]
        else:
            self.data[qid] = pid
            self.size[qid] += self.size[pid]

    def connected(self, p, q):
        return self.root(p) == self.root(q)

    def root(self, p):
        while p != self.data[p]:
            p = self.data[p]
        return p


class KruskalsMST:

    def __init__(self, graph):
        self.all_edges = []
        self.total_weight = 0.0

        queue = MinPQ()
        wqu = WeightedQuickUnion(graph.get_v())

        def create_data():
            for v in range(graph.get_v()):
                for e in graph.get_adj(v):
                    queue.put(e)

        def execute():

            while not queue.is_empty():
                edge = queue.delete_min()
                v = edge.either()
                w = edge.other(v)
                if not wqu.connected(v, w):
                    wqu.union(v, w)
                    self.all_edges.append(edge)
                    self.total_weight += edge.get_weight()

        create_data()
        execute()

    def edges(self):
        for edge in self.all_edges:
            yield edge

    def weight(self):
        return self.total_weight


def test_kruskals_mst():
    graph = EdgeWeightedGraph(0)
    graph.init_from('tinyEWG.txt')
    print()
    print(graph)

    print()
    mst = KruskalsMST(graph)
    print(f'weight: {mst.weight()} mst:')
    for edge in mst.edges():
        print(f'\t{edge}')


if __name__ == '__main__':
    test_kruskals_mst()
