"""Implement quick sort in Python.
Input a list.
Output a sorted list."""
def quicksort(array):
    import random

    random.shuffle(array)

    def exch(i, j):
        temp = array[i]
        array[i] = array[j]
        array[j] = temp

    def sort(lo, hi):
        if hi <= lo:
            return

        lt = lo + 1
        gt = hi
        while True:

            while array[lt] < array[lo]:
                if lt >= hi:
                    break
                lt += 1

            while array[lo] < array[gt]:
                if gt <= lo:
                    break
                gt -= 1

            if lt >= gt:
                break

            exch(lt, gt)

        exch(lo, gt)

        sort(lo, gt - 1)
        sort(gt + 1, hi)
    sort(0, len(array) - 1)
    return test

test = [21, 4, 1, 3, 9, 20, 25, 6, 21, 14]
print(quicksort(test))
