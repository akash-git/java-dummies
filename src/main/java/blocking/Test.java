package blocking;

import blocking.executors.DoubleThreadExecutors;
import blocking.executors.SingleExecutors;
import blocking.executors.VirtualThreadsExecutors;

public class Test {
    public static void main(String[] args) {
        new SingleExecutors().start();
        new DoubleThreadExecutors().start();
        new VirtualThreadsExecutors().start();
    }
}
