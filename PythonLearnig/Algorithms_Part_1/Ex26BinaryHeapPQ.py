import math


class MaxPQ:

    def __init__(self):
        self.data = []

    def insert(self, item):
        self.data.append(item)
        self.swim(len(self.data) - 1)

    def delete_max(self):
        if self.is_empty():
            return None

        item = self.max()
        self.data[0] = self.data[len(self) - 1]
        self.sink(0)
        return item

    def max(self):
        if self.is_empty():
            return None
        return self.data[0]

    def is_empty(self):
        return len(self) == 0

    def __len__(self):
        return len(self.data)

    def __str__(self):
        return str(self.data)

    def exch(self, i, j):
        temp = self.data[i]
        self.data[i] = self.data[j]
        self.data[j] = temp

    def swim(self, child):

        if child <= 0:
            return

        parent = int(math.floor((child + 1) / 2)) - 1
        if self.data[parent] < self.data[child]:
            self.exch(parent, child)
            self.swim(parent)

    def sink(self, parent):

        if parent * 2 >= len(self):
            return

        child = (parent + 1) * 2 - 1
        if child < len(self) - 1 and \
                self.data[child] < self.data[child + 1]:
            child += 1
        if self.data[child] > self.data[parent]:
            self.exch(parent, child)
            self.sink(child)


def test_binary_heap_max_pq():
    import random

    max_pq = MaxPQ()
    for _ in range(15):
        item = random.randint(-100, 100)
        max_pq.insert(item)
        print(f'Inserted {item}: max: {max_pq.max()} - {max_pq}')
        if random.randint(0, 10) > 8:
            print(f'\tDelete-max : {max_pq.delete_max()} max: {max_pq.max()} - {max_pq}')


if __name__ == '__main__':
    test_binary_heap_max_pq()
