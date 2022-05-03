import java.util.HashMap;

import java.util.HashMap;
import java.util.ArrayList;

public class l001 {

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



}