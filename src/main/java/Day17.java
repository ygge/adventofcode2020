import util.Util;

import java.util.*;

public class Day17 {

    public static void main(String[] args) {
        var input = Util.readBoard('#', '.');
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(boolean[][] input) {
        Map<Pos4, Boolean> states = new HashMap<>();
        for (int i = 0; i < input.length; ++i) {
            for (int j = 0; j < input[i].length; ++j) {
                states.put(new Pos4(i, j, 0, 0), input[i][j]);
            }
        }
        for (int i = 0; i < 6; ++i) {
            Map<Pos4, Boolean> newStates = new HashMap<>();
            for (Pos4 pos : states.keySet()) {
                if (!states.get(pos)) {
                    continue;
                }
                for (int x = -1; x <= 1; ++x) {
                    for (int y = -1; y <= 1; ++y) {
                        for (int z = -1; z <= 1; ++z) {
                            for (int a = -1; a <= 1; ++a) {
                                var newPos = new Pos4(pos.x + x, pos.y + y, pos.z + z, pos.a+a);
                                if (!newStates.containsKey(newPos)) {
                                    boolean active = calc(states, newPos);
                                    newStates.put(newPos, active);
                                }
                            }
                        }
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

    private static boolean calc(Map<Pos4, Boolean> states, Pos4 pos) {
        int count = count(states, pos);
        if ((states.containsKey(pos) && states.get(pos)) && (count == 2 || count == 3)) {
            return true;
        } else if ((!states.containsKey(pos) || !states.get(pos)) && (count == 3)) {
            return true;
        }
        return false;
    }

    private static int count(Map<Pos4, Boolean> states, Pos4 pos) {
        int count = 0;
        for (int x = -1; x <= 1; ++x) {
            for (int y = -1; y <= 1; ++y) {
                for (int z = -1; z <= 1; ++z) {
                    for (int a = -1; a <= 1; ++a) {
                        if (x == 0 && y == 0 && z == 0 && a == 0) {
                            continue;
                        }
                        var newPos = new Pos4(pos.x + x, pos.y + y, pos.z + z, pos.a+a);
                        if (states.containsKey(newPos) && states.get(newPos)) {
                            ++count;
                        }
                    }
                }
            }
        }
        return count;
    }

    private static int part1(boolean[][] input) {
        Map<Pos3, Boolean> states = new HashMap<>();
        for (int i = 0; i < input.length; ++i) {
            for (int j = 0; j < input[i].length; ++j) {
                states.put(new Pos3(i, j, 0), input[i][j]);
            }
        }
        for (int i = 0; i < 6; ++i) {
            Map<Pos3, Boolean> newStates = new HashMap<>();
            for (Pos3 pos : states.keySet()) {
                if (!states.get(pos)) {
                    continue;
                }
                for (int x = -1; x <= 1; ++x) {
                    for (int y = -1; y <= 1; ++y) {
                        for (int z = -1; z <= 1; ++z) {
                            var newPos = new Pos3(pos.x+x, pos.y+y, pos.z+z);
                            if (!newStates.containsKey(newPos)) {
                                boolean active = calc(states, newPos);
                                newStates.put(newPos, active);
                            }
                        }
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

    private static boolean calc(Map<Pos3, Boolean> states, Pos3 pos) {
        int count = count(states, pos);
        if ((states.containsKey(pos) && states.get(pos)) && (count == 2 || count == 3)) {
            return true;
        } else if ((!states.containsKey(pos) || !states.get(pos)) && (count == 3)) {
            return true;
        }
        return false;
    }

    private static int count(Map<Pos3, Boolean> states, Pos3 pos) {
        int count = 0;
        for (int x = -1; x <= 1; ++x) {
            for (int y = -1; y <= 1; ++y) {
                for (int z = -1; z <= 1; ++z) {
                    if (x == 0 && y == 0 && z == 0) {
                        continue;
                    }
                    var newPos = new Pos3(pos.x+x, pos.y+y, pos.z+z);
                    if (states.containsKey(newPos) && states.get(newPos)) {
                        ++count;
                    }
                }
            }
        }
        return count;
    }

    private static class Pos3 {
        int x, y, z;

        public Pos3(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pos3 pos3 = (Pos3) o;
            return x == pos3.x && y == pos3.y && z == pos3.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public String toString() {
            return "Pos3{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }

    private static class Pos4 {
        int x, y, z, a;

        public Pos4(int x, int y, int z, int a) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.a = a;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pos4 pos4 = (Pos4) o;
            return x == pos4.x && y == pos4.y && z == pos4.z && a == pos4.a;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z, a);
        }
    }
}
