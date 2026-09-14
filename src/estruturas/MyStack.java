package estruturas;

public interface MyStack<E>{
    void push(E element); //Insere no topo da pilha
    E pop(); //Remove e retorna o elemento no topo da pilha
    E top(); //Retorna o elemento no topo sem remover
    boolean isEmpty(); //Verifica se está vazia
    int size; //Retorna a quantidade
}