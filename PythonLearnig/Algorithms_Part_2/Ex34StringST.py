class SymbolTable:

    """
    create a symbol table with string keys
    """
    def __init__(self):
        pass

    # put key-value pair into the symbol table
    def put(self, key, value):
        pass

    # value paired with key
    def get(self, key):
        pass

    # if symbol table contains the key-value pair
    def contains(self, key):
        pass

    # delete key and corresponding value
    def delete(self, key):
        pass

    # all keys
    def keys(self):
        pass

    # keys having s as prefix
    def keys_with_prefix(self, s):
        pass

    # keys that match s (where . is a wildcard)
    def keys_that_match(self, s):
        pass

    # longest key that is a prefix of s
    def longest_prefix_of(self, s):
        pass

    def floor_key(self, key):
        pass

    def ceil_key(self, key):
        pass
