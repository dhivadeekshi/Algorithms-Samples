def search(pattern, text):
    R = 256
    bad_match_table = [-1] * R

    n = len(text)
    m = len(pattern)

    for i in range(m):
        bad_match_table[ord(pattern[i])] = m - i

    i = m - 1
    while i < n:
        j = 0
        while pattern[m - 1 - j] == text[i - j]:
            j += 1
            if j == m:
                return i - m + 1
        i += max(1, bad_match_table[ord(pattern[m - 1 - j])])

    return -1


def test_boyer_moore_string_search():

    def test(pattern, text):
        index = search(pattern, text)
        if index != -1:
            builder = text[:index] + '*' + \
                text[index: index + len(pattern)] + '*' + \
                text[index + len(pattern):]
        else:
            builder = 'not found'
        print(f'search for "{pattern}" in "{text}": {builder}')

    test("ABRA", "ABACADABRAC")
    test("BAAAAAAAAA", "ABAAAABAAAAAAAAA")
    test("BAAAABAAAA", "ABAAAABAAAAAAAAA")
    test("BAAABAAAA", "ABAAAABAAAAAAAAA")
    test("NEEDLE", "THIS IS THE NEED TO EEDLE FIND THE NEEDLE IN A HAY STACK")


if __name__ == '__main__':
    test_boyer_moore_string_search()
