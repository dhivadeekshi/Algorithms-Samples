/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Ex28BouncingBalls {

    public static void main(String[] args) {
        int N = 100;
        if (args.length > 0) N = Integer.parseInt(args[0]);
        StdOut.println(N);
        Ex28Ball[] balls = new Ex28Ball[N];
        for (int i = 0; i < N; i++)
            balls[i] = new Ex28Ball();

        StdDraw.enableDoubleBuffering();
        while (true) {
            StdDraw.clear();
            for (int i = 0; i < N; i++) {
                balls[i].move(0.5);
                balls[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(50);
        }
    }
}
