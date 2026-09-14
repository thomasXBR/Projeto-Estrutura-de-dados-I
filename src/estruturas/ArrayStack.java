package estrturas;

public class ArrayStack<E> implements MyStack<E>{
    private E[] elements; //Vetor que armazena os elementos da pilha
    private int topIndex; //Indica a posição do elemento que está no topo da pilha
    private static final int DEFAULT_CAPACITY = 10; //Capacidade inicial do vetor

    @SuppressWarnings("unchecked")
    public ArrayStack(){
        elements = (E[]) new Object[DEFAULT_CAPACITY]; //Cria vetor de objetos
        topIndex = -1; //ainda não existe nenhum elemento na pilha (vazia)
    }

    @Override
    public void push(E element){ //Insere elemento no topo da pilha
        if (topIndex == elements.length - 1){ //verifica se o topo já está na última posição disponível
            throw new IllegalStateException("Pilha Cheia"); //Se estver a pilha está cheia
        }
        topIndex++;
        elements[topIndex] = element; //Coloca o novo elemento na posição indicada 
    }

    @Override
    public E pop(){ //Remove e retorna o elemento que está no topo da pilha
        if (isEmpty()){ //Antes verifica se está vazia
            throw new IllegalStateException("Pilha Vazia");
        }
        E element = elements[topIndex]; //Guarda o elemento que está no topo
        elements[topIndex] = null; //Null na posição que foi ocupada
        topIndex--; 
        return element; //Retorna o elemento que foi removido
    }

    @Override
    public E top(){ //Consulta elemento no topo sem remover
        if (isEmpty()){ //Verifica se está vazia
            throw new IllegalStateException("Pilha Vazia");
        }
        return elements [topIndex]; //Retorna o elemento no topo
    }

    @Override
    public boolean isEmpty() {return topIndex == -1;} //Verifica se a pilha está vazia

    @Override
    public int size() {return topIndex + 1;} // Retorna a quantidade de elementos atualmente na pilha
}