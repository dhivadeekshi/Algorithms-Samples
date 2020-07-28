from Ex6Digraph import Digraph


class ReversePostOrder:

    def __init__(self, digraph):
        self.reverse_order = []
        self.marked = [False] * digraph.get_v()

        def dfs(vertex):
            self.marked[vertex] = True
            for adj in digraph.get_adj(vertex):
                if not self.marked[adj]:
                    dfs(adj)
            self.reverse_order.insert(0, vertex)

        for i in range(digraph.get_v()):
            if not self.marked[i]:
                dfs(i)

    def order(self):
        for vertex in self.reverse_order:
            yield vertex


def test_reverse_post_order():

    digraph = Digraph(0)
    digraph.init_from('tinyDG.txt')

    print()
    print(digraph)

    print()
    topological_sort = ReversePostOrder(digraph)
    print(f'Order: {list(topological_sort.order())}')


if __name__ == '__main__':
    test_reverse_post_order()
