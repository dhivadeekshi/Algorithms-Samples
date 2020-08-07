from Ex45BinaryIn import BinaryIn
from Ex46BinaryOut import BinaryOut
from Ex15KruskalsMST import MinPQ

R = 256


class Node:

    def __init__(self, ch, freq, left, right):
        self.ch = ch
        self.freq = freq
        self.left = left
        self.right = right

    def is_leaf_node(self):
        return self.left == self.right is None

    def compare_to(self, that):
        return self.freq - that.freq


def compress(input_name, output_name):

    freq = [0] * R
    with open(input_name) as f:
        for c in f.read():
            freq[ord(c)] += 1
    N = sum(freq)

    root = __compute_trie(freq)

    i_stream = BinaryIn(input_name)
    o_stream = BinaryOut(output_name)

    st = {}
    __write_trie__(o_stream, root, st, [])
    o_stream.write_int_32(N)

    while not i_stream.is_empty():
        ch = i_stream.read_char_r(8)
        for bit in st[ch]:
            o_stream.write_boolean(bit)

    i_stream.close()
    o_stream.close()


def expand(input_name, output_name):

    i_stream = BinaryIn(input_name)
    o_stream = BinaryOut(output_name)

    root = __read_trie__(i_stream)
    N = i_stream.read_int()

    for _ in range(N):

        node = root
        while not node.is_leaf_node():

            bit = i_stream.read_boolean()
            if bit:
                node = node.right
            else:
                node = node.left

        o_stream.write_char_r(node.ch, 8)

    i_stream.close()
    o_stream.close()


def __compute_trie(freq):

    min_pq = MinPQ()
    for i in range(R):
        if freq[i] > 0:
            min_pq.put(Node(chr(i), freq[i], None, None))

    while min_pq.size() > 1:
        left = min_pq.delete_min()
        right = min_pq.delete_min()
        min_pq.put(Node('\0', left.freq + right.freq, left, right))

    return min_pq.delete_min()


def __write_trie__(o_stream, node, st, key):
    if node is None:
        return

    if node.is_leaf_node():
        o_stream.write_boolean(True)
        o_stream.write_char_r(node.ch, 8)
        st[node.ch] = key
    else:
        o_stream.write_boolean(False)
        __write_trie__(o_stream, node.left, st, key+[0])
        __write_trie__(o_stream, node.right, st, key+[1])


def __read_trie__(i_stream):

    if i_stream.is_empty():
        return None

    bit = i_stream.read_boolean()

    if bit:
        ch = i_stream.read_char_r(8)
        node = Node(ch, 0, None, None)
    else:
        left = __read_trie__(i_stream)
        right = __read_trie__(i_stream)
        node = Node('\0', 0, left, right)

    return node


def test_huffman_compression():

    input_file_name = 'input_file.txt'
    compressed_file_name = 'compressed_file.txt'
    output_file_name = 'output_file.txt'

    compress(input_file_name, compressed_file_name)
    expand(compressed_file_name, output_file_name)

    def read_text(file_name):
        try:
            with open(file_name) as f:
                return f.read()
        except FileNotFoundError:
            return ''

    original = read_text(input_file_name)
    compressed = read_text(compressed_file_name)
    output = read_text(output_file_name)

    print()
    print(f'Original   : {original}')
    print(f'Compressed : {compressed}')
    print(f'Output     : {output}')
    print()
    print(f'Is same? {original == output}')


if __name__ == '__main__':
    test_huffman_compression()
