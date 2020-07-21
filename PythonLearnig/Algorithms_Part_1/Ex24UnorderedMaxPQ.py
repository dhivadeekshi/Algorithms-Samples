class MaxPQ:

    def __init__(self):
        self.size = 0
        self.data = []

    def insert(self, item):
        self.size += 1
        self.data.append(item)

    def delete_max(self):

        if self.is_empty():
            return None

        item = self.max()
        self.data.remove(item)

        return item

    def max(self):

        if self.is_empty():
            return None

        max_item = self.data[0]
        for item in self.data:
            if item > max_item:
                max_item = item

        return max_item

    def is_empty(self):
        return self.size == 0

    def __len__(self):
        return self.size

    def __str__(self):
        return str(self.data)


def test_unordered_max_pq():
    import random

    max_pq = MaxPQ()
    n = 15
    for _ in range(n):
        item = random.randint(-100, 100)
        max_pq.insert(item)
        print(f'Insert {item}: max: {max_pq.max()} - {max_pq}')
        if random.randint(0, 10) > 8:
            print(f'\tDelete-max : {max_pq.delete_max()} max: {max_pq.max()} - {max_pq}')


if __name__ == '__main__':
    test_unordered_max_pq()
