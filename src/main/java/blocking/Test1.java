package blocking;

import blocking.executors.DoubleThreadExecutors;
import blocking.executors.VirtualThreadsExecutors;

public class Test1 {
    public static void main(String[] args) {

//        new Loop().start();
        new VirtualThreadsExecutors().start();
        System.out.println("Hello World!");
    }
}
