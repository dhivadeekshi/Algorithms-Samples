def shuffle(sorted_list):
    import random

    def exch(first, second):
        temp = sorted_list[first]
        sorted_list[first] = sorted_list[second]
        sorted_list[second] = temp

    for i in range(len(sorted_list)):
        exch(i, random.randint(0, i))


def test_knuth_shuffle():
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
    test_knuth_shuffle()