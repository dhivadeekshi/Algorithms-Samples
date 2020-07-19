def sort(unsorted_list):

    n = len(unsorted_list)
    max_value = max(unsorted_list) + 1

    for i in range(n):
        min_value = max_value
        min_index = 0
        for j in range(i, n):
            if unsorted_list[j] < min_value:
                min_value = unsorted_list[j]
                min_index = j

        # exchange
        temp = unsorted_list[i]
        unsorted_list[i] = unsorted_list[min_index]
        unsorted_list[min_index] = temp


def test_selection_sort():

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


if __name__ == '__main__':
    test_selection_sort()