from Ex28SymbolTable import SymbolTable


class BinaryTreeST(SymbolTable):

    class Node:

        def __init__(self, key, value):
            self.key = key
            self.value = value
            self.right = None
            self.left = None
            self.count = 1

        def update_count(self):
            self.count = 1
            if self.left is not None:
                self.count += self.left.count
            if self.right is not None:
                self.count += self.right.count

        def __str__(self):

            def get_key(node):
                if node is None:
                    return None
                return node.key

            return f'Key: {self.key}, Value: {self.value}, left: {get_key(self.left)}, ' \
                   f'right: {get_key(self.right)}, count: {self.count} '

    def __init__(self):
        super().__init__()
        self.root = None
        self.sz = 0

    def put(self, key, value):

        def put_i(node):
            if node is None:
                self.sz += 1
                return self.Node(key, value)

            if node.key < key:
                node.right = put_i(node.right)
            elif key < node.key:
                node.left = put_i(node.left)
            else:
                node.value = value
            node.update_count()
            return node
        self.root = put_i(self.root)

    def get(self, key):

        def get_i(node):
            if node is None:
                return None
            if node.key < key:
                return get_i(node.right)
            elif key < node.key:
                return get_i(node.left)
            else:
                return node.value
        return get_i(self.root)

    def size(self):
        return self.sz

    def contains(self, key):
        return self.get(key) is not None

    def is_empty(self):
        return self.size() == 0

    def floor_key(self, key):

        def floor_key_i(node):
            if node is None:
                return None

            if node.key < key:
                result = floor_key_i(node.right)
                if result is not None:
                    return result
                else:
                    return node.key
            elif key < node.key:
                return floor_key_i(node.left)
            else:
                return key

        return floor_key_i(self.root)

    def ceil_key(self, key):

        def ceil_key_i(node):
            if node is None:
                return None

            if node.key < key:
                return ceil_key_i(node.right)
            elif key < node.key:
                result = ceil_key_i(node.left)
                if result is not None:
                    return result
                else:
                    return node.key
            else:
                return key

        return ceil_key_i(self.root)

    def min_key(self):

        node = self.root
        if node is None:
            return None
        while node.left is not None:
            node = node.left
        return node.key

    def max_key(self):

        node = self.root
        if node is None:
            return None
        while node.right is not None:
            node = node.right
        return node.key

    def rank(self, key):

        def size_i(node):
            if node is None:
                return 0
            else:
                return node.count

        def rank_i(node):
            if node is None:
                return None

            if key < node.key:
                return rank_i(node.left)
            elif node.key < key:
                return size_i(node.left) + rank_i(node.right) + 1
            else:
                return size_i(node.left) + 1
        return rank_i(self.root)

    def select(self, k):

        def select_i(node, left_size):
            if node is None:
                return None

            rank = left_size + 1
            if node.left is not None:
                rank += node.left.count

            if rank < k:
                return select_i(node.right, rank)
            elif k < rank:
                return select_i(node.left, left_size)
            else:
                return node.key

        return select_i(self.root, 0)

    def keys_bw(self, lo, hi):
        for key in self.keys():
            if lo <= key <= hi:
                yield key

    def keys(self):

        def keys_i(node):
            if node is not None:
                for k in keys_i(node.left):
                    yield k
                yield node.key
                for k in keys_i(node.right):
                    yield k

        for key in keys_i(self.root):
            yield key

    def delete_min_i(self, node):
        if node is None:
            return None, None, None
        if node.left is None:
            self.sz -= 1
            return node.right, node.key, node.value
        node.left, k, v = self.delete_min_i(node.left)
        node.update_count()
        return node, k, v

    def delete_min(self):

        self.root, key, value = self.delete_min_i(self.root)
        return key, value

    def delete_max(self):

        def delete_max_i(node):
            if node is None:
                return None, None, None
            if node.right is None:
                self.sz -= 1
                return node.left, node.key, node.value
            node.right, k, v = delete_max_i(node.right)
            node.update_count()
            return node, k, v

        self.root, key, value = delete_max_i(self.root)
        return key, value

    def delete(self, key):

        def delete_i(node):
            if node is None:
                return None, None

            if node.key < key:
                node.right, v = delete_i(node.right)
            elif node.key > key:
                node.left, v = delete_i(node.left)
            else:
                v = node.value
                node.value = None
                t, t_key, t_value = self.delete_min_i(node.right)
                print(node)
                print(t)
                if t is None:
                    self.sz -= 1
                    return node.left, v
                else:
                    node.key = t_key
                    node.value = t_value
                    node.right = t
            node.update_count()
            return node, v

        self.root, value = delete_i(self.root)
        return key, value

    def __str__(self):

        def str_i(node, tab, from_node):
            if node is None:
                return ''

            build = tab + from_node + " => " + str(node) + '\n'
            tab += '\t'
            build += str_i(node.left, tab, 'Left')
            build += str_i(node.right, tab, 'Right')
            return build

        return "\n" + str_i(self.root, '', 'Root')


def test_binary_tree_st():

    def test(keys, lo, hi, floor1, floor2):

        st = BinaryTreeST()
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
    test_binary_tree_st()
