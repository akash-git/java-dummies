package mmap;

import java.nio.channels.FileChannel;

public class Writer {
    private static int MAX = 5;
    public static void main(String[] args) {

        try {
            MMap instance = MMap.get(Constant.fileName, FileChannel.MapMode.READ_WRITE);
            Writer.Task task = new Writer.Task(instance);
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

            for (int i = 0; i < 100; i++) {
                System.out.println("writer mmap loop");
                updateMmap();
                try {
                    Thread.sleep(5000);
                }catch (Exception e) {}
            }
        }

        void updateMmap() {
            mmap.reload();

            char prev = (char)mmap.read(0);
            for (int i = 1; i < mmap.capacity(); i++) {
//                char c = (char)(((int)mmap.read(i) + 1) % MAX);
                char c = (char)mmap.read(i);

                try {
                    mmap.write(i, (byte) prev);
                    prev = c;
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }


            try {
                mmap.write(0, (byte) prev);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            System.out.println("");

            printMmap();

            mmap.load();
        }

        void printMmap() {
            System.out.print("Writer - ");
            for (int i = 0; i < mmap.capacity(); i++) {
                System.out.print((char)mmap.read(i));
            }
            System.out.println("");
        }
    }
}
