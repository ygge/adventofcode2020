import java.util.*;

public class Day7 {

    public static void main(String[] args) {
        List<String> input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        var map = new HashMap<String, List<String>>();
        for (String row : input) {
            var types = row.split("contain");
            var bag = types[0];
            bag = bag.substring(0, bag.length()-2);
            var children = types[1].split(", ");
            var b = new ArrayList<String>();
            for (String child : children) {
                var childBag = child;
                if (childBag.startsWith(" ")) {
                    childBag = childBag.substring(1);
                }
                if (childBag.charAt(childBag.length()-1) == '.') {
                    childBag = childBag.substring(0, childBag.length()-1);
                }
                if (childBag.charAt(childBag.length()-1) == 's') {
                    childBag = childBag.substring(0, childBag.length()-1);
                }
                b.add(childBag);
            }
            map.put(bag, b);
        }
        return count(map, "shiny gold bag");
    }

    private static int count(HashMap<String, List<String>> map, String current) {
        int count = 0;
        if (map.containsKey(current)) {
            for (String s : map.get(current)) {
                int space = s.indexOf(' ');
                String prefix = s.substring(0, space);
                if (!prefix.equals("no")) {
                    int num = Integer.parseInt(prefix);
                    var name = s.substring(space+1);
                    count += num*(1+count(map, name));
                }
            }
        }
        return count;
    }

    private static int part1(List<String> input) {
        var map = new HashMap<String, List<String>>();
        for (String row : input) {
            var types = row.split("contain");
            var bag = types[0];
            bag = bag.substring(0, bag.length()-2);
            var children = types[1].split(", ");
            var b = new ArrayList<String>();
            for (String child : children) {
                var space = child.indexOf(' ', 1);
                var childBag = child.substring(space+1);
                if (childBag.charAt(childBag.length()-1) == '.') {
                    childBag = childBag.substring(0, childBag.length()-1);
                }
                if (childBag.charAt(childBag.length()-1) == 's') {
                    childBag = childBag.substring(0, childBag.length()-1);
                }
                b.add(childBag);
            }
            map.put(bag, b);
        }
        int count = 0;
        for (String bag : map.keySet()) {
            if (bag.equals("shiny gold bag")) {
                continue;
            }
            if (canContain(map, bag, "shiny gold bag")) {
                ++count;
            }
        }
        return count;
    }

    private static boolean canContain(Map<String, List<String>> map, String currentBag, String goalBag) {
        if (currentBag.equals(goalBag)) {
            return true;
        }
        if (!map.containsKey(currentBag)) {
            return false;
        }
        for (String bag : map.get(currentBag)) {
            if (canContain(map, bag, goalBag)) {
                return true;
            }
        }
        return false;
    }
}
