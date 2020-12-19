import util.Util;

import java.util.*;

public class Day19 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        Map<Integer, Rule> rules = new HashMap<>();
        int index = 0;
        for (; index < input.size(); ++index) {
            var row = input.get(index);
            if (row.length() == 0) {
                break;
            }
            var s = row.split(": ");
            int p = Integer.parseInt(s[0]);
            if (p == 8) {
                rules.put(8, new Rule("42 | 42 8"));
            } else if (p == 11) {
                rules.put(11, new Rule("42 31 | 42 11 31"));
            } else {
                rules.put(p, new Rule(s[1]));
            }
        }
        int count = 0;
        for (; index < input.size(); ++index) {
            String str = input.get(index);
            List<Integer> matches = matches2(str, rules, 0, 0);
            for (Integer match : matches) {
                if (match == str.length()) {
                    ++count;
                    break;
                }
            }
        }
        return count;
    }

    private static List<Integer> matches2(String str, Map<Integer, Rule> rules, int ruleIndex, int strIndex) {
        Rule rule = rules.get(ruleIndex);
        if (rule.value != null) {
            if (str.substring(strIndex).startsWith(rule.value)) {
                return Collections.singletonList(strIndex+rule.value.length());
            } else {
                return Collections.emptyList();
            }
        } else {
            List<Integer> res = new ArrayList<>();
            for (List<Integer> otherRules : rule.rules) {
                applyRules(res, str, rules, otherRules, 0, strIndex);
            }
            return res;
        }
    }

    private static void applyRules(List<Integer> res, String str, Map<Integer, Rule> rules, List<Integer> otherRules, int ruleIndex, int strIndex) {
        if (ruleIndex == otherRules.size()) {
            res.add(strIndex);
            return;
        }
        List<Integer> result = matches2(str, rules, otherRules.get(ruleIndex), strIndex);
        for (Integer newStrIndex : result) {
            applyRules(res, str, rules, otherRules, ruleIndex+1, newStrIndex);
        }
    }

    private static int part1(List<String> input) {
        Map<Integer, Rule> rules = new HashMap<>();
        int index = 0;
        for (; index < input.size(); ++index) {
            var row = input.get(index);
            if (row.length() == 0) {
                break;
            }
            var s = row.split(": ");
            int p = Integer.parseInt(s[0]);
            rules.put(p, new Rule(s[1]));
        }
        int count = 0;
        for (; index < input.size(); ++index) {
            String str = input.get(index);
            var result = matches(str, rules, 0, 0);
            if (result.matches && result.index == str.length()) {
                ++count;
            }
        }
        return count;
    }

    private static Result matches(String str, Map<Integer, Rule> rules, int ruleIndex, int strIndex) {
        Rule rule = rules.get(ruleIndex);
        if (rule.value != null) {
            if (str.substring(strIndex).startsWith(rule.value)) {
                return new Result(true, strIndex+rule.value.length());
            } else {
                return new Result(false, 0);
            }
        } else {
            for (List<Integer> otherRules : rule.rules) {
                int index = strIndex;
                for (Integer otherRule : otherRules) {
                    var result = matches(str, rules, otherRule, index);
                    if (!result.matches) {
                        index = -1;
                        break;
                    }
                    index = result.index;
                }
                if (index != -1) {
                    return new Result(true, index);
                }
            }
        }
        return new Result(false, 0);
    }

    public static class Result {
        boolean matches;
        int index;

        public Result(boolean matches, int index) {
            this.matches = matches;
            this.index = index;
        }
    }

    public static class Rule {
        public String value;
        public List<List<Integer>> rules;

        public Rule(String s) {
            if (s.startsWith("\"")) {
                value = s.substring(1, s.length()-1);
            } else {
                var ss = s.split(" ");
                rules = new ArrayList<>();
                var rule = new ArrayList<Integer>();
                for (String item : ss) {
                    if (item.equals("|")) {
                        rules.add(rule);
                        rule = new ArrayList<>();
                    } else {
                        rule.add(Integer.parseInt(item));
                    }
                }
                rules.add(rule);
            }
        }
    }
}
