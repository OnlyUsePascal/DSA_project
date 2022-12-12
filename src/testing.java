public class testing {
    static void callOut(int n){
        System.out.println(n);
        if (n < Integer.MAX_VALUE - 2) callOut(n+1);
    }

    public static void main(String[] args) {
        LinkedListStack<String> s = new LinkedListStack<String>();
        callOut(Integer.MIN_VALUE);
    }
}
