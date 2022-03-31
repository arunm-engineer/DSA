import java.util.Arrays;

public class my_questions {

    // https://www.geeksforgeeks.org/cutting-a-rod-dp-13/
    public int cutRod(int price[], int n) {
        int[] dp = new int[n+1];
        Arrays.fill(dp, -1);

        // return cutRod_mem(price, n, dp);
        return cutRod_tab(price, n, dp);
    }
    
    public static int cutRod_mem(int[] price, int n, int[] dp) {
        if (n == 0)
            return dp[n] = 0;
            
        if (dp[n] != -1)
            return dp[n];
            
        int max = 0;
        for (int i = 1; i <= n; i++) {
            max = Math.max(max, price[i-1] + cutRod_mem(price, n-i, dp));
        }
        
        return dp[n] = max;
    }
    
    public static int cutRod_tab(int[] price, int N, int[] dp) {
        for (int n = 0; n <= N; n++) {
            if (n == 0) {
                dp[n] = 0;
                continue;
            }
                
            int max = 0;
            for (int i = 1; i <= n; i++) {
                max = Math.max(max, price[i-1] + dp[n-i]);
            }
            
            dp[n] = max;
        }
        
        return dp[N];
    }

    /******************************************************/ 
}
