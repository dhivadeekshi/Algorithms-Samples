def sort(unsorted_list):

    import random

    def exch(first, second):
        temp = unsorted_list[first]
        unsorted_list[first] = unsorted_list[second]
        unsorted_list[second] = temp

    def sort_i(lo, hi):
        if hi <= lo:
            return
        j = partition(lo, hi)
        sort_i(lo, j - 1)
        sort_i(j + 1, hi)

    def partition(lo, hi):

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
        return j

    random.shuffle(unsorted_list)
    sort_i(0, len(unsorted_list) - 1)


def is_sorted(sorted_list):
    for i in range(len(sorted_list) - 1):
        if sorted_list[i + 1] < sorted_list[i]:
            return False
    return True


def test_quick_sort():
    import random
    unsorted_list = [random.randint(-100, 100) for _ in range(15)]
    print("Original:", unsorted_list)
    sort(unsorted_list)
    print("Sorted  :", unsorted_list)
    print("IsSorted? ", is_sorted(unsorted_list))

    print()
    unsorted_list = [chr(random.randint(ord('A'), ord('Z'))) for _ in range(15)]
    print("Original:", unsorted_list)
    sort(unsorted_list)
    print("Sorted  :", unsorted_list)
    print("IsSorted? ", is_sorted(unsorted_list))


if __name__ == '__main__':
    test_quick_sort()
