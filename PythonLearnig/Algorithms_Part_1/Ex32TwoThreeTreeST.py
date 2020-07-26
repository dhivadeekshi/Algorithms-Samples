from Ex28SymbolTable import SymbolTable


class TwoThreeTreeST(SymbolTable):

    class Node:

        def __init__(self, key, value):

            self.middle_key = key
            self.middle_value = value

            self.left_node = self.right_node = self.middle_node = self.middle_node_extra = None
            self.left_key = self.right_key = None
            self.left_value = self.right_value = None

            self.count = 1
            self.is_two_node = True
            self.is_three_node = False
            self.is_split_node = False

        def update_count(self):

            self.count = 1
            if self.is_three_node:
                self.count += 1
            if self.left_node is not None:
                self.count += self.left_node.count
            if self.middle_node is not None:
                self.count += self.middle_node.count
            if self.right_node is not None:
                self.count += self.right_node.count

        def move_middle_to_right_put_in_left(self, key, value):

            self.right_key = self.middle_key
            self.right_value = self.middle_value

            self.left_key = key
            self.left_value = value

            self.middle_key = None
            self.middle_value = None

        def move_middle_to_left_put_in_right(self, key, value):

            self.left_key = self.middle_key
            self.left_value = self.middle_value

            self.right_key = key
            self.right_value = value

            self.middle_key = None
            self.middle_value = None

        def move_left_to_middle(self, key, value):

            self.middle_key = self.left_key
            self.middle_value = self.left_value

            self.left_key = key
            self.left_value = value

        def move_right_to_middle(self, key, value):

            self.middle_key = self.right_key
            self.middle_value = self.right_value

            self.right_key = key
            self.right_value = value

        def put(self, key, value):

            def put_in_two_node():

                if key < self.middle_key:
                    self.move_middle_to_right_put_in_left(key, value)
                elif self.middle_key < key:
                    self.move_middle_to_left_put_in_right(key, value)
                else:
                    self.middle_value = value

                self.is_two_node = False
                self.is_three_node = True
                self.is_split_node = False
                self.update_count()

            def put_in_three_node():

                if key == self.left_key:
                    self.left_value = value
                elif key == self.right_key:
                    self.right_value = value
                elif key < self.left_key:
                    self.move_left_to_middle(key, value)
                elif self.right_key < key:
                    self.move_right_to_middle(key, value)
                else:
                    self.middle_key = key
                    self.middle_value = value

                self.is_three_node = False
                self.update_count()

            if self.is_two_node:
                put_in_two_node()
                return None
            else:
                put_in_three_node()
                return self.split_four_node()

        def update_split_node_from_left(self):

            ref_node = self.left_node
            self.left_node = None

            if self.is_two_node:
                self.move_middle_to_right_put_in_left(ref_node.middle_key, ref_node.middle_value)
                self.left_node = ref_node.left_node
                self.middle_node = ref_node.right_node
                self.is_three_node = True
                self.is_two_node = False
                self.update_count()
            else:
                self.move_left_to_middle(ref_node.middle_key, ref_node.middle_value)
                self.middle_node_extra = self.middle_node
                self.middle_node = ref_node.right_node
                self.left_node = ref_node.left_node
                self.is_three_node = False
                self.split_four_node()

        def update_split_node_from_right(self):

            ref_node = self.right_node
            self.right_node = None

            if self.is_two_node:
                self.move_middle_to_left_put_in_right(ref_node.middle_key, ref_node.middle_value)
                self.middle_node = ref_node.left_node
                self.right_node = ref_node.right_node
                self.is_three_node = True
                self.is_two_node = False
                self.update_count()
            else:
                self.move_right_to_middle(ref_node.middle_key, ref_node.middle_value)
                self.middle_node_extra = ref_node.left_node
                self.right_node = ref_node.right_node
                self.is_three_node = False
                self.split_four_node()

        def update_split_node_from_middle(self):

            self.middle_key = self.middle_node.middle_key
            self.middle_value = self.middle_node.middle_value
            self.middle_node_extra = self.middle_node.right_node
            self.middle_node = self.middle_node.left_node
            self.is_three_node = False
            self.split_four_node()

        def split_four_node(self):

            new_left_node = TwoThreeTreeST.Node(self.left_key, self.left_value)
            new_right_node = TwoThreeTreeST.Node(self.right_key, self.right_value)

            new_left_node.left_node = self.left_node
            new_left_node.right_node = self.middle_node

            new_right_node.left_node = self.middle_node_extra
            new_right_node.right_node = self.right_node

            self.left_node = new_left_node
            self.right_node = new_right_node
            self.middle_node = self.middle_node_extra = None

            self.is_split_node = True
            self.is_two_node = True

            new_right_node.update_count()
            new_left_node.update_count()
            self.update_count()

            return self

        def is_leaf_node(self):
            return self.left_node == self.middle_node == self.right_node is None

        def __str__(self):

            def str_i(node):
                if node is None:
                    return None
                if node.is_two_node:
                    return node.middle_key
                else:
                    return [node.left_key, node.right_key]

            if self.is_two_node:
                return f'Key: {self.middle_key} Value: {self.middle_value} left: {str_i(self.left_node)} ' \
                        f'right: {str_i(self.right_node)} count: {self.count} leaf? {self.is_leaf_node()}'
            else:
                return f'Key: {[self.left_key,self.right_key]} Value: {[self.left_value, self.right_value]} ' \
                        f'left: {str_i(self.left_node)} middle: {str_i(self.middle_node)} ' \
                        f'right: {str_i(self.right_node)} count: {self.count} leaf? {self.is_leaf_node()}'

    def __init__(self):
        super().__init__()
        self.root = None

    def put(self, key, value):

        def put_i(node):

            result = None
            if node.is_leaf_node():
                result = node.put(key, value)
            elif node.is_two_node:

                if node.middle_key < key:
                    result = put_i(node.right_node)
                    if result is not None and result.is_split_node:
                        result = node.update_split_node_from_right()
                elif key < node.middle_key:
                    result = put_i(node.left_node)
                    if result is not None and result.is_split_node:
                        result = node.update_split_node_from_left()
                else:
                    node.middle_value = value

            else:

                if node.left_key == key:
                    node.left_value = value
                elif node.right_key == key:
                    node.right_value = value
                elif node.right_key < key:
                    result = put_i(node.right_node)
                    if result is not None and result.is_split_node:
                        result = node.update_split_node_from_right()
                elif key < node.left_key:
                    result = put_i(node.left_node)
                    if result is not None and result.is_split_node:
                        result = node.update_split_node_from_left()
                else:
                    result = put_i(node.middle_node)
                    if result is not None and result.is_split_node:
                        result = node.update_split_node_from_middle()

            node.update_count()
            return result

        if self.root is None:
            self.root = self.Node(key, value)
        else:
            put_i(self.root)

    def get(self, key):

        def get_i(node):
            if node is None:
                return None

            if node.is_two_node:
                if node.middle_key < key:
                    return get_i(node.right_node)
                elif key < node.middle_key:
                    return get_i(node.left_node)
                else:
                    return node.middle_value
            else:
                if node.left_key == key:
                    return node.left_value
                elif node.right_key == key:
                    return node.right_value
                elif key < node.left_key:
                    return get_i(node.left_node)
                elif node.right_key < key:
                    return get_i(node.right_node)
                else:
                    return get_i(node.middle_node)

        return get_i(self.root)

    def size(self):
        if self.root is None:
            return 0
        return self.root.count

    def contains(self, key):
        return self.get(key) is not None

    def is_empty(self):
        return self.size() == 0

    def min_key(self):

        node = self.root
        if node is None:
            return None
        while not node.is_leaf_node:
            node = node.left_node
        if node.is_two_node:
            return node.middle_key
        else:
            return node.left_key

    def max_key(self):

        node = self.root
        if node is None:
            return None
        while not node.is_leaf_node:
            node = node.right_node
        if node.is_two_node:
            return node.middle_key
        else:
            return node.right_key

    def floor_key(self, key):

        def floor_key_i(node_key, left_node, right_node):
            if node_key is None:
                return None

            if node_key < key:
                result = floor_i(right_node)
                if result is not None:
                    return result
                return node_key
            elif key < node_key:
                return floor_i(left_node)
            else:
                return key

        def floor_i(node):
            if node is None:
                return None

            if node.is_two_node:
                return floor_key_i(node.middle_key, node.left_node, node.right_node)
            else:
                result = floor_key_i(node.right_key, node.middle_node, node.right_node)
                if result is not None:
                    return result
                return floor_key_i(node.left_key, node.left_node, node.middle_node)

        return floor_i(self.root)

    def ceil_key(self, key):

        def ceil_key_i(node_key, left_node, right_node):
            if node_key is None:
                return None

            if node_key < key:
                return ceil_i(right_node)
            elif key < node_key:
                result = ceil_i(left_node)
                if result is not None:
                    return result
                return node_key
            else:
                return node_key

        def ceil_i(node):
            if node is None:
                return None

            if node.is_two_node:
                return ceil_key_i(node.middle_key, node.left_node, node.right_node)
            else:
                result = ceil_key_i(node.left_key, node.left_node, node.middle_node)
                if result is not None:
                    return result
                return ceil_key_i(node.right_key, node.middle_node, node.right_node)

        return ceil_i(self.root)

    def keys_bw(self, lo, hi):
        for key in self.keys():
            if lo <= key <= hi:
                yield key

    def keys(self):

        def keys_i(node):

            if node is not None:
                for k in keys_i(node.left_node):
                    yield k
                if node.is_three_node:
                    yield node.left_key
                    for k in keys_i(node.middle_node):
                        yield k
                    yield node.right_key
                else:
                    yield node.middle_key
                for k in keys_i(node.right_node):
                    yield k

        for key in keys_i(self.root):
            yield key

    def rank(self, key):

        def size_i(node):
            if node is None:
                return 0
            return node.count

        def rank_ii(node_key, left_node, right_node, min_rank):
            if node_key is None:
                return 0

            rank = min_rank + 1
            if left_node is not None:
                rank += size_i(left_node)

            if node_key < key:
                return rank_i(right_node, rank)
            elif key < node_key:
                return rank_i(left_node, min_rank)
            elif node_key == key:
                return rank
            else:
                return 0

        def rank_i(node, min_rank):
            if node is None:
                return 0

            l_size = size_i(node.left_node)
            if node.is_two_node:
                return rank_ii(node.middle_key, node.left_node, node.right_node, min_rank)
            else:
                if key <= node.left_key:
                    return rank_ii(node.left_key, node.left_node, node.middle_node, min_rank)
                return rank_ii(node.right_key, node.middle_node, node.right_node, min_rank + l_size + 1)

        return rank_i(self.root, 0)

    def select(self, k):
        if 0 > k >= self.size():
            return None

        def size_i(node):
            if node is None:
                return 0
            return node.count

        def select_ii(node_key, left_node, right_node, min_rank):
            if node_key is None:
                return None

            rank = min_rank + 1
            if left_node is not None:
                rank += left_node.count
            if rank < k:
                return select_i(right_node, rank)
            elif k < rank:
                return select_i(left_node, min_rank)
            else:
                return node_key

        def select_i(node, min_rank):
            if node is None:
                return None
            if node.is_two_node:
                return select_ii(node.middle_key, node.left_node, node.right_node, min_rank)
            else:
                result = select_ii(node.left_key, node.left_node, node.middle_node, min_rank)
                if result is not None:
                    return result
                return select_ii(node.right_key, node.middle_node, node.right_node,
                                 min_rank + size_i(node.left_node) + 1)

        return select_i(self.root, 0)

    def delete_min(self):
        return self.delete(self.min_key())

    def delete_max(self):
        return self.delete(self.max_key())

    def delete(self, key):

        def borrow_from_middle_child(node):
            if node.middle_node is None or node.middle_node.is_two_node:
                return None

            if node.is_leaf_node:
                return None

        def delete_i(node):
            if node is None:
                return None, None

            if node.is_three_node:

                if node.is_leaf_node:
                    if node.right_key == key:
                        value_i = node.right_value
                        node.move_left_to_middle(None, None)
                        node.right_key = None
                        node.right_value = None
                    elif node.left_key == key:
                        value_i = node.left_value
                        node.move_right_to_middle(None, None)
                        node.left_key = None
                        node.left_value = None
                    else:
                        return None, None
                    node.is_three_node = False
                    node.is_two_node = True
                    return node, value_i
                else:
                    if node.right_key == key:
                        pass
                    elif node.left_key == key:
                        pass
                    elif node.right_key < key:
                        pass
                    elif key < node.left_key:
                        pass
                    else:
                        pass
            else:

                if node.is_leaf_node:
                    pass
                else:
                    pass

            return None, None

        # self.root, value = delete_i(self.root)
        # return key, value

    def __str__(self):

        def str_i(node, tab):
            if node is None:
                return ''

            builder = tab + str(node) + '\n'
            tab += '\t'
            builder += str_i(node.left_node, tab)
            if node.is_three_node:
                builder += str_i(node.middle_node, tab)
            builder += str_i(node.right_node, tab)
            return builder

        return '\n' + str_i(self.root, '')


def test_two_three_tree_st():

    def test(keys, lo, hi, floor1, floor2):

        st = TwoThreeTreeST()
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
    test_two_three_tree_st()
