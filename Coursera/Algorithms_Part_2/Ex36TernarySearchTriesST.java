/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Ex36TernarySearchTriesST<Value> implements Ex34StringST<Value> {

    private static final class Node {
        private char c;
        private Object value;
        private Node left, middle, right;

        public Node(char c) {
            this.c = c;
        }
    }

    private Node root = null;

    @Override
    public void put(String key, Value value) {
        root = put(root, key, value, 0);
    }

    @Override
    public Value get(String key) {
        Node node = get(root, key, 0);
        if (node == null) return null;
        return (Value) node.value;
    }

    @Override
    public boolean contains(String key) {
        return get(key) != null;
    }

    @Override
    public void delete(String key) {
        root = delete(root, key, 0);
    }

    @Override
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<>();
        keys(root, "", queue);
        return queue;
    }

    @Override
    public Iterable<String> keysWithPrefix(String s) {
        Queue<String> queue = new Queue<>();
        int d = 0;
        Node node = root;
        while (node != null && d < s.length()) {
            char c = s.charAt(d);
            if (c < node.c) node = node.left;
            else if (c > node.c) node = node.right;
            else {
                node = node.middle;
                d++;
            }
        }
        keys(node, s, queue);
        return queue;
    }

    @Override
    public Iterable<String> keysThatMatch(String s) {
        Queue<String> queue = new Queue<>();
        return queue;
    }

    @Override
    public String longestPrefixOf(String s) {
        int d = 0, longest = 0;
        Node node = root;
        while (node != null && d < s.length()) {
            if (node.value != null) longest = d;
            char c = s.charAt(d);
            if (c < node.c) node = node.left;
            else if (c > node.c) node = node.right;
            else {
                node = node.middle;
                d++;
            }
        }
        return s.substring(0, longest + 1);
    }

    @Override
    public String floor(String key) {
        return floor(root, key, "", 0);
    }

    @Override
    public String ceil(String key) {
        return ceil(root, key, "", 0);
    }

    private Node put(Node node, String key, Value value, int d) {
        char c = key.charAt(d);
        if (node == null) node = new Node(c);
        if (c < node.c) node.left = put(node.left, key, value, d);
        else if (c > node.c) node.right = put(node.right, key, value, d);
        else if (d == key.length() - 1) node.value = value;
        else node.middle = put(node.middle, key, value, d + 1);
        return node;
    }

    private Node get(Node node, String key, int d) {
        if (node == null) return null;
        char c = key.charAt(d);
        if (c < node.c) return get(node.left, key, d);
        else if (c > node.c) return get(node.right, key, d);
        else if (d == key.length() - 1) return node;
        else return get(node.middle, key, d + 1);
    }

    private Node delete(Node node, String key, int d) {
        if (node == null) return null;
        char c = key.charAt(d);
        if (c < node.c) node.left = delete(node.left, key, d);
        else if (c > node.c) node.right = delete(node.right, key, d);
        else if (d < key.length() - 1) node.middle = delete(node.middle, key, d + 1);
        else node.value = null;
        if (node.left == null && node.right == null && node.middle == null && node.value == null)
            return null;
        return node;
    }

    private void keys(Node node, String prefix, Queue<String> queue) {
        if (node == null) return;
        keys(node.left, prefix, queue);
        if (node.value != null) queue.enqueue(prefix + node.c);
        keys(node.middle, prefix + node.c, queue);
        keys(node.right, prefix, queue);
    }

    private String floor(Node node, String key, String prefix, int d) {
        if (node == null) return null;
        if (d == key.length()) return node.value != null ? key : null;
        char c = key.charAt(d);
        String floorKey;
        if (c < node.c) floorKey = floor(node.left, key, prefix, d);
        else if (c > node.c) floorKey = floor(node.right, key, prefix, d);
        else floorKey = floor(node.middle, key, prefix + node.c, d + 1);

        if (floorKey == null && c > node.c)
            floorKey = anyFloorKey(node.middle, prefix + node.c);
        if (floorKey == null && c >= node.c)
            floorKey = anyFloorKey(node.left, prefix);

        return floorKey;
    }

    private String anyFloorKey(Node node, String prefix) {
        if (node == null) return null;
        if (node.value != null) return prefix + node.c;
        String key = anyFloorKey(node.right, prefix);
        if (key == null) key = anyFloorKey(node.middle, prefix + node.c);
        if (key == null) key = anyFloorKey(node.left, prefix);
        return key;
    }

    private String ceil(Node node, String key, String prefix, int d) {
        if (node == null) return null;
        if (d == key.length()) return node.value != null ? key : null;
        char c = key.charAt(d);
        String ceilKey;
        if (c < node.c) ceilKey = ceil(node.left, key, prefix, d);
        else if (c > node.c) ceilKey = ceil(node.right, key, prefix, d);
        else ceilKey = ceil(node.middle, key, prefix + node.c, d + 1);

        if (ceilKey == null && c < node.c)
            ceilKey = anyCeilKey(node.middle, prefix + node.c);
        if (ceilKey == null && c <= node.c)
            ceilKey = anyCeilKey(node.right, prefix);

        return ceilKey;
    }

    private String anyCeilKey(Node node, String prefix) {
        if (node == null) return null;
        if (node.value != null) return prefix + node.c;
        String key = anyCeilKey(node.left, prefix);
        if (key == null) key = anyCeilKey(node.middle, prefix + node.c);
        if (key == null) key = anyCeilKey(node.right, prefix);
        return key;
    }

    public static void main(String[] args) {
        Ex36TernarySearchTriesST<Integer> st = new Ex36TernarySearchTriesST<>();
        st.put("she", 0);
        st.put("sells", 1);
        st.put("sea", 2);
        st.put("shells", 3);
        st.put("by", 4);
        st.put("the", 5);
        st.put("sea", 6);
        st.put("shore", 7);
        st.put("sean", 8);

        StdOut.println("get 'hello': " + st.get("hello"));
        st.delete("shells");
        StringBuilder builder = new StringBuilder();
        builder.append("keys:");
        for (String s : st.keys()) {
            builder.append(" ").append(s);
            StdOut.println("get '" + s + "': " + st.get(s));
        }
        StdOut.println(builder.toString());

        StdOut.print("keys with prefix 'se':");
        for (String s : st.keysWithPrefix("se"))
            StdOut.print(" " + s);
        StdOut.println();
        StdOut.print("keys that match 'ell':");
        for (String s : st.keysThatMatch("ell"))
            StdOut.print(" " + s);
        StdOut.println();

        StdOut.println("longest prefix of the: " + st.longestPrefixOf("the"));
        StdOut.println("longest prefix of there: " + st.longestPrefixOf("there"));
        StdOut.println("floor of 'hello': " + st.floor("hello"));
        StdOut.println("ceil of 'hello': " + st.ceil("hello"));
        StdOut.println("floor of 'she': " + st.floor("she"));
        StdOut.println("ceil of 'she': " + st.ceil("she"));
        StdOut.println("floor of 'baby': " + st.floor("baby"));
        StdOut.println("ceil of 'baby': " + st.ceil("baby"));
        StdOut.println("floor of 'thus': " + st.floor("thus"));
        StdOut.println("ceil of 'thus': " + st.ceil("thus"));
        StdOut.println("floor of 'shoe': " + st.floor("shoe"));
        StdOut.println("ceil of 'shoe': " + st.ceil("shoe"));

    }
}
