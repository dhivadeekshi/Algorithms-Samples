from Ex34StringST import SymbolTable


class RWayTrieST(SymbolTable):

    R = 256

    class Node:

        def __init__(self):
            self.value = None
            self.next = [None] * RWayTrieST.R

        def is_leaf_node(self):
            for i in range(RWayTrieST.R):
                if self.next[i] is not None:
                    return False
            return True

    def __init__(self):
        super(RWayTrieST, self).__init__()
        self.root = self.Node()

    @staticmethod
    def __char_at(s, d):
        if len(s) <= d:
            return -1
        return ord(s[d])

    def put(self, key, value):

        def put_i(node, d=0):
            if node is None:
                node = self.Node()

            if d == len(key):
                node.value = value
            else:
                node.next[self.__char_at(key, d)] = \
                    put_i(node.next[self.__char_at(key, d)], d + 1)

            return node

        put_i(self.root)

    def get(self, key):

        def get_i(node, d=0):
            if node is None:
                return None

            if d == len(key):
                return node.value
            return get_i(node.next[self.__char_at(key, d)], d + 1)

        return get_i(self.root)

    def contains(self, key):
        return self.get(key) is not None

    def delete(self, key):

        def delete_i(node, d):
            if node is None:
                return None, None

            if d == len(key):
                val = node.value
                if node.value is not None:
                    node.value = None
            else:
                node.next[self.__char_at(key, d)], val = \
                    delete_i(node.next[self.__char_at(key, d)], d + 1)

            if node.is_leaf_node() and node.value is not None:
                return None, val
            return node, val

        root, value = delete_i(self.root, 0)
        return key, value

    def __keys_i(self, node, prefix=''):
        if node is not None:
            if node.value is not None:
                yield prefix
            for i in range(self.R):
                for k in self.__keys_i(node.next[i], prefix + chr(i)):
                    yield k

    def keys(self):
        for key in self.__keys_i(self.root):
            yield key

    def keys_with_prefix(self, s):
        node = self.root
        d = 0
        while node is not None and d < len(s):
            node = node.next[self.__char_at(s, d)]
            d += 1
        for key in self.__keys_i(node, s):
            yield key

    def keys_that_match(self, s):

        def keys_i(node, prefix='', d=0):
            if node is not None:

                if d == len(s):
                    if node.value is not None:
                        yield prefix
                else:
                    if s[d] == '.':
                        for i in range(self.R):
                            for k in keys_i(node.next[i],
                                            prefix + chr(i), d + 1):
                                yield k
                    else:
                        for k in keys_i(node.next[self.__char_at(s, d)],
                                        prefix + s[d], d + 1):
                            yield k

        for key in keys_i(self.root):
            yield key

    def longest_prefix_of(self, s):

        def lcp(node, prefix='', lp='', d=0):
            if node is None:
                return lp

            if node.value is not None:
                lp = prefix

            if len(prefix) == len(s):
                return lp

            prefix += s[d]
            return lcp(node.next[self.__char_at(s, d)], prefix, lp, d + 1)

        return lcp(self.root)

    def floor_key(self, key):

        def floor_key_i(node, prefix='', d=0):
            if node is None:
                return None

            if d == len(key):
                if node.value is None:
                    return None
                return prefix

            f_key = floor_key_i(node.next[self.__char_at(key, d)],
                                prefix + key[d], d + 1)
            if f_key is not None:
                return f_key
            for i in range(self.__char_at(key, d) - 1, -1, -1):
                for f_key in self.__keys_i(node.next[i], prefix + chr(i)):
                    if f_key is not None:
                        return f_key
            return None

        return floor_key_i(self.root)

    def ceil_key(self, key):

        def ceil_key_i(node, prefix='', d=0):
            if node is None:
                return None

            c_key = None
            if d >= len(key):
                if node.value is not None:
                    return prefix
            else:
                c_key = ceil_key_i(node.next[self.__char_at(key, d)],
                                   prefix + key[d], d + 1)

            if c_key is not None:
                return c_key
            for i in range(self.__char_at(key, d) + 1, self.R):
                for c_key in self.__keys_i(node.next[i], prefix + chr(i)):
                    if c_key is not None:
                        return c_key
            return None

        return ceil_key_i(self.root)


def test_r_way_trie_st():

    st = RWayTrieST()
    st.put('she', 0)
    st.put('sells', 1)
    st.put('sea', 2)
    st.put('shells', 3)
    st.put('by', 4)
    st.put('the', 5)
    st.put('she', 6)
    st.put('shore', 7)

    st.delete('sea')
    print()
    for key in st.keys():
        print(f'get {key}: {st.get(key)}')

    print()
    print(f'Keys: {list(st.keys())}')
    print(f'Keys with prefix "sh": {list(st.keys_with_prefix("sh"))}')
    print(f'Keys that match ".ell.": {list(st.keys_that_match(".ell."))}')

    print()
    print(f'Longest prefix of "there": {st.longest_prefix_of("there")}')
    print(f'floor of "hello": {st.floor_key("hello")}')
    print(f'ceil of "hello": {st.ceil_key("hello")}')
    print(f'floor of "she": {st.floor_key("she")}')
    print(f'ceil of "she": {st.ceil_key("she")}')
    print(f'floor of "baby": {st.floor_key("baby")}')
    print(f'ceil of "baby": {st.ceil_key("baby")}')
    print(f'floor of "thus": {st.floor_key("thus")}')
    print(f'ceil of "thus": {st.ceil_key("thus")}')
    print(f'floor of "shoe": {st.floor_key("shoe")}')
    print(f'ceil of "shoe": {st.ceil_key("shoe")}')


if __name__ == '__main__':
    test_r_way_trie_st()
