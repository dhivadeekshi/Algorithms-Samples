from Ex26FlowNetwork import FlowNetwork


class FordFulkerson:

    def __init__(self, flow_network, s, t):
        self.max_flow = 0.0
        self.edge_to = [None] * flow_network.v
        self.marked = [False] * flow_network.v

        def has_augmenting_path():
            self.edge_to = [None] * flow_network.v
            self.marked = [False] * flow_network.v

            queue = [s]
            self.marked[s] = True
            while len(queue) > 0:
                ve = queue.pop(0)
                self.marked[ve] = True
                for edge in flow_network.get_adj(ve):
                    w = edge.other(ve)
                    if not self.marked[w] and edge.residual_capacity_to(w) > 0:
                        self.edge_to[w] = edge
                        self.marked[w] = True
                        queue.append(w)

            return self.marked[t]

        while has_augmenting_path():
            capacity = float('inf')
            v = t
            while v != s:
                capacity = min(capacity, self.edge_to[v].residual_capacity_to(v))
                v = self.edge_to[v].other(v)

            v = t
            while v != s:
                self.edge_to[v].add_residual_capacity_to(v, capacity)
                v = self.edge_to[v].other(v)

            self.max_flow += capacity

    def in_cut(self, v):
        return self.marked[v]


def test_ford_fulkerson():
    fn = FlowNetwork(0)
    fn.init_from('tinyFN.txt')
    print()
    print(fn)

    ff = FordFulkerson(fn, 0, fn.v - 1)
    print()
    print(f'max_flow: {ff.max_flow}')
    from_s = [num for num in range(fn.v) if ff.in_cut(num)]
    from_t = [num for num in range(fn.v) if not ff.in_cut(num)]
    print(f'S: {from_s}')
    print(f'T: {from_t}')


if __name__ == '__main__':
    test_ford_fulkerson()
