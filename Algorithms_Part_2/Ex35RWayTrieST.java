/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Ex35RWayTrieST<Value> implements Ex34StringST<Value> {

    private static final int R = 256;

    private static class Node {
        private Object value;
        private Node[] nodes = new Node[R];
    }

    private Node root = new Node();

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
        Node node = root;
        for (int i = 0; i < s.length(); i++)
            if (node != null) node = node.nodes[s.charAt(i)];
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
        Node node = root;
        int d = 0, longest = 0;
        while (node != null && d <= s.length()) {
            if (node.value != null) longest = d;
            if (d < s.length()) node = node.nodes[s.charAt(d)];
            d++;
        }
        return s.substring(0, longest);
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
        if (node == null) node = new Node();
        if (d == key.length()) node.value = value;
        else {
            int c = key.charAt(d);
            node.nodes[c] = put(node.nodes[c], key, value, d + 1);
        }
        return node;
    }

    private Node get(Node node, String key, int d) {
        if (node == null) return null;
        if (d == key.length()) return node;
        int c = key.charAt(d);
        return get(node.nodes[c], key, d + 1);
    }

    private Node delete(Node node, String key, int d) {
        if (node == null) return null;
        if (d == key.length()) node.value = null;
        else {
            int c = key.charAt(d);
            node.nodes[c] = delete(node.nodes[c], key, d + 1);
        }
        boolean deleteNode = true;
        for (int i = 0; i < R; i++) {
            if (node.nodes[i] != null) {
                deleteNode = false;
                break;
            }
        }
        if (deleteNode) return null;
        else return node;
    }

    private void keys(Node node, String prefix, Queue<String> queue) {
        if (node == null) return;
        if (node.value != null) queue.enqueue(prefix);
        for (int i = 0; i < R; i++)
            keys(node.nodes[i], prefix + (char) i, queue);
    }

    private String floor(Node node, String key, String prefix, int d) {
        if (node == null) return null;
        if (d == key.length()) return node.value != null ? key : null;
        int c = key.charAt(d);
        String floorKey = floor(node.nodes[c], key, prefix + (char) c, d + 1);
        while (floorKey == null && --c >= 0)
            floorKey = anyFloorKey(node.nodes[c], prefix + (char) c);
        return floorKey;
    }

    private String ceil(Node node, String key, String prefix, int d) {
        if (node == null) return null;
        if (d == key.length()) return node.value != null ? key : null;
        int c = key.charAt(d);
        String ceilKey = ceil(node.nodes[c], key, prefix + (char) c, d + 1);
        while (ceilKey == null && ++c < R)
            ceilKey = anyCeilKey(node.nodes[c], prefix + (char) c);
        return ceilKey;
    }

    private String anyFloorKey(Node node, String prefix) {
        if (node == null) return null;
        if (node.value != null) return prefix;
        String key = null;
        for (int i = R - 1; i >= 0 && key == null; i--)
            key = anyFloorKey(node.nodes[i], prefix + (char) i);
        return key;
    }

    private String anyCeilKey(Node node, String prefix) {
        if (node == null) return null;
        if (node.value != null) return prefix;
        String key = null;
        for (int i = 0; i < R && key == null; i++)
            key = anyCeilKey(node.nodes[i], prefix + (char) i);
        return key;
    }

    public static void main(String[] args) {
        Ex35RWayTrieST<Integer> st = new Ex35RWayTrieST<>();
        st.put("she", 0);
        st.put("sells", 1);
        st.put("sea", 2);
        st.put("shells", 3);
        st.put("by", 4);
        st.put("the", 5);
        st.put("sea", 6);
        st.put("shore", 7);

        st.delete("sea");
        StringBuilder builder = new StringBuilder();
        builder.append("keys:");
        for (String s : st.keys()) {
            builder.append(" ").append(s);
            StdOut.println("get '" + s + "': " + st.get(s));
        }
        StdOut.println(builder.toString());

        StdOut.print("keys with prefix 'sh':");
        for (String s : st.keysWithPrefix("sh"))
            StdOut.print(" " + s);
        StdOut.println();
        StdOut.print("keys that match 'ell':");
        for (String s : st.keysThatMatch("ell"))
            StdOut.print(" " + s);
        StdOut.println();

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
