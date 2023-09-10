package trie;

import java.util.HashMap;
import java.util.Map;

public final class Trie {

    boolean isWord;

    NextLink next;

    final int range;

    private static int DEFAULT_RANGE = 26;

    private Trie(int range) {
        this.range = range;

        next = nextLinkFactory();
    }

    public static Trie build(String[] words, int range) {
        Trie root = new Trie(range);

        for (String w : words) {
            root.add(w);
        }

        return root;
    }

    public static Trie build(String[] words) {
        return build(words, DEFAULT_RANGE);
    }

    NextLink nextLinkFactory() {
        return new MapLink();
    }

    protected void add(String word) {
        Trie current = this;

        for (int i = 0; i < word.length(); i++) {
            current = current.addNextTrie(word.charAt(i));
        }

        current.isWord = true;

    }

    protected Trie addNextTrie(char ch) {
        return next.addNextTrie(ch);
    }

    public Trie has(char ch) {
        return next.has(ch);
    }

    public boolean isComplete() {
        return this.isWord;
    }


    public boolean search(String word) {
        Trie current = this;

        for (int i = 0; i < word.length(); i++) {
            current = current.has(word.charAt(i));
            if (current == null) {
                return false;
            }
        }

        return current.isWord == true;
    }

    interface NextLink {
        Trie has(char ch);

        Trie addNextTrie(char ch);
    }

    class ArrayLink implements NextLink {
        Trie[] next;

        public Trie has(char ch) {
            if (next == null) {
                return null;
            }

            return next[index(ch)];
        }

        void initNext() {
            if (next == null) {
                next = new Trie[Trie.this.range];
            }
        }

        public Trie addNextTrie(char ch) {
            int index = index(ch);

            initNext();

            if (this.next[index] == null) {
                this.next[index] = new Trie(Trie.this.range);
            }

            return this.next[index];
        }

        private int index(char ch) {
            int index = ch - 'a';
            return index;
        }

    }

    class MapLink implements NextLink {

        Map<Character, Trie> next;

        public Trie has(char ch) {
            if (next == null) {
                return null;
            }

            return next.get(ch);
        }

        void initNext() {
            if (next == null) {
                next = new HashMap<>();
            }
        }

        public Trie addNextTrie(char ch) {
            initNext();

            next.putIfAbsent(ch, new Trie(Trie.this.range));

            return this.next.get(ch);
        }
    }

//    public <R> R search() {
//
//    }
}
