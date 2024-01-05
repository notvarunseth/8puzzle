import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {


    private static class Node {
        Board current;
        Board previous;
        int distance;
        int steps;
        boolean isTwin;

        public Node(Board current, Board previous, int steps, int distance, boolean isTwin) {
            this.current = current;
            this.previous = previous;
            this.steps = steps;
            this.distance = distance;
            this.isTwin = isTwin;
        }
    }

    private Comparator<Node> c = (o1, o2) -> {
        int cost1 = o1.steps + o1.distance;
        int cost2 = o2.steps + o2.distance;
        if (o1.isTwin) {
            cost1 *= 2;
        }
        if (o2.isTwin) {
            cost2 *= 2;
        }

        if (cost1 != cost2) {
            // if (!o1.isTwin && o2.isTwin) {
            //     return -1;
            // }
            // else if (o1.isTwin && !o2.isTwin) {
            //     return 1;
            // }
            return cost1 - cost2;
        }
        return o1.steps - o2.steps;

    };


    private boolean solvable;
    private List<Board> solution = new ArrayList<>();

    private Node simulate(Node node, MinPQ<Node> q) {

        for (Board neighbor : node.current.neighbors()) {
            int distance = neighbor.manhattan();
            // StdOut.println(
            //         "distance: " + distance + " steps: " + steps + " qSize: " + q.size());
            if (neighbor.equals(node.previous)) {
                continue;
            }


            Node newNode = new Node(neighbor, node.current, node.steps + 1, distance, node.isTwin);


            if (distance == 0) {
                // StdOut.println("solved: " + neighbor + " from: " + node.current);
                this.solvable = !node.isTwin;
                return newNode;
            }
            q.insert(newNode);

        }
        return null;
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        MinPQ<Node> q1 = new MinPQ<>(c);

        List<Node> history = new ArrayList<Node>();

        q1.insert(new Node(initial, null, 0, initial.manhattan(), false));
        Board twin = initial.twin();
        q1.insert(new Node(twin, null, 0, twin.manhattan(), true));

        while (!q1.isEmpty()) {
            Node node1 = q1.delMin();

            if (!node1.isTwin) {
                history.add(node1);
            }

            if (node1.distance == 0) {
                // first node is already solved
                this.solvable = !node1.isTwin;
                break;
            }

            Node goalNode = simulate(node1, q1);
            if (goalNode != null) {
                if (!goalNode.isTwin) {
                    history.add(goalNode);
                }

                break;
            }

        }

        // StdOut.println("solved the board");
        if (this.solvable) {
            // process
            Node lastElement = null;
            for (int i = history.size() - 1; i >= 0; i--) {
                Node element = history.get(i);
                if (lastElement == null || lastElement.previous == element.current) {
                    this.solution.add(element.current);
                    lastElement = element;
                }

            }
            // StdOut.println("generated the solution");
            List<Board> solution2 = new ArrayList<Board>();
            for (int i = solution.size() - 1; i >= 0; i--) {
                solution2.add(solution.get(i));
            }
            // StdOut.println("generated the solution2");
            this.solution = solution2;
        }
        else {
            history.clear();
            this.solution = null;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (this.solution == null) {
            return -1;
        }
        return this.solution.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return this.solution;
    }


    public static void main(String[] args) {

        // create initial board from file
        long time1 = System.currentTimeMillis();
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
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
            StdOut.println("moves: " + solver.moves());
        }

        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

        StdOut.println("time: " + (System.currentTimeMillis() - time1));

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
