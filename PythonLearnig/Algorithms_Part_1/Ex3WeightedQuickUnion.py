class WeightedQuickUnion:

    def __init__(self, n):
        self.data = list(range(n))
        self.size = [1] * n

    def union(self, p, q):
        pid = self.root(p)
        qid = self.root(q)

        if self.size[pid] >= self.size[qid]:
            self.data[pid] = qid
            self.size[pid] += self.size[qid]
        else:
            self.data[qid] = pid
            self.size[qid] += self.size[pid]

    def is_connected(self, p, q):
        return self.root(p) == self.root(q)

    def __str__(self):
        return str(self.data)

    def root(self, p):
        while p != self.data[p]:
            p = self.data[p]
        return p


def test_weighted_quick_union():

    n = int(input("Enter the no of nodes: "))
    m = int(input("Enter the no of unions: "))

    wqu = WeightedQuickUnion(n)
    for i in range(m):
        p = int(input())
        q = int(input())

        if not wqu.is_connected(p, q):
            wqu.union(p, q)
            print(f"Connected ({p},{q}) : {wqu}")


if __name__ == '__main__':
    test_weighted_quick_union()
