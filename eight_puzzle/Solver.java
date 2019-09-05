package eight_puzzle;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private class Node implements Comparable<Node> {
        private final Board board;
        private final Node previousNode;
        private int numMoves;
        private final int manhattanValue;

        public Node(Board board, Node previousNode) {
            this.board = board;
            this.previousNode = previousNode;

            this.manhattanValue = board.manhattan(); // get manhattan calc value for the passed in board

            if (previousNode != null)
                numMoves = previousNode.numMoves + 1;
            else
                numMoves = 0;
        }

        @Override
        public int compareTo(Node that) {
            int priorityDifference = (this.manhattanValue + this.numMoves) - (that.manhattanValue + that.numMoves);
            if (priorityDifference == 0)
                return this.manhattanValue - that.manhattanValue;
            return priorityDifference;
        }
    }

    private boolean isSolvable;
    private final Stack<Board> solutions;

    public Solver(Board initial) {
        boolean solutionNotFound;
        if (initial == null)
            throw new IllegalArgumentException("Cannot pass in a null board");

        isSolvable = false;
        solutions = new Stack<>();

        // create new min priority queue of search nodes
        MinPQ<Node> searchNodes = new MinPQ<>();

        // add initial board state and a twin board state to this min PQ
        searchNodes.insert(new Node(initial, null));
        searchNodes.insert(new Node(initial.twin(), null));

        // check to see if initial board is goal
        solutionNotFound = !searchNodes.min().board.isGoal();
        // find a solution by exploring further board possibilities
        while (solutionNotFound) {
            Node searchNode = searchNodes.delMin(); // take the front node to explore
            // for each neighbouring board of the current search node:
            //  if the previous nodes board is not the current neighbouring board, explore deeper
            for (Board neighbouringBoard : searchNode.board.neighbors())
                if (searchNode.previousNode == null || (searchNode.previousNode!= null && !searchNode.previousNode.board.equals(neighbouringBoard)))
                    searchNodes.insert(new Node(neighbouringBoard, searchNode));

            solutionNotFound = !searchNodes.min().board.isGoal();
        }

        // retrieve solution
        Node currentNode = searchNodes.min();
        // backtrack until we get to the first node
        while (currentNode.previousNode != null) {
            solutions.push(currentNode.board);
            currentNode = currentNode.previousNode;
        }
        solutions.push(currentNode.board);

        if (currentNode.board.equals(initial))
            isSolvable = true;
    }

    public boolean isSolvable() {
        return this.isSolvable;
    }

    public int moves() {
        if (!isSolvable())
            return -1;
        return solutions.size() - 1; // number of board states we go to until solution found
    }

    public Iterable<Board> solution() {
        if (isSolvable())
            return solutions;
        return null;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In("eight_puzzle/p1.txt");
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
    }

}