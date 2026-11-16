package estruturas;

public class MyLinkedQueue<E> implements MyQueue<E>{
    private Node<E> frontNode; //Referência para o primeiro nó da fila
    private Node<E> rearNode; //Referência para o último nó da fila
    private int size; //Guarda a quantidade de elementos existentes na fila

    public MyLinkedQueue(){ //Construtor da fila
        frontNode = null; //No começo não existe primeiro elemento
        rearNode = null; //No começo não existe último elemento
        size = 0; //Fila começa vazia
    }

    @Override
    public void enqueue(E element){ //Insere elemento no final da fila
        Node<E> novo = new Node<>(element, null); //Cria novo nó contendo o elemento recebido
        if (isEmpty()){ //Verifica se a fila está vazia
            frontNode = novo; //Se estiver, novo nó é o primeiro e último elemento
        } else{
            rearNode.setNext(novo); //Se já existirem, antigo último nó aponta para o novo
        }
        rearNode = novo; //Atualiza a referência do último elemento
        size++; //Atualiza a quantidade de elementos
    }

    @Override
    public E dequeue(){ //Remove o elemento do início da fila
        if (isEmpty()){ //Verifica se a fila está vazia
            throw new IllegalStateException("Fila vazia");
        }
        E element = frontNode.getElement(); //Guarda o elemento que está no início
        frontNode = frontNode.getNext(); //Início da fila passa a ser o próximo nó
        size--; //Atualiza a quantidade de elementos
        if (isEmpty()){ //Verifica se a fila ficou vazia após a remoção
            rearNode = null; //Como não existe, o último deve apontar para null
        }
        return element; //Retorna o elemento removido
    }

    @Override
    public E front(){ //Consulta o primeiro elemento da fila
        if (isEmpty()){ //Verifica se a fila está vazia
            throw new IllegalStateException("Fila vazia");
        }
        return frontNode.getElement(); //Retorna o elemento que está no início
    }

    @Override
    public boolean isEmpty(){ //Verifica se a fila está vazia
        return size == 0; //size zero, fila vazia
    }

    @Override
    public int size(){ //Retorna a quantidade de elementos
        return size;
    }
}