from Ex45BinaryIn import BinaryIn
from Ex46BinaryOut import BinaryOut

C_LEN = 8
COUNT_MAX = pow(2, C_LEN)


def compress(input_name, output_name):

    i_stream = BinaryIn(input_name)
    o_stream = BinaryOut(output_name)

    def write():
        o_stream.write_bits_r(count, C_LEN)

    bit = False
    count = 0
    while not i_stream.is_empty():
        c_bit = i_stream.read_boolean()

        if bit != c_bit or count == COUNT_MAX:
            write()
            count = 0
            if bit == c_bit:
                write()

        count += 1
        bit = c_bit

    if count > 0:
        write()

    i_stream.close()
    o_stream.close()


def expand(input_name, output_name):

    i_stream = BinaryIn(input_name)
    o_stream = BinaryOut(output_name)

    bit = False
    while not i_stream.is_empty():
        count = i_stream.read_int_r(C_LEN)
        for i in range(count):
            o_stream.write_boolean(bit)

        bit = not bit

    i_stream.close()
    o_stream.close()


def test_run_length_coding_compression():

    input_file_name = "input_file.txt"
    compressed_file_name = "compressed_file.txt"
    output_file_name = "output_file.txt"

    compress(input_file_name, compressed_file_name)
    expand(compressed_file_name, output_file_name)

    def read_text(file_name):
        try:
            with open(file_name) as f:
                return f.read()
        except FileNotFoundError:
            return ""

    original = read_text(input_file_name)
    compressed = read_text(compressed_file_name)
    output = read_text(output_file_name)

    print()
    print(f"Original   : {original}")
    print(f"Compressed : {compressed}")
    print(f"Output     : {output}")
    print()
    print(f"Is Same? {original == output}")


if __name__ == '__main__':
    test_run_length_coding_compression()
