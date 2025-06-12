// In a character string, remove all words that contain letters that are not in the allowed character set.  
  
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
        Scanner in = new Scanner(System.in).useLocale(Locale.US);  
  
        System.out.println("Enter allowed character set:");  
        String input = in.nextLine();  
  
        boolean[] allowedSet = new boolean[128];  
        for (int i = 0; i < input.length(); i++)  
            allowedSet[input.charAt(i)] = true;  
  
        System.out.println("Enter string:");  
        input = in.nextLine();  
        StringBuilder output = new StringBuilder();  
  
        boolean word = false;  
        boolean valid = true;  
        int startWord = 0;  
  
        for (int i = 0; i <= input.length(); i++) {  
            char ch = (i < input.length()) ? input.charAt(i) : ' ';  
            if (ch < 128 && letters[ch]) {  
                if (!word) {  
                    startWord = i;  
                    word = true;  
                    valid = true;  
                }  
                if (!allowedSet[ch])  
                    valid = false;  
            }  
            else if (!letters[ch]) {  
                if (word && valid)  
                    output.append(input, startWord, i);  
                else if (word && i < input.length())  
                    output.append(ch);  
                if (ch == ' ')  
                    output.append(ch);  
                word = false;  
            }  
        }  
  
        System.out.println(output);  
    }  
}
