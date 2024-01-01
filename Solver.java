import edu.princeton.cs.algs4.In;
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
            // StdOut.println("distance: " + distance + " steps: " + steps);
            if (neighbor == node.previous) {
                continue;
            }

            int cost = steps + 1 + distance;
            q.insert(new Node(neighbor, node.current, cost));

            if (distance == 0) {
                // StdOut.println("solved: " + neighbor + " from: " + node.current);
                this.solvable = !isTwin;
                return true;
            }

        }
        return false;
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        List<Node> history = new ArrayList<Node>();

        q1.insert(new Node(initial, null, initial.manhattan()));
        Board twin = initial.twin();
        q2.insert(new Node(twin, null, twin.manhattan()));

        while (!q1.isEmpty() && !q2.isEmpty()) {
            Node node1 = q1.delMin();
            // if (history.size() > 100) {
            //     StdOut.println("exiting");
            //     System.exit(0);
            // }
            history.add(node1);
            if (node1.cost == 0) {
                this.solvable = true;
                break;
            }
            // StdOut.println("history length: " + history.size());

            if (simulate(node1, q1, false)) {
                history.add(q1.min());
                break;
            }

            Node node2 = q2.delMin();
            if (simulate(node2, q2, true)) {
                break;
            }
            // StdOut.println("q1 length: " + q1.size());

            // StdOut.println("q2 length: " + q2.size());
        }

        if (this.solvable) {
            // process
            Node lastElement = null;
            for (int i = history.size() - 1; i >= 0; i--) {
                Node element = history.get(i);
                if (lastElement == null) {
                    lastElement = element;
                    this.solution.add(element.current);
                    // StdOut.println("state: " + element.current.manhattan() + " " + element.current);
                    continue;
                }
                if (lastElement.previous == element.current) {
                    this.solution.add(element.current);
                    // StdOut.println("state: " + element.current.manhattan() + " " + element.current);
                }
                lastElement = element;
            }
            List<Board> solution2 = new ArrayList<Board>();
            for (int i = solution.size() - 1; i >= 0; i--) {
                solution2.add(solution.get(i));
            }
            this.solution = solution2;
        }
        else {
            history.clear();
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return this.solution.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return this.solution;
    }


    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

        /*

        {
            int[][] tiles = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
            Board board = new Board(tiles);
            Solver solver = new Solver(board);
            StdOut.println("solvable?: " + solver.isSolvable());
            StdOut.println("moves?: " + solver.moves());
            StdOut.println("solution?: " + solver.solution());
        }
        StdOut.println("------------------------------");

        {
            int[][] tiles = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 } };
            Board board = new Board(tiles);
            Solver solver = new Solver(board);
            StdOut.println("solvable?: " + solver.isSolvable());
            StdOut.println("moves?: " + solver.moves());
            StdOut.println("solution?: " + solver.solution());
        }
        StdOut.println("------------------------------");

        {
            int[][] tiles = { { 1, 2, 3 }, { 4, 8, 6 }, { 7, 0, 5 } };
            Board board = new Board(tiles);
            Solver solver = new Solver(board);
            StdOut.println("solvable?: " + solver.isSolvable());
            StdOut.println("moves?: " + solver.moves());
            StdOut.println("solution?: " + solver.solution());

        }

        StdOut.println("------------------------------");


        {
            int[][] tiles = { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
            Board board = new Board(tiles);
            Solver solver = new Solver(board);
            StdOut.println("solvable?: " + solver.isSolvable());
            StdOut.println("moves?: " + solver.moves());
            StdOut.println("solution?: " + solver.solution());

        }

     */


    }

}
