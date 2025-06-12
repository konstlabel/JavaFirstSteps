// Count the number of words that consist only of different letters.
import java.util.Arrays;  
import java.util.Locale;  
import java.util.Scanner;  
  
public class Main {  
    static boolean[] letters = new boolean[128];  
  
    static {  
        for (char c = 'a'; c <= 'z'; c++)  
            letters[c] = true;  
        for (char c = 'A'; c <= 'Z'; c++)  
            letters[c] = true;  
    }  
  
    public static void main(String[] args) {  
        int count = 0;  
        Scanner in = new Scanner(System.in).useLocale(Locale.US);  
  
        System.out.println("Enter string:");  
        String s = in.nextLine();  
  
        boolean[] used = new boolean[128];  
        boolean word = false;  
        boolean valid = false;  
        int i = 0;  
  
        while (i < s.length() || word) {  
            char ch = (i < s.length()) ? s.charAt(i) : ' ';  
            if (ch < 128 && letters[ch]) {  
                if (!word) {  
                    word = true;  
                    valid = true;  
                }  
                if (used[ch])  
                    valid = false;  
                else  
                    used[ch] = true;  
            }  
            else if (word) {  
                if (valid)  
                    count++;  
                word = false;  
                valid = false;  
                Arrays.fill(used, false);  
            }  
            i++;  
        }  
        System.out.println(count);  
    }  
}
