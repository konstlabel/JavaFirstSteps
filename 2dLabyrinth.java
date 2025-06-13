// this is raw solution 

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

    public static int[][] getLabyrinth() {
        return labyrinth;
    }

    public static void fillMazeSolver(int[][] copyLabyrinth, int[] newStart, int[] newEnd) {
        try {
            int rows = copyLabyrinth.length;
            int columns = copyLabyrinth[0].length;

            labyrinth = new int[rows][columns];
            for (int i = 0; i < rows; i++)
                System.arraycopy(copyLabyrinth[i], 0, labyrinth[i], 0, columns);

            start = newStart;
            end = newEnd;
        } catch (NullPointerException e) {
            System.err.println("Error! Null pointer: " + e.getMessage());
        }
    }

    private static boolean isPath(int row, int col) {
        return labyrinth[row][col] != 1;
    }
    private static boolean isLeftPath(int row, int col) {
        if (col == 0)
            return false;
        return labyrinth[row][col - 1] != 1;
    }
    private static boolean isRightPath(int row, int col) {
        if (col == labyrinth[0].length - 1)
            return false;
        return labyrinth[row][col + 1] != 1;
    }
    private static boolean isBotPath(int row, int col) {
        if (row == labyrinth.length - 1)
            return false;
        return labyrinth[row + 1][col] != 1;
    }
    private static boolean isTopPath(int row, int col) {
        if (row == 0)
            return false;
        return labyrinth[row - 1][col] != 1;
    }

    private static void fillDeadEnd(int i, int j) {

        if (isLeftPath(i,j))
            j--;
        else if (isRightPath(i,j))
            j++;
        else if (isBotPath(i,j))
            i++;
        else
            i--;

        if (i == start[0] && j == start[1] || i == end[0] && j == end[1])
            return;

        int paths = 0;
        if (isLeftPath(i,j))
            paths++;
        if (isRightPath(i,j))
            paths++;
        if (isBotPath(i,j))
            paths++;
        if (isTopPath(i,j))
            paths++;
        if (paths < 2) {
            labyrinth[i][j] = 1;
            fillDeadEnd(i, j);
        }
    }

    private static void findDeadEnds() {
        int rows = labyrinth.length;
        int columns  = labyrinth[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == start[0] && j == start[1] || i == end[0] && j == end[1])
                    continue;
                if (isPath(i,j)) {
                    int paths = 0;
                    if (isLeftPath(i,j))
                        paths++;
                    if (isRightPath(i,j))
                        paths++;
                    if (isBotPath(i,j))
                        paths++;
                    if (isTopPath(i,j))
                        paths++;
                    if (paths < 2) {
                        labyrinth[i][j] = 1;
                        fillDeadEnd(i, j);
                    }
                }
            }
        }
    }

    public static void findSolution() {
        findDeadEnds();
    }
}

class InOutputProcess {
    public int[] inputSize(BufferedReader bf) throws IOException {
        int[] size = new int[]{3, 3};
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
            System.out.print("0| ");
            for (int i = 1; i <= size[1]; i ++) {
                System.out.print(i + " ");
            }
            System.out.println();
            for (int i = 0; i <= size[0]; i ++) {
                System.out.print("--");
            }
            System.out.println();
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
                        System.out.println("Error! Expected '0' or '1', got " + x + "(index = " + column + "). Rewrite column.");
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
                    int x = Integer.parseInt(st.nextToken());
                    if (x < 0 || count == 0 && x > size[0] || count == 1 && x > size[1]) {
                        System.out.println("Error! Expected " + size[0] + " by rows and " + size[1]+ " by columns, got " + x);
                        continue;
                    }
                    point[count++] = x-1;
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
            System.out.print("0| ");
            for (int i = 1; i <= size[1]; i ++) {
                System.out.print(i + " ");
            }
            System.out.println();
            for (int i = 0; i <= size[1]; i ++) {
                System.out.print("--");
            }
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
    public static void main() throws IOException {
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

        // Create labyrinth
        System.out.println("Enter elements of array (1 - wall, 0 - path)");
        ip.fillLabyrinth(br, labyrinthium.getLabyrinth(), labyrinthium.getSize());

        // Initialized start point
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

        // Find a solution
        MazeSolver.fillMazeSolver(labyrinthium.getLabyrinth(), start, end);
        MazeSolver.findSolution();

        // Output labyrinth
        System.out.println("\nLabyrinth:");
        ip.outputLabyrinth(labyrinthium.getLabyrinth(), labyrinthium.getSize());
        System.out.println("\nSolution:");
        ip.outputLabyrinth(MazeSolver.getLabyrinth(), labyrinthium.getSize());

    }
}
