import util.Pos;
import util.Util;

import java.util.*;

public class Day24 {

    public static void main(String[] args) {
        Util.verifySubmission();
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        Set<Pos> black = startBoard(input);
        for (int i = 0; i < 100; ++i) {
            System.out.println(i + " " + countBlackTiles(black));
            black = update(black);
        }
        return countBlackTiles(black);
    }

    private static Set<Pos> update(Set<Pos> black) {
        Set<Pos> newBlack = new HashSet<>();
        for (Pos pos : black) {
            int c = countBlackTiles(black, pos);
            if (c == 1 || c == 2) {
                newBlack.add(pos);
            }
            for (Pos neighbour : neighbours(pos)) {
                c = countBlackTiles(black, neighbour);
                if (c == 2) {
                    newBlack.add(neighbour);
                }
            }
        }
        return newBlack;
    }

    private static int countBlackTiles(Set<Pos> black, Pos pos) {
        int count = 0;
        for (Pos neighbour : neighbours(pos)) {
            if (black.contains(neighbour)) {
                ++count;
            }
        }
        return count;
    }

    private static List<Pos> neighbours(Pos pos) {
        return Arrays.asList(
                new Pos(pos.x-2, pos.y),
                new Pos(pos.x+2, pos.y),
                new Pos(pos.x-1, pos.y-1),
                new Pos(pos.x-1, pos.y+1),
                new Pos(pos.x+1, pos.y-1),
                new Pos(pos.x+1, pos.y+1)
        );
    }

    private static int part1(List<String> input) {
        Set<Pos> black = startBoard(input);
        return countBlackTiles(black);
    }

    private static int countBlackTiles(Set<Pos> black) {
        return black.size();
    }

    private static Set<Pos> startBoard(List<String> input) {
        Set<Pos> black = new HashSet<>();
        for (String row : input) {
            Pos pos = new Pos(0, 0);
            for (int i = 0; i < row.length(); ++i) {
                char c = row.charAt(i);
                int dx = 0;
                int dy = 0;
                if (c == 'e') {
                    dx = 2;
                } else if (c == 'w') {
                    dx = -2;
                } else if (c == 'n' || c == 's') {
                    dy = c == 'n' ? -1 : 1;
                    c = row.charAt(++i);
                    dx = c == 'e' ? 1 : -1;
                }
                pos = new Pos(pos.x+dx, pos.y+dy);
            }
            if (black.contains(pos)) {
                black.remove(pos);
            } else {
                black.add(pos);
            }
        }
        return black;
    }
}
