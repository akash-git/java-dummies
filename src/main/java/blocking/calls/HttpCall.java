package blocking.calls;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpCall implements API {

    private static int counter = 0;

    private int index = 0;

    private static String URL = "https://httpbin.org/delay/";

    int sleepTime;

    public HttpCall(int sleep) {
        sleepTime = sleep;
        index = ++counter;
    }

    public String call() {

        try {
            String url = URL + sleepTime;
            HttpRequest request = HttpRequest.newBuilder(new URI(url)).GET().build();

            HttpClient client = HttpClient.newHttpClient();

            long start = System.currentTimeMillis();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//            System.out.println(response);
            String res = "[" + Thread.currentThread() + "] [" + index + "] " + url + " - Time taken: " + (System.currentTimeMillis() - start);
//            System.out.println(res);
            return res;

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "ERROR";
    }
}
