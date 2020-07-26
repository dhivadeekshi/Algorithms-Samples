import math
from Ex28SymbolTable import SymbolTable


class BinarySearchInST(SymbolTable):

    def __init__(self):
        super().__init__()
        self.all_keys = []
        self.values = []

    def put(self, key, value):
        if self.is_empty():
            self.all_keys.insert(0, key)
            self.values.insert(0, value)
            return
        for i in range(self.size()):
            if self.all_keys[i] == key:
                self.values[i] = value
                return
            elif self.all_keys[i] > key:
                self.all_keys.insert(i, key)
                self.values.insert(i, value)
                return
        self.all_keys.append(key)
        self.values.append(value)

    def get(self, key):

        lo = 0
        hi = self.size() - 1
        while lo <= hi:
            mid = lo + int(math.floor(hi - lo) / 2)
            if self.all_keys[mid] < key:
                lo = mid + 1
            elif self.all_keys[mid] > key:
                hi = mid - 1
            else:
                return self.values[mid]
        return None

    def size(self):
        return len(self.all_keys)

    def keys_bw(self, lo, hi):
        for key in self.all_keys:
            if lo <= key <= hi:
                yield key

    def keys(self):
        for key in self.all_keys:
            yield key

    def is_empty(self):
        return self.size() == 0

    def max_key(self):
        if self.is_empty():
            return None
        return self.all_keys[-1]

    def min_key(self):
        if self.is_empty():
            return None
        return self.all_keys[0]

    def floor_key(self, key):
        if self.is_empty():
            return None

        lo = 0
        hi = self.size() - 1
        while lo <= hi:
            mid = lo + int(math.floor(hi - lo) / 2)
            if self.all_keys[mid] < key:
                lo = mid + 1
            elif self.all_keys[mid] > key:
                hi = mid - 1
            else:
                return self.all_keys[mid]
        return self.all_keys[hi]

    def ceil_key(self, key):
        if self.is_empty():
            return None

        lo = 0
        hi = self.size() - 1
        while lo <= hi:
            mid = lo + int(math.floor(hi - lo) / 2)
            if self.all_keys[mid] < key:
                lo = mid + 1
            elif self.all_keys[mid] > key:
                hi = mid - 1
            else:
                return self.all_keys[mid]
        return self.all_keys[lo]

    def delete_max(self):
        return self.all_keys.pop(), self.values.pop()

    def delete_min(self):
        return self.all_keys.pop(0), self.values.pop(0)

    def delete(self, key):
        lo = 0
        hi = self.size() - 1
        while lo <= hi:
            mid = lo + int(math.floor(hi - lo) / 2)
            if self.all_keys[mid] < key:
                lo = mid + 1
            elif self.all_keys[mid] > key:
                hi = mid - 1
            else:
                return self.all_keys.pop(mid), self.values.pop(mid)

    def select(self, k):
        return self.all_keys[k - 1]

    def rank(self, key):
        lo = 0
        hi = self.size() - 1
        while lo <= hi:
            mid = lo + int(math.floor(hi - lo) / 2)
            if self.all_keys[mid] < key:
                lo = mid + 1
            elif self.all_keys[mid] > key:
                hi = mid - 1
            else:
                return mid + 1

    def contains(self, key):
        return self.get(key) is not None

    def size_bw(self, lo, hi):
        return self.rank(self.ceil_key(hi)) - self.rank(self.floor_key(lo)) + 1

    def __str__(self):

        result = ''
        for i in range(self.size()):
            result += f'\nKey: {self.all_keys[i]} Value: {self.values[i]}'
        return result + '\n'


def test_binary_search_tree_st():

    def test(keys, lo, hi, floor1, floor2):

        st = BinarySearchInST()
        index = 1
        for key in keys:
            st.put(key, index)
            print(f'Insert {key} = {index} : {list(st.keys())}')
            index += 1

        print('='*10, st, '='*10)
        print(f'InOrder: {[(key, st.get(key)) for key in st.keys()]}')
        print(f"Size: {st.size()} Min: {st.min_key()} Max: {st.max_key()}")
        print("Keys:", list(st.keys()))
        print(f"Keys [{lo}..{hi}]: {list(st.keys_bw(lo, hi))}")
        print(f"floor ({floor1}): {st.floor_key(floor1)} ceil({floor1}): {st.ceil_key(floor1)}")
        print(f"floor ({floor2}): {st.floor_key(floor2)} ceil({floor2}): {st.ceil_key(floor2)}")
        print("Rank :", [(key, st.rank(key)) for key in st.keys()])
        print('Select:', [(rank, st.select(rank)) for rank in range(1, st.size() + 1)])
        key = st.select(5)
        print('Select 5:', key)
        print(f"After deleting rank 5: {st.delete(key)} keys: {list(st.keys())}")
        print(f"After deleting min: {st.delete_min()} keys: {list(st.keys())}")
        print(f"After deleting max: {st.delete_max()} keys: {list(st.keys())}")

        print('='*10, st, '='*10)
        print('Size:', st.size())
        print()
        print()

    print("Test case 1:")
    test_case = [54, 5, -9, 53, 1, 0, 15]
    test(test_case, 0, 20, 10, 15)

    print("Test case 2:")
    test_case = ['S', 'O', 'R', 'T', 'E', 'X', 'A', 'M', 'P', 'L', 'E']
    test(test_case, 'B', 'P', 'G', 'P')

    print(' END '.center(100, '-'))


if __name__ == '__main__':
    test_binary_search_tree_st()
