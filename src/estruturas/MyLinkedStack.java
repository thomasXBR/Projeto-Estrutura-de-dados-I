package estruturas;

public class MyLinkedStack<E> implements MyStack<E>{
    private Node<E> topNode; //Referência para o nó que está no topo da pilha
    private int size; //Quantidade de elementos na pilha

    public MyLinkedStack(){ //Construtor da pilha
        topNode = null; //pilha não possui elementos no começo
        size = 0; //Quantidade inicial de elementos
    }

    @Override
    public boolean isEmpty (){ //Verifica se a pilha está vazia
        return size == 0; //Se o size é zero, não existem elementos
    }

    @Override
    public int size(){ //Retorna a quantidade de elementos
        return size;
    }

    @Override
    public void push(E element){ //Inserir elemento no topo
        Node<E> novo = new Node<>(element, topNode); //Novo nó contendo o elemento recebido
        topNode = novo; //Novo nó é o topo da pilha
        size++; //Atualiza a quantidade de elementos    
    }

    @Override
    public E pop(){ //Remover elemento do topo
        if (isEmpty()){ //Verifica se a pilha está vazia
            throw new IllegalStateException("Pilha vazia");
        }
        E element = topNode.getElement(); //Guarda o elemento que está no topo
        topNode = topNode.getNext(); //Topo passa a apontar para o próximo nó
        size--; //Atualiza a quantidade de elementos
        return element; //Retorna o elemento removido
    }

    @Override
    public E top(){ //Consulta o elemento no topo
        if (isEmpty()){ //Verifica se a pilha está vazia
            throw new IllegalStateException("Pilha vazia");
        }
        return topNode.getElement(); //Retorna o elemento do topo sem removê-lo
    }

}