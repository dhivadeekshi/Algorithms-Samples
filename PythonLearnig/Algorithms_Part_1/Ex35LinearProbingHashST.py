class LinearProbingHashST:

    M = 100

    def __init__(self):
        self.all_keys = [None] * self.M
        self.values = [None] * self.M
        self.sz = 0

    def put(self, key, value):

        i = self.hash(key)
        while True:
            if self.all_keys[i] is None:
                self.all_keys[i] = key
                self.values[i] = value
                self.sz += 1
                break
            elif self.all_keys[i] is key:
                self.values[i] = value
                break
            i = self.next_index(i)

    def get(self, key):

        i = self.hash(key)
        while self.all_keys[i] is not None:
            if self.all_keys[i] is key:
                return self.values[i]
            i = self.next_index(i)
        return None

    def contains(self, key):
        return self.get(key) is not None

    def size(self):
        return self.sz

    def is_empty(self):
        return self.size() == 0

    def keys(self):
        for key in self.all_keys:
            if key is not None:
                yield key

    def delete(self, key):

        key_hash = self.hash(key)

        if self.all_keys[key_hash] is None:
            return None

        i = key_hash
        while self.all_keys[i] is not None and self.all_keys[i] is not key:
            i = self.next_index(i)
            if i == key_hash:
                return None

        value = self.values[i]
        next_i = self.next_index(i)
        self.all_keys[i] = None
        self.values[i] = None
        self.sz -= 1

        while self.all_keys[next_i] is not None and next_i != key_hash:

            if self.hash(self.all_keys[next_i]) == key_hash:
                self.all_keys[i] = self.all_keys[next_i]
                self.values[i] = self.values[next_i]
                self.all_keys[next_i] = None
                self.values[next_i] = None
                i = next_i

            next_i = self.next_index(i)

        return key, value

    def hash(self, key):
        return (hash(key) * 0x7fffffff) % self.M

    def next_index(self, i):
        return (i + 1) % self.M

    def __str__(self):

        result = '\n'
        for i in range(self.M):
            if self.all_keys[i] is not None:
                result += f'Key @ ({i}) : {self.all_keys[i]} Value: {self.values[i]}\n'
        return result


def test_linear_probing_hash_st():

    def test(keys, test_keys):
        import random

        st = LinearProbingHashST()
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

    print(' END '.center(100, '-'))


if __name__ == '__main__':
    test_linear_probing_hash_st()
