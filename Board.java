import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Board {
    private int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        // TODO
        this.tiles = tiles;
    }

    // string representation of this board
    public String toString() {
        String s = "";
        s += this.dimension() + "\n";
        for (int[] row : this.tiles) {
            for (int col : row) {
                s += col + " ";
            }
            s += "\n";
        }
        return s;
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int distance = 0;
        int size = this.dimension();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int number = 1 + (size * i) + j;

                if (this.tiles[i][j] != 0 && this.tiles[i][j] != number) {
                    distance++;
                }
            }
        }
        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;
        int size = this.dimension();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // int number = (1 + (size * i) + j) % (size * size);
                int value = (this.tiles[i][j] - 1);
                if (value == -1) {
                    continue;
                }
                int col = value % size;
                int row = value / size;
                distance += (Math.abs(i - row) + Math.abs(j - col));

            }
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        // TODO
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y.getClass() != Board.class) {
            return false;
        }
        Board y1 = (Board) y;
        if (y1.dimension() != this.dimension()) {
            return false;
        }
        int size = this.dimension();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.tiles[i][j] != y1.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        // TODO
        return new ArrayList<Board>();
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // TODO
        return this;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // int[][] tiles = { { 1, 2, 3 }, { 1, 2, 3 }, { 1, 2, 0 } };
        int[][] tiles = { { 1, 2, 3 }, { 4, 5, 6 }, { 8, 7, 0 } };
        // int[][] tiles = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        Board board = new Board(tiles);
        StdOut.println(board.toString());

        StdOut.println("hamming: " + board.hamming());
        StdOut.println("manhattan: " + board.manhattan());

        int[][] tiles2 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        Board board2 = new Board(tiles2);
        StdOut.println("isEqual: " + board.equals(board2));

    }

}
