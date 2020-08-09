def sort(unsorted_array):
    check = True
    while check:
        check = False
        for i in range(len(unsorted_array) - 1):
            if unsorted_array[i + 1] < unsorted_array[i]:
                check = True
                temp = unsorted_array[i]
                unsorted_array[i] = unsorted_array[i + 1]
                unsorted_array[i + 1] = temp


def test_bubble_sort():
    import random

    unsorted_array = list(map(lambda _: random.randint(-100, 100), list(range(15))))
    print()
    print(f'Original: {unsorted_array}')
    sort(unsorted_array)
    print(f'Sorted  : {unsorted_array}')


if __name__ == '__main__':
    test_bubble_sort()
