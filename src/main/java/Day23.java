import util.Util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day23 {

    public static void main(String[] args) {
        Util.verifySubmission();
        var input = Util.readString();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static long part2(String input) {
        Map<Integer, Node> map = new HashMap<>();
        Node current = new Node(input.charAt(0) - '0');
        Node loop = current;
        map.put(current.v, current);
        for (int i = 1; i < input.length(); ++i) {
            Node n = new Node(input.charAt(i) - '0');
            loop.next = n;
            loop = n;
            map.put(n.v, n);
        }
        for (int i = input.length()+1; i <= 1000000; ++i) {
            Node n = new Node(i);
            loop.next = n;
            loop = n;
            map.put(n.v, n);
        }
        loop.next = current;
        for (int i = 0; i < 10000000; ++i) {
            loop = current;
            Set<Integer> removed = new HashSet<>();
            for (int j = 0; j < 3; ++j) {
                loop = loop.next;
                removed.add(loop.v);
            }
            Node node = current.next;
            current.next = loop.next;

            int target = current.v - 1;
            if (target == 0) {
                target = 1000000;
            }
            while (removed.contains(target)) {
                if (--target == 0) {
                    target = 1000000;
                }
            }
            loop = map.get(target);

            Node next = loop.next;
            loop.next = node;
            for (int j = 0; j < 3; ++j) {
                loop = loop.next;
            }
            loop.next = next;
            current = current.next;
        }
        current = map.get(1);
        return (long) current.next.v * current.next.next.v;
    }

    private static String part1(String input) {
        int index = 0;
        var str = input;
        for (int i = 0; i < 100; ++i) {
            char current = str.charAt(index);
            String c;
            if (index + 4 < str.length()) {
                c = str.substring(index + 1, index + 4);
                str = str.substring(0, index + 1) + str.substring(index + 4);
            } else {
                c = str.substring(index + 1);
                int len = c.length();
                c += str.substring(0, 3 - len);
                str = str.substring(3 - len, index + 1);
            }
            int dest = -1;
            char target = (char) (current - 1);
            while (dest == -1) {
                for (int j = 0; j < str.length(); ++j) {
                    if (str.charAt(j) == target) {
                        dest = j;
                        break;
                    }
                }
                --target;
            }
            str = str.substring(0, dest + 1) + c + str.substring(dest + 1);
            while (str.charAt(index) != current) {
                char cc = str.charAt(0);
                str = str.substring(1) + cc;
            }
            if (++index == str.length()) {
                index = 0;
            }
        }
        int k = 0;
        for (; k < str.length(); ++k) {
            if (str.charAt(k) == '1') {
                break;
            }
        }
        ++k;
        var res = new StringBuilder();
        while (str.length() > 1) {
            if (k == str.length()) {
                k = 0;
            }
            res.append(str.charAt(k));
            str = str.substring(0, k) + str.substring(k + 1);
        }
        return res.toString();
    }

    private static class Node {
        int v;
        Node next;

        public Node(int v) {
            this.v = v;
        }
    }
}
