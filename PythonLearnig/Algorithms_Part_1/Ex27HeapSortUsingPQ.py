import math


def sort(unsorted_list):

    def exch(i, j):
        temp = unsorted_list[i]
        unsorted_list[i] = unsorted_list[j]
        unsorted_list[j] = temp

    def sink(parent, n):

        child = (parent + 1) * 2 - 1
        if child >= n:
            return
        if child + 1 < n and \
                unsorted_list[child] < unsorted_list[child + 1]:
            child += 1
        if unsorted_list[parent] < unsorted_list[child]:
            exch(parent, child)
            sink(child, n)

    def is_binary_heap(n):
        for parent in range(n):
            child = (parent + 1) * 2 - 1
            if child < n:
                if unsorted_list[parent] < unsorted_list[child]:
                    return False
                if child + 1 < n:
                    if unsorted_list[parent] < unsorted_list[child + 1]:
                        return False
        return True

    # Convert to heap
    for index in range(math.floor(len(unsorted_list) / 2), -1, -1):
        sink(index, len(unsorted_list))
    # print(unsorted_list)
    # print(f'Is Binary Heap ? {is_binary_heap()}')

    # Sort by deleting max
    for size in range(len(unsorted_list) - 1, -1, -1):
        exch(0, size)
        sink(0, size)
        # print(f'Size {size} Heap ? {is_binary_heap(size)} {unsorted_list}')


def test_heap_sort():

    import random

    def is_sorted(sorted_list):
        for i in range(len(sorted_list) - 1):
            if sorted_list[i] > sorted_list[i+1]:
                return False
        return True

    print()
    unsorted_list = [random.randint(-100, 100) for _ in range(15)]
    print('Original:', unsorted_list)
    sort(unsorted_list)
    print('Sorted  :', unsorted_list)
    print('Is Sorted?', is_sorted(unsorted_list))

    print()
    unsorted_list = [chr(random.randint(ord('A'), ord('Z'))) for _ in range(15)]
    print('Original:', unsorted_list)
    sort(unsorted_list)
    print('Sorted  :', unsorted_list)
    print('Is Sorted?', is_sorted(unsorted_list))


if __name__ == '__main__':
    test_heap_sort()
