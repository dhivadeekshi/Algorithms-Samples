def search(pattern, text):

    R = 256
    l_prime = get_longest_prime(1000000)

    n = len(text)
    m = len(pattern)

    def get_hash(string):
        h = 0
        for k in range(len(string)):
            h = ((h * R) + ord(string[k])) % l_prime
        return h

    pattern_hash = get_hash(pattern)
    hash_match = get_hash(text[:m])

    if hash_match == pattern_hash:
        return 0

    def get_RM():
        rm = 1
        for i in range(1, m):
            rm = (rm * R) % l_prime
        return rm

    RM = get_RM()
    for i in range(m, n):
        hash_match = (hash_match + l_prime - (RM * ord(text[i-m])) % l_prime) % l_prime
        hash_match = (hash_match * R + (ord(text[i]))) % l_prime
        if hash_match == pattern_hash:
            return i - m + 1

    return -1


def get_longest_prime(n):
    if n <= 2:
        return 2

    visited = [False] * n
    prime = 2

    for i in range(2, n):
        if not visited[i]:
            prime = i
            for j in range(i, n, i):
                visited[j] = True

    return prime


def test_rabin_karp_string_search():

    def test(pattern, text):
        index = search(pattern, text)
        if index != -1:
            builder = text[:index] + '*' + \
                      text[index:index + len(pattern)] + '*' + \
                      text[index + len(pattern):]
        else:
            builder = 'not found'
        print(f'search for "{pattern}" in "{text}": {builder}')

    print()
    test("ABRA", "ABACADABRAC")
    test("BAAAAAAAAA", "ABAAAABAAAAAAAAA")
    test("BAAAABAAAA", "ABAAAABAAAAAAAAA")
    test("BAAABAAAA", "ABAAAABAAAAAAAAA")
    test("NEEDLE", "THIS IS THE NEED TO NEEEDLEE N EEDLE "
                   "NEDLEEE FIND THE NEEDLE IN A HAY STACK")
    test("26535", "3141592653589793")
    test('ABCD', 'ABCDEFGH')


if __name__ == '__main__':
    test_rabin_karp_string_search()
