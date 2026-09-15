package estruturas;

<<<<<<< HEAD
public interface Stack<E>{
=======
public interface MyStack<E>{
>>>>>>> 2761991d0af8238eae4b6f6ee704b8995ad04092
    void push(E element); //Insere no topo da pilha
    E pop(); //Remove e retorna o elemento no topo da pilha
    E top(); //Retorna o elemento no topo sem remover
    boolean isEmpty(); //Verifica se está vazia
    int size; //Retorna a quantidade
}