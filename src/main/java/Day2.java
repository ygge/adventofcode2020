import java.util.List;

public class Day2 {

    public static void main(String[] args) {
        var passwords = Util.readStrings();
        part1(passwords);
        part2(passwords);
    }

    private static void part1(List<String> passwords) {
        int valid = 0;
        for (var p : passwords) {
            var parts = p.split(" ");
            var nums = parts[0].split("-");
            var min = Integer.parseInt(nums[0]);
            var max = Integer.parseInt(nums[1]);
            var c = parts[1].charAt(0);
            int count = 0;
            for (int i = 0; i < parts[2].length(); ++i) {
                if (parts[2].charAt(i) == c) {
                    ++count;
                }
            }
            if (count >= min && count <= max) {
                ++valid;
            }
        }
        System.out.println(valid);
    }

    private static void part2(List<String> passwords) {
        int valid = 0;
        for (var p : passwords) {
            var parts = p.split(" ");
            var nums = parts[0].split("-");
            var min = Integer.parseInt(nums[0]);
            var max = Integer.parseInt(nums[1]);
            var c = parts[1].charAt(0);
            var vMin = parts[2].charAt(min-1) == c;
            var vMax = parts[2].charAt(max-1) == c;
            if (vMin != vMax) {
                ++valid;
            }
        }
        System.out.println(valid);
    }
}
