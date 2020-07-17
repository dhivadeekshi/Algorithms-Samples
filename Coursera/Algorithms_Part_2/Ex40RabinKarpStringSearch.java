/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class Ex40RabinKarpStringSearch {

    private static final int R = 256;
    private static final int lPrime = getLongestPrime(1000000);
    // (Integer.MAX_VALUE / 4);
    private final int hash;
    private final int m;
    private final int rm;
    private final String pattern;

    public Ex40RabinKarpStringSearch(String pattern) {
        this.pattern = pattern;
        this.m = pattern.length();
        this.rm = getRM();
        hash = getHash(pattern, m);
    }

    public int search(String text) {
        int hashMatch = getHash(text, m);
        if (hashMatch == hash && pattern.equals(text.substring(m))) return 0;
        for (int i = m; i < text.length(); i++) {
            hashMatch = (hashMatch + lPrime - rm * text.charAt(i - m) % lPrime) % lPrime;
            hashMatch = (hashMatch * R + text.charAt(i)) % lPrime;
            if (hashMatch == hash && pattern.equals(text.substring(i - m + 1, i + 1)))
                return i - m + 1;
        }
        return -1;
    }

    private int getHash(String text, int n) {
        int h = 0;
        for (int i = 0; i < n; i++) {
            h = ((h * R) + text.charAt(i)) % lPrime;
        }
        return h;
    }

    private int getRM() {
        int rM = 1;
        for (int i = 1; i <= m - 1; i++)
            rM = (R * rM) % lPrime;
        return rM;
    }

    private static int getLongestPrime(int max) {
        int prime = 0;
        boolean[] marked = new boolean[max];
        for (int i = 2; i < max; i++) {
            if (marked[i]) continue;
            prime = i;
            for (int j = i; j < max; j += i) marked[j] = true;
        }
        return prime;
    }

    private static void test(String pattern, String text) {
        Ex40RabinKarpStringSearch kmSearch = new Ex40RabinKarpStringSearch(pattern);
        StringBuilder builder = new StringBuilder();
        int index = kmSearch.search(text);
        if (index > -1) {
            builder.append(text);
            builder.insert(index + pattern.length(), '*');
            builder.insert(index, '*');
        }
        else builder.append("not found");
        StdOut.println("search for " + pattern + " in " + text + ": " + builder.toString());
    }

    public static void main(String[] args) {
        test("ABRA", "ABACADABRAC");
        test("BAAAAAAAAA", "ABAAAABAAAAAAAAA");
        test("BAAAABAAAA", "ABAAAABAAAAAAAAA");
        test("BAAABAAAA", "ABAAAABAAAAAAAAA");
        test("NEEDLE",
             "THIS IS THE NEED TO NEEEDLEE N EEDLE NEDLEEE FIND THE NEEDLE IN A HAY STACK");
        test("26535", "3141592653589793");
    }
}
