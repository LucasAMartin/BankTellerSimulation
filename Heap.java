
import java.util.Iterator;

public class Heap<Key> implements Iterable<Key> {
    private Key[] heap;
    private int size;


    public Heap(int initCapacity)
    {
        heap = (Key[]) new Object[initCapacity + 1];
        size = 0;
    }

    public Heap()
    {
        this(1);
    }


    public boolean isEmpty()
    {
        return size == 0;
    }


    public Key max()
    {
        if (isEmpty())
        {
            return null;
        }
        return heap[1];
    }


    private void swapKeys(int i, int j)
    {
        Key exchange = heap[i];
        heap[i] = heap[j];
        heap[j] = exchange;
    }

    private void resize(int capacity)
    {
        assert capacity > size;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= size; i++) temp[i] = heap[i];
        heap = temp;
    }

    public void insert(Key x)
    {
        if (size >= heap.length - 1) resize(2 * heap.length);
        heap[++size] = x;
        swimUp(size);
    }

    public Key deleteMaximum()
    {
        if (isEmpty())
        {
            return null;
        }
        Key max = heap[1];
        swapKeys(1, size--);
        sinkDown(1);
        heap[size+1] = null;
        if ((size > 0) && (size == (heap.length - 1) / 4)) resize(heap.length / 2);
        return max;
    }

    private void swimUp(int i)
    {
        while (i > 1 && isLess(i/2, i))
        {
            swapKeys(i, i/2);
            i = i/2;
        }
    }


    public int size()
    {
        return size;
    }


    private void sinkDown(int i)
    {
        while (2*i <= size)
        {
            int j = 2*i;
            if (j < size && isLess(j, j+1)) j++;
            if (!isLess(i, j)) break;
            swapKeys(i, j);
            i = j;
        }
    }


    private boolean isLess(int i, int j)
    {
        return ((Comparable<Key>) heap[i]).compareTo(heap[j]) < 0;
    }

    public Iterator<Key> iterator()
    {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key>
    {
        private Heap<Key> dup;

        public HeapIterator() {
            dup = new Heap<Key>(size());

            for (int i = 1; i <= size; i++)
                dup.insert(heap[i]);
        }

        public boolean hasNext()
        {
            return !dup.isEmpty();
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Key next()
        {
            if (!hasNext())
            {
                return null;
            }
            return dup.deleteMaximum();
        }
    }
}
