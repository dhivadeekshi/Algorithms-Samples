class QuickFind:

    def __init__(self, n):
        self.data = list(range(n))

    def union(self, p, q):
        pid = self.data[p]
        qid = self.data[q]
        for i in range(len(self.data)):
            if self.data[i] == pid:
                self.data[i] = qid

    def is_connected(self, p, q):
        return self.data[p] == self.data[q]

    def __str__(self):
        return str(self.data)


def test_quick_find():

    n = int(input("Please enter the no of vertices: "))
    qf = QuickFind(n)

    for i in range(n):
        p = int(input())
        q = int(input())

        if not qf.is_connected(p,q):
            qf.union(p,q)
            print(f"Connected({p},{q}) : {qf}")


if __name__ == "__main__":
    test_quick_find()
