class LongestRepeatedSubstring:

    def __init__(self, string):
        self.suffixes = []
        for i in range(len(string)):
            self.suffixes.append(string[i:])
        self.suffixes.sort()

    def find_lrs(self):

        def lcp(s1, s2):
            n = min(len(s1), len(s2))
            for ii in range(n):
                if s1[ii] != s2[ii]:
                    return ii
            return n

        max_lcp = 0
        lcp_index = len(self.suffixes)
        for i in range(len(self.suffixes) - 1):
            lcp_i = lcp(self.suffixes[i], self.suffixes[i + 1])
            if lcp_i > max_lcp:
                max_lcp = lcp_i
                lcp_index = i

        indices = []
        substring = self.suffixes[lcp_index][:max_lcp - 1]
        while lcp_index < len(self.suffixes) and \
                substring == self.suffixes[lcp_index][:max_lcp - 1]:
            indices.append(len(self.suffixes) - len(self.suffixes[lcp_index]))
            lcp_index += 1

        indices.sort()
        return indices


def test_longest_repeated_substring():

    with open('tale.txt') as f:
        text = f.read()

    lrs = LongestRepeatedSubstring(text)
    indices = lrs.find_lrs()

    if len(indices) == 0:
        print('Longest repeated substring was not found')
    else:
        print(f'Longest repeated substring was found {len(indices)} times: {indices}')
        builder = text
        for i in range(len(indices) - 1, -1, -1):
            builder = builder[:indices[i]] + '*' + builder[indices[i]:]
        print(builder)


if __name__ == '__main__':
    test_longest_repeated_substring()
