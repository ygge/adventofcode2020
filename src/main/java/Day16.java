import util.Util;

import java.util.*;

public class Day16 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static long part2(List<String> input) {
        Map<String, List<Integer>> valid = new HashMap<>();
        int i = 0;
        for (; i < input.size(); ++i) {
            String row = input.get(i);
            if (row.length() == 0) {
                break;
            }
            var split = row.split(": ");
            var key = split[0];
            var intervals = new ArrayList<Integer>();
            for (String v : split[1].split(" or ")) {
                var vv = v.split("-");
                intervals.add(Integer.parseInt(vv[0]));
                intervals.add(Integer.parseInt(vv[1]));
            }
            valid.put(key, intervals);
        }
        List<String> tickets = new ArrayList<>();
        for (i += 5; i < input.size(); ++i) {
            String row = input.get(i);
            boolean isValid = true;
            for (String s : row.split(",")) {
                var v = Integer.parseInt(s);
                isValid = false;
                for (List<Integer> intervals : valid.values()) {
                    for (int j = 0; j < intervals.size(); j += 2) {
                        if (intervals.get(j) <= v && intervals.get(j+1) >= v) {
                            isValid = true;
                            break;
                        }
                    }
                }
                if (!isValid) {
                    break;
                }
            }
            if (isValid) {
                tickets.add(row);
            }
        }

        List<List<Integer>> ticketValues = new ArrayList<>();
        for (String s : tickets) {
            List<Integer> a = new ArrayList<>();
            for (String ss : s.split(",")) {
                a.add(Integer.parseInt(ss));
            }
            ticketValues.add(a);
        }
        Map<String, Integer> mapping = new HashMap<>();
        Set<Integer> mappedIndexes = new HashSet<>();
        while (mapping.size() != ticketValues.get(0).size()) {
            for (i = 0; i < ticketValues.get(0).size(); ++i) {
                if (!mappedIndexes.contains(i)) {
                    Set<String> ok = new HashSet<>(valid.keySet());
                    for (String s : mapping.keySet()) {
                        ok.remove(s);
                    }
                    for (List<Integer> ticketValue : ticketValues) {
                        var vv = ticketValue.get(i);
                        for (Map.Entry<String, List<Integer>> entry : valid.entrySet()) {
                            if (!mapping.containsKey(entry.getKey())) {
                                boolean v = false;
                                for (int j = 0; j < entry.getValue().size(); j += 2) {
                                    if (entry.getValue().get(j) <= vv && entry.getValue().get(j+1) >= vv) {
                                        v = true;
                                        break;
                                    }
                                }
                                if (!v) {
                                    ok.remove(entry.getKey());
                                }
                            }
                        }
                    }
                    if (ok.size() == 1) {
                        var key = new ArrayList<>(ok).get(0);
                        mappedIndexes.add(i);
                        mapping.put(key, i);
                    }
                }
            }
        }
        String ticket = input.get(i + 2);
        List<Integer> myTicket = new ArrayList<>();
        for (String ss : ticket.split(",")) {
            myTicket.add(Integer.parseInt(ss));
        }
        long prod = 1;
        for (Map.Entry<String, Integer> entry : mapping.entrySet()) {
            if (entry.getKey().startsWith("departure")) {
                prod *= myTicket.get(entry.getValue());
            }
        }
        return prod;
    }

    private static int part1(List<String> input) {
        Map<String, List<Integer>> valid = new HashMap<>();
        int i = 0;
        for (; i < input.size(); ++i) {
            String row = input.get(i);
            if (row.length() == 0) {
                break;
            }
            var split = row.split(": ");
            var key = split[0];
            var intervals = new ArrayList<Integer>();
            for (String v : split[1].split(" or ")) {
                var vv = v.split("-");
                intervals.add(Integer.parseInt(vv[0]));
                intervals.add(Integer.parseInt(vv[1]));
            }
            valid.put(key, intervals);
        }
        int sum = 0;
        for (i += 5; i < input.size(); ++i) {
            String row = input.get(i);
            for (String s : row.split(",")) {
                boolean isValid;
                var v = Integer.parseInt(s);
                isValid = false;
                for (List<Integer> intervals : valid.values()) {
                    for (int j = 0; j < intervals.size(); j += 2) {
                        if (intervals.get(j) <= v && intervals.get(j+1) >= v) {
                            isValid = true;
                            break;
                        }
                    }
                    if (isValid) {
                        break;
                    }
                }
                if (!isValid) {
                    sum += v;
                }
            }
        }
        return sum;
    }
}
