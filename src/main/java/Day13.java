import util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static long part2(List<String> input) {
        var times = Arrays.stream(input.get(1).split(","))
                .map(t -> t.equals("x") ? -1 : Long.parseLong(t))
                .collect(Collectors.toList());
        long start = 0;
        long mul = 0;
        for (int i = 0; i < times.size(); ++i) {
            var v = times.get(i);
            if (v == -1) {
                continue;
            }
            if (start == 0) {
                start = v;
                mul = v;
            } else {
                long b = v;
                var goal = start + i;
                while (b != goal) {
                    var a = goal / v;
                    if (a*v == goal) {
                        break;
                    }
                    b = (a + 1) * v;
                    while (b > goal) {
                        goal += mul;
                    }
                }
                var goal2 = goal+mul;
                while (b != goal2) {
                    var a = goal2 / v;
                    if (a*v == goal2) {
                        break;
                    }
                    b = (a + 1) * v;
                    while (b > goal2) {
                        goal2 += mul;
                    }
                }
                start = goal-i;
                mul = goal2 - goal;
            }
        }
        return start;
    }

    private static int part1(List<String> input) {
        int start = Integer.parseInt(input.get(0));
        var times = input.get(1).split(",");
        var best = Integer.MAX_VALUE;
        var result = 0;
        for (String time : times) {
            if (time.equals("x")) {
                continue;
            }
            var t = Integer.parseInt(time);
            if (start%t == 0) {
                best = 0;
                result = 0;
                break;
            } else {
                var d = start/t;
                var m = (d+1)*t - start;
                if (m < best) {
                    best = m;
                    result = best*t;
                }
            }
        }
        return result;
    }
}
