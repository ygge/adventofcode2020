import java.util.Collections;
import java.util.List;

public class Day10 {

    public static void main(String[] args) {
        var input = Util.readInts();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static long part2(List<Integer> input) {
        Collections.sort(input);
        var count = new long[input.size()];
        for (int i = 0; i < input.size(); ++i) {
            count[i] = 0;
            var v = input.get(i);
            if (v <= 3) {
                count[i] = 1;
            }
            for (int j = i-1; j >= 0 && input.get(j) >= v-3; --j) {
                count[i] += count[j];
            }
        }
        int end = input.get(input.size()-1) + 3;
        long result = 0;
        for (int i = 0; i < count.length; ++i) {
            if (input.get(i) >= end-3) {
                result += count[i];
            }
        }
        return result;
    }

    private static int part1(List<Integer> input) {
        Collections.sort(input);
        var ones = 0;
        var threes = 1;
        int current = 0;
        for (Integer v : input) {
            if (v == current+1) {
                ++ones;
            } else if (v == current+3) {
                ++threes;
            }
            current = v;
        }
        return ones*threes;
    }
}
