package io.sancta.sanctorum.structures.queue;

public interface Queue<T> {

    void put(T element);

    T peek();

    T poll();
}
