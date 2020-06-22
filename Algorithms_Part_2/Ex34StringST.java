/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public interface Ex34StringST<Value> {
    // constructor                          // create a symbol table with string keys
    void put(String key, Value value);      // put key-value pair into the symbol table

    Value get(String key);                  // value paired with key

    boolean contains(String key);           // if symbol table contains the key-value pair

    void delete(String key);                // delete key and corresponding value


    Iterable<String> keys();                // all keys

    Iterable<String> keysWithPrefix(String s);  // keys having s as prefix

    Iterable<String> keysThatMatch(String s);   // keys that mathch s (where . is a wildcard)

    String longestPrefixOf(String s);       // longest key that is a prefix of s


    String floor(String key);

    String ceil(String key);
}
