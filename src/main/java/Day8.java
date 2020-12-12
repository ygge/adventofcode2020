import util.Util;

import java.util.HashSet;
import java.util.List;

public class Day8 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        for (int i = 0; i < input.size(); ++i) {
            var str = input.get(i);
            if (str.startsWith("nop")) {
                input.set(i, "jmp" + str.substring(3));
                var ans = runProgram(input);
                if (ans.terminated) {
                    return ans.acc;
                }
                input.set(i, str);
            } else if (str.startsWith("jmp")) {
                input.set(i, "nop" + str.substring(3));
                var ans = runProgram(input);
                if (ans.terminated) {
                    return ans.acc;
                }
                input.set(i, str);
            }
        }
        throw new IllegalStateException();
    }

    private static int part1(List<String> input) {
        return runProgram(input).acc;
    }

    private static Result runProgram(List<String> input) {
        var seen = new HashSet<Integer>();
        int acc = 0;
        for (int i = 0;;) {
            if (i == input.size()) {
                return new Result(true, acc);
            }
            if (!seen.add(i)) {
                return new Result(false, acc);
            }
            var split = input.get(i).split(" ");
            var op = split[0];
            var arg = Integer.parseInt(split[1]);
            switch (op) {
                case "acc" -> {
                    acc += arg;
                    ++i;
                }
                case "jmp" -> i += arg;
                case "nop" -> ++i;
                default -> throw new IllegalStateException(input.get(i));
            }
        }
    }

    private static class Result {
        boolean terminated;
        int acc;

        public Result(boolean terminated, int acc) {
            this.terminated = terminated;
            this.acc = acc;
        }
    }
}
