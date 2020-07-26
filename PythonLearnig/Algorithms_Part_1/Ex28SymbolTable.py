class SymbolTable:

    def __init__(self):
        pass

    # put key value pair into the table (remove key from table if value is null)
    def put(self, key, value):
        pass

    # value paired with key (null if key is absent)
    def get(self, key):
        pass

    # remove key (and its value) from table
    def delete(self, key):
        pass

    # is there a value paired with key?
    def contains(self, key):
        pass

    # is the table empty?
    def is_empty(self):
        pass

    # number of key-valued pairs
    def size(self):
        pass

    def __len__(self):
        pass

    # smallest key
    def min_key(self):
        pass

    # largest key
    def max_key(self):
        pass

    # largest key less than or equal to key
    def floor_key(self, key):
        pass

    # smallest key greater than or equal to key
    def ceil_key(self, key):
        pass

    # number of keys less than key
    def rank(self, key):
        pass

    # key of rank k
    def select(self, k):
        pass

    # delete smallest key
    def delete_min(self):
        pass

    # delete largest key
    def delete_max(self):
        pass

    # number of keys in [lo..hi]
    def size_bw(self, lo, hi):
        pass

    # keys in [lo..hi], in sorted order
    def keys_bw(self, lo, hi):
        pass

    # all keys in the table, in sorted order
    def keys(self):
        pass
