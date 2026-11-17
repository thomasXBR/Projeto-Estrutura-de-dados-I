package estruturas;

public class MySLinkedList<E> implements MyLinkedList<E>{
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public MySLinkedList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public int size(){ //Retorna a quantidade de elementos
        return size;
    }

    @Override 
    public boolean isEmpty(){ //Verifica se a lista está vazia
        return size == 0; // Se o size é zero, então não existem elementos
    }

    @Override
    public E first(){
        if (isEmpty()) throw new IllegalStateException();
        return head.getElement();
    }

    @Override
    public E last(){
        if (isEmpty()) throw new IllegalStateException();
        return tail.getElement();
    }
    public Node<E> head(){
        return head;
    }

    @Override
    public void addFirst(E element){
        Node<E> novo = new Node<>(element,head);
        head = novo;
        if (size == 0)tail = novo;
        size++;
    }

    @Override
    public void addLast(E element){

    }

    @Override
    public E removeFirst(){
        throw new UnsupportedOperationException("Unimplemented method 'removeFirst'");
    }
}