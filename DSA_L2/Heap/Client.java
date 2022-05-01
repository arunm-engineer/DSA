public class Client {
    public static void main(String[] args) {
        Heap hp = new Heap();
        int[] arr = { 10, 20, 30, -2, -3, -4, 14, 6, 7, 8, 9, 22, 11, 13, 5 };

        for (int elem : arr)
            hp.add(elem);

        while (hp.size() > 0) {
            System.out.print(hp.remove() + " ");
        }
    }
}
