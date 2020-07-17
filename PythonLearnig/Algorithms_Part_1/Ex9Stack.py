class Node:

    def __init__(self, value, previous):
        self.next = previous
        self.value = value


class Stack:

    def __init__(self):
        self.first = -1
        self.size = 0

    def push(self, item):
        self.first = Node(item, self.first)
        self.size += 1

    def pop(self):
        value = self.first.value
        self.first = self.first.next
        self.size -= 1
        return value

    def __len__(self):
        return self.size

    def is_empty(self):
        return self.first == -1


def test_stack_of_strings():
    my_stack = Stack()
    my_stack.push("Hello")
    my_stack.push("World!")
    print(f"Size: {len(my_stack)} IsEmpty? {my_stack.is_empty()}")
    print(my_stack.pop(), my_stack.pop())
    print(f"Size: {len(my_stack)} IsEmpty? {my_stack.is_empty()}")

    my_stack.push("Rupees")
    my_stack.push(100)
    print(my_stack.pop(), my_stack.pop())


if __name__ == '__main__':
    test_stack_of_strings()
