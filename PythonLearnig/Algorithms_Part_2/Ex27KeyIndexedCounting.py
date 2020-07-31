def key_indexed_counting(unsorted_array):

    R = 256
    index_counter = [0] * (R + 1)
    aux = unsorted_array.copy()

    n = len(unsorted_array)
    for i in range(n):
        index_counter[ord(unsorted_array[i]) + 1] += 1

    for i in range(R):
        index_counter[i + 1] += index_counter[i]

    for i in range(n):
        unsorted_array[index_counter[ord(aux[i])]] = aux[i]
        index_counter[ord(aux[i])] += 1


def test_key_indexed_array():
    import random

    print()
    print('Test case 1:')
    unsorted_array = [chr(random.randint(ord('0'), ord('9'))) for _ in range(15)]
    print(f'Original: {unsorted_array}')
    key_indexed_counting(unsorted_array)
    print(f'Sorted  : {unsorted_array}')

    print()
    print('Test case 2:')
    unsorted_array = [chr(random.randint(ord('A'), ord('Z')))
                      for _ in range(15)]
    print(f'Original: {unsorted_array}')
    key_indexed_counting(unsorted_array)
    print(f'Sorted  : {unsorted_array}')

    print()
    print('Test case 3:')
    unsorted_array = [chr(random.randint(ord('a'), ord('z'))) for _ in range(15)]
    print(f'Original: {unsorted_array}')
    key_indexed_counting(unsorted_array)
    print(f'Sorted  : {unsorted_array}')

    print()
    print('Test case 4:')
    unsorted_array = [chr(random.randint(ord('A'), ord('Z'))) for _ in range(15)]
    unsorted_array.extend([chr(random.randint(ord('a'), ord('z'))) for _ in range(15)])
    unsorted_array.extend([chr(random.randint(ord('0'), ord('9'))) for _ in range(15)])
    print(f'Original: \n\t{unsorted_array[:15]}\n\t{unsorted_array[15:30]}\n\t{unsorted_array[30:]}')
    key_indexed_counting(unsorted_array)
    print(f'Sorted  : \n\t{unsorted_array[:15]}\n\t{unsorted_array[15:30]}\n\t{unsorted_array[30:]}')


if __name__ == '__main__':
    test_key_indexed_array()
