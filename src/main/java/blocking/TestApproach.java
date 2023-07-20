package blocking;

public abstract class TestApproach {
    String name;

    long start;

    public TestApproach(String name) {
        this.name = name;
    }

    public void start() {
        System.out.println("Starting test - " + name + " -- " + System.currentTimeMillis());
        start = System.currentTimeMillis();

        run();

        long end =System.currentTimeMillis();

        System.out.println("Ending test - " + name + " -- " + end);
        System.out.println("Time taken for test - " + name + " -- " + (end - start));
    }

    protected abstract void run();


}
