package estruturas;

public interface MyQueue<E> {
    void enqueue(E element);   // Insere no final
    E dequeue();               // Remove e retorna o inicio
    E front();                 // Consulta só o inicio da fila
    boolean isEmpty();         // Verifica se esta vazia
    int size();                // Retorna a quantidade
}
