import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class questionsl002 {

    // LC 39 => Same Simple Infinite Combination problem
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> smallAns = new ArrayList<>();
        
        infiniteCombination(candidates, target, 0, ans, smallAns);
        return ans;
    }
    
    public static int infiniteCombination(int[] arr, int tar, int idx, List<List<Integer>> ans, List<Integer> smallAns) {
        if (tar == 0) {
            List<Integer> base = new ArrayList<>(smallAns);
            ans.add(base);
            return 1;            
        }

        int count = 0;
        for (int i = idx;i < arr.length;i++) {
            if (tar-arr[i] >= 0) {
                smallAns.add(arr[i]);
                count += infiniteCombination(arr, tar-arr[i], i, ans, smallAns);
                smallAns.remove(smallAns.size()-1);
            }
        }

        return count;
    }

    /******************************************************/ 

    // LC 40 => Mixture of single Combination && prev+cur logic to avoid duplicacy
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> smallAns = new ArrayList<>();
        
        Arrays.sort(candidates); //  Sorted to use prev+cur logic to avoid duplicacy
        singleCombination(candidates, target, 0, ans, smallAns);
        return ans;
    }
    
    public static int singleCombination(int[] arr, int tar, int idx, List<List<Integer>> ans, List<Integer> smallAns) {
        if (tar == 0) {
            List<Integer> base = new ArrayList<>(smallAns);
            ans.add(base);
            return 1;            
        }

        int count = 0;
        int prev = -1;
        for (int i = idx;i < arr.length;i++) {
            int cur = arr[i];
            if (prev != cur && tar-arr[i] >= 0) {
                smallAns.add(arr[i]);
                count += singleCombination(arr, tar-arr[i], i+1, ans, smallAns);
                smallAns.remove(smallAns.size()-1);
            }
            prev = cur;
        }

        return count;
    }

    /******************************************************/ 

    // https://www.interviewbit.com/old/problems/subset/
    public ArrayList<ArrayList<Integer>> subsets(ArrayList<Integer> A) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> smallAns = new ArrayList<>();
        
        Collections.sort(A); // Have to sort to match output, nothing else
        subsetHelper_subsq(A, 0, ans, smallAns);
        return ans;
    }
    
    public static int subsetHelper_subsq(ArrayList<Integer> list, int idx, ArrayList<ArrayList<Integer>> ans,
    ArrayList<Integer> smallAns) {
        ArrayList<Integer> copy = new ArrayList<>(smallAns);
        ans.add(copy);
        
        if (idx == list.size())
            return 1;
        
        int count = 0;

        // nCr method
        for (int i = idx;i < list.size();i++) {
            smallAns.add(list.get(i));
            count += subsetHelper_subsq(list, i+1, ans, smallAns);
            smallAns.remove(smallAns.size()-1);
        }
        
        return count;
    }

    /******************************************************/ 

}
