package GummyWormDisco;

public class Pair<A, B> {
    private A first;
    private B secound;

    public Pair(A first, B secound) {
        this.first = first;
        this.secound = secound;
    }

    public A _1() {
        return first;
    }

    public B _2() {
        return secound;
    }
}
