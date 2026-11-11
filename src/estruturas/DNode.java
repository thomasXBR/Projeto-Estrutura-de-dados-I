package estruturas;

public class DNode<E>{
    private E element; //Elemento armazenado dentro do nó.
    private DNode<E> prev; //Referência para o nó anterior
    private DNode<E> next; //Referência para o próximo nó

    public DNode (E e, DNode<E> p, DNode<E> n){ //Costutor do nó, recebe elemento, nó anterior e próximo
        element = e;
        prev = p;
        next = n;
    }

    public E getElement(){ //Retorna o elemento armazenado no nó
        return element;
    }
    public void setElement(E element){ //Altera o elemento armazenado no nó
        this.element = element;
    }
    public DNode<E> getPrev(){ //Retorna a referência para o nó anterior
        return prev;
    }
    public void setPrev(DNode<E> prev){ //Altera a referência para o nó anterior
        this.prev = prev;
    }
    public DNode<E> getNext(){ //Retorna a referência para o próximo nó
        return next;
    }
    public void setNext(DNode<E> next){ //Altera a referência para o próximo nó
        this.next = next;
    }
}