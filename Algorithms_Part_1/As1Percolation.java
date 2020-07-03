/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class As1Percolation {

    private int[] sites;
    private int[] sizes;
    private boolean[] siteOpened;
    private final int gridN;
    private int nOpenedSites;

    // creates n-by-n grid, with all sites initially blocked
    public As1Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        gridN = n;
        int size = (n * n) + 2;
        sites = new int[size];
        sizes = new int[size];
        siteOpened = new boolean[size];
        nOpenedSites = 0;
        for (int i = 0; i < size; i++) {
            sites[i] = i;
            sizes[i] = 1;
            siteOpened[i] = false;
        }

        // Only for extra two - 0 and size - 1 are virtual sites at top and bottom
        siteOpened[0] = true;
        siteOpened[size - 1] = true;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > gridN || col <= 0 || col > gridN)
            throw new IllegalArgumentException();
        int i = index(row, col);
        if (siteOpened[i]) return;
        siteOpened[i] = true;
        nOpenedSites++;
        // Up
        if (row > 1) {
            int ii = index(row - 1, col);
            if (siteOpened[ii])
                union(i, ii);
        }
        else
            union(0, i);
        // Down
        if (row < gridN) {
            int ii = index(row + 1, col);
            if (siteOpened[ii])
                union(i, ii);
        }
        // Left
        if (col > 1) {
            int ii = index(row, col - 1);
            if (siteOpened[ii])
                union(i, ii);
        }
        // Right
        if (col < gridN) {
            int ii = index(row, col + 1);
            if (siteOpened[ii])
                union(i, ii);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > gridN || col <= 0 || col > gridN)
            throw new IllegalArgumentException();
        int i = index(row, col);
        return siteOpened[i];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > gridN || col <= 0 || col > gridN)
            throw new IllegalArgumentException();
        int i = index(row, col);
        return find(i) == find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return nOpenedSites;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = index(gridN, 1); i < sites.length; i++) {
            if (find(0) == find(i))
                return true;
        }
        return false;
    }

    private int index(int row, int col) {
        return ((row - 1) * gridN) + col;
    }

    private void union(int p, int q) {
        int pid = find(p);
        int qid = find(q);
        if (pid == qid) return;
        if (sizes[pid] < sizes[qid]) {
            sites[pid] = qid;
            sizes[qid] += sizes[pid];
        }
        else {
            sites[qid] = pid;
            sizes[pid] += sizes[qid];
        }
    }

    private int find(int i) {
        while (sites[i] != i) {
            sites[i] = sites[sites[i]];
            i = sites[i];
        }
        return i;
    }
}
