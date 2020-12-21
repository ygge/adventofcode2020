import util.Util;

import java.util.*;

public class Day21 {

    public static void main(String[] args) {
        Util.verifySubmission();
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static String part2(List<String> input) {
        Map<String, Set<String>> mapping = new HashMap<>();
        for (String row : input) {
            int index = row.indexOf('(');
            var ingredients = new HashSet<>(Arrays.asList(row.substring(0, index - 1).split(" ")));
            var allergens = row.substring(index+10, row.length()-1).split(", ");
            for (String allergen : allergens) {
                if (mapping.containsKey(allergen)) {
                    mapping.get(allergen).retainAll(ingredients);
                } else {
                    mapping.put(allergen, new HashSet<>(ingredients));
                }
            }
        }
        System.out.println(mapping);
        Map<String, String> finalMapping = new HashMap<>();
        while (!mapping.isEmpty()) {
            String allergen = null;
            for (Map.Entry<String, Set<String>> entry : mapping.entrySet()) {
                if (entry.getValue().size() == 1) {
                    allergen = entry.getKey();
                    String ingredient = new ArrayList<>(entry.getValue()).get(0);
                    finalMapping.put(ingredient, entry.getKey());
                    for (Set<String> value : mapping.values()) {
                        value.remove(ingredient);
                    }
                    break;
                }
            }
            if (allergen == null) {
                throw new RuntimeException();
            }
            mapping.remove(allergen);
        }
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : finalMapping.entrySet()) {
            list.add(entry.getValue() + ";" + entry.getKey());
        }
        Collections.sort(list);
        StringBuilder ans = new StringBuilder();
        for (String s : list) {
            if (ans.length() > 0) {
                ans.append(",");
            }
            int index = s.indexOf(';');
            ans.append(s.substring(index + 1));
        }
        return ans.toString();
    }

    private static int part1(List<String> input) {
        Map<String, Set<String>> mapping = new HashMap<>();
        Map<String, Integer> ingredientsCount = new HashMap<>();
        for (String row : input) {
            int index = row.indexOf('(');
            var ingredients = new HashSet<>(Arrays.asList(row.substring(0, index - 1).split(" ")));
            var allergens = row.substring(index+10, row.length()-1).split(", ");
            for (String allergen : allergens) {
                if (mapping.containsKey(allergen)) {
                    mapping.get(allergen).retainAll(ingredients);
                } else {
                    mapping.put(allergen, new HashSet<>(ingredients));
                }
            }
            for (String ingredient : ingredients) {
                var count = ingredientsCount.get(ingredient);
                ingredientsCount.put(ingredient, (count == null ? 0 : count)+1);
            }
        }
        var sum = 0;
        for (Map.Entry<String, Integer> entry : ingredientsCount.entrySet()) {
            boolean found = false;
            for (Set<String> value : mapping.values()) {
                if (value.contains(entry.getKey())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                sum += entry.getValue();
            }
        }
        return sum;
    }
}
