def search(pattern, text):

    n = len(text)
    m = len(pattern)

    for i in range(n - m + 1):
        for j in range(m):
            if text[i + j] != pattern[j]:
                break
            if j == m - 1:
                return i

    return -1


def test_brute_force_string_search():

    def test(pattern, text):
        index = search(pattern, text)
        if index != -1:
            builder = text[:index] + '*' + \
                      text[index:index + len(pattern)] + '*' + \
                      text[index + len(pattern):]
        else:
            builder = 'not found'
        print(f'search for "{pattern}" in "{text}": {builder}')

    test("ABRA", "ABACADABRAC")
    test("BAAAAAAAAA", "ABAAAABAAAAAAAAA")
    test("BAAAABAAAA", "ABAAAABAAAAAAAAA")
    test("BAAABAAAA", "ABAAAABAAAAAAAAA")


if __name__ == '__main__':
    test_brute_force_string_search()
