from Ex34StringST import SymbolTable


class TernarySearchTrieST(SymbolTable):

    class Node:

        def __init__(self, key):
            self.key = key
            self.value = None
            self.left = self.middle = self.right = None

        def __str__(self):

            def str_i(node):
                if node is None:
                    return None
                return node.key

            return f'Key: {self.key} Value: {self.value} ' \
                   f'Left: {str_i(self.left)} Middle: {str_i(self.middle)} ' \
                   f'Right: {str_i(self.right)}'

        def is_leaf_node(self):
            return self.left == self.middle == self.right is None

    def __init__(self):
        super(TernarySearchTrieST, self).__init__()
        self.root = None

    def put(self, key, value):

        def put_i(node, d=0):
            if node is None:
                node = self.Node(key[d])

            if node.key < key[d]:
                node.right = put_i(node.right, d)
            elif key[d] < node.key:
                node.left = put_i(node.left, d)
            elif d == len(key) - 1:
                node.value = value
            else:
                node.middle = put_i(node.middle, d + 1)

            return node

        self.root = put_i(self.root)

    def get(self, key):

        def get_i(node, d=0):
            if node is None:
                return None

            if node.key < key[d]:
                return get_i(node.right, d)
            elif key[d] < node.key:
                return get_i(node.left, d)
            elif d == len(key) - 1:
                return node.value
            else:
                return get_i(node.middle, d + 1)

        return get_i(self.root)

    def contains(self, key):
        return self.get(key) is not None

    def delete(self, key):

        def delete_i(node, d=0):
            if node is None:
                return None, None

            if node.key < key[d]:
                node.right, val = delete_i(node.right, d)
            elif key[d] < node.key:
                node.left, val = delete_i(node.left, d)
            elif d == len(key) - 1:
                val = node.value
                node.value = None
            else:
                node.middle, val = delete_i(node.middle, d + 1)

            if node.is_leaf_node() and node.value is None:
                return None, val
            return node, val

        root, value = delete_i(self.root)
        return key, value

    def __keys(self, node, prefix=''):
        if node is not None:
            for k in self.__keys(node.left, prefix):
                yield k

            if node.value is not None:
                yield prefix + node.key
            for k in self.__keys(node.middle, prefix + node.key):
                yield k

            for k in self.__keys(node.right, prefix):
                yield k

    def keys(self):
        for key in self.__keys(self.root):
            yield key

    def keys_with_prefix(self, s):

        def keys_i(node, d=0):
            if node is not None:

                if node.key < s[d]:
                    for k in keys_i(node.right, d):
                        yield k
                elif s[d] < node.key:
                    for k in keys_i(node.left, d):
                        yield k
                elif d == len(s) - 1:
                    if node.value is not None:
                        yield s
                    for k in self.__keys(node.middle, s):
                        yield k
                else:
                    for k in keys_i(node.middle, d + 1):
                        yield k

        for key in keys_i(self.root):
            yield key

    def keys_that_match(self, s):
        yield None

    def longest_prefix_of(self, s):

        def lcp(node, prefix='', lp='', d=0):
            if node is None:
                return lp

            if node.value is not None:
                lp = prefix + node.key

            if node.key < s[d]:
                return lcp(node.right, prefix, lp, d)
            elif s[d] < node.key:
                return lcp(node.left, prefix, lp, d)
            elif d == len(s) - 1:
                return lp
            else:
                return lcp(node.middle, prefix + node.key, lp, d + 1)

        return lcp(self.root)

    def __any_key(self, node, prefix=''):
        for k in self.__keys(node, prefix):
            if k is not None:
                return k

    def floor_key(self, key):

        def floor_i(node, prefix='', d=0):
            if node is None:
                return None

            f_key = None
            if node.value is not None:
                f_key = prefix + node.key

            if node.key < key[d]:
                f_key = floor_i(node.right, prefix, d)
                if f_key is None:
                    if node.value is not None:
                        f_key = prefix + node.key
                    else:
                        f_key = self.__any_key(node.middle, prefix + node.key)
                if f_key is None:
                    f_key = self.__any_key(node.left, prefix)
            elif key < node.key:
                f_key = floor_i(node.left, prefix, d)
            elif d == len(key) - 1:
                pass
            else:
                f_key = floor_i(node.middle, prefix + node.key, d + 1)
                if f_key is None:
                    f_key = self.__any_key(node.left, prefix)

            return f_key

        return floor_i(self.root)

    def ceil_key(self, key):

        def ceil_i(node, prefix='', d=0):
            if node is None:
                return None

            c_key = None
            if node.value is not None:
                c_key = prefix + node.key

            if node.key < key[d]:
                c_key = ceil_i(node.right, prefix, d)
            elif key[d] < node.key:
                c_key = ceil_i(node.left, prefix, d)
                if c_key is None:
                    if node.value is not None:
                        c_key = prefix + node.key
                    else:
                        c_key = self.__any_key(node.middle, prefix + node.key)
                if c_key is None:
                    c_key = self.__any_key(node.right, prefix)
            elif d == len(key) - 1:
                if c_key is None:
                    c_key = self.__any_key(node.middle, prefix + node.key)
            else:
                c_key = ceil_i(node.middle, prefix + node.key, d + 1)
                if c_key is None:
                    c_key = self.__any_key(node.right, prefix)

            return c_key

        return ceil_i(self.root)


def test_ternary_search_trie_st():

    st = TernarySearchTrieST()
    st.put('she', 0)
    st.put('sells', 1)
    st.put('sea', 2)
    st.put('shells', 3)
    st.put('by', 4)
    st.put('the', 5)
    st.put('she', 6)
    st.put('shore', 7)

    print()
    print(f'Delete "sea": {st.delete("sea")}')

    print()
    for key in st.keys():
        print(f'get "{key}": {st.get(key)}')

    print()
    print(f'Keys: {list(st.keys())}')
    print(f'Keys with prefix "sh": {list(st.keys_with_prefix("sh"))}')
    print(f'Keys that match ".ell.": {list(st.keys_that_match(".ell."))}')
    print(f'Longest prefix of "there": {st.longest_prefix_of("there")}')

    def test_floor_ceil(k):
        print(f'floor of "{k}": {st.floor_key(k)}')
        print(f'ceil of "{k}": {st.ceil_key(k)}')

    print()
    test_floor_ceil("hello")
    test_floor_ceil("she")
    test_floor_ceil("baby")
    test_floor_ceil("thus")
    test_floor_ceil("shoe")


if __name__ == '__main__':
    test_ternary_search_trie_st()
