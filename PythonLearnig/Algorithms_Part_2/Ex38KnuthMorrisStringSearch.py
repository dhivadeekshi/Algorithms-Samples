def search(pattern, text):
    n = len(text)
    m = len(pattern)
    R = 256

    pfa = [[0] * R] * m
    x = 0
    for i in range(m):
        x = pfa[x][ord(pattern[i])]
        pfa[i] = pfa[x].copy()
        pfa[i][ord(pattern[i])] = i + 1

    j = 0
    for i in range(n):
        j = pfa[j][ord(text[i])]
        if j == m:
            return i - j + 1

    return -1


def test_knuth_morris_string_search():

    def test(pattern, text):
        index = search(pattern, text)
        if index != -1:
            builder = text[:index] + '*' + \
                text[index:index+len(pattern)] + '*' + \
                text[index+len(pattern):]
        else:
            builder = 'not found'
        print(f'search for "{pattern}" in "{text}": {builder}')

    test("ABRA", "ABACADABRAC")
    test("BAAAAAAAAA", "ABAAAABAAAAAAAAA")
    test("BAAAABAAAA", "ABAAAABAAAAAAAAA")
    test("BAAABAAAA", "ABAAAABAAAAAAAAA")


if __name__ == '__main__':
    test_knuth_morris_string_search()
