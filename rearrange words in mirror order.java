// Rearrange words in a character string in mirror order

import java.util.Scanner;
import java.util.Locale;

class Process {
    static boolean[] letters = new boolean[128];
    static {
        for (char c = 'a'; c <= 'z'; c++)
            letters[c] = true;
        for (char c = 'A'; c <= 'Z'; c++)
            letters[c] = true;
    }

    private String text = null;
    private final StringBuilder result = new StringBuilder();

    public void setText(String text) {
        this.text = text + " ";
    }

    public String getResult() {
        return this.result.toString().replaceAll(" +", " ").trim();
    }

    public void process() {
        if (text.isEmpty())
            return;

        boolean inWord = false;
        int end = text.length()-1;

        for (int i = text.length()-1; i >= 0 ; i--) {
            char ch = text.charAt(i);
            if (ch >= 128)
                break;
            if (i == 0 && inWord) {
                result.append(text, i, end);
                result.append(" ");
                break;
            }
            if (letters[ch]) {
                if (!inWord) {
                    inWord = true;
                    end = i+2;
                }
            }
            else {
                if (inWord) {
                    result.append(text, i, end);
                    result.append(" ");
                }
                inWord = false;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in).useLocale(Locale.US);

        System.out.println("Enter string:");

        Process obj = new Process();
        obj.setText(in.nextLine());
        obj.process();
        System.out.println(obj.getResult());
    }
}
