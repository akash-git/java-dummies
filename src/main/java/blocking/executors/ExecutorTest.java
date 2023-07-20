package blocking.executors;

import blocking.TestApproach;
import blocking.calls.RequestProducer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class ExecutorTest extends TestApproach {
    ExecutorService executor;

    Set<Future<String>> set = new HashSet<>();
    public  ExecutorTest(String name) {
        super(name);
    }

    protected List<Callable<String>> getTasks() {
        return new RequestProducer().getCallables();
    }

    @Override
    protected void run() {
        List<Callable<String>> callables = getTasks();

        List<Future<String>> futures = new ArrayList<>();

        for (Callable<String> callable : callables) {
            Future<String> f = executor.submit(callable);
            futures.add(f);
        }

        handleFutures(futures);

        executor.shutdown();
    }


    void handleFutures(List<Future<String>> futures) {
        int total = 0;

        while (total < futures.size()) {
            for (Future<String> future : futures) {
                try {
                    if (!set.contains(future) && future.isDone()) {
                        total++;
                        System.out.println(future.get());
                        set.add(future);
                    }
                    else {
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
