// Count the number of words that have fewer consonants than vowels
import java.util.Locale;  
import java.util.Scanner;  
  
public class Main {  
    static boolean[] vowels = new boolean[128];  
    static boolean[] letters = new boolean[128];  
  
     static {  
        for (char ch : "aeiouAEIOU".toCharArray())  
            vowels[ch] = true;  
        for (char ch = 'a'; ch <= 'z'; ch++)  
            letters[ch] = true;  
        for (char ch = 'A'; ch <= 'Z'; ch++)  
            letters[ch] = true;  
    }  
  
    public static void main (String[] args) {  
        String s;  
        Scanner in = new Scanner(System.in).useLocale(Locale.US);  
  
        System.out.println("Enter string:");  
        s = in.nextLine();  
  
        s += " ";  
  
        int count = 0;  
        int vowelCount = 0;  
        int consonantCount = 0;  
  
        for (int i = 0; i < s.length(); i++) {  
            char ch = s.charAt(i);  
            if (ch < 128 && letters[ch]) {  
                if (vowels[ch])  
                    vowelCount++;  
                else  
                    consonantCount++;  
            }  
            else {  
                if (vowelCount > consonantCount)  
                    count++;  
                vowelCount = 0;  
                consonantCount = 0;  
            }  
        }  
        System.out.println(count);  
    }  
}
