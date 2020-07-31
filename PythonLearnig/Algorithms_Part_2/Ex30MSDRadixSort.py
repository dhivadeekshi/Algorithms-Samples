def sort(unsorted_array):

    R = 256
    n = len(unsorted_array)
    aux = [''] * n
    max_d = max([len(string) for string in unsorted_array])

    def char_at(s, d):
        if len(s) <= d:
            return 0
        return ord(s[d])

    def sort_i(lo, hi, d, tab=''):

        if lo >= hi:
            return
        if d >= max_d:
            return

        count = [0] * (R + 1)

        for c in range(lo, hi + 1):
            count[char_at(unsorted_array[c], d) + 1] += 1
            aux[c] = unsorted_array[c]

        # print(f"{tab}{count[ord('A') - 1: ord('D') + 1]} lo: {lo} hi: {hi} d: {d}")
        for c in range(R):
            count[c + 1] += count[c]

        for c in range(lo, hi + 1):
            unsorted_array[lo + count[char_at(aux[c], d)]] = aux[c]
            count[char_at(aux[c], d)] += 1

        # print(tab, unsorted_array[lo:hi + 1])
        start = 0
        for c in range(R - 1):
            if count[c] < start:
                continue
            sort_i(lo + start, lo + count[c] - 1, d + 1, tab+'\t')
            start = count[c]

    sort_i(0, n-1, 0)


def test_msd_radix_sort():
    import random

    unsorted_array = []
    for i in range(15):
        unsorted_array.append('')
        for j in range(random.randint(5, 10)):
            unsorted_array[i] += chr(random.randint(ord('A'), ord('E')))

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
            is_sorted = False
            print(f'{unsorted_array[i]} > {unsorted_array[i + 1]}')

    print(f'Is Sorted? {is_sorted}')


if __name__ == '__main__':
    test_msd_radix_sort()
