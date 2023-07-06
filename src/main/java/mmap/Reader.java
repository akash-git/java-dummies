package mmap;

import java.nio.channels.FileChannel;

public class Reader {
    public static void main(String[] args) {

        try {
            MMap instance = MMap.get(Constant.fileName, FileChannel.MapMode.READ_ONLY);
            Runnable task = new Task(instance);
            Thread thread = new Thread(task);
            thread.start();
            System.out.println("main");
            thread.join();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            System.out.println("finally");
        }
    }

    static class Task implements Runnable {
        MMap mmap;

        Task(MMap mmapObj) {
            mmap = mmapObj;
        }

        @Override
        public void run() {
            System.out.println("Task");

            for (int i = 0; i < 10; i++) {

                System.out.println("reader mmap loop");
                printMmap();
                try {
                    Thread.sleep(10000);
                }catch (Exception e) {}
            }

            mmap.close();
        }

        void printMmap() {
            mmap.reload();
            System.out.print("Reader - ");
            for (int i = 0; i < mmap.capacity(); i++) {
                System.out.print((char)mmap.read(i));
            }
            System.out.println("");
        }
    }
}
