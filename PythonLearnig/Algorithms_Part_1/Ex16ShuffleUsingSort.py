def shuffle(sorted_list):
    import random

    to_sort = [(random.uniform(0, 1), x) for x in sorted_list]
    to_sort.sort()
    for i in range(len(sorted_list)):
        sorted_list[i] = to_sort[i][1]


def test_shuffle_using_sort():
    sorted_list = [x for x in range(10)]
    print("Original:", sorted_list)
    shuffle(sorted_list)
    print("Shuffled:", sorted_list)
    print("IsShuffled? ", set(sorted_list) == set([x for x in range(10)]))

    print()
    sorted_list = [chr(x) for x in range(ord('A'), ord('I'))]
    print("Original:", sorted_list)
    shuffle(sorted_list)
    print("Shuffled:", sorted_list)
    print("IsShuffled? ", set(sorted_list) == set([chr(x) for x in range(ord('A'), ord('I'))]))


if __name__ == '__main__':
    test_shuffle_using_sort()
