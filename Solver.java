import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Solver {


    private class Node {
        Board current;
        Board previous;
        int cost;

        public Node(Board current, Board previous, int cost) {
            this.current = current;
            this.previous = previous;
            this.cost = cost;
        }
    }

    private MinPQ<Node> q1 = new MinPQ<>((o1, o2) -> o1.cost - o2.cost);
    private MinPQ<Node> q2 = new MinPQ<>((o1, o2) -> o1.cost - o2.cost);
    private boolean solvable;
    private List<Board> solution = new ArrayList<>();

    private boolean simulate(Node node, MinPQ<Node> q, boolean isTwin) {
        int steps = node.cost - node.current.manhattan();
        for (Board neighbor : node.current.neighbors()) {
            int distance = neighbor.manhattan();
            if (distance == 0) {
                this.solvable = !isTwin;
                return true;
            }
            if (neighbor == node.previous) {
                continue;
            }
            int cost = steps + 1 + distance;
            q.insert(new Node(neighbor, node.current, cost));

        }
        return false;
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        q1.insert(new Node(initial, null, initial.manhattan()));
        Board twin = initial.twin();
        q2.insert(new Node(twin, null, twin.manhattan()));

        while (!q1.isEmpty() && !q2.isEmpty()) {
            Node node1 = q1.delMin();
            if (simulate(node1, q1, false)) {
                break;
            }

            Node node2 = q2.delMin();
            if (simulate(node2, q2, true)) {
                break;
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.solvable;
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
        {
            int[][] tiles = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
            Board board = new Board(tiles);
            Solver solver = new Solver(board);
            StdOut.println("solvable?: " + solver.isSolvable());
        }

        {
            int[][] tiles = { { 1, 2, 3 }, { 4, 5, 6 }, { 8, 7, 0 } };
            Board board = new Board(tiles);
            Solver solver = new Solver(board);
            StdOut.println("solvable?: " + solver.isSolvable());
        }


        {
            int[][] tiles = { { 1, 2, 3 }, { 4, 6, 5 }, { 8, 7, 0 } };
            Board board = new Board(tiles);
            Solver solver = new Solver(board);
            StdOut.println("solvable?: " + solver.isSolvable());
        }


    }

}
