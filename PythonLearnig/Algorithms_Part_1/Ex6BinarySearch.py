import math


def find(sorted_list, value):

    lo, hi = 0, len(sorted_list) - 1

    while lo <= hi:
        mid = lo + int(math.floor((hi - lo) / 2))

        if sorted_list[mid] < value:
            lo = mid + 1
        elif sorted_list[mid] > value:
            hi = mid - 1
        else:
            return mid

    return -1


def test_binary_search():

    import random
    import math
    n = 100
    value = [random.randint(-100, 100) for _ in range(n)]
    value.sort()
    print(value)

    test_cases = [random.randint(-100, 100) for _ in range(math.floor(n / 4))]
    for test in test_cases:
        index = find(value, test)
        if index == -1:
            print(f"Find {test}: Not Found")
        else:
            print(f"Find {test}: {index}")


if __name__ == '__main__':
    test_binary_search()
