// Recursion Way-Down (we use ArrayList)
import java.util.ArrayList;

public class l003RAL {

    public static ArrayList<String> subseq(String str) {
        if (str.length() == 0) {
            ArrayList<String> base = new ArrayList<>();
            base.add("");
            return base;
        }

        char ch = str.charAt(0);
        String rest = str.substring(1);

        ArrayList<String> recAns = subseq(rest);
        // ArrayList<String> myAns = new ArrayList<>(recAns);
        ArrayList<String> myAns = new ArrayList<>();

        for (String s : recAns) {
            myAns.add(s);  // No
            myAns.add(ch + s);  // Yes
        }

        return myAns;
    }

    public static ArrayList<String> getKPC(String str, String[] keypad) {
        if (str.length() == 0) {
            ArrayList<String> base = new ArrayList<>();
            base.add("");
            return base;
        }

        int key = str.charAt(0) - '0';
        String rest = str.substring(1);
        String keyLetters = keypad[key];

        ArrayList<String> recAns = getKPC(rest, keypad);
        ArrayList<String> myAns = new ArrayList<>();

        for (int i = 0;i < keyLetters.length();i++) {
            char ch = keyLetters.charAt(i);
            for (String s : recAns) {
                myAns.add(ch + s);
            }
        }

        return myAns;
    }

    // Corner case code if you want to consider 02, 05 as valid mappings
    public static ArrayList<String> decodeWays(String str) {
        ArrayList<String> base = new ArrayList<>();
        if (str.length() == 0) {
            base.add("");
            return base;
        }
        else if (str.length() == 1 && str.charAt(0)-'0' != 0) {
            char ch = (char) ('a' + str.charAt(0) - '1');
            base.add(ch + "");
            return base;
        }
        else if (str.length() == 1) {
            base.add("");
            return base;
        }

        int ch1 = str.charAt(0) - '0';
        int ch2 = Integer.parseInt(str.substring(0, 2)); // Or (str.charAt(0)-'0')*10 + str.charAt(1)-'0'
        
        ArrayList<String> recAns1 = new ArrayList<>();
        ArrayList<String> recAns2 = new ArrayList<>();

        if (ch1 != 0) recAns1 = decodeWays(str.substring(1));
        if (ch2 != 0 && ch2 <= 26) recAns2 = decodeWays(str.substring(2));

        ArrayList<String> myAns = new ArrayList<>();

        for (String s : recAns1) {
            char ch = (char) ('a' + ch1 - '1');
            myAns.add(ch + s);
        }
        for (String s : recAns2) {
            char ch = (char) ('a' + ch2 - 1);
            myAns.add(ch + s);
        }

        return myAns;
    }


    // Can refer above code as well just for casual view
    public static ArrayList<String> decodeWays_01(String str) {
        ArrayList<String> base = new ArrayList<>();
        if (str.length() == 0) {
            base.add("");
            return base;
        }
        
        if ( str.charAt(0) == '0') return base;
        else if (str.length() == 1) {
            char ch = (char) ('a' + str.charAt(0) - '1');
            base.add(ch + "");
            return base;
        }
        
        int ch1 = str.charAt(0) - '0';
        int ch2 = Integer.parseInt(str.substring(0, 2)); // Or (str.charAt(0)-'0')*10 + str.charAt(1)-'0'
        
        if (ch1 == '0' || ch2 > 26) return base; // Edge case for '02' since not valid

        ArrayList<String> recAns1 = decodeWays_01(str.substring(1));
        ArrayList<String> recAns2 = decodeWays_01(str.substring(2));

        ArrayList<String> myAns = new ArrayList<>();

        for (String s : recAns1) {
            char ch = (char) ('a' + ch1 - 1);
            myAns.add(ch + s);
        }
        for (String s : recAns2) {
            char ch = (char) ('a' + ch2 - 1);
            myAns.add(ch + s);
        }

        return myAns;
    }

    // Combination Keypad KPC problem + Decode Ways1 problem (from above)
    public static ArrayList<String> decodeWays_02(String str, String[] keypad) {
        ArrayList<String> base = new ArrayList<>();
        if (str.length() == 0) {
            base.add("");
            return base;
        }
        
        if ( str.charAt(0) == '0') return base;
        else if (str.length() == 1) {
            char ch = (char) ('a' + str.charAt(0) - '1');
            base.add(ch + "");
            return base;
        }
        
        int ch1 = str.charAt(0) - '0';
        int ch2 = Integer.parseInt(str.substring(0, 2)); // Or (str.charAt(0)-'0')*10 + str.charAt(1)-'0'
        
        if (ch1 == '0' || ch2 > 11) return base; // Edge case for '02' since not valid

        ArrayList<String> recAns1 = decodeWays_02(str.substring(1), keypad);
        ArrayList<String> recAns2 = decodeWays_02(str.substring(2), keypad);

        ArrayList<String> myAns = new ArrayList<>();

        String keyLetters1 = keypad[ch1];
        String keyLetters2 = keypad[ch2];

        for (int i = 0;i < keyLetters1.length();i++) {
            char ch = keyLetters1.charAt(i);
            for (String s : recAns1) {
                myAns.add(ch + s);
            }
        }

        for (int i = 0;i < keyLetters2.length();i++) {
            char ch = keyLetters2.charAt(i);
            for (String s : recAns2) {
                myAns.add(ch + s);
            }
        }

        return myAns;
    }

    public static void main(String[] args) {
        // System.out.println(subseq("abc"));

        // String[] keypad = { ".;", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tu", "vwx", "yz" };
        // System.out.println(getKPC("478", keypad));
        
        // System.out.println(decodeWays("102"));
        // System.out.println(decodeWays_01("102"));
        
        String[] keypad = { ".;", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tu", "vwx", "yz", "+-*", "<>/%" };
        System.out.println(decodeWays_02("110", keypad));

    }
}
