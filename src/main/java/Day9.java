import java.util.List;

public class Day9 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        long v1 = part1(input);
        //Util.submitPart1(v1);
        Util.submitPart2(part2(input, v1));
    }

    private static long part2(List<String> input, long v1) {
        for (int i = 0; i < input.size(); ++i) {
            long sum = 0;
            long min = Long.MAX_VALUE;
            long max = Long.MIN_VALUE;
            for (int j = i; ; ++j) {
                long v = Long.parseLong(input.get(j));
                min = Math.min(min, v);
                max = Math.max(max, v);
                sum += v;
                if (sum == v1) {
                    return min + max;
                } else if (sum > v1) {
                    break;
                }
            }
        }
        throw new IllegalStateException();
    }

    private static long part1(List<String> input) {
        for (int i = 25; i < input.size(); ++i) {
            var v = Long.parseLong(input.get(i));
            boolean found = false;
            for (int j = i-25; j < i && !found; ++j) {
                for (int k = j+1; k < i; ++k) {
                    if (Long.parseLong(input.get(j)) + Long.parseLong(input.get(k)) == v) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                return v;
            }
        }
        throw  new IllegalStateException();
    }
}
