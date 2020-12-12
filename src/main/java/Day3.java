import util.Util;

import java.util.List;

public class Day3 {

    public static void main(String[] args) {
        var board = Util.readStrings();
        Util.submitPart1(part1(board));
        Util.submitPart2(part2(board));
    }

    private static long part2(List<String> board) {
        return (long)calc(board, 1, 1)
                * calc(board, 3, 1)
                * calc(board, 5, 1)
                * calc(board, 7, 1)
                * calc(board, 1, 2);
    }

    private static int part1(List<String> board) {
        return calc(board, 3, 1);
    }

    private static int calc(List<String> board, int dx, int dy) {
        int x = 0;
        int y = 0;
        int count = 0;
        while (y < board.size()) {
            var row = board.get(y);
            if (row.charAt(x%row.length()) == '#') {
                ++count;
            }
            x += dx;
            y += dy;
        }
        return count;
    }
}
