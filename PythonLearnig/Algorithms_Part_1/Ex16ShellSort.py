def sort(unsorted_list):

    def exch(first, second):
        temp = unsorted_list[first]
        unsorted_list[first] = unsorted_list[second]
        unsorted_list[second] = temp

    def less(first, second):
        return unsorted_list[first] < unsorted_list[second]

    h = 1
    n = len(unsorted_list)
    while h * 3 < n:
        h = h * 3 + 1

    while h > 0:

        for i in range(0, n, h):
            for j in range(i, 0, -h):
                if less(j, j-h):
                    exch(j, j-h)

        h = int(round((h - 1) / 3))


def test_shell_sort():

    import random
    unsorted_list = [random.randint(-100, 100) for _ in range(10)]
    print(f"Unsorted : {unsorted_list}")
    sort(unsorted_list)
    print(f"Sorted   : {unsorted_list}")

    is_sorted = True
    for i in range(len(unsorted_list) - 1):
        if unsorted_list[i] > unsorted_list[i + 1]:
            is_sorted = False
            break

    print(f"Is Sorted ? {is_sorted}")

    print()
    unsorted_list = [chr(random.randint(ord('A'), ord('Z') + 1)) for _ in range(10)]
    print(f"Unsorted : {unsorted_list}")
    sort(unsorted_list)
    print(f"Sorted   : {unsorted_list}")

    is_sorted = True
    for i in range(len(unsorted_list) - 1):
        if unsorted_list[i] > unsorted_list[i + 1]:
            is_sorted = False
            break

    print(f"Is Sorted ? {is_sorted}")


if __name__ == '__main__':
    test_shell_sort()
