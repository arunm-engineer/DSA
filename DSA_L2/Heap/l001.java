import java.util.HashMap;
import java.util.ArrayList;

public class l001 {

    // https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/number-of-employees-under-every-manager-official/ojquestion
    public static void formEmployeeManagerTree(HashMap<String, String> employees) {
        HashMap<String, ArrayList<String>> tree = new HashMap<>();
        String ceo = "";

        for (String employee : employees.keySet()) {
            String manager = employees.get(employee);
            if (employee.equals(manager)) { // starting root of tree hierarchy
                ceo = employee;
            }
            else {
                tree.putIfAbsent(manager, new ArrayList<>());
                tree.get(manager).add(employee);    
            }
        }

        HashMap<String, Integer> employeeCount = new HashMap<>();
        getEmployeeCountForManagers(tree, ceo, employeeCount);

        // For o/p purpose
        for (String manager : employeeCount.keySet())
            System.out.println(manager + " " + employeeCount.get(manager));
    }

    public static int getEmployeeCountForManagers(HashMap<String, ArrayList<String>> tree, String manager, HashMap<String, Integer> employeeCount) {
        if (!tree.containsKey(manager)) {
            employeeCount.put(manager, 0);
            return 1; // manager - including self
        }
            

        int count = 0;
        for (String employee : tree.get(manager)) {
            count += getEmployeeCountForManagers(tree, employee, employeeCount);
        }

        employeeCount.put(manager, count); // num of employees reporting to me (self)
        return count + 1; // +1 represents including employee (himself) in count for parent node in hierarchy
    }

    /****************************************************************************************************/

    // https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/find-itinerary-from-tickets-official/ojquestion
    public static void findItinerary(HashMap<String, String> map) {
        HashMap<String, Boolean> potentialStart = new HashMap<>(); // to find potential overall start point

        for (String start : map.keySet()) {
            String dest = map.get(start);
            
            if (!potentialStart.containsKey(start)) // what if this src was alreaddy added as an dest entry, so don't change
                potentialStart.put(start, true);

            potentialStart.put(dest, false);
        }

        String overallStart = "";
        for (String start : potentialStart.keySet()) {
            if (potentialStart.get(start)) {
                overallStart = start;
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        printItinerary(overallStart, map, sb);
    }

    public static void printItinerary(String src, HashMap<String, String> map, StringBuilder sb) {
        while (src != null) {
            String dest = map.get(src);
            if (dest != null) sb.append(src + " -> ");
            else sb.append(src + ".");
            src = dest;
        }

        System.out.println(sb);
    }

    /****************************************************************************************************/

	// https://www.pepcoding.com/resources/data-structures-and-algorithms-in-java-levelup/hashmap-and-heaps/count-distinct-elements-in-every-window-of-size-k-official/ojquestion
	public static ArrayList<Integer> countDistinctElementsInAllKWindows(int[] arr, int k) {
        ArrayList<Integer> ans = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>(); // freq map

        for (int i = 0; i < k-1; i++) {
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
        }


        for (int i = k-1; i < arr.length; i++) {
            // 1. acquire
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
            // 2. work
            ans.add(map.size());
            // 3. release
            int j = i - k + 1; // to release starting elem on moving to next window
            if (map.get(arr[j]) == 1) map.remove(arr[j]);
            else map.put(arr[j], map.get(arr[j]) - 1);
        }

        return ans;
    }

    /****************************************************************************************************/

    public static boolean checkArraySumPairsDivisibleByK(int[] arr, int k) {
        HashMap<Integer, Integer> map = new HashMap<>(); // stores freq of "remainder" of arr elems ; map<rem, remFreq>

        // forming freq count of remainder
        for (int num : arr) {
            int rem = num % k;
            map.put(rem, map.getOrDefault(rem, 0) + 1);
        }

        for (int num : arr) {
            int rem = num % k;
            if (rem == 0) {
                int freq = map.get(rem);
                if (freq%2 != 0) // odd freq -> not valid
                    return false;
            }
            else if (rem*2 == k) { // not (rem == k/2) => this gives issue if k is odd, so (rem*2 == k)
                int freq = map.get(rem);
                if (freq%2 != 0)  // odd freq -> not valid
                    return false;
            }
            else {
                int remFreq = map.get(rem); // x -> freq
                int otherFreq = map.getOrDefault(k - rem, 0); // k-x -> freq
                if (remFreq != otherFreq)
                    return false;
            }
        }

        return true; // means all valid sum pairs formed in arr divisible by k
    }

    /****************************************************************************************************/

    public static int largestSubArrWithZeroSum(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>(); // map<sum, i>

        int maxLen = 0; // has to be zero, think accr to len formula

        int i = -1;
        int sum = 0;
        map.put(sum, i); // trivial case

        while (++i < arr.length) {
            sum += arr[i];
            if (map.containsKey(sum)) { // same sum exists -> means a '0' sum subarr found in-btw
                int subArrLen = i - map.get(sum);
                maxLen = Math.max(subArrLen, maxLen);
            }
            else {
                map.put(sum, i);
            }
        }

        return maxLen;
    }

}