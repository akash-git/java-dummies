package blocking.executors;

import java.util.concurrent.*;

public class SingleExecutors extends ExecutorTest {

    public SingleExecutors() {
        super("SingleExecutors");
        executor = Executors.newSingleThreadExecutor();
    }


}
