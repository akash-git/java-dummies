package blocking.calls;

import blocking.calls.HttpCall;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class RequestProducer {
    public API[] get() {
        API[] apis = new HttpCall[2];

        for (int i = 0; i < 2; i++) {
            apis[i] = new HttpCall(5);
        }

        return apis;
    }

    public List<Callable<String>> getCallables() {
        return getCallables(3);
    }

    public List<Callable<String>> getCallables(int n) {
        List<Callable<String>> callables = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            callables.add(getCallableObj());
        }

        return callables;
    }

    public Callable<String> getCallableObj() {
        int time = ThreadLocalRandom.current().nextInt(2, 10);

        return getCallableObj(time);
    }
    public Callable<String> getCallableObj(int time) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                API api = new HttpCall(time);
                System.out.println("calling callback call method");
                return api.call();
            }
        };
    }
}
