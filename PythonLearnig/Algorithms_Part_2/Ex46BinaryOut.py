class BinaryOut:

    def __init__(self, file_name):
        self.o_stream = open(file_name, 'wb')
        self.buffer = 0
        self.n = 0

    def __write_bit(self, x):
        self.buffer <<= 1
        if x:
            self.buffer |= 1

        self.n += 1
        if self.n == 8:
            self.__clear_buffer()

    def __write_byte(self, x):
        if x < 0 or x >= 256:
            raise ValueError

        if self.n == 0:
            try:
                self.o_stream.write(bytearray([x]))
            except BufferError:
                print("Buffer error while writing to the output file")
            return

        for i in range(8):
            bit = ((x >> (8 - i - 1)) & 1) == 1
            self.__write_bit(bit)

    def __clear_buffer(self):
        if self.n == 0:
            return
        if self.n > 0:
            self.buffer <<= (8 - self.n)
        try:
            self.o_stream.write(bytearray([self.buffer]))
        except:
            print('Error while clearing buffer')
        self.n = 0
        self.buffer = 0

    def flush(self):
        self.__clear_buffer()
        try:
            self.o_stream.flush()
        except:
            print('Error while flushing')

    def close(self):
        self.flush()
        try:
            self.o_stream.close()
        except:
            print('Error while closing the output stream')

    def write_boolean(self, x):
        self.__write_bit(int(x))

    def write_byte(self, x):
        self.__write_byte(x & 0xff)

    def write_int_32(self, x):
        self.__write_byte((x >> 24) & 0xff)
        self.__write_byte((x >> 16) & 0xff)
        self.__write_byte((x >>  8) & 0xff)
        self.__write_byte((x >>  0) & 0xff)

    def write_bits_r(self, x, r):
        if r == 32:
            self.write_int_32(x)
        if r < 1 or r > 32:
            raise ValueError
        if x >= (1 << r):
            raise ValueError

        for i in range(r):
            bit = ((x >> (r - i - 1)) & 1) == 1
            self.write_boolean(bit)

    def write_float(self, x):
        self.write_int_32(int(x))

    def write_short(self, x):
        self.__write_byte((x >>  8) & 0xff)
        self.__write_byte((x >>  0) & 0xff)

    def write_char(self, x):
        if ord(x) < 0 or ord(x) >= 256:
            raise ValueError
        self.__write_byte(ord(x))

    def write_char_r(self, x, r):
        if r == 8:
            self.write_char(x)
            return

        if ord(x) < 1 or ord(x) > 16:
            raise ValueError
        if ord(x) >= (1 << r):
            raise ValueError
        for i in range(r):
            bit = ((ord(x) >> (r - i - 1)) & 1) == 1
            self.write_boolean(bit)

    def write_string(self, s):
        for c in s:
            self.write_char(c)

    def write_string_r(self, s, r):
        for c in s:
            self.write_char_r(c, r)

