import util.Util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day4 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        int count = 0;
        Set<String> seen = resetSeen();
        boolean valid = true;
        for (String p : input) {
            if (p.length() == 0) {
                if (seen.isEmpty() && valid) {
                    ++count;
                }
                seen = resetSeen();
                valid = true;
            }
            var a = p.split(" ");
            for (String b : a) {
                if (b.length() == 0) {
                    continue;
                }
                String[] s = b.split(":");
                var c = s[0];
                var v = s[1];
                seen.remove(c);
                if (c.equals("byr") && !num(v, 1920, 2002)) {
                    valid = false;
                }
                if (c.equals("iyr") && !num(v, 2010, 2020)) {
                    valid = false;
                }
                if (c.equals("eyr") && !num(v, 2020, 2030)) {
                    valid = false;
                }
                if (c.equals("hgt")) {
                    var u = v.substring(v.length()-2);
                    if (u.equals("cm")) {
                        if (!num(v.substring(0, v.length() - 2), 150, 193)) {
                            valid = false;
                        }
                    } else if (u.equals("in")) {
                        if (!num(v.substring(0, v.length()-2), 59, 76)) {
                            valid = false;
                        }
                    } else {
                        valid = false;
                    }
                }
                if (c.equals("hcl")) {
                    if (v.length() != 7 || v.charAt(0) != '#' || !hex(v.substring(1))) {
                        valid = false;
                    }
                }
                if (c.equals("ecl") && !(v.equals("amb") || v.equals("blu") || v.equals("brn") || v.equals("gry")
                        || v.equals("grn") || v.equals("hzl") || v.equals("oth"))) {
                    valid = false;
                }
                if (c.equals("pid") && !(v.length() == 9 && num(v, 0, 999999999))) {
                    valid = false;
                }
            }
        }
        return count;
    }

    private static boolean hex(String v) {
        try {
            Integer.parseInt(v, 16);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean num(String v, int min, int max) {
        try {
            int n = Integer.parseInt(v);
            return n >= min && n <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int part1(List<String> input) {
        int count = 0;
        Set<String> seen = resetSeen();
        for (String p : input) {
            if (p.length() == 0) {
                if (seen.isEmpty()) {
                    ++count;
                }
                seen = resetSeen();
            }
            var a = p.split(" ");
            for (String b : a) {
                String[] s = b.split(":");
                var c = s[0];
                seen.remove(c);
            }
        }
        return count;
    }

    private static Set<String> resetSeen() {
        return new HashSet<>(Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"));
    }
}
