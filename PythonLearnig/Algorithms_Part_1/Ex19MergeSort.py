def sort(unsorted_list):
    import math

    def sort_i(lo, hi):
        if hi <= lo:
            return
        mid = lo + int(math.floor((hi - lo) / 2))
        sort_i(lo, mid)
        sort_i(mid + 1, hi)
        merge(lo, mid, hi)

    def merge(lo, mid, hi):

        aux = unsorted_list.copy()

        i = lo
        j = mid + 1
        for k in range(lo, hi+1):
            if i > mid:
                unsorted_list[k] = aux[j]
                j += 1
            elif j > hi:
                unsorted_list[k] = aux[i]
                i += 1
            elif aux[j] < aux[i]:
                unsorted_list[k] = aux[j]
                j += 1
            else:
                unsorted_list[k] = aux[i]
                i += 1

    sort_i(0, len(unsorted_list) - 1)


def is_sorted(sorted_list):
    for i in range(len(sorted_list) - 1):
        if sorted_list[i + 1] < sorted_list[i]:
            return False
    return True


def test_merge_sort():
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
    test_merge_sort()
