package structures.List;

import structures.AbstractCollection;
import structures.Utils;

import java.util.*;

public class DoubleLinkedList <T> extends AbstractCollection<T> implements List<T> {

    private DoubleSegment<T> firstSegment;
    private DoubleSegment<T> lastSegment;
    private int size;

    public boolean quickSort;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(T element) {
        add(size, element);
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Not correct index");

        DoubleSegment<T> newSegment = new DoubleSegment<>();
        newSegment.element = element;

        if (size == 0) {
            firstSegment = lastSegment = newSegment;
        } else if (size == index) {
            lastSegment.nextSegment = newSegment;
            newSegment.prevSegment = lastSegment;
            lastSegment = newSegment;
        } else if (index == 0) {
            firstSegment.prevSegment = newSegment;
            newSegment.nextSegment = firstSegment;
            firstSegment = newSegment;
        } else {
            DoubleSegment<T> next = getSegment(index);
            newSegment.nextSegment = next;
            newSegment.prevSegment = next.prevSegment;
            next.prevSegment.nextSegment = newSegment;
            next.prevSegment = newSegment;
        }
        size++;
    }

    @Override
    public void remove(int index) {

        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Not correct index");
        }
        if (size == 1) {
            firstSegment = null;
            lastSegment = null;
        } else if (size == 2) {
            if (index == 0) {
                firstSegment = lastSegment;
                lastSegment.prevSegment = null;
            } else {
                lastSegment = firstSegment;
                firstSegment.nextSegment = null;
            }
        } else if (index == 0) {
            firstSegment = firstSegment.nextSegment;
            firstSegment.prevSegment = null;
        } else if (index == size - 1) {
            lastSegment = lastSegment.prevSegment;
            lastSegment.nextSegment = null;
        } else {
            DoubleSegment<T> segment = getSegment(index);
            segment.prevSegment.nextSegment = segment.nextSegment;
            segment.nextSegment.prevSegment = segment.prevSegment;
        }
        size--;
    }

    @Override
    public boolean remove(T element) {

        if (size == 0) {
            return false;
        }
        if (size == 1) {
            if (Objects.equals(firstSegment.element, element)) {
                firstSegment = null;
                lastSegment = null;
                size--;
                return true;
            } else {
                return false;
            }
        }

        if (size == 2)
            if (Objects.equals(firstSegment.element, element)) {
                firstSegment = lastSegment;
                lastSegment.prevSegment = null;
                size--;
                return true;
            } else if (Objects.equals(lastSegment.element, element)) {
                lastSegment = firstSegment;
                firstSegment.nextSegment = null;
                size--;
                return true;
            } else return false;

        if (Objects.equals(firstSegment.element, element)) {
            firstSegment = firstSegment.nextSegment;
            firstSegment.prevSegment = null;
            size--;
            return true;
        } else {
            DoubleSegment<T> previous = firstSegment;
            for (int i = 1; i < size; i++) {
                if (Objects.equals(previous.nextSegment.element, element)) {
                    previous.nextSegment = previous.nextSegment.nextSegment;
                    if (i == size - 1)
                        lastSegment = previous;
                    else
                        previous.nextSegment.prevSegment = previous;
                    size--;
                    return true;
                }
                previous = previous.nextSegment;
            }
        }
        return false;
    }

    @Override
    public T get(int index) {
        return getSegment(index).element;
    }

    private DoubleSegment<T> getSegment(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Not correct index");
        }
        if (index < size / 2) {
            DoubleSegment<T> segment = firstSegment;
            for (int i = 0; i < index; i++)
                segment = segment.nextSegment;
            return segment;
        } else {
            DoubleSegment<T> segment = lastSegment;
            for (int i = size - 1; i > index; i--)
                segment = segment.prevSegment;
            return segment;
        }
    }

    @Override
    public void set(int index, T change) {
        DoubleSegment<T> segment = getSegment(index);
        segment.element = change;
    }

    @Override
    public int indexOf(T element) {
        DoubleSegment<T> segment = firstSegment;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(segment.element, element))
                return i;
            segment = segment.nextSegment;
        }
        /*DoubleSegment<T> segmentFirst = firstSegment;
        DoubleSegment<T> segmentLast = lastSegment;
        for (int i = 0, j = sizeList - 1; i <= j; i++, j--) {
            if (Objects.equals(segmentFirst.element, element))
                return i;
            if (Objects.equals(segmentLast.element, element))
                return j;
            segmentFirst = segmentFirst.nextSegment;
            segmentLast = segmentLast.prevSegment;
        }*/
        return -1;
    }

    @Override
    public void clear() {
        firstSegment = null;
        lastSegment = null;
        size = 0;
    }


    @Override
    public void sort(boolean back) {
        if (quickSort)
            sort(firstSegment, size, back);
        else {
            for (int i = size; i > 1; i--) {
                DoubleSegment<T> segment = firstSegment;
                for (int j = 1; j < i; j++) {
                    if (Utils.compare(segment.element, segment.nextSegment.element, back)) {
                        T swap = segment.element;
                        segment.element = segment.nextSegment.element;
                        segment.nextSegment.element = swap;
                    }
                    segment = segment.nextSegment;
                }
            }
        }
    }

    private void sort(DoubleSegment<T> begin, int size, boolean back) {

        if (size <= 1) {
            return;
        }
        Random r = new Random();

        DoubleSegment<T> segment = begin;
        for (int i = r.nextInt(size); i > 0; i--) {
            segment = segment.nextSegment;
        }

        T tmp = segment.element;
        segment.element = begin.element;
        begin.element = tmp;
        segment = begin.nextSegment;
        int idx = 0;

        DoubleSegment<T> current = begin;

        for (int i = 1; i < size; i++) {
            if (Utils.compare(tmp, segment.element, back)) {
                current.element = segment.element;
                segment.element = current.nextSegment.element;
                current.nextSegment.element = tmp;
                current = current.nextSegment;
                idx++;
            }
            segment = segment.nextSegment;
        }

        sortParts(begin, size, back, idx, current);
    }

    private void sortParts(DoubleSegment<T> begin, int size, boolean back, int idx, DoubleSegment<T> current) {
        if (Runtime.getRuntime().availableProcessors() > 1) { // Проверка количество ядер процессора
            // Реализация многопоточности!
            Thread t = new Thread(() -> sort(begin, idx, back)); // Создаём поток и передаём ему лямбда-выражение
            // - что будет выполняться в этом потоке
            t.start(); // Запускаем фоновый поток
            sort(current.nextSegment, size - idx - 1, back); // В текущем потоке сортируем вторую часть списка
            try {
                t.join(); // Ждём завершения фонового потока, если первая часть списка оказалась больше второй
            } catch (InterruptedException e) {
                // Во время ожидания поток может быть прерван - это проверяемое исключение
            }
        } else {
            sort(begin, idx, back);
            sort(current.nextSegment, size - idx - 1, back);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator() {
            private DoubleSegment<T> segment = firstSegment;

            @Override
            public boolean hasNext() {
                return segment != null;
            }

            @Override
            public T next() {
                if (segment == null)
                    throw new NoSuchElementException();
                T temp = segment.element;
                segment = segment.nextSegment;
                return temp;
            }
        };
    }
}
