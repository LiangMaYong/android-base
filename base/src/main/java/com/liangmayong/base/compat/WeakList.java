package com.liangmayong.base.compat;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractSequentialList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class WeakList<E> extends AbstractSequentialList<E> implements Queue<E> {

    private ReferenceQueue<E> queue = new ReferenceQueue<E>();

    private Entry root = new Entry();

    private int size = 0;

    private class Entry extends WeakReference<E> {

        private Entry prev, next;

        private Entry() {
            super(null, null);
            this.prev = this;
            this.next = this;
        }

        private Entry(E referent, Entry next, Entry prev) {
            super(referent, queue);
            prev.next = this;
            this.prev = prev;
            this.next = next;
            next.prev = this;
        }

        private void remove() {
            prev.next = this.next;
            next.prev = this.prev;

            this.prev = null;
            this.next = null;
        }
    }

    private Entry addBefore(E e, Entry entry) {
        Entry newEntry = new Entry(e, entry, entry.prev);
        size++;
        modCount++;
        return newEntry;
    }

    private E remove(Entry e) {
        if (e == root) {
            throw new NoSuchElementException();
        }

        E result = e.get();
        e.remove();
        size--;
        modCount++;
        return result;
    }

    @SuppressWarnings("unchecked")
    private void expungeStaleEntries() {
        if (size == 0) {
            return;
        }
        for (; ; ) {
            Entry e = (Entry) queue.poll();
            if (e == null) {
                break;
            }
            remove(e);
        }
    }

    private Entry find(E element) {
        for (Entry e = root.next; e != root; e = e.next) {
            if (element.equals(e.get()))
                return e;
        }
        return null;
    }

    /* private */Entry find(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Entry e = root;
        if (index < size / 2) {
            for (int i = 0; i <= index; i++)
                e = e.next;
        } else {
            for (int i = size; i > index; i--)
                e = e.prev;
        }
        return e;
    }

    //
    //
    //

    public void addFirst(E e) {
        if (e == null) {
            throw new IllegalArgumentException();
        }
        expungeStaleEntries();
        addBefore(e, root.next);
    }

    public void addLast(E e) {
        if (e == null) {
            throw new IllegalArgumentException();
        }
        expungeStaleEntries();
        addBefore(e, root);
    }

    public E getFirst() {
        expungeStaleEntries();
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return root.next.get();
    }

    public E getLast() {
        expungeStaleEntries();
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return root.prev.get();
    }

    public E removeFirst() {
        expungeStaleEntries();
        return remove(root.next);
    }

    public E removeLast() {
        expungeStaleEntries();
        return remove(root.prev);
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        expungeStaleEntries();
        @SuppressWarnings("unchecked")
        E e = (E) o;
        return find(e) != null;
    }

    public int size() {
        expungeStaleEntries();
        return size;
    }

    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new IllegalArgumentException();
        }
        expungeStaleEntries();
        addBefore(e, root);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o != null) {
            expungeStaleEntries();

            @SuppressWarnings("unchecked")
            Entry e = find((E) o);
            if (e != null) {
                remove(e);
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        for (Entry e = root.next; e != root; ) {
            Entry next = e.next;
            remove(e);
            e = next;
        }
        root.next = root.prev = root;
        size = 0;
        modCount++;
    }

    //
    //
    //

    @Override
    public Iterator<E> iterator() {
        expungeStaleEntries();
        return new Itr();
    }

    private class Itr implements Iterator<E> {

        private Entry current = root.next, lastRet = null;

        private int expectedModCount = modCount;

        public boolean hasNext() {
            return current != root;
        }

        public E next() {
            checkForComodification();
            lastRet = current;
            current = current.next;
            return lastRet.get();
        }

        public void remove() {
            if (lastRet == null) {
                throw new IllegalStateException();
            }
            checkForComodification();
            WeakList.this.remove(lastRet);
            lastRet = null;
            expectedModCount = modCount;
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListItr(index);
    }

    private class ListItr implements ListIterator<E> {
        private Entry lastReturned = root;

        private Entry next;

        private int nextIndex;

        private int expectedModCount = modCount;

        ListItr(int index) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            if (index < size / 2) {
                next = root.next;
                for (nextIndex = 0; nextIndex < index; nextIndex++)
                    next = next.next;
            } else {
                next = root;
                for (nextIndex = size; nextIndex > index; nextIndex--)
                    next = next.prev;
            }
        }

        public boolean hasNext() {
            return nextIndex != size;
        }

        public E next() {
            checkForComodification();
            if (nextIndex == size)
                throw new NoSuchElementException();

            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.get();
        }

        public boolean hasPrevious() {
            return nextIndex != 0;
        }

        public E previous() {
            if (nextIndex == 0)
                throw new NoSuchElementException();

            lastReturned = next = next.prev;
            nextIndex--;
            checkForComodification();
            return lastReturned.get();
        }

        public int nextIndex() {
            return nextIndex;
        }

        public int previousIndex() {
            return nextIndex - 1;
        }

        public void remove() {
            checkForComodification();
            Entry lastNext = lastReturned.next;
            try {
                WeakList.this.remove(lastReturned);
            } catch (NoSuchElementException e) {
                throw new IllegalStateException();
            }
            if (next == lastReturned)
                next = lastNext;
            else
                nextIndex--;
            lastReturned = root;
            expectedModCount++;
        }

        public void set(E e) {
            throw new UnsupportedOperationException();
        }

        public void add(E e) {
            checkForComodification();
            lastReturned = root;
            addBefore(e, next);
            nextIndex++;
            expectedModCount++;
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    //
    // Queue
    //

    public E peek() {
        expungeStaleEntries();
        if (size == 0) {
            return null;
        }
        return root.next.get();
    }

    public E element() {
        return getFirst();
    }

    public E poll() {
        expungeStaleEntries();
        if (size == 0)
            return null;
        return remove(root.next);
    }

    public E remove() {
        return removeFirst();
    }

    public boolean offer(E e) {
        return add(e);
    }

}