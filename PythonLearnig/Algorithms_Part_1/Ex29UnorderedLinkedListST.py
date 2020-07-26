from Ex28SymbolTable import SymbolTable


class UnorderedLinkedListST(SymbolTable):

    class Node:

        def __init__(self, key, value, node):
            self.key = key
            self.value = value
            self.node = node

    def __init__(self):
        super().__init__()
        self.root = None
        self.sz = 0

    def put(self, key, value):
        node = self.root
        while node is not None and node.key != key:
            node = node.node
        if node is not None:
            node.value = value
        else:
            self.root = UnorderedLinkedListST.Node(key, value, self.root)
            self.sz += 1

    def get(self, key):
        node = self.root
        while node is not None and node.key != key:
            node = node.node
        if node is not None:
            return node.value
        return None

    def contains(self, key):
        node = self.root
        while node is not None and node.key != key:
            node = node.node
        return node is not None

    def size(self):
        return self.sz

    def max_key(self):
        if self.is_empty():
            return None
        node = self.root
        max_key = node.key
        while node is not None:
            if node.key > max_key:
                max_key = node.key
            node = node.node
        return max_key

    def min_key(self):
        if self.is_empty():
            return None
        node = self.root
        min_key = node.key
        while node is not None:
            if node.key < min_key:
                min_key = node.key
            node = node.node
        return min_key

    def delete_max(self):
        return self.delete(self.max_key())

    def delete_min(self):
        return self.delete(self.min_key())

    def delete(self, key):

        def delete_i(node):
            if node is None:
                return None, None
            if node.key == key:
                self.sz -= 1
                return node.node, node.value
            else:
                node.node, val = delete_i(node.node)
            return node, val

        if self.is_empty():
            return None, None
        self.root, value = delete_i(self.root)
        return key, value

    def floor_key(self, key):
        node = self.root
        if node is None:
            return None
        f_key = node.key
        while node is not None:
            if node.key == key:
                return node.key
            if f_key > key:
                f_key = node.key
            if key > node.key > f_key:
                f_key = node.key
            node = node.node

        if f_key > key:
            return None
        return f_key

    def ceil_key(self, key):
        node = self.root
        if node is None:
            return None
        c_key = node.key
        while node is not None:
            if node.key == key:
                return node.key
            if c_key < key:
                c_key = node.key
            if c_key < node.key < key:
                c_key = node.key
            node = node.node

        if c_key < key:
            return None
        return c_key

    def keys_bw(self, lo, hi):
        node = self.root
        while node is not None:
            if lo <= node.key <= hi:
                yield node.key
            node = node.node

    def keys(self):
        node = self.root
        while node is not None:
            yield node.key
            node = node.node

    def __str__(self):
        result = ''
        node = self.root
        while node is not None:
            result += f'\nKey: {node.key} Value: {node.value}'
            node = node.node
        return result + '\n'


def test_unordered_linked_list_st():

    def test(keys, lo, hi, floor1, floor2):

        st = UnorderedLinkedListST()
        index = 1
        for key in keys:
            st.put(key, index)
            print(f'Insert {key} = {index} : {list(st.keys())}')
            index += 1

        print('='*10, st, '='*10)
        print(f'Key,Value: {[(key, st.get(key)) for key in st.keys()]}')
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
    test_unordered_linked_list_st()
