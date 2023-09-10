package trie;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrieTest {

    @Test
    public void firstTest() {
        Trie trie = Trie.build(new String[] {"a"});

        assertNotNull(trie);
    }


    @Test
    public void addSingleTest() {
        Trie trie = Trie.build(new String[] {"a"});

        assertNotNull(trie.has('a'));

        assertTrue(trie.has('a').isComplete());
    }

    @Test
    public void addSingleNonWordTest() {
        Trie trie = Trie.build(new String[] {"a"});

        assertNull(trie.has('c'));
    }

    @Test
    public void addMultiLengthTest() {
        Trie trie = Trie.build(new String[] {"ab"});

        assertFalse(trie.has('a').isComplete());
        assertTrue(
                    trie.has('a').has('b').
                    isComplete()
        );

        assertNull(
                trie.has('a').has('c')
        );
    }

    @Test
    public void addTest() {
        Trie trie = Trie.build(new String[] {"ab"});

        trie.add("cd");

        assertFalse(trie.has('c').isComplete());
        assertTrue(
                trie.has('c').has('d').
                        isComplete()
        );

        assertNull(
                trie.has('c').has('e')
        );
    }


    @Test
    public void addNextTest() {
        Trie trie = Trie.build(new String[] {"ab"});

        Trie d = trie.addNextTrie('d');

        assertNotNull(trie.has('d'));

        assertSame(trie.has('d'), d);

        assertFalse(trie.has('d').isComplete());
    }



    @Test
    public void searchTest() {
        Trie trie = Trie.build(new String[]{"ab", "bc", "de"});

        assertTrue(trie.search("ab"));
        assertTrue(trie.search("bc"));
        assertTrue(trie.search("de"));

        assertFalse(trie.search("abc"));
        assertFalse(trie.search("bb"));
        assertFalse(trie.search("bd"));
        assertFalse(trie.search("ba"));
        assertFalse(trie.search("ae"));
    }


    @Test
    public void searchLargeRangeTest() {
        Trie trie = Trie.build(new String[]{"aB", "bC", "dE"}, 52);

        assertTrue(trie.search("aB"));
        assertTrue(trie.search("bC"));
        assertTrue(trie.search("dE"));

        assertFalse(trie.search("aBc"));
        assertFalse(trie.search("bB"));
        assertFalse(trie.search("bd"));
        assertFalse(trie.search("bA"));
        assertFalse(trie.search("aE"));
    }


    @Test
    public void nextLinkTest() {
        Trie trie = Trie.build(new String[]{"ab", "bc", "de"});

        Trie.NextLink nl = trie.nextLinkFactory();

        assertNotNull(nl);

        assertInstanceOf(Trie.NextLink.class, nl);

    }
}
