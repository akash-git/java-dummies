package blocking.executors;

import java.util.concurrent.Executors;

public class VirtualThreadsExecutors extends ExecutorTest {

    public VirtualThreadsExecutors() {
        super("VirtualThreadsExecutors");
        executor = Executors.newVirtualThreadPerTaskExecutor();
    }
}
