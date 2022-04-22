public class l001 {
    
    // LC 925
    // Two pointers logic => TC O(n+m) SC O(1)
    public boolean isLongPressedName(String name, String typed) {
        int i = 0, j = 0, n = name.length(), m = typed.length();
        while (i < n && j < m) {
            char ch1 = name.charAt(i), ch2 = typed.charAt(j);
            if (ch1 == ch2) { // Property 1 => Both equal
                i++;
                j++;
            }
            else if (j-1 >= 0 && ch2 == typed.charAt(j-1)) // Property 2 => Prev equal (repetition)
                j++;
            else // wrong char
                return false;
        }
        
        while (j < m) { // i alone exhausted
            char ch2 = typed.charAt(j);
            if (ch2 == typed.charAt(j-1)) // Property 2 => Prev equal (repetition)
                j++;
            else // "abc", "abccd"
                return false;
        }
        
        if (i < n) // j alone exhausted
            return false;
        
        return true;
    }

    /****************************************************************************************************/

    // LC 11
    // TC O(n) SC O(1)
    public int maxArea(int[] height) {
        int i = 0, j = height.length-1;
        int maxWater = -(int) 1e9;
        
        while (i < j) {
            int l = j - i; // len
            int h = Math.min(height[i], height[j]); // since min decides how much it can store ht. wise
            int water = l * h; // curr area of water stored
            maxWater = Math.max(maxWater, water);
            
            if (height[i] < height[j]) { // neglect min & make a move to make maxWater
                i++;
            }
            else {
                j--;                
            }
        }
        
        return maxWater;
    }

    /****************************************************************************************************/

    // LC 977
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
            
        int i = 0, j = n-1;
        for (int k = n-1; k >= 0; k--) {
            int val1 = nums[i]*nums[i];
            int val2 = nums[j]*nums[j];
            
            if (val1 > val2) {
                ans[k] = val1;
                i++;
            }
            else {
                ans[k] = val2;
                j--;
            }
        }
        
        return ans;
    }

    /****************************************************************************************************/

    // LC 169
    // Moore's Voting Algorithm => TC O(n) SC O(1)
    public int majorityElement(int[] nums) {
        int i = 1, n = nums.length;
        
        int val = nums[0];
        int count = 1;
        
        while (i < n) {
            if (nums[i] == val) {
                count++;
            }
            else { // Distinct elem got
                if (count > 0) {
                    count--; // Pair distinct elems
                }
                else { // Create next valid candidate for majority elem
                    val = nums[i];
                    count = 1;
                }
            }
            i++;
        }
        
        return val; // Ques. says maj. always exist
        
        
//         If maj. might not exist then below steps [Try this on Pep portal]
        
//         int freq = 0;
//         for (int j = 0; j < n; j++) {
//             if (nums[j] == val)
//                 freq++;
//         }
        
//         if (freq > n/2)
//             return true; // Majority elem exist
//         else 
//             return false; // Majority elem doesn't exist
    }

    /****************************************************************************************************/

    // LC 229
    public List<Integer> majorityElement(int[] nums) {
        int n = nums.length;
        
        int val1 = nums[0];
        int count1 = 1;
        
        int val2 = -(int) 1e9; // assume -(int) 1e9 just for temp purpose, since count2 will handle initialization
        int count2 = 0; // we need to yet initialize, so 0
        
        for (int i = 1; i < n; i++) {
            if (nums[i] == val1)
                count1++;
            else if (nums[i] == val2)
                count2++;
            else { // distinct elem
                if (count1 == 0) { // val1 initialization
                    val1 = nums[i];
                    count1 = 1;
                }
                else if (count2 == 0) { // val2 initialization
                    val2 = nums[i];
                    count2 = 1;
                }
                else { // pair up distinct elems
                    count1--;
                    count2--;
                }
            }
        }
        
        // check for val1 & val2, freq > n/3
        int freq1 = 0, freq2 = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == val1)
                freq1++;
            else if (nums[i] == val2)
                freq2++;
        }

        List<Integer> ans = new ArrayList<>();
        if (freq1 > n/3)
            ans.add(val1);
        if (freq2 > n/3)
            ans.add(val2);
        
        return ans;
    }

    /****************************************************************************************************/

}
