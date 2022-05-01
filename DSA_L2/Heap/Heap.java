import java.util.ArrayList;

import java.util.ArrayList;

public class Heap {
    private ArrayList<Integer> arr;

    public Heap() {
        this.arr = new ArrayList<>();
    }

    // TC O(n + nlogn) => By looking below steps
    // TC O(n) => actually proved mathematically
    public Heap(int[] arr) {
        this(); // calling default constructor for initialization
        for (int elem : arr)
            this.arr.add(elem);

        for (int i = this.arr.size()-1; i >= 0; i--)  // check heap property and fix at each subtree node
            downHeapify(i);
    }

    // TC O(1)
    // custom compareTo
    // From compareTo result, always take action on the basis of "this's" value
    private boolean compareTo(int i, int j) {
        return this.arr.get(i) > this.arr.get(j); // this is for max-heap
    }

    // TC O(1)
    private void swap(int i, int j) {
        int val1 = this.arr.get(i);
        int val2 = this.arr.get(j);

        this.arr.set(i, val2);
        this.arr.set(j, val1);
    }

    // TC O(logn)
    private void downHeapify(int pi) {
        int lci = 2 * pi + 1;
        int rci = 2 * pi + 2;
        int maxIdx = pi;

        if (lci < this.arr.size() && compareTo(lci, pi)) 
            maxIdx = lci;

        if (rci < this.arr.size() && compareTo(rci, pi)) 
            maxIdx = rci;

        if (maxIdx != pi) { // means Heap property violated, another max found so fix heap
            swap(pi, maxIdx); 
            downHeapify(maxIdx); // now parent follows heap, but not sure of child subtree so fix below recursively
        }
    }

    // TC O(logn)
    private void upheapify(int ci) {
        int pi = (ci-1)/2;

        if (pi >= 0 && compareTo(ci, pi)) {
            swap(ci, pi);
            upheapify(pi);
        }
    }

    // TC O(logn)
    public int remove() {
        int rVal = this.arr.get(0);

        swap(0, this.arr.size()-1); //  since removing directly 0th idx will cost O(n) shifting in list, so this swap
        this.arr.remove(this.arr.size()-1);
        downHeapify(0); // since after swap heap property might be violated, so fix again from the start point

        return rVal;
    }

    // TC O(1)
    public int peek() {
        return this.arr.get(0);
    }

    // TC O(logn)
    public void add(int data) {
        this.arr.add(data);
        upheapify(this.arr.size()-1);
    }

    public int size() {
        return this.arr.size();
    }
}
