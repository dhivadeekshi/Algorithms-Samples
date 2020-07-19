def sort(unsorted_list):

    n = len(unsorted_list)
    for i in range(n):
        for j in range(i, 0, -1):
            if unsorted_list[j] < unsorted_list[j - 1]:

                # exchange
                temp = unsorted_list[j - 1]
                unsorted_list[j - 1] = unsorted_list[j]
                unsorted_list[j] = temp
            else:
                break


def test_insertion_sort():

    import random
    unsorted_list = [random.randint(-100, 100) for _ in range(10)]
    print(f"Unsorted : {unsorted_list}")
    sort(unsorted_list)
    print(f"Sorted   : {unsorted_list}")

    is_sorted = True
    for i in range(len(unsorted_list) - 1):
        if unsorted_list[i] > unsorted_list[i+1]:
            is_sorted = False
            break

    print(f"Is Sorted ? {is_sorted}")

    print()
    unsorted_list = [chr(random.randint(ord('A'), ord('Z')+1)) for _ in range(10)]
    print(f"Unsorted : {unsorted_list}")
    sort(unsorted_list)
    print(f"Sorted   : {unsorted_list}")

    is_sorted = True
    for i in range(len(unsorted_list) - 1):
        if unsorted_list[i] > unsorted_list[i + 1]:
            is_sorted = False
            break

    print(f"Is Sorted ? {is_sorted}")


if __name__ == '__main__':
    test_insertion_sort()
