class MaxPQ:

    def __init__(self):
        self.data = []

    def insert(self, item):

        index = len(self)
        for i in range(len(self)):
            if item <= self.data[i]:
                index = i
                break
        self.data.insert(index, item)

    def delete_max(self):
        if self.is_empty():
            return None

        return self.data.pop()

    def max(self):
        if self.is_empty():
            return None

        return self.data[-1]

    def is_empty(self):
        return len(self) == 0

    def __len__(self):
        return len(self.data)

    def __str__(self):
        return str(self.data)


def test_ordered_max_pq():
    import random

    def insert(item):
        max_pq.insert(item)
        print(f"Insert {item}: max: {max_pq.max()} - {max_pq}")

    def delete_max():
        print(f"\tDelete-max : {max_pq.delete_max()} max: {max_pq.max()} - {max_pq}")

    max_pq = MaxPQ()
    for _ in range(15):
        insert(random.randint(-100, 100))
        if random.randint(0, 10) > 8:
            delete_max()

    print()
    max_pq = MaxPQ()
    insert('P')
    insert('Q')
    insert('E')
    delete_max()
    insert('X')
    insert('A')
    insert('M')
    delete_max()
    insert('P')
    insert('L')
    insert('E')
    delete_max()


if __name__ == '__main__':
    test_ordered_max_pq()
