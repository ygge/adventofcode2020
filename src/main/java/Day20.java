import util.Util;

import java.util.*;

public class Day20 {

    public static void main(String[] args) {
        Util.verifySubmission();
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < input.size(); ++i) {
            String row = input.get(i);
            var id = Integer.parseInt(row.substring(5, row.length()-1));
            List<String> image = new ArrayList<>();
            for (++i; i < input.size(); ++i) {
                if (input.get(i).length() == 0) {
                    break;
                }
                image.add(input.get(i));
            }
            tiles.add(new Tile(id, image));
        }
        Map<Edge, Integer> seen = new HashMap<>();
        for (Tile tile : tiles) {
            for (Edge edge : tile.edges) {
                Integer v = seen.get(edge);
                seen.put(edge, (v == null ? 0 : v) + 1);
            }
        }
        Tile startTile = null;
        List<Edge> imageEdges = new ArrayList<>();
        for (Tile tile : tiles) {
            int noMatch = 0;
            imageEdges = new ArrayList<>();
            for (Edge edge : tile.edges) {
                if (seen.get(edge) == 1) {
                    ++noMatch;
                    imageEdges.add(edge);
                }
            }
            if (noMatch == 2) {
                startTile = tile;
                break;
            }
        }
        assert startTile != null;
        int s = 1;
        while (s*s != tiles.size()) {
            ++s;
        }
        var board = new Board(s);
        board.addStartTile(startTile, imageEdges);
        for (int y = 1; y < s; ++y) {
            Edge edge = board.tiles[y-1][0].bottomEdge();
            var tile = findTile(tiles, board.tiles[y-1][0].id, edge);
            if (!board.addTile(tile, 0, y)) {
                throw new RuntimeException("Tile could not be placed");
            }
        }
        for (int y = 0; y < s; ++y) {
            for (int x = 1; x < s; ++x) {
                Edge edge = board.tiles[y][x-1].rightEdge();
                var tile = findTile(tiles, board.tiles[y][x-1].id, edge);
                if (!board.addTile(tile, x, y)) {
                    throw new RuntimeException("Tile could not be placed");
                }
            }
        }
        int count = 0;
        while (!monsters(board.board)) {
            ++count;
            if (count == 4) {
                board.flip();
                count = 0;
            } else {
                board.rotate();
            }
        }
        colorMonsters(board.board);
        int ans = 0;
        for (char[] chars : board.board) {
            for (char c : chars) {
                if (c == '#') {
                    ++ans;
                }
            }
        }
        return ans;
    }

    private static void colorMonsters(char[][] board) {
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[i].length; ++j) {
                if (monster(board, i, j)) {
                    colorMonster(board, i, j);
                }
            }
        }
    }

    private static boolean monsters(char[][] board) {
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[i].length; ++j) {
                if (monster(board, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void colorMonster(char[][] board, int y, int x) {
        board[y][x+18] = 'X';
        board[y+1][x] = 'X';
        board[y+1][x+5] = 'X';
        board[y+1][x+6] = 'X';
        board[y+1][x+11] = 'X';
        board[y+1][x+12] = 'X';
        board[y+1][x+17] = 'X';
        board[y+1][x+18] = 'X';
        board[y+1][x+19] = 'X';
        board[y+2][x+1] = 'X';
        board[y+2][x+4] = 'X';
        board[y+2][x+7] = 'X';
        board[y+2][x+10] = 'X';
        board[y+2][x+13] = 'X';
        board[y+2][x+16] = 'X';
    }

    private static boolean monster(char[][] board, int y, int x) {
        if (y+2 >= board.length || x+19 >= board[y].length) {
            return false;
        }
        return board[y][x+18] == '#'
                && board[y+1][x] == '#'
                && board[y+1][x+5] == '#'
                && board[y+1][x+6] == '#'
                && board[y+1][x+11] == '#'
                && board[y+1][x+12] == '#'
                && board[y+1][x+17] == '#'
                && board[y+1][x+18] == '#'
                && board[y+1][x+19] == '#'
                && board[y+2][x+1] == '#'
                && board[y+2][x+4] == '#'
                && board[y+2][x+7] == '#'
                && board[y+2][x+10] == '#'
                && board[y+2][x+13] == '#'
                && board[y+2][x+16] == '#';
        /*
.#...#.###...#.##.O#
O.##.OO#.#.OO.##.OOO
#O.#O#.O##O..O.#O##.
         */
    }

    private static Tile findTile(List<Tile> tiles, int id, Edge edge) {
        var fEdge = new Edge(edge);
        fEdge.flip();
        for (Tile tile : tiles) {
            if (tile.id != id && (tile.hasEdge(edge) || tile.hasEdge(fEdge))) {
                return tile;
            }
        }
        throw new RuntimeException("Tile not found");
    }

    private static long part1(List<String> input) {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < input.size(); ++i) {
            String row = input.get(i);
            var id = Integer.parseInt(row.substring(5, row.length()-1));
            List<String> image = new ArrayList<>();
            for (++i; i < input.size(); ++i) {
                if (input.get(i).length() == 0) {
                    break;
                }
                image.add(input.get(i));
            }
            tiles.add(new Tile(id, image));
        }
        Map<Edge, Integer> seen = new HashMap<>();
        for (Tile tile : tiles) {
            for (Edge edge : tile.edges) {
                Integer v = seen.get(edge);
                seen.put(edge, (v == null ? 0 : v) + 1);
            }
        }
        long v = 1;
        for (Tile tile : tiles) {
            int numEdges = 0;
            for (Edge edge : tile.edges) {
                if (seen.get(edge) == 1) {
                    ++numEdges;
                }
            }
            if (numEdges == 2) {
                v *= tile.id;
            }
        }
        return v;
    }

    private static class Tile {
        int id;
        char[][] image;
        Edge[] origEdges;
        Edge[] edges;

        public Tile(int id, List<String> image) {
            this.id = id;
            this.image = new char[image.size()][image.get(0).length()];
            for (int i = 0; i < this.image.length; ++i) {
                String row = image.get(i);
                for (int j = 0; j < this.image[i].length; ++j) {
                    this.image[i][j] = row.charAt(j);
                }
            }
            this.edges = new Edge[4];
            this.origEdges = new Edge[4];
            for (int i = 0; i < 4; ++i) {
                this.edges[i] = new Edge(this.image, i);
                this.edges[i].checkFlip();
                this.origEdges[i] = new Edge(this.image, i);
            }
        }

        public void flip() {
            for (int i = 0; i < image.length; ++i) {
                for (int j = 0; j < image[i].length/2; ++j) {
                    var c = image[i][j];
                    image[i][j] = image[i][image[i].length-1-j];
                    image[i][image[i].length-1-j] = c;
                }
            }
        }

        public void rotate() {
            char[][] newImage = new char[image[0].length][image.length];
            for (int i = 0; i < image.length; ++i) {
                for (int j = 0; j < image[0].length; ++j) {
                    newImage[i][j] = image[image.length-1-j][i];
                }
            }
            image = newImage;
        }

        public boolean hasEdge(Edge edge) {
            for (Edge e : edges) {
                if (e.equals(edge)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isTop(Edge edge) {
            for (int i = 0; i < image[0].length; ++i) {
                if (edge.data[i] != (image[0][i] == '#')) {
                    return false;
                }
            }
            return true;
        }

        public boolean isLeft(Edge edge) {
            for (int i = 0; i < image.length; ++i) {
                if (edge.data[i] != (image[i][0] == '#')) {
                    return false;
                }
            }
            return true;
        }

        public Edge bottomEdge() {
            return new Edge(this.image, 2);
        }

        public Edge rightEdge() {
            return new Edge(this.image, 1);
        }
    }

    private static class Edge {
        boolean[] data;

        public Edge(char[][] image, int edge) {
            data = new boolean[image.length];
            int x = 0;
            int y = 0;
            int dx = 0;
            int dy = 0;
            if (edge == 0) {
                dx = 1;
            } else if (edge == 1) {
                x = image.length-1;
                dy = 1;
            } else if (edge == 2) {
                y = image.length-1;
                dx = 1;
            } else if (edge == 3) {
                dy = 1;
            }
            for (int i = 0; i < data.length; ++i) {
                data[i] = image[y+dy*i][x+dx*i] == '#';
            }
        }

        public Edge(Edge edge) {
            data = new boolean[edge.data.length];
            System.arraycopy(edge.data, 0, data, 0, data.length);
        }

        public void checkFlip() {
            for (int i = 0; i < data.length/2; ++i) {
                if (data[i] != data[data.length-1-i]) {
                    if (data[i] && !data[data.length-1-i]) {
                        flip();
                    }
                    break;
                }
            }
        }

        private void flip() {
            for (int i = 0; i < data.length/2; ++i) {
                boolean v = data[i];
                data[i] = data[data.length-1-i];
                data[data.length-1-i] = v;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return Arrays.equals(data, edge.data);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(data);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "data=" + Arrays.toString(data) +
                    '}';
        }
    }

    public static class Board {
        char[][] printedBoard;
        char[][] board;
        Tile[][] tiles;

        public Board(int size) {
            tiles = new Tile[size][size];
            printedBoard = new char[size*10][size*10];
            board = new char[size*8][size*8];
        }

        public void addStartTile(Tile tile, List<Edge> imageEdges) {
            Edge top = imageEdges.get(0);
            Edge topF = new Edge(top);
            topF.flip();
            Edge left = imageEdges.get(1);
            Edge leftF = new Edge(left);
            leftF.flip();
            int count = 0;
            while (!(tile.isTop(top) || tile.isTop(topF)) || !(tile.isLeft(left) || tile.isLeft(leftF))) {
                if (++count == 4) {
                    tile.flip();
                    count = 0;
                } else {
                    tile.rotate();
                }
            }
            add(tile, 0, 0);
        }

        public boolean addTile(Tile tile, int x, int y) {
            if (x == 0) {
                Edge bottom = tiles[y-1][0].bottomEdge();
                int count = 0;
                while (!tile.isTop(bottom)) {
                    ++count;
                    if (count == 8) {
                        return false;
                    }
                    if (count == 4) {
                        tile.flip();
                    } else {
                        tile.rotate();
                    }
                }
                add(tile, x, y);
                return true;
            } else {
                Edge right = tiles[y][x-1].rightEdge();
                int count = 0;
                while (!tile.isLeft(right)) {
                    ++count;
                    if (count == 8) {
                        return false;
                    }
                    if (count == 4) {
                        tile.flip();
                    } else {
                        tile.rotate();
                    }
                }
                add(tile, x, y);
                return true;
            }
        }

        public void flip() {
            for (int i = 0; i < board.length; ++i) {
                for (int j = 0; j < board[i].length/2; ++j) {
                    var c = board[i][j];
                    board[i][j] = board[i][board[i].length-1-j];
                    board[i][board[i].length-1-j] = c;
                }
            }
        }

        public void rotate() {
            char[][] newImage = new char[board[0].length][board.length];
            for (int i = 0; i < board.length; ++i) {
                for (int j = 0; j < board[0].length; ++j) {
                    newImage[i][j] = board[board.length-1-j][i];
                }
            }
            board = newImage;
        }

        private void add(Tile tile, int x, int y) {
            tiles[y][x] = tile;
            for (int dy = 0; dy < 10; ++dy) {
                for (int dx = 0; dx < 10; ++dx) {
                    printedBoard[y*10+dy][x*10+dx] = tile.image[dy][dx];
                    if (dy != 0 && dx != 0 && dy < 9 && dx < 9) {
                        board[y*8+dy-1][x*8+dx-1] = tile.image[dy][dx];
                    }
                }
            }
        }
    }
}
