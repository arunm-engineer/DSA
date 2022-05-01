class Solution {
    // reference -> https://leetcode.com/problems/backspace-string-compare/discuss/1997024/Java-or-time-O(n)-space-O(1)-or-More-readable-than-off-solution
    public boolean backspaceCompare(String s, String t) {
        int n = s.length(), m = t.length();
        int i = n-1, j = m-1; 
        
        while (i >= 0 && j >= 0) {
            i = moveToValidCharPosition(s, i);
            j = moveToValidCharPosition(t, j);
            
            if (i < 0 || j < 0) { // either one is exhausted by backspace
                if (i == j) return true; // both exhausted by backspaces
                else return false; // chars remaining (can't match)
            }
            else if (s.charAt(i) != t.charAt(j)) // both diff chars, so cannot form equal str further as well
                return false;
            
            i--;
            j--;
        }
        
        return true; // both matched
    }
    
    private int moveToValidCharPosition(String str, int idx) {
        int cur = idx;
        int count = 0; // Backspace times count to skip or del chars
        
        while (cur >= 0) {
            if (str.charAt(cur) == '#') {
                count++;
            }
            else {
                if (count == 0)  // means all chars are deleted or skipped, reached valid char position
                    break;
                
                count--;
            }
            
            cur--;
        }
        
        return cur;
    }
}