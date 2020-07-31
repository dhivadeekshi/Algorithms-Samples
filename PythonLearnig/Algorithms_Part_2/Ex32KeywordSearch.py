import math


class KeyWordSearch:

    def __init__(self, string):
        self.suffixes = []
        for i in range(len(string)):
            self.suffixes.append(string[i:])
        self.suffixes.sort()

    def find(self, pattern):

        def get_index(s, lo, hi):
            if hi <= lo:
                return -1
            mid = lo + int(math.floor((hi - lo) / 2))
            check = self.suffixes[mid][:len(pattern)]

            if check < s:
                return get_index(s, mid + 1, hi)
            elif s < check:
                return get_index(s, lo, mid)
            else:
                while mid > 0 and \
                        self.suffixes[mid - 1][:len(pattern)] == pattern:
                    mid -= 1
                return mid

        indices = []
        index = get_index(pattern, 0, len(self.suffixes) - 1)
        if index != -1:
            while index < len(self.suffixes) and \
                    self.suffixes[index][:len(pattern)] == pattern:
                indices.append(len(self.suffixes) - len(self.suffixes[index]))
                index += 1

        indices.sort()
        return indices


def test_key_word_search():

    with open('tale.txt') as f:
        text = f.read()

    kws = KeyWordSearch(text)

    def search(pattern):
        print()
        print('-'*50)
        print(f'Search for "{pattern}"')

        indices = kws.find(pattern)

        if len(indices) == 0:
            print(f'"{pattern}" was not found')
        else:
            print(f'"{pattern}" was found {len(indices)} times in indices: {indices}')
            builder = text
            for i in range(len(indices) - 1, -1, -1):
                builder = builder[:indices[i]] + '*' + builder[indices[i]:]
            print(builder)

    search('was')
    search('age')
    search('time')
    search('dark')
    search('ness')


if __name__ == '__main__':
    test_key_word_search()
