import util.Util;

import java.util.List;

public class Day11 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        char[][] seats = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); ++i) {
            for (int j = 0; j < input.get(i).length(); ++j) {
                seats[i][j] = input.get(i).charAt(j);
            }
        }
        var change = true;
        while (change) {
            char[][] seats2 = new char[seats.length][seats[0].length];
            change = false;
            for (int i = 0; i < seats.length; ++i) {
                for (int j = 0; j < seats[i].length; ++j) {
                    if (seats[i][j] == '.') {
                        seats2[i][j] = '.';
                    } else {
                        int count = count2(seats, i, j);
                        if (seats[i][j] == 'L' && count == 0) {
                            seats2[i][j] = '#';
                            change = true;
                        } else if (seats[i][j] == '#' && count >= 5) {
                            seats2[i][j] = 'L';
                            change = true;
                        } else {
                            seats2[i][j] = seats[i][j];
                        }
                    }
                }
            }
            seats = seats2;
        }
        int count = 0;
        for (char[] seat : seats) {
            for (char c : seat) {
                if (c == '#') {
                    ++count;
                }
            }
        }
        return count;
    }

    private static int count2(char[][] seats, int i, int j) {
        int count = 0;
        for (int ii = -1; ii <= 1; ++ii) {
            for (int jj = -1; jj <= 1; ++jj) {
                if (ii == 0 && jj == 0) {
                    continue;
                }
                int d = 1;
                while (i+ii*d >= 0 && i+ii*d < seats.length
                        && j+jj*d >= 0 && j+jj*d < seats[i].length
                        && seats[i+ii*d][j+jj*d] == '.') {
                    ++d;
                }
                if (i+ii*d >= 0 && i+ii*d < seats.length
                        && j+jj*d >= 0 && j+jj*d < seats[i].length
                        && seats[i+ii*d][j+jj*d] == '#') {
                    ++count;
                }
            }
        }
        return count;
    }

    private static int part1(List<String> input) {
        char[][] seats = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); ++i) {
            for (int j = 0; j < input.get(i).length(); ++j) {
                seats[i][j] = input.get(i).charAt(j);
            }
        }
        var change = true;
        while (change) {
            char[][] seats2 = new char[seats.length][seats[0].length];
            change = false;
            for (int i = 0; i < seats.length; ++i) {
                for (int j = 0; j < seats[i].length; ++j) {
                    if (seats[i][j] == '.') {
                        seats2[i][j] = '.';
                    } else {
                        int count = count(seats, i, j);
                        if (seats[i][j] == 'L' && count == 0) {
                            seats2[i][j] = '#';
                            change = true;
                        } else if (seats[i][j] == '#' && count >= 4) {
                            seats2[i][j] = 'L';
                            change = true;
                        } else {
                            seats2[i][j] = seats[i][j];
                        }
                    }
                }
            }
            seats = seats2;
        }
        int count = 0;
        for (char[] seat : seats) {
            for (char c : seat) {
                if (c == '#') {
                    ++count;
                }
            }
        }
        return count;
    }

    private static int count(char[][] seats, int i, int j) {
        int count = 0;
        for (int ii = -1; ii <= 1; ++ii) {
            for (int jj = -1; jj <= 1; ++jj) {
                if (ii == 0 && jj == 0) {
                    continue;
                }
                if (i+ii >= 0 && i+ii < seats.length
                        && j+jj >= 0 && j+jj < seats[i].length
                        && seats[i+ii][j+jj] == '#') {
                    ++count;
                }
            }
        }
        return count;
    }
}
