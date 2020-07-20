def sort(unsorted_list):

    def merge(lo_m, mid_m, hi_m):

        aux = unsorted_list.copy()

        i = lo_m
        j = mid_m + 1
        for kk in range(lo_m, hi_m + 1):
            if i > mid_m:
                unsorted_list[kk] = aux[j]
                j += 1
            elif j > hi_m:
                unsorted_list[kk] = aux[i]
                i += 1
            elif aux[j] < aux[i]:
                unsorted_list[kk] = aux[j]
                j += 1
            else:
                unsorted_list[kk] = aux[i]
                i += 1

    k = 1
    n = len(unsorted_list)
    while k < n - 1:
        for lo in range(0, n, k*2):
            mid = lo + k - 1
            hi = min(mid + k, n - 1)
            merge(lo, mid, hi)
        k *= 2


def is_sorted(sorted_list):
    for i in range(len(sorted_list) - 1):
        if sorted_list[i + 1] < sorted_list[i]:
            return False
    return True


def test_bottom_up_merge_sort():
    import random
    unsorted_list = [random.randint(-100, 100) for _ in range(15)]
    print("Original:", unsorted_list)
    sort(unsorted_list)
    print("Sorted  :", unsorted_list)
    print("IsSorted? ", is_sorted(unsorted_list))

    print()
    unsorted_list = [chr(random.randint(ord('A'), ord('Z'))) for _ in range(random.randint(10, 16))]
    print("Original:", unsorted_list)
    sort(unsorted_list)
    print("Sorted  :", unsorted_list)
    print("IsSorted? ", is_sorted(unsorted_list))


if __name__ == '__main__':
    test_bottom_up_merge_sort()
