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
        // TODO
        return 0;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        // TODO
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        // TODO
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        // TODO
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
        int[][] tiles = { { 1, 2, 3 }, { 1, 2, 3 }, { 1, 2, 0 } };
        Board board = new Board(tiles);
        StdOut.println(board.toString());

    }

}
