import util.Util;

import java.util.List;

public class Day25 {

    public static void main(String[] args) {
        Util.verifySubmission();
        var input = Util.readLongs();
        Util.submitPart1(part1(input));
        Util.submitPart2(0);
    }

    private static long part1(List<Long> input) {
        var a = input.get(0);
        var b = input.get(1);
        long p = 7;
        int loop = 1;
        while (p != a) {
            ++loop;
            p = (p*7)%20201227;
        }
        long res = b;
        for (int i = 1; i < loop; ++i) {
            res = (res*b)%20201227;
        }
        return res;
    }
}
