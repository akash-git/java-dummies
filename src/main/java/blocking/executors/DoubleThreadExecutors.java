package blocking.executors;

import java.util.concurrent.Executors;

public class DoubleThreadExecutors extends ExecutorTest {

    public DoubleThreadExecutors() {
        super("DoubleThreadExecutors");
        executor = Executors.newFixedThreadPool(2);
    }


}
