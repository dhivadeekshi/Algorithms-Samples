/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class Ex28Ball {
    private double rx, ry; // position
    private double vx, vy; // velocity
    private final double radius;    // radius

    public Ex28Ball() {
        radius = 0.01;
        rx = StdRandom.uniform(radius, 1.0 - radius);
        ry = StdRandom.uniform(radius, 1.0 - radius);
        vx = StdRandom.uniform(-0.05, 0.05);
        vy = StdRandom.uniform(-0.05, 0.05);
    }

    public void move(double dt) {
        if ((rx + dt * vx < radius) || (rx + vx * dt > 1.0 - radius)) vx = -vx;
        if ((ry + dt * vy < radius) || (ry + vy * dt > 1.0 - radius)) vy = -vy;
        rx = rx + vx * dt;
        ry = ry + vy * dt;
    }

    public void draw() {
        StdDraw.filledCircle(rx, ry, radius);
    }

}
