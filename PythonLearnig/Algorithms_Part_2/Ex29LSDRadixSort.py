def sort(unsorted_array):

    R = 256
    n = len(unsorted_array)

    def sort_i(d):

        count = [0] * (R + 1)
        for c in range(n):
            count[ord(unsorted_array[c][d]) + 1] += 1

        for c in range(R):
            count[c + 1] += count[c]

        aux = unsorted_array.copy()
        for c in range(n):
            unsorted_array[count[ord(aux[c][d])]] = aux[c]
            count[ord(aux[c][d])] += 1

    for i in range(len(unsorted_array[0])-1, -1, -1):
        sort_i(i)


def test_lsd_radix_sort():
    import random

    unsorted_array = []
    size = random.randint(5, 10)
    for i in range(15):
        unsorted_array.append('')
        for _ in range(size):
            unsorted_array[i] += chr(random.randint(ord('A'), ord('C')))
    unsorted_array.insert(0, 'A' * (size - 1) + 'B')
    unsorted_array.append('A' * size)

    print()
    print('Original:')
    for key in unsorted_array:
        print(f'\t{key}')
    sort(unsorted_array)
    print('Sorted  :')
    for key in unsorted_array:
        print(f'\t{key}')


if __name__ == '__main__':
    test_lsd_radix_sort()
