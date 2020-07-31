def sort(unsorted_array):

    def char_at(s, d):
        if len(s) <= d:
            return -1
        return ord(s[d])

    def exch(i, j):
        temp = unsorted_array[i]
        unsorted_array[i] = unsorted_array[j]
        unsorted_array[j] = temp

    def sort_i(lo, hi, d):
        if hi <= lo:
            return

        lt = lo
        gt = hi
        k = lo
        v = char_at(unsorted_array[lo], d)
        while k <= gt:
            c = char_at(unsorted_array[k], d)
            if c < v:
                exch(k, lt)
                k += 1
                lt += 1
            elif c > v:
                exch(k, gt)
                gt -= 1
            else:
                k += 1

        # print(f'sort_i lo: {lo} hi: {hi} lt: {lt} gt: {gt} d: {d}')
        sort_i(lo, lt - 1, d)
        if v > 0:
            sort_i(lt, gt, d + 1)
        sort_i(gt + 1, hi, d)

    sort_i(0, len(unsorted_array) - 1, 0)


def test_three_way_radix_sort():
    import random

    unsorted_array = []
    for i in range(15):
        unsorted_array.append('')
        for j in range(random.randint(5, 10)):
            unsorted_array[i] += chr(random.randint(ord('A'), ord('B')))

    for _ in range(15):
        unsorted_array.insert(random.randint(0, len(unsorted_array) - 1),
                              unsorted_array[random.randint(0, len(unsorted_array) - 1)])

    print()
    print('Original:')
    for key in unsorted_array:
        print(f'\t{key}')
    sort(unsorted_array)
    print('Sorted  :')
    for key in unsorted_array:
        print(f'\t{key}')

    print()
    is_sorted = True
    for i in range(14):
        if unsorted_array[i] > unsorted_array[i + 1]:
            print(f'{unsorted_array[i]} > {unsorted_array[i + 1]}')
            is_sorted = False
    print(f'Is Sorted? {is_sorted}')


if __name__ == '__main__':
    test_three_way_radix_sort()
