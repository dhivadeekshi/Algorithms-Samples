from Ex28SymbolTable import SymbolTable


# left leaning red black binary search tree
class RedBlackBstST(SymbolTable):

    BLACK_COLOR_NODE = False
    RED_COLOR_NODE = True

    class Node:

        def __init__(self, key, value, color):
            self.key = key
            self.value = value
            self.color = color
            self.left_node = None
            self.right_node = None
            self.count = 1

        def __str__(self):

            def str_i(node):
                if node is not None:
                    return node.key
                return None

            return f'Key: {self.key} Value: {self.value} Left: {str_i(self.left_node)} ' \
                f'Right: {str_i(self.right_node)} Count: {self.count} IsRed? {self.color}'

        def update_count(self):
            def size_i(node):
                if node is None:
                    return 0
                return node.count
            self.count = size_i(self.left_node) + size_i(self.right_node) + 1

    def __init__(self):
        super().__init__()
        self.root = None

    def put(self, key, value):

        def rotate_right(node):
            left = node.left_node
            node.left_node = left.right_node
            left.right_node = node
            left.color = node.color
            node.color = RedBlackBstST.RED_COLOR_NODE
            node.update_count()
            return left

        def rotate_left(node):
            right = node.right_node
            node.right_node = right.left_node
            right.left_node = node
            right.color = node.color
            node.color = RedBlackBstST.RED_COLOR_NODE
            node.update_count()
            return right

        def flip_colors(node):
            node.color = RedBlackBstST.RED_COLOR_NODE
            node.left_node.color = RedBlackBstST.BLACK_COLOR_NODE
            node.right_node.color = RedBlackBstST.BLACK_COLOR_NODE

        def is_red_node(node):
            if node is None:
                return False
            return node.color

        def put_i(node):
            if node is None:
                node = self.Node(key, value, self.RED_COLOR_NODE)

            if node.key < key:
                node.right_node = put_i(node.right_node)
            elif key < node.key:
                node.left_node = put_i(node.left_node)
            else:
                node.value = value

            if not is_red_node(node.left_node) and is_red_node(node.right_node):
                node = rotate_left(node)
            if is_red_node(node.left_node) and is_red_node(node.left_node.left_node):
                node = rotate_right(node)
            if is_red_node(node.left_node) and is_red_node(node.right_node):
                flip_colors(node)

            node.update_count()
            return node

        self.root = put_i(self.root)

    def get(self, key):

        def get_i(node):
            if node is None:
                return None

            if node.key < key:
                return get_i(node.right_node)
            elif key < node.key:
                return get_i(node.left_node)
            else:
                return node.value

        return get_i(self.root)

    def size(self):
        if self.root is None:
            return 0
        return self.root.count

    def is_empty(self):
        return self.size() == 0

    def contains(self, key):
        return self.get(key) is not None

    def min_key(self):

        node = self.root
        if node is None:
            return None
        while node.left_node is not None:
            node = node.left_node
        return node.key

    def max_key(self):

        node = self.root
        if node is None:
            return None
        while node.right_node is not None:
            node = node.right_node
        return node.key

    def floor_key(self, key):

        def floor_i(node):
            if node is None:
                return None

            if node.key < key:
                result = floor_i(node.right_node)
                if result is not None:
                    return result
            elif key < node.key:
                return floor_i(node.left_node)
            return node.key

        return floor_i(self.root)

    def ceil_key(self, key):

        def ceil_i(node):
            if node is None:
                return None

            if node.key < key:
                return ceil_i(node.right_node)
            elif key < node.key:
                result = ceil_i(node.left_node)
                if result is not None:
                    return result
            return node.key

        return ceil_i(self.root)

    def rank(self, key):

        def size_i(node):
            if node is None:
                return 0
            return node.count

        def rank_i(node):
            if node is None:
                return 0

            if node.key < key:
                return rank_i(node.right_node) + size_i(node.left_node) + 1
            elif key < node.key:
                return rank_i(node.left_node)
            else:
                return size_i(node.left_node) + 1

        return rank_i(self.root)

    def select(self, k):

        def select_i(node, min_rank):
            if node is None:
                return None

            rank = min_rank + 1
            if node.left_node is not None:
                rank += node.left_node.count

            if rank < k:
                return select_i(node.right_node, rank)
            elif k < rank:
                return select_i(node.left_node, min_rank)
            else:
                return node.key

        return select_i(self.root, 0)

    def keys(self):

        def keys_i(node):
            if node is not None:
                for k in keys_i(node.left_node):
                    yield k
                yield node.key
                for k in keys_i(node.right_node):
                    yield k

        for key in keys_i(self.root):
            yield key

    def keys_bw(self, lo, hi):
        for key in self.keys():
            if lo <= key <= hi:
                yield key

    def __str__(self):

        def str_i(node, tab=''):

            if node is None:
                return ''

            builder = tab + str(node) + '\n'
            tab += '\t'
            builder += str_i(node.left_node, tab)
            builder += str_i(node.right_node, tab)
            return builder

        return '\n' + str_i(self.root)

    def delete_min(self):
        return self.delete(self.min_key())

    def delete_max(self):
        return self.delete(self.max_key())

    def delete(self, key):
        return None


def test_red_black_bst_st():

    def test(keys, lo, hi, floor1, floor2):

        st = RedBlackBstST()
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
    test_red_black_bst_st()
