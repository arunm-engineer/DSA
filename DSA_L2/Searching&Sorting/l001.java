import java.util.Arrays;

public class l001 {

    // https://practice.geeksforgeeks.org/problems/marks-of-pcm2529/1
    public void customSort (int phy[], int chem[], int math[], int N) {
        Pair[] arr = new Pair[N];
        for (int i = 0; i < N; i++) 
            arr[i] = new Pair(phy[i], chem[i], math[i]);
            
        Arrays.sort(arr);
        
        for (int i = 0; i < N; i++) {
            phy[i] = arr[i].p;
            chem[i] = arr[i].c;
            math[i] = arr[i].m;
        }
    }
    
    private class Pair implements Comparable<Pair>{
        int p;
        int c;
        int m;
        
        public Pair() {}
        
        public Pair(int p, int c, int m) {
            this.p = p;
            this.c = c;
            this.m = m;
        }
        
        public int compareTo(Pair other) {
            if (this.p - other.p == 0) {
                if (this.c - other.c == 0) {
                    return this.m - other.m;
                }
                else {
                    return other.c - this.c;
                }
            }
            else {
                return this.p - other.p;
            }
        }
    }

    /****************************************************************************************************/

    
}
