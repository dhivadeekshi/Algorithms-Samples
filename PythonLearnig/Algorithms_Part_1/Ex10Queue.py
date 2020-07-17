class Node:

    def __init__(self, value, previous):
        self.value = value
        self.next = previous


class Queue:

    def __init__(self):
        self.first = -1
        self.last = -1
        self.size = 0

    def enqueue(self, value):
        entry = Node(value, -1)
        if self.last != -1:
            self.last.next = entry
        self.last = entry
        self.size += 1
        if self.first == -1:
            self.first = self.last

    def deque(self):
        value = self.first.value
        self.first = self.first.next
        self.size -= 1
        if self.size == 0:
            self.first = -1
            self.last = -1
        return value

    def __len__(self):
        return self.size

    def is_empty(self):
        return self.size == 0


def test_queue_of_strings():

    queue = Queue()
    queue.enqueue("Hello")
    queue.enqueue("World!")
    print(f"Size: {len(queue)} IsEmpty? {queue.is_empty()}")
    print(queue.deque(), queue.deque())
    print(f"Size: {len(queue)} IsEmpty? {queue.is_empty()}")

    queue.enqueue(100)
    queue.enqueue("Rupees")
    print(queue.deque(), queue.deque())


if __name__ == '__main__':
    test_queue_of_strings()