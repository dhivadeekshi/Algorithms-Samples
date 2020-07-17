class QuickUnion:

    def __init__(self, n):
        self.data = list(range(n))

    def union(self, p, q):
        pid = self.root(p)
        qid = self.root(q)
        self.data[pid] = qid

    def is_connected(self, p, q):
        return self.root(p) == self.root(q)

    def __str__(self):
        return str(self.data)

    def root(self, p):
        while p != self.data[p]:
            p = self.data[p]
        return p


def test_quick_union():

    n = int(input("Please enter the no of nodes: "))
    m = int(input("Please enter no of unions: "))

    qu = QuickUnion(n)
    for i in range(m):

        p = int(input())
        q = int(input())

        if not qu.is_connected(p, q):
            qu.union(p, q)
            print(f"Connected ({p},{q}) : {qu}")


if __name__ == '__main__':
    test_quick_union()