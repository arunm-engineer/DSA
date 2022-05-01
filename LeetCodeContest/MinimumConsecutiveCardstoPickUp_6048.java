class Solution {
    // TC O(n) SC constant
    // For SC use HashMap instead, much space will not be wasted as in array
    public int minimumCardPickup(int[] cards) {
        int n = cards.length;
        Integer[] previousValIdxTrack = new Integer[(int) 1e6+1]; // keeps track of previous idx occurence of each val, helps to calc gap
        
        int minGap = (int) 1e9;
        for (int i = 0; i < n; i++) {
            int val = cards[i];
            
            if (previousValIdxTrack[val] != null) {
                int currGap = i - previousValIdxTrack[val] + 1;
                minGap = Math.min(currGap, minGap);
            }
            
            previousValIdxTrack[val] = i; // updating previous idx
        }
        
        return minGap == (int) 1e9 ? -1 : minGap;
    }
}