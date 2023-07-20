package blocking.executors;

import blocking.calls.RequestProducer;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class VirtualThreadsExecutors extends ExecutorTest {

    public VirtualThreadsExecutors() {
        super("VirtualThreadsExecutors");
        executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    protected List<Callable<String>> getTasks() {
        return new RequestProducer().getCallables(5);
    }

}
