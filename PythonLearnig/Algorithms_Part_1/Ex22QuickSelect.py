def select(unsorted_list, index):
    import random

    def exch(first, second):
        temp = unsorted_list[first]
        unsorted_list[first] = unsorted_list[second]
        unsorted_list[second] = temp

    random.shuffle(unsorted_list)

    lo = 0
    hi = len(unsorted_list) - 1

    while lo <= hi:

        i = lo + 1
        j = hi

        while True:

            while unsorted_list[i] <= unsorted_list[lo]:
                if i == hi:
                    break
                i += 1

            while unsorted_list[lo] <= unsorted_list[j]:
                if j == lo:
                    break
                j -= 1

            if i >= j:
                break

            exch(i, j)

        exch(lo, j)
        if j < index:
            lo = j + 1
        elif j > index:
            hi = j - 1
        else:
            return unsorted_list[j]

    return -1


def test_quick_select():
    import random

    unsorted_list = [random.randint(-100, 100) for _ in range(15)]

    def print_info(i):
        print(f'Item at index {i} is {select(unsorted_list, i)}')

    from Ex21QuickSort import sort
    print("Original:", unsorted_list)
    sort(unsorted_list)
    print("Sorted  :", unsorted_list)
    print_info(5)
    print_info(2)
    print_info(9)
    print_info(0)


if __name__ == '__main__':
    test_quick_select()
