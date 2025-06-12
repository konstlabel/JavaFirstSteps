// In an array of 10 integers, zero out 3 elements that have a sum less than

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class MinZeroer {
    static final int N = 10;
    static final int M = 3;

    private final int[] array = new int[N];
    private final int[] mins = new int[M];
    private int size = 0;

    public void fillArray(BufferedReader br) throws IOException {
        int count = 0;
        while (count < N) {
            String line = br.readLine();
            if (line == null) break;
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens() && count < N) {
                array[count++] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public void fillMins() {
        for (int i = 0; i < N; i++) {
            int j = 0;
            while (j < size && array[i] >= array[mins[j]])
                j++;
            if (j < 3) {
                for (int k = Math.min(M - 1, size); k > j; k--)
                    mins[k] = mins[k - 1];
                mins[j] = i;
                if (size < 3)
                    size++;
            }
        }
    }

    public void zeroElements() {
        for (int i = 0; i < size; i++) {
            array[mins[i]] = 0;
        }
    }

    public void printArray() {
        for (int x : array) {
            System.out.print(x + " ");
        }
        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        MinZeroer obj = new MinZeroer();

        System.out.println("Enter array elements:");
        obj.fillArray(br);
        obj.fillMins();

        System.out.println("Original array:");
        obj.printArray();
        obj.zeroElements();

        System.out.println("Resulting array:");
        obj.printArray();
    }
}
