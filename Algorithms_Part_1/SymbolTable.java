/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public interface SymbolTable<Key extends Comparable<Key>, Value> {
    void put(Key key,
             Value value);      // put key value pair into the table (remove key from table if value is null)

    Value get(Key key); // value paired with key (null if key is absent)

    void delete(Key key); // remove key (and its value) from table

    boolean contains(Key key); // is there a value paired with key?

    boolean isEmpty(); // is the table empty?

    int size(); // number of key-valued pairs

    Key min(); // smallest key

    Key max(); // largest key

    Key floor(Key key); // largest key less than or equal to key

    Key ceiling(Key key); // smallest key greater than or equal to key

    int rank(Key key); // number of keys less than key

    Key select(int k); // key of rank k

    void deleteMin(); // delete smallest key

    void deleteMax(); // delete largest key

    int size(Key lo, Key hi); // number of keys in [lo..hi]

    Iterable<Key> keys(Key lo, Key hi); // keys in [lo..hi], in sorted order

    Iterable<Key> keys(); // all keys in the table, in sorted order
}
