package blocking;

import blocking.executors.DoubleThreadExecutors;
import blocking.executors.SingleExecutors;

public class Test {
    public static void main(String[] args) {
        new SingleExecutors().start();
        new DoubleThreadExecutors().start();
    }
}
