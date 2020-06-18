/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

/*
public BaseballElimination(String filename)                    // create a baseball division from given filename in format specified below
public              int numberOfTeams()                        // number of teams
public Iterable<String> teams()                                // all teams
public              int wins(String team)                      // number of wins for given team
public              int losses(String team)                    // number of losses for given team
public              int remaining(String team)                 // number of remaining games for given team
public              int against(String team1, String team2)    // number of remaining games between team1 and team2
public          boolean isEliminated(String team)              // is given team eliminated?
public Iterable<String> certificateOfElimination(String team)  // subset R of teams that eliminates given team; null if not eliminated
*/

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class As3BaseballElimination {

    private static final String NEWLINE = "\n";

    private final HashMap<String, As3BaseballTeam> teams;
    private final int nTeams;

    public As3BaseballElimination(String filename) {
        In in = new In(filename);
        nTeams = in.readInt();
        teams = new HashMap<>();

        for (int i = 0; i < nTeams; i++) {
            String team = in.readString();
            teams.put(team, new As3BaseballTeam(team, i, in, nTeams));
        }

        for (String team : teams()) eliminate(team);
    }

    public int numberOfTeams() {
        return nTeams;
    }

    public Iterable<String> teams() {
        return teams.keySet();
    }

    public int wins(String team) {
        return getTeam(team).wins();
    }

    public int losses(String team) {
        return getTeam(team).losses();
    }

    public int remaining(String team) {
        return getTeam(team).remaining();
    }

    public int against(String team1, String team2) {
        return getTeam(team1).against(getTeam(team2).index());
    }

    public boolean isEliminated(String team) {
        return getTeam(team).isEliminated();
    }

    public Iterable<String> certificateOfElimination(String team) {
        return getTeam(team).eliminatedBy();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(nTeams);
        for (As3BaseballTeam team : teams.values())
            builder.append(NEWLINE).append(team);
        return builder.toString();
    }

    private As3BaseballTeam getTeam(String teamName) {
        validateTeam(teamName);
        return teams.get(teamName);
    }

    private void validateTeam(String team) {
        if (team == null || team.isEmpty())
            throw new IllegalArgumentException("team name is empty");
        if (!teams.containsKey(team)) throw new IllegalArgumentException("team is not valid");
    }

    private void eliminate(String teamName) {
        As3BaseballTeam team = getTeam(teamName);

        String[] teamNames = new String[nTeams - 1];
        int[] teamsWins = new int[nTeams - 1];
        int index = 0;
        int teamProbableWins = team.wins() + team.remaining();

        // Trivial
        for (String opponentName : teams()) {
            if (teamName.equals(opponentName)) continue;
            As3BaseballTeam opponent = getTeam(opponentName);
            teamNames[index] = opponentName;
            teamsWins[index++] = opponent.wins();
            if (teamProbableWins < opponent.wins())
                team.eliminate(opponentName);
        }

        // Non trivial
        if (!team.isEliminated()) {
            int teamVertices = nTeams - 1; // teams
            int gameVertices = (teamVertices * (teamVertices - 1)) / 2; // games
            int n = teamVertices + gameVertices + 2; // s and t
            int s = 0;
            int t = n - 1;

            // Create network
            FlowNetwork fn = new FlowNetwork(n);
            double maxFlow = 0.0;
            int vertex = teamVertices;
            for (int i = 0; i < teamVertices; i++) {
                fn.addEdge(new FlowEdge(i + 1, t, teamProbableWins - teamsWins[i]));
                for (int j = i + 1; j < teamVertices; j++) {
                    vertex++;
                    int gij = against(teamNames[i], teamNames[j]);
                    maxFlow += gij;
                    fn.addEdge(new FlowEdge(0, vertex, gij));
                    fn.addEdge(new FlowEdge(vertex, i + 1, Double.POSITIVE_INFINITY));
                    fn.addEdge(new FlowEdge(vertex, j + 1, Double.POSITIVE_INFINITY));
                }
            }

            // Max flow - Min cut
            FordFulkerson ff = new FordFulkerson(fn, s, t);
            // StdOut.println(fn);
            // StdOut.println(ff);
            // StdOut.println("eliminate:" + teamName +
            //                        " value:" + ff.value() + " maxFlow:" + maxFlow);
            if (Double.compare(ff.value(), maxFlow) != 0) {
                // x is eliminated
                // StdOut.println("value is not equal to maxFlow eliminate " + teamName);
                for (int i = 1; i <= teamVertices; i++) {
                    if (ff.inCut(i))
                        team.eliminate(teamNames[i - 1]);
                }
            }
        }
    }

    public static void main(String[] args) {
        As3BaseballElimination division = new As3BaseballElimination("teams4.txt");

        StdOut.println(division);
        StdOut.print("Teams:");
        for (String team : division.teams())
            StdOut.print("  " + team);
        StdOut.println();
        StdOut.println();

        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
