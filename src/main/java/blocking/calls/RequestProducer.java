package blocking.calls;

import blocking.calls.HttpCall;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class RequestProducer {
    public API[] get() {
        API[] apis = new HttpCall[2];

        for (int i = 0; i < 2; i++) {
            apis[i] = new HttpCall(5);
        }

        return apis;
    }

    public List<Callable<String>> getCallables() {
        List<Callable<String>> callables = new ArrayList<>();

        callables.add(getCallableObj(5));
        callables.add(getCallableObj(5));

        return callables;
    }

    public Callable<String> getCallableObj() {
        int time = 5;

        return getCallableObj(time);
    }
    public Callable<String> getCallableObj(int time) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("calling callback call method");
                API api = new HttpCall(time);
                return api.call();
            }
        };
    }
}
