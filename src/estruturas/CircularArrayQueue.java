package estruturas;

public class CircularArrayQueue<E> implements MyQueue<E>
{
    private E[] elements;      // Array que armazena os dados
    private int front;         // Indice do inicio (frente)
    private int rear;          // Indice do final (tras)
    private int count;         // Quantos elementos
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public CircularArrayQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
        front = 0;
        rear = -1;  // -1 significa: fila vazia!
        count = 0;
    }

    @Override
    public void enqueue(E element) {
        if (count == elements.length) {
            throw new IllegalStateException("Fila cheia");
        }
        rear = (rear + 1) % elements.length;
        elements[rear] = element;
        count++;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Fila vazia");
        }
        E element = elements[front];
        elements[front] = null;      // Libera referencia
        front = (front + 1) % elements.length;
        count--;
        return element;
    }

    @Override
    public E front() {
        if (isEmpty()) throw new IllegalStateException("Fila vazia");
        return elements[front];
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }
}
