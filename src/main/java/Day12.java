import util.Direction;
import util.Pos;
import util.Util;

import java.util.List;

public class Day12 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private static int part2(List<String> input) {
        Pos p = new Pos(0, 0);
        Pos w = new Pos(10, -1);
        for (String s : input) {
            char d = s.charAt(0);
            int v = Integer.parseInt(s.substring(1));
            if (d == 'R') {
                if (v == 90) {
                    w = new Pos(-w.y, w.x);
                } else if (v == 180) {
                    w = new Pos(-w.x, -w.y);
                } else if (v == 270) {
                    w = new Pos(w.y, -w.x);
                }
            } else if (d == 'L') {
                if (v == 90) {
                    w = new Pos(w.y, -w.x);
                } else if (v == 180) {
                    w = new Pos(-w.x, -w.y);
                } else if (v == 270) {
                    w = new Pos(-w.y, w.x);
                }
            } else if (d == 'F') {
                for (int i = 0; i < v; ++i) {
                    p = new Pos(p.x + w.x, p.y + w.y);
                }
            } else if (d == 'N') {
                for (int i = 0; i < v; ++i) {
                    w = w.move(Direction.UP);
                }
            } else if (d == 'E') {
                for (int i = 0; i < v; ++i) {
                    w = w.move(Direction.RIGHT);
                }
            } else if (d == 'S') {
                for (int i = 0; i < v; ++i) {
                    w = w.move(Direction.DOWN);
                }
            } else if (d == 'W') {
                for (int i = 0; i < v; ++i) {
                    w = w.move(Direction.LEFT);
                }
            }
        }
        return Math.abs(p.x) + Math.abs(p.y);
    }

    private static int part1(List<String> input) {
        Pos p = new Pos(0, 0);
        Direction dir = Direction.RIGHT;
        for (String s : input) {
            char d = s.charAt(0);
            int v = Integer.parseInt(s.substring(1));
            if (d == 'R') {
                if (v == 90) {
                    dir = dir.turnRight();
                } else if (v == 180) {
                    dir = dir.reverse();
                } else if (v == 270) {
                    dir = dir.turnLeft();
                }
            } else if (d == 'L') {
                if (v == 90) {
                    dir = dir.turnLeft();
                } else if (v == 180) {
                    dir = dir.reverse();
                } else if (v == 270) {
                    dir = dir.turnRight();
                }
            } else if (d == 'F') {
                for (int i = 0; i < v; ++i) {
                    p = p.move(dir);
                }
            } else if (d == 'N') {
                for (int i = 0; i < v; ++i) {
                    p = p.move(Direction.UP);
                }
            } else if (d == 'E') {
                for (int i = 0; i < v; ++i) {
                    p = p.move(Direction.RIGHT);
                }
            } else if (d == 'S') {
                for (int i = 0; i < v; ++i) {
                    p = p.move(Direction.DOWN);
                }
            } else if (d == 'W') {
                for (int i = 0; i < v; ++i) {
                    p = p.move(Direction.LEFT);
                }
            }
        }
        return Math.abs(p.x) + Math.abs(p.y);
    }
}
