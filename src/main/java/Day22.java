import util.Util;

import java.util.*;

public class Day22 {

    public static void main(String[] args) {
        Util.verifySubmission();
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        List<LinkedList<Integer>> cards = new ArrayList<>();
        cards.add(new LinkedList<>());
        cards.add(new LinkedList<>());
        int index = 1;
        for (; ; ++index) {
            if (input.get(index).length() == 0) {
                break;
            }
            cards.get(0).add(Integer.parseInt(input.get(index)));
        }
        for (index += 2; index < input.size(); ++index) {
            cards.get(1).add(Integer.parseInt(input.get(index)));
        }
        playGame(cards);
        return calcScore(cards);
    }

    private static boolean playGame(List<LinkedList<Integer>> cards) {
        Set<State> seenStates = new HashSet<>();
        while (!cards.get(0).isEmpty() && !cards.get(1).isEmpty()) {
            if (!seenStates.add(new State(cards))) {
                return true;
            }
            var a = cards.get(0).poll();
            var b = cards.get(1).poll();
            if (a <= cards.get(0).size() && b <= cards.get(1).size()) {
                var newCards = new ArrayList<LinkedList<Integer>>();
                var list1 = new LinkedList<Integer>();
                for (int i = 0; i < a; ++i) {
                    list1.addLast(cards.get(0).get(i));
                }
                newCards.add(list1);
                var list2 = new LinkedList<Integer>();
                for (int i = 0; i < b; ++i) {
                    list2.addLast(cards.get(1).get(i));
                }
                newCards.add(list2);
                if (playGame(newCards)) {
                    cards.get(0).addLast(a);
                    cards.get(0).addLast(b);
                } else {
                    cards.get(1).addLast(b);
                    cards.get(1).addLast(a);
                }
            } else if (a > b) {
                cards.get(0).addLast(a);
                cards.get(0).addLast(b);
            } else {
                cards.get(1).addLast(b);
                cards.get(1).addLast(a);
            }
        }
        return cards.get(1).isEmpty();
    }

    private static int part1(List<String> input) {
        List<LinkedList<Integer>> cards = new ArrayList<>();
        cards.add(new LinkedList<>());
        cards.add(new LinkedList<>());
        int index = 1;
        for (; ; ++index) {
            if (input.get(index).length() == 0) {
                break;
            }
            cards.get(0).add(Integer.parseInt(input.get(index)));
        }
        for (index += 2; index < input.size(); ++index) {
            cards.get(1).add(Integer.parseInt(input.get(index)));
        }
        while (!cards.get(0).isEmpty() && !cards.get(1).isEmpty()) {
            var a = cards.get(0).poll();
            var b = cards.get(1).poll();
            if (a > b) {
                cards.get(0).addLast(a);
                cards.get(0).addLast(b);
            } else {
                cards.get(1).addLast(b);
                cards.get(1).addLast(a);
            }
        }
        return calcScore(cards);
    }

    private static int calcScore(List<LinkedList<Integer>> cards) {
        int score = 0;
        Deque<Integer> winner = cards.get(0).isEmpty() ? cards.get(1) : cards.get(0);
        int mul = winner.size();
        while (!winner.isEmpty()) {
            score += mul * winner.poll();
            --mul;
        }
        return score;
    }

    private static class State {
        private final List<List<Integer>> cards;

        private State(List<LinkedList<Integer>> cards) {
            this.cards = new ArrayList<>();
            for (List<Integer> card : cards) {
                this.cards.add(new ArrayList<>(card));
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return Objects.equals(cards, state.cards);
        }

        @Override
        public int hashCode() {
            return Objects.hash(cards);
        }
    }
}
