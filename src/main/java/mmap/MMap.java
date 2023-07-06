package mmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class MMap {

    private RandomAccessFile file;

    private FileChannel.MapMode mode;

    private CharBuffer charBuffer = null;
    private MappedByteBuffer mappedByteBuffer;

    private String fileName;

    MMap() {

    }

    private void init(String name, FileChannel.MapMode mode) throws MMapException {
        Path path = Paths.get(name);

        try (RandomAccessFile file1 = new RandomAccessFile(path.toFile(), getFileMode(mode))) {
            file = file1;
            fileName = name;

            mappedByteBuffer = file.getChannel().map(mode, 0, file.length());

            if (mappedByteBuffer != null) {
                charBuffer = Charset.forName("UTF-8").decode(mappedByteBuffer);
            }
            this.mode = mode;
        }
        catch (IOException e) {
            throw new MMapException(e.getMessage());
        }
    }

    protected String getFileMode(FileChannel.MapMode mode) {
        if (mode == FileChannel.MapMode.READ_WRITE) {
            return "rw";
        }
        else if (mode == FileChannel.MapMode.PRIVATE) {
            return "rws";
        }

        return "r";
    }

    void reload() {
        reload(0);
    }

    void reload(int append) {
        try {
            Path path = Paths.get(fileName);

            if (mappedByteBuffer.capacity() != Files.size(path) || append != 0) {

                long size = Files.size(path) + append;
                System.out.println("reload - " + size);
                file.setLength(size);
                mappedByteBuffer = file.getChannel().map(mode, 0, size);
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



    int capacity() {
        return mappedByteBuffer.capacity();
    }

    byte read(int index) {
        if (index < 0 || index >= mappedByteBuffer.capacity()) {
            return 0;
        }

        return mappedByteBuffer.get(index);
    }

    boolean write(int index, byte data) throws MMapException {
        if (index < 0 || index >= mappedByteBuffer.capacity()) {
            throw new MMapException("Invalid index");
        }

        mappedByteBuffer.put(index, data);

        return true;
    }


    boolean append(byte data) throws MMapException {
        // not working because of odd behaviour
        reload(1);

        System.out.println("append - " + mappedByteBuffer.capacity() + " - " + data);

        return write(mappedByteBuffer.capacity() - 1, data);
    }

    void load() {
        if (mappedByteBuffer != null) {
            mappedByteBuffer.load();
        }
    }

    void close() {
        try {
            if (file != null) {
                file.close();
            }

            if (mappedByteBuffer != null) {
                mappedByteBuffer = null;
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public CharBuffer getCharBuffer() {
        return charBuffer;
    }

    public static MMap get(String name, FileChannel.MapMode mode) throws MMapException {
        if (mode == null) {
            throw new NullPointerException("mode is null");
        }

        Validation validation = new Validation();

        if (!validation.validateFile(name)) {
            throw new MMapException("Invalid file name");
        }

        MMap instance =  new MMap();

        instance.init(name, mode);
        return instance;
    }

    protected static class Validation {

        public boolean validateFile(String name) throws MMapException {
            if (name == null || name.equals("")) {
                throw new MMapException("Invalid file name");
            }

            return fileExists(name);
        }

        public boolean fileExists(String name) {
            File file = new File(name);
            return Files.exists(file.toPath());
        }

        public static void printPaths(File file) {
            try {
                System.out.println("Absolute Path: " + file.getAbsolutePath());
                System.out.println("Canonical Path: " + file.getCanonicalPath());
                System.out.println("Path: " + file.getPath());
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
    }


}
