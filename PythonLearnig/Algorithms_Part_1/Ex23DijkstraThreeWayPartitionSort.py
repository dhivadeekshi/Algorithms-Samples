def sort(unsorted_list):

    def exch(first, second):
        temp = unsorted_list[first]
        unsorted_list[first] = unsorted_list[second]
        unsorted_list[second] = temp

    def sort_i(lo, hi):
        if hi <= lo:
            return

        lt = lo
        gt = hi
        k = lo
        v = unsorted_list[lo]

        while k <= gt:
            if unsorted_list[k] < v:
                exch(k, lt)
                k += 1
                lt += 1
            elif unsorted_list[k] > v:
                exch(k, gt)
                gt -= 1
            else:
                k += 1

        sort_i(lo, lt - 1)
        sort_i(gt + 1, hi)

    sort_i(0, len(unsorted_list) - 1)


def test_three_way_partition_sort():
    import random

    def is_sorted(sorted_list):
        for i in range(len(sorted_list) - 1):
            if sorted_list[i + 1] < sorted_list[i]:
                return False
        return True

    print()
    unsorted_list = [random.randint(-100, 100) for _ in range(15)]
    value = unsorted_list[random.randint(0, len(unsorted_list) - 1)]
    for _ in range(random.randint(4, 8)):
        unsorted_list.insert(random.randint(0, len(unsorted_list) - 1), value)
    print('Original:', unsorted_list)
    sort(unsorted_list)
    print('Sorted  :', unsorted_list)
    print('Is Sorted?', is_sorted(unsorted_list))

    print()
    unsorted_list = [chr(random.randint(ord('A'), ord('Z'))) for _ in range(15)]
    value = unsorted_list[random.randint(0, len(unsorted_list) - 1)]
    for _ in range(random.randint(4, 8)):
        unsorted_list.insert(random.randint(0, len(unsorted_list) - 1), value)
    print('Original:', unsorted_list)
    sort(unsorted_list)
    print('Sorted  :', unsorted_list)
    print('Is Sorted?', is_sorted(unsorted_list))


if __name__ == '__main__':
    test_three_way_partition_sort()
