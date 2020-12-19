import util.Util;

import java.util.ArrayList;
import java.util.List;

public class Day18 {

    public static void main(String[] args) {
        Util.verifySubmission();
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static long part2(List<String> input) {
        long sum = 0;
        for (String row : input) {
            var terms = row
                    .replaceAll("\\(", "( ")
                    .replaceAll("\\)", " )")
                    .split(" ");
            sum += calc2(terms, 0).sum;
        }
        return sum;
    }

    private static long part1(List<String> input) {
        long sum = 0;
        for (String row : input) {
            var terms = row
                    .replaceAll("\\(", "( ")
                    .replaceAll("\\)", " )")
                    .split(" ");
            sum += calc(terms, 0).sum;
        }
        return sum;
    }

    private static Result calc2(String[] terms, int index) {
        long sum = 0;
        List<Long> toMul = new ArrayList<>();
        boolean addition = true;
        for (int i = index; i < terms.length; ++i) {
            var term = terms[i];
            if (term.equals("+")) {
                addition = true;
            } else if (term.equals("*")) {
                addition = false;
            } else if (term.equals("(")) {
                var result = calc2(terms, i+1);
                if (addition) {
                    sum += result.sum;
                } else {
                    toMul.add(sum);
                    sum = result.sum;
                }
                i = result.index;
            } else if (term.equals(")")) {
                for (Long add : toMul) {
                    sum *= add;
                }
                return new Result(sum, i);
            } else {
                if (addition) {
                    sum += Integer.parseInt(term);
                } else {
                    toMul.add(sum);
                    sum = Integer.parseInt(term);
                }
            }
        }
        for (Long add : toMul) {
            sum *= add;
        }
        return new Result(sum, terms.length);
    }

    private static Result calc(String[] terms, int index) {
        long sum = 0;
        boolean addition = true;
        for (int i = index; i < terms.length; ++i) {
            var term = terms[i];
            if (term.equals("+")) {
                addition = true;
            } else if (term.equals("*")) {
                addition = false;
            } else if (term.equals("(")) {
                var result = calc(terms, i+1);
                if (addition) {
                    sum += result.sum;
                } else {
                    sum *= result.sum;
                }
                i = result.index;
            } else if (term.equals(")")) {
                return new Result(sum, i);
            } else {
                if (addition) {
                    sum += Integer.parseInt(term);
                } else {
                    sum *= Integer.parseInt(term);
                }
            }
        }
        return new Result(sum, terms.length);
    }

    public static class Result {
        public long sum;
        public int index;

        public Result(long sum, int index) {
            this.sum = sum;
            this.index = index;
        }
    }
}
