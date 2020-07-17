/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ex9BareBonesWebCrawler {
    private Queue<String> queue = new Queue<>();
    private SET<String> marked = new SET<>();

    public Ex9BareBonesWebCrawler(String root) {
        crawl(root);
    }

    public Iterable<String> pages() {
        return marked;
    }

    private void crawl(String root) {
        queue.enqueue(root);
        marked.add(root);
        while (!queue.isEmpty()) {
            String page = queue.dequeue();
            StdOut.println(page);
            In in;
            try {
                in = new In(page);
            }
            catch (IllegalArgumentException e) {
                continue;
            }
            String input = in.readAll();

            String regexp = "https://(\\w+\\.)*(\\w+)";
            Pattern pattern = Pattern.compile(regexp);
            Matcher matcher = pattern.matcher(input);

            while (matcher.find()) {
                String w = matcher.group();
                if (!marked.contains(w)) {
                    marked.add(w);
                    queue.enqueue(w);
                }
            }
        }
    }

    public static void main(String[] args) {
        Ex9BareBonesWebCrawler webCrawler = new Ex9BareBonesWebCrawler("https://www.princeton.edu");
    }
}
