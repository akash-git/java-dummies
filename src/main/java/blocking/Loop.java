package blocking;

import blocking.calls.API;
import blocking.calls.HttpCall;
import blocking.calls.RequestProducer;

public class Loop extends TestApproach {
    Loop() {
        super("loop");
    }

    @Override
    protected void run() {
        RequestProducer requestProducer = new RequestProducer();
        API[] apis = requestProducer.get();

        for (API api : apis) {
            api.call();
        }
    }
}
