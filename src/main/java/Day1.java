import java.util.HashSet;

public class Day1 {

    public static void main(String[] args) {
        var num = new HashSet<>(Util.readInts());
        Util.submitPart1(part1(num));
        Util.submitPart2(part2(num));
    }

    private static int part1(HashSet<Integer> num) {
        for (Integer n : num) {
            if (num.contains(2020-n)) {
                return n*(2020-n);
            }
        }
        throw new IllegalStateException();
    }

    private static int part2(HashSet<Integer> num) {
        for (Integer n1 : num) {
            for (Integer n2 : num) {
                int n = n1+n2;
                if (num.contains(2020-n)) {
                    return n1*n2*(2020-n);
                }
            }
        }
        throw new IllegalStateException();
    }
}
