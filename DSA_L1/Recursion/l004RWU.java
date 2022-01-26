// Recursion Way-Up
import java.util.ArrayList;

public class l004RWU {

    public static int subsequence(String str, String ans) {
        if (str.length() == 0) {
            System.out.println(ans);
            return 1; // Indicates that we have a reached valid solution
        }

        char ch = str.charAt(0);
        String ros = str.substring(1);
        int count = 0;

        count += subsequence(ros, ans); // No
        count += subsequence(ros, ans + ch); // Yes

        return count;
    }

    public static int getKPC(String str, String ans, String[] keypad) {
        if (str.length() == 0) {
            System.out.println(ans);
            return 1; // Indicates that we have a reached valid solution
        }

        char ch = str.charAt(0);
        String ros = str.substring(1);
        String keys = keypad[ch - '0'];

        int count = 0;

        for (int i = 0; i < keys.length(); i++)
            count += getKPC(ros, ans + keys.charAt(i), keypad);

        return count;
    }

    public static int stairPath(int n, String path) {
        if (n == 0) {
            System.out.println(path);
            return 1; // "Indicates" that we have a path
        }

        int count = 0;

        for (int jump = 1; jump <= n && n - jump >= 0; jump++)
            count += stairPath(n - jump, path + jump);

        return count;
    }

    public static int boardPath(int n, String ans) {
        if (n == 0) {
            System.out.println(ans);
            return 1;
        }

        int count = 0;

        for (int dice = 1; dice <= 6 && n - dice >= 0; dice++)
            count += boardPath(n - dice, ans + dice);

        return count;
    }

    public static int boardPathWithJumps(int[] arr, int n, String ans) {
        if (n == 0) {
            System.out.println(ans);
            return 1;
        }

        int count = 0;

        for (int jump : arr) {
            if (n - jump >= 0)
                count += boardPathWithJumps(arr, n - jump, ans + jump);
        }

        return count;
    }

    public static int mazePath(int sr, int sc, int er, int ec, String path) {
        if (sr == er && sc == ec) {
            System.out.println(path);
            return 1;
        }

        int count = 0;

        if (sc + 1 <= ec)
            count += mazePath(sr, sc + 1, er, ec, path + "h"); // Horizontal
        if (sr + 1 <= er)
            count += mazePath(sr + 1, sc, er, ec, path + "v"); // Vertical

        return count;
    }

    // Having sr, sc, dr, dc for generic code
    public static int mazePathWithJumps(int sr, int sc, int er, int ec, String path) {
        if (sr == er && sc == ec) {
            System.out.println(path);
            return 1;
        }

        int count = 0;

        for (int jump = 1; sc + jump <= ec; jump++)
            count += mazePathWithJumps(sr, sc + jump, er, ec, path + "H" + jump); // Horizontal

        for (int jump = 1; sr + jump <= er && sc + jump <= ec; jump++) // Both conditions will help you when maze is a
                                                                       // rectangle. Square maze, one condition is
                                                                       // enough
            count += mazePathWithJumps(sr + jump, sc + jump, er, ec, path + "D" + jump); // Diagonal

        for (int jump = 1; sr + jump <= er; jump++)
            count += mazePathWithJumps(sr + jump, sc, er, ec, path + "V" + jump); // Vertical

        return count;
    }

    public static int permutationWithoutDuplicates(String str, String ans) {
        if (str.length() == 0) {
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            String ros = str.substring(0, i) + str.substring(i + 1);
            count += permutationWithoutDuplicates(ros, ans + ch);
        }

        return count;
    }

    public static int permutationWithDuplicatesHelper(String str, String ans) {
        if (str.length() == 0) {
            System.out.println(ans);
            return 1;
        }

        int count = 0;
        char prev = '$'; // Just some random indicatioon to start with
        for (int i = 0; i < str.length(); i++) {
            char cur = str.charAt(i);
            if (prev != cur) {
                String ros = str.substring(0, i) + str.substring(i + 1);
                count += permutationWithDuplicatesHelper(ros, ans + str.charAt(i));
            }
            prev = str.charAt(i);
        }

        return count;
    }

    public static int permutationWithoutDuplicates(String str, StringBuilder ans, ArrayList<String> list) {
        if (str.length() == 0) {
            String s = ans.toString();
            list.add(s);
            return 1;
        }

        int count = 0;

        for (int i = 0;i < str.length();i++) {
            String ros = str.substring(0, i) + str.substring(i+1);
            ans.append(str.charAt(i));
            count += permutationWithoutDuplicates(ros, ans, list);
            ans.deleteCharAt(ans.length()-1);
        }

        return count;
    }

    public static int permutationWithDuplicates(String str, String ans) {
        int[] freq = new int[26];
        for (int i = 0; i < str.length(); i++) {
            freq[str.charAt(i) - 'a']++;
        }

        String sortedStr = "";
        for (int i = 0; i < freq.length; i++) {
            for (int j = 0; j < freq[i]; j++) {
                sortedStr += (char) ('a' + i);
            }
        }

        return permutationWithDuplicatesHelper(sortedStr, ans);
    }

    public static int decodeWays(String str, String ans, String s) {
        if (str.length() == 0) {
            System.out.println(ans + " " + s);
            return 1;
        }

        int count = 0;

        // Case 1 for 1 char
        char num1 = str.charAt(0);
        if (num1 == '0')
            return count;

        char ch = (char) ('a' + num1 - '1');
        count += decodeWays(str.substring(1), ans + ch, s + "," + num1);

        // Case 2 for 2 chars
        if (str.length() > 1) {
            int num2 = (str.charAt(0) - '0') * 10 + (str.charAt(1) - '0');
            if (num2 <= 26) {
                ch = (char) ('a' + num2 - 1);
                count += decodeWays(str.substring(2), ans + ch, s + "," + num2);
            }
        }

        return count;
    }

    public static void main(String[] args) {
        // System.out.println(subsequence("abc", ""));

        // String[] keypad = { ".;", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tu",
        // "vwx", "yz" };
        // System.out.println(getKPC("478", "", keypad));

        // int n = 3;
        // stairPath(n, "");

        // int n = 10;
        // System.out.println(boardPath(n, ""));
        // int[] arr = { 2, 5, 1, 3 };
        // System.out.println(boardPathWithJumps(arr, n, ""));

        // int n = 3, m = 3;
        // System.out.println(mazePath(0, 0, n-1, m-1, ""));
        // System.out.println(mazePathWithJumps(0, 0, n-1, m-1, ""));

        // System.out.println(permutationWithoutDuplicates("abc", ""));
        // System.out.println(permutationWithDuplicates("aba", ""));
        ArrayList<String> list = new ArrayList<>();
        System.out.println(permutationWithoutDuplicates("abc", new StringBuilder(), list));
        System.out.println(list);

        // System.out.println(decodeWays("112043", "", ""));
    }
}