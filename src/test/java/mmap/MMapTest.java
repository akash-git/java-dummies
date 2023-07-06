package mmap;

import org.junit.*;
import org.junit.jupiter.api.Nested;

import java.nio.channels.FileChannel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MMapTest {

    @Test(expected=NullPointerException.class)
    public void testGetInstanceNullMode() throws Exception {
        MMap instance = MMap.get("", null);
    }

    @Test(expected=MMapException.class)
    public void testGetInstanceNullFile() throws Exception {
        MMap instance = MMap.get(null, FileChannel.MapMode.READ_ONLY);
    }


    @Test
    public void testGetInstanceValidFile() throws MMapException {
        MMap instance = MMap.get(Constant.fileName, FileChannel.MapMode.READ_ONLY);
        assertTrue(instance != null);
    }

    @Test
    public void testGetFileReadMode() {
        MMap map = new MMap();

        assertThat(map.getFileMode(FileChannel.MapMode.READ_ONLY), org.hamcrest.CoreMatchers.is("r"));
    }

    @Test
    public void testGetFileWriteMode() {
        MMap map = new MMap();

        assertThat(map.getFileMode(FileChannel.MapMode.READ_WRITE), org.hamcrest.CoreMatchers.is("rw"));
    }

    @Test
    public void testGetFileProtectedMode() {
        MMap map = new MMap();

        assertThat(map.getFileMode(FileChannel.MapMode.PRIVATE), org.hamcrest.CoreMatchers.is("rws"));
    }

    @Test
    public void validateFileExistTest() {
        MMap.Validation validation = new MMap.Validation();
        assertTrue(validation.fileExists(Constant.fileName));
    }

    @Test
    public void validateFileNotExistTest() {
        MMap.Validation validation = new MMap.Validation();
        assertFalse(validation.fileExists("test1.txt"));
    }



}
