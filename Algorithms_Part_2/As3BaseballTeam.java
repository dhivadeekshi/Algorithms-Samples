/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class As3BaseballTeam {
    private static final String GAP = "  ";

    private final String teamName;
    private final int index;
    private final int wins;
    private final int losses;
    private final int remaining;
    private final int[] against;
    private boolean isEliminated;
    private Bag<String> eliminatedBy;

    public As3BaseballTeam(String teamName, int index, In in, int nTeams) {
        this.teamName = teamName;
        this.index = index;
        this.wins = in.readInt();
        this.losses = in.readInt();
        this.remaining = in.readInt();
        this.against = new int[nTeams];
        for (int i = 0; i < nTeams; i++)
            this.against[i] = in.readInt();
    }

    public int wins() {
        return wins;
    }

    public int losses() {
        return losses;
    }

    public int remaining() {
        return remaining;
    }

    public int against(int team) {
        return against[team];
    }

    public boolean isEliminated() {
        return isEliminated;
    }

    public void eliminate(String by) {
        this.isEliminated = true;
        if (this.eliminatedBy == null)
            eliminatedBy = new Bag<>();
        this.eliminatedBy.add(by);
    }

    public Iterable<String> eliminatedBy() {
        return eliminatedBy;
    }

    public int index() {
        return index;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(index).append(":").append(GAP);
        builder.append(teamName).append(GAP).append(GAP).append(GAP);
        builder.append(wins).append(GAP);
        builder.append(losses).append(GAP);
        builder.append(remaining).append(GAP);
        for (int i = 0; i < against.length; i++) {
            if (i == index()) builder.append("-");
            else builder.append(i).append(":").append(against(i));
            builder.append(GAP);
        }
        builder.append(isEliminated).append(GAP);
        return builder.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        for (int i = 0; i < n; i++) {
            As3BaseballTeam team = new As3BaseballTeam(in.readString(), i, in, n);
            StdOut.println(team);
        }
    }
}
