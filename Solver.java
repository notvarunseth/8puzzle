import java.util.ArrayList;

public class Solver {
    private Board board;
    private Board draob;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.board = initial;
        this.draob = board.twin();

        
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return 0;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return new ArrayList<Board>();
    }

    // test client (see below)
    public static void main(String[] args) {

    }

}
