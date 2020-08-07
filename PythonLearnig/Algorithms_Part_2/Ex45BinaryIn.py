class BinaryIn:

    EOF = -1

    def __init__(self, file_name):
        self.i_stream = open(file_name, 'rb')
        self.buffer = self.EOF
        self.n = -1
        self.fill_buffer()

    def fill_buffer(self):
        try:
            self.buffer = bytearray(self.i_stream.read(1))[0]
            self.n = 8
        except IndexError:
            self.buffer = self.EOF
            self.n = -1

    def exists(self):
        return self.i_stream is not None

    def is_empty(self):
        return self.buffer == self.EOF

    def read_boolean(self):
        if self.is_empty():
            raise ValueError
        self.n -= 1
        bit = ((self.buffer >> self.n) & 1) == 1
        if self.n == 0:
            self.fill_buffer()
        return bit

    def read_char(self):
        if self.is_empty():
            raise ValueError

        x = self.buffer
        if self.n == 8:
            self.fill_buffer()
            return chr(x & 0xff)

        x <<= (8 - self.n)
        old_n = self.n
        self.fill_buffer()
        if self.is_empty():
            raise ValueError
        self.n = old_n
        x |= (self.buffer >> self.n)
        return chr(x & 0xff)

    def read_char_r(self, r):
        if r < 1 or r > 16:
            raise ValueError

        if r == 8:
            return self.read_char()

        x = chr(0)
        for i in range(r):
            x <<= 1
            bit = self.read_boolean()
            if bit:
                x |= 1

        return x

    def read_string(self):
        if self.is_empty():
            raise ValueError

        builder = ""
        while not self.is_empty():
            builder += self.read_char()

        return builder

    def read_short(self):
        x = 0
        for i in range(2):
            c = self.read_char()
            x <<= 8
            x |= c
        return x

    def read_int(self):
        x = 0
        for i in range(4):
            c = self.read_char()
            x <<= 8
            x |= ord(c)
        return x

    def read_int_r(self, r):
        if r < 1 or r > 32:
            raise ValueError

        if r == 32:
            return self.read_int()

        x = 0
        for i in range(r):
            x <<= 1
            bit = self.read_boolean()
            if bit:
                x |= 1

        return x

    def read_float(self):
        return float(self.read_int())

    def read_byte(self):
        c = self.read_char()
        return bytes(ord(c) & 0xff)

    def close(self):
        try:
            self.i_stream.close()
        except:
            print('Error while closing input stream')
