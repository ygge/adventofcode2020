import util.MultiDimPos;
import util.Util;

import java.util.*;

public class Day17 {

    public static void main(String[] args) {
        var input = Util.readBoard('#', '.');
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(boolean[][] input) {
        Map<MultiDimPos, Boolean> states = new HashMap<>();
        for (int i = 0; i < input.length; ++i) {
            for (int j = 0; j < input[i].length; ++j) {
                states.put(new MultiDimPos(i, j, 0, 0), input[i][j]);
            }
        }
        return calc(states);
    }

    private static int part1(boolean[][] input) {
        Map<MultiDimPos, Boolean> states = new HashMap<>();
        for (int i = 0; i < input.length; ++i) {
            for (int j = 0; j < input[i].length; ++j) {
                states.put(new MultiDimPos(i, j, 0), input[i][j]);
            }
        }
        return calc(states);
    }

    private static int calc(Map<MultiDimPos, Boolean> states) {
        for (int i = 0; i < 6; ++i) {
            Map<MultiDimPos, Boolean> newStates = new HashMap<>();
            for (MultiDimPos pos : states.keySet()) {
                if (!states.get(pos)) {
                    continue;
                }
                for (MultiDimPos neighbour : pos.neighbours()) {
                    if (!newStates.containsKey(neighbour)) {
                        boolean active = calc(states, neighbour);
                        newStates.put(neighbour, active);
                    }
                }
            }
            states = newStates;
        }
        int sum = 0;
        for (Boolean value : states.values()) {
            if (value) {
                ++sum;
            }
        }
        return sum;
    }

    private static boolean calc(Map<MultiDimPos, Boolean> states, MultiDimPos pos) {
        int count = count(states, pos);
        if ((states.containsKey(pos) && states.get(pos)) && (count == 2 || count == 3)) {
            return true;
        } else if ((!states.containsKey(pos) || !states.get(pos)) && (count == 3)) {
            return true;
        }
        return false;
    }

    private static int count(Map<MultiDimPos, Boolean> states, MultiDimPos pos) {
        int count = 0;
        for (MultiDimPos neighbour : pos.neighbours()) {
            if (states.containsKey(neighbour) && states.get(neighbour)) {
                ++count;
            }
        }
        return count;
    }
}
