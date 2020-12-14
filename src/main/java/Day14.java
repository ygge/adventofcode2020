import util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static long part2(List<String> input) {
        Map<Long, Long> mem = new HashMap<>();
        String mask = "";
        for (String s : input) {
            String[] split = s.split(" = ");
            String a = split[0];
            if (a.equals("mask")) {
                mask = split[1];
            } else {
                long add = Long.parseLong(a.substring(4, a.length()-1));
                long v = Long.parseLong(split[1]);
                long vv = add;
                for (int i = 0; i < mask.length(); ++i) {
                    char c = mask.charAt(mask.length()-i-1);
                    if (c == '1') {
                        long bit = 1L<<i;
                        add |= bit;
                        vv &= ~bit;
                    } else if (c == 'X') {
                        long bit = 1L<<i;
                        add &= ~bit;
                        vv |= bit;
                    }
                }
                List<Long> addresses = new ArrayList<>();
                addresses.add(add);
                for (int i = 0; i < 36; ++i) {
                    long bit = 1L<<i;
                    if ((vv&bit) > 0) {
                        List<Long> newAdd = new ArrayList<>();
                        for (Long address : addresses) {
                            newAdd.add(address | bit);
                        }
                        addresses.addAll(newAdd);
                    }
                }
                for (Long address : addresses) {
                    mem.put(address, v);
                }
            }
        }
        long ans = 0;
        for (Long value : mem.values()) {
            ans += value;
        }
        return ans;
    }

    private static long part1(List<String> input) {
        long[] mem = new long[100000];
        String mask = "";
        for (String s : input) {
            String[] split = s.split(" = ");
            String a = split[0];
            if (a.equals("mask")) {
                mask = split[1];
            } else {
                int add = Integer.parseInt(a.substring(4, a.length()-1));
                long v = Long.parseLong(split[1]);
                for (int i = 0; i < mask.length(); ++i) {
                    char c = mask.charAt(mask.length()-i-1);
                    if (c != 'X') {
                        long vv = c-'0';
                        long bit = 1L<<i;
                        if ((v&bit) > 0 && vv == 0) {
                            v &= ~bit;
                        } else if ((v&bit) == 0 && vv == 1) {
                            v |= bit;
                        }
                    }
                }
                mem[add] = v;
            }
        }
        long ans = 0;
        for (long m : mem) {
            ans += m;
        }
        return ans;
    }
}
