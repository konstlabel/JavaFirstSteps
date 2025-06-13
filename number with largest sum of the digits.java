// In an array of 10 integers, find and output 2 numbers with the largest sum of the digits they consist of.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class MaxDigitSums {
    static final int N = 10;
    static final int M = 2;

    private final int[] array = new int[N];
    private final int[] digitSums = new int[N];
    private final int[] maxIndexes = new int[M];
    private int size = 0;

    public void readArray(BufferedReader br) throws IOException {
        int count = 0;
        while (count < N) {
            String line = br.readLine();
            if (line == null)
                break;
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens() && count < N) {
                array[count++] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private void sumDigits(int index , int x) {
        digitSums[index] = 0;
        while (x != 0) {
            digitSums[index] += x % 10;
            x = x / 10;
        }
    }

    private void processMaxes(int index) {
        int j = 0;
        while (j < size && digitSums[index] <= digitSums[maxIndexes[j]])
            j++;
        if (j < M) {
            for (int k = Math.min(M - 1, size); k > j; k--)
                maxIndexes[k] = maxIndexes[k - 1];
            maxIndexes[j] = index;
            if (size < M)
                size++;
        }
    }

    public void processArray() {
        for (int i = 0; i < N; i++) {
            int x = (array[i] >= 0) ? array[i] : -array[i]; // Math.abs(array[i])
            this.sumDigits(i, x);
            this.processMaxes(i);
        }
    }

    public void printArray() {
        System.out.println("Array:");
        for (int i = 0; i < N; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public void printMaxes() {
        System.out.println("Elements with the largest sum of the digits:");
        for (int i = 0; i < M; i++) {
            System.out.print(maxIndexes[i] + ". " + array[maxIndexes[i]] + "\t");
        }
        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        MaxDigitSums obj = new MaxDigitSums();
        obj.readArray(br);
        obj.processArray();
        obj.printArray();
        obj.printMaxes();
    }
}
