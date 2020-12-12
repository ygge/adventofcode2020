import util.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day5 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        Set<Integer> seen = new HashSet<>();
        for (String d : input) {
            int seatId = seatId(d);
            seen.add(seatId);
        }
        for (int i = 0; ; ++i) {
            if (!seen.contains(i) && seen.contains(i - 1) && seen.contains(i + 1)) {
                return i;
            }
        }
    }

    private static int seatId(String d) {
        int row = row(d);
        int seat = seat(d);
        return row * 8 + seat;
    }

    private static int part1(List<String> input) {
        int res = 0;
        for (String d : input) {
            res = Math.max(res, seatId(d));
        }
        return res;
    }

    private static int seat(String d) {
        int seat = 0;
        int b = 4;
        for (int i = 7; i < 10; ++i) {
            seat += d.charAt(i) == 'R' ? b : 0;
            b /= 2;
        }
        return seat;
    }

    private static int row(String d) {
        int row = 0;
        int b = 64;
        for (int i = 0; i < 7; ++i) {
            row += d.charAt(i) == 'B' ? b : 0;
            b /= 2;
        }
        return row;
    }
}
