from Ex45BinaryIn import BinaryIn
from Ex46BinaryOut import BinaryOut
from Ex36TernarySearchTrieST import TernarySearchTrieST

R = 256
L = 4096
W = 12


def compress(input_name, output_name):

    i_stream = open(input_name)
    o_stream = BinaryOut(output_name)

    table = TernarySearchTrieST()
    for i in range(R):
        table.put(chr(i), i)
    count = R + 1

    text = i_stream.read()
    while len(text) > 0:

        key = table.longest_prefix_of(text)
        o_stream.write_bits_r(table.get(key), W)
        text = text[len(key):]
        if count < L and len(text) > 0:
            table.put(key + text[0], count)
            count += 1

    o_stream.write_bits_r(R, W)
    i_stream.close()
    o_stream.close()


def expand(input_file, output_file):

    i_stream = BinaryIn(input_file)
    o_stream = open(output_file, 'w')

    table = [''] * L
    for i in range(R):
        table[i] = chr(i)
    count = R + 1

    prev = ""
    while not i_stream.is_empty():
        key = i_stream.read_int_r(W)
        if key == R:
            break
        string = table[key]
        if string is None or string == '':
            string = prev + prev[0]
        o_stream.write(string)
        if len(prev) > 0 and count < L:
            table[count] = prev + string[0]
            count += 1
        prev = table[key]

    i_stream.close()
    o_stream.close()


def test_lzw_compression():

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
    print(f'Is Same? {original == output}')


if __name__ == '__main__':
    test_lzw_compression()
