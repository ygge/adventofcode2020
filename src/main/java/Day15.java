import util.Util;

import java.util.HashMap;
import java.util.Map;

public class Day15 {

    public static void main(String[] args) {
        var input = Util.readString();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static long part2(String input) {
        return solve(input, 30000000);
    }

    private static int part1(String input) {
        return solve(input, 2020);
    }

    private static int solve(String input, int limit) {
        Map<Integer, Integer> seen = new HashMap<>();
        var split = input.split(",");
        int latest = 0;
        for (int i = 0; i < split.length; ++i) {
            if (i > 0) {
                seen.put(latest, i);
            }
            latest = Integer.parseInt(split[i]);
        }
        for (int j = split.length; j < limit; ++j) {
            int next = seen.containsKey(latest) ? j - seen.get(latest) : 0;
            seen.put(latest, j);
            latest = next;
        }
        return latest;
    }
}
