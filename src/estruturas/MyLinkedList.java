package estruturas;

public interface MyLinkedList<E> {
    int size(); //Retorna a quantidade de elementos da lista
    boolean isEmpty(); //Verifica se a lista está vazia
    E first(); //Retorna o primeiro elemento sem removê-lo
    E last(); //Retorna o último elemento sem removê-lo
    void addFirst(E element); //Insere um elemento no início da lista
    void addLast(E element); //Insere um elemento no final da lista
    E removeFirst(); //Remove e retorna o primeiro elemento
}