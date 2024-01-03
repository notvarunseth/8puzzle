import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] tiles;
    private int zeroRow;
    private int zeroCol;
    private int manhattan = -1;
    private int hamming = -1;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException();
        }
        this.tiles = this.copyTiles(tiles);
        this.locateZero();
    }

    private void locateZero() {
        int size = this.dimension();
        this.zeroRow = -1;
        this.zeroCol = -1;
        for (int i = 0; i < size && (this.zeroRow == -1); i++) {
            for (int j = 0; j < size && (this.zeroCol == -1); j++) {
                // StdOut.println("i: " + i + "j: " + j + "size: " + size);
                if (tiles[i][j] == 0) {
                    this.zeroRow = i;
                    this.zeroCol = j;
                }
            }
        }
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
        if (this.hamming >= 0) {
            return this.hamming;
        }
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
        this.hamming = distance;
        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (this.manhattan >= 0) {
            return this.manhattan;
        }

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
        this.manhattan = distance;
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || y.getClass() != Board.class) {
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

    private int[][] copyTiles(int[][] tiles) {
        int size = tiles.length;
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }
        return newTiles;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        // this.zeroRow; this.zeroCol
        int size = this.dimension();
        List<Board> mylist = new ArrayList<Board>();
        int[][] directions = { { 0, -1 }, { 1, 0 }, { 0, 1 }, { -1, 0 } };
        for (int[] direction : directions) {
            int newRow = this.zeroRow + direction[0];
            int newCol = this.zeroCol + direction[1];
            if (newRow < 0 || newCol < 0 || newRow >= size || newCol >= size) {
                continue;
            }

            int[][] newTiles = this.copyTiles(this.tiles);
            newTiles[zeroRow][zeroCol] = newTiles[newRow][newCol];
            newTiles[newRow][newCol] = 0;
            Board board = new Board(newTiles);
            mylist.add(board);
        }
        return mylist;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTiles = this.copyTiles(this.tiles);
        // Board boards = new ArrayList<Board>();
        int count = 0;
        Board board1 = null;
        Board board2 = null;
        for (Board board : this.neighbors()) {
            if (count == 0) {
                board1 = board;
            }
            if (count == 1) {
                board2 = board;
            }
            count++;
        }
        twinTiles[board1.zeroRow][board1.zeroCol] = this.tiles[board2.zeroRow][board2.zeroCol];
        twinTiles[board2.zeroRow][board2.zeroCol] = this.tiles[board1.zeroRow][board1.zeroCol];
        return new Board(twinTiles);
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

        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
        }


        StdOut.println("twin: " + board.twin());


    }

}
