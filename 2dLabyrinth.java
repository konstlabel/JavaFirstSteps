// Specify a labyrinth as a two-dimensional integer array of size N by K,
// where, for example, walls are defined by ones and passages by zeros.
// The program must find an exit from a given point in the labyrinth in any way.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Labyrinthium {
    private int[][] labyrinth;
    private int[] size;

    public Labyrinthium(int[] size) {
        try {
            this.size = size;
            this.labyrinth = new int[size[0]][size[1]];
        } catch (NullPointerException e) {
            System.err.println("Error! Null pointer: " + e.getMessage());
        }
    }

    public int[][] getLabyrinth() {
        return labyrinth;
    }
    public int[] getSize() {
        return size;
    }
    public int getPoint(int row, int column) {
        return labyrinth[row][column];
    }
}

class MazeSolver {
    private static int[][] labyrinth;
    private static int[] start;
    private static int[] end;
    private final static int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} }; // up, down, left, right

    public static void fillMazeSolver(int[][] original, int[] newStart, int[] newEnd) {
        labyrinth = original;
        start = newStart;
        end = newEnd;
    }

    private static boolean isPath(int row, int col) {
        return labyrinth[row][col] != 1;
    }
    private static boolean isValid(int row, int col) {
        return row >= 0 && row < labyrinth.length && col >= 0 && col < labyrinth[0].length;
    }
    private static int countAvailablePath(int row, int col) {
        int count = 0;
        for (int[] d : directions) {
            int newRow = row + d[0];
            int newCol = col + d[1];
            if (isValid(newRow, newCol) && labyrinth[newRow][newCol] == 0)
                count++;
        }
        return count;
    }

    private static void fillDeadEnd(int row, int col) {
        int[][] stack = new int[labyrinth.length * labyrinth[0].length][2];
        int top = 0;
        stack[top++] = new int[] {row, col};

        while (top > 0) {
            int[] current = stack[--top];
            int r = current[0];
            int c = current[1];
            labyrinth[r][c] = 1;

            for (int[] d : directions) {
                int newRow = r + d[0];
                int newCol = c + d[1];
                if ((newRow == start[0] && newCol == start[1]) || (newRow == end[0] && newCol == end[1]))
                    continue;
                if (isValid(newRow, newCol) && isPath(newRow, newCol) && countAvailablePath(newRow, newCol) < 2) {
                    stack[top++] = new int[] {newRow, newCol};
                }
            }
        }
    }

    public static void findSolution() {
        int rows = labyrinth.length;
        int columns  = labyrinth[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == start[0] && j == start[1] || i == end[0] && j == end[1])
                    continue;
                if (isPath(i,j) && countAvailablePath(i,j) < 2) {
                    fillDeadEnd(i, j);
                }
            }
        }
    }
}

class InOutputProcess {
    public int[] inputSize(BufferedReader bf) throws IOException {
        int[] size = new int[2];
        int count = 0;

        while (count < 2) {
            String line = bf.readLine();
            if (line == null)
                break;

            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens() && count < 2) {
                size[count++] = Integer.parseInt(st.nextToken());
            }
        }

        return size;
    }

    public void fillLabyrinth (BufferedReader br, int[][] labyrinth, int[] size) {
        try {
            // Output header
            System.out.print("   ");
            for (int i = 1; i <= size[1]; i ++) {
                System.out.print(i + " ");
            }
            System.out.println();
            for (int i = 0; i <= size[0]; i ++) {
                System.out.print("__");
            }
            System.out.println();

            // Input rows
            for (int row = 0; row < size[0]; row++) {
                System.out.print(row + 1 + "| ");

                String[] tokens = br.readLine().trim().split("\\s+");
                if (tokens.length != size[1]) {
                    System.out.println("Error! Expected " + size[1] + " values, got " + tokens.length);
                    row--;
                    continue;
                }

                for (int column = 0; column < size[1]; column++) {
                    int x = Integer.parseInt(tokens[column]);
                    if (x > 1 || x < 0) {
                        System.out.println("Error! Expected '0' or '1', got " + x + "(index = " + (column+1) + "). Rewrite column.");
                        row--;
                        continue;
                    }
                    labyrinth[row][column] = x;
                }
            }
        } catch (IOException e) {
            System.err.println("Error! Reading is failed: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Error! Null pointer: " + e.getMessage());
        }
    }

    public int[] inputPoint(BufferedReader br, int[] size) {
        int[] point = new int[2];
        int count = 0;

        try {
            while (count < 2) {
                String line = br.readLine();
                if (line == null)
                    break;

                StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreTokens() && count < 2) {
                    int x = Integer.parseInt(st.nextToken()) - 1;
                    if (x < 0 || count == 0 && x >= size[0] || count == 1 && x >= size[1]) {
                        System.out.println("Error! Expected min 1 and max " + size[0] + " rows and min 1 and max " + size[1] + " columns, got " + (x+1));
                        continue;
                    }

                    point[count++] = x;
                }
            }
        } catch (IOException e) {
            System.err.println("Error! Reading is failed: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Error! Null pointer: " + e.getMessage());
        }

        return point;
    }

    public void outputLabyrinth(int[][] labyrinth, int[] size) {
        try {
            // Output header
            System.out.print("   ");
            for (int i = 1; i <= size[1]; i ++) {
                System.out.print(i + " ");
            }
            System.out.println();
            for (int i = 0; i <= size[1]; i ++) {
                System.out.print("__");
            }

            // Output rows
            System.out.println();
            for (int i = 0; i < size[0]; i++) {
                System.out.print(i + 1 + "| ");
                for (int j = 0; j < size[1]; j++) {
                    System.out.print(labyrinth[i][j] + " ");
                }
                System.out.println();
            }
        } catch (NullPointerException e) {
            System.err.println("Error! Null pointer: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Enter size
        InOutputProcess ip = new InOutputProcess();
        int[] size;
        do {
            System.out.println("Enter number of columns and rows (bother >= 3):");
            size = ip.inputSize(br);
        } while (size == null || size[0] < 3 || size[1] < 3);

        // Initialized labyrinth
        Labyrinthium labyrinthium = new Labyrinthium(size);

        // Create (fill) labyrinth
        System.out.println("Enter elements of array (1 - wall, 0 - path)");
        ip.fillLabyrinth(br, labyrinthium.getLabyrinth(), labyrinthium.getSize());

        // Initialized start and end points
        System.out.println("Enter start point (it must contain 0)");
        int[] start;
        do {
            start = ip.inputPoint(br, size);
        } while (labyrinthium.getPoint(start[0], start[1]) != 0);
        System.out.println("Enter end point (it must contain 0)");
        int[] end;
        do {
            end = ip.inputPoint(br, size);
        } while (labyrinthium.getPoint(end[0], end[1]) != 0);

        // Output labyrinth
        System.out.println("\nLabyrinth:");
        ip.outputLabyrinth(labyrinthium.getLabyrinth(), labyrinthium.getSize());

        // Find a solution
        MazeSolver.fillMazeSolver(labyrinthium.getLabyrinth(), start, end);
        MazeSolver.findSolution();

        // Output solution
        System.out.println("\nSolution:");
        ip.outputLabyrinth(labyrinthium.getLabyrinth(), labyrinthium.getSize());
    }
}
