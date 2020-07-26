class SeparateChainingHashST:

    M = 10

    class Node:

        def __init__(self, key, value, node=None):
            self.key = key
            self.value = value
            self.node = node

        def __str__(self):
            def str_i(node):
                if node is None:
                    return None
                return node.key
            return f'Key: {self.key} Value: {self.value} next: {str_i(self.node)}'

    def __init__(self):
        self.data = [None] * self.M
        self.sz = 0

    def put(self, key, value):

        def put_i(node):
            if node is None:
                node = self.Node(key, value)
                self.sz += 1
            elif node.key < key:
                node.node = put_i(node.node)
            elif key < node.key:
                node = self.Node(key, value, node)
                self.sz += 1
            else:
                node.value = value
            return node

        i = self.hash(key)
        self.data[i] = put_i(self.data[i])

    def get(self, key):

        def get_i(node):
            if node is None:
                return None

            if node.key is key:
                return node.value
            return get_i(node.node)

        return get_i(self.data[self.hash(key)])

    def size(self):
        return self.sz

    def contains(self, key):
        return self.get(key) is not None

    def is_empty(self):
        return self.size() == 0

    def keys(self):
        for node in self.data:
            while node is not None:
                yield node.key
                node = node.node

    def hash(self, key):
        return (hash(key) & 0x7fffffff) % self.M

    def delete(self, key):

        def delete_i(node):
            if node is None:
                return None, None

            if node.key is key:
                self.sz -= 1
                return node.node, node.value
            node.node, val = delete_i(node.node)
            return node, val

        if self.is_empty():
            return None, None
        i = self.hash(key)
        self.data[i], value = delete_i(self.data[i])
        return key, value

    def __str__(self):

        def str_i(node):
            if node is None:
                return ''
            builder = ''
            while node is not None:
                builder += '\t' + str(node) + '\n'
                node = node.node
            return builder

        result = '\n'
        for i in range(self.M):
            result += f'Index {i}:\n'
            result += str_i(self.data[i])
        return result


def test_separate_chaining_hash_st():

    def test(keys, test_keys):
        import random

        st = SeparateChainingHashST()
        index = 1
        for key in keys:
            st.put(key, index)
            print(f'Insert {key} = {index} : {list(st.keys())} size: {st.size()}')
            index += 1

        print('='*10, st, '='*10)
        print(f'Key,Value: {[(key, st.get(key)) for key in st.keys()]}')
        print(f"Size: {st.size()}")
        print("Keys:", list(st.keys()))
        for key in test_keys:
            print(f'Contains {key}? {st.contains(key)} Get {key}: {st.get(key)}')
            st.put(key, random.randint(500, 600))
        for key in test_keys:
            print(f"After deleting key {key}: {st.delete(key)} keys: {list(st.keys())}")

        print('='*10, st, '='*10)
        print('Size:', st.size())
        print()
        print()

    print("Test case 1:")
    test_case = [54, 5, -9, 53, 1, 0, 15]
    test(test_case, [0, 20, 10, 15])

    print("Test case 2:")
    test_case = ['S', 'O', 'R', 'T', 'E', 'X', 'A', 'M', 'P', 'L', 'E']
    test(test_case, ['B', 'P', 'G', 'P'])


if __name__ == '__main__':
    test_separate_chaining_hash_st()
