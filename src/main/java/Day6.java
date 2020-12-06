import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day6 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        int count = 0;
        Set<Character> seen = new HashSet<>();
        boolean first = true;
        for (String row : input) {
            if (row.length() == 0) {
                first = true;
                count += seen.size();
                seen.clear();
            } else {
                if (first) {
                    for (int i = 0; i < row.length(); ++i) {
                        seen.add(row.charAt(i));
                    }
                    first = false;
                } else {
                    Set<Character> a = new HashSet<>();
                    for (int i = 0; i < row.length(); ++i) {
                        a.add(row.charAt(i));
                    }
                    seen.removeIf(v -> !a.contains(v));
                }
            }
        }
        count += seen.size();
        return count;
    }

    private static int part1(List<String> input) {
        int count = 0;
        Set<Character> seen = new HashSet<>();
        for (String row : input) {
            if (row.length() == 0) {
                count += seen.size();
                seen.clear();
            } else {
                for (int i = 0; i < row.length(); ++i) {
                    seen.add(row.charAt(i));
                }
            }
        }
        count += seen.size();
        return count;
    }
}
