package estruturas;

public class MySLinkedList<E> implements MyLinkedList<E>{
    private Node<E> head; //Guarda o primeiro nó da lista
    private Node<E> tail; //Guarda o último nó da lista
    private int size; //Guarda a quantidade de elementos existentes

    public MySLinkedList(){ //Construtor da lista
        this.head = null; //Não existe primeiro nó no começo
        this.tail = null; //Não existe último nó no começo
        this.size = 0; //Lista começa vazia
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
        if (isEmpty()) throw new IllegalStateException(); //Verifica se a lista está vazia
        return head.getElement(); //Retorna o elemento que está no primeiro nó
    }

    @Override
    public E last(){
        if (isEmpty()) throw new IllegalStateException(); //Verifica se a lista está vazia
        return tail.getElement(); //Retorna o elemento que está no último nó
    }
    public Node<E> head(){
        return head; //Retorna o primeiro nó da lista (Percorre os elementos)
    }

    @Override
    public void addFirst(E element){
        Node<E> novo = new Node<>(element,head); //Cria um novo  nó contendo o elemento que aponta para o antigo primeiro nó
        head = novo; //Novo nó passa a ser o primeiro da lista
        if (size == 0)tail = novo; //Se a lista estava vazia, o novo nó também é o último
        size++; //Aumenta a quantidade de elementos
    }

    @Override
    public void addLast(E element){
        Node<E> novo = new Node<>(element,null); //Cria um novo nó contendo o elemento, já que é o  último aponta para null
        if (size == 0){ //Verifica se a lista está vazia
            head = novo; //Se estiver, o  novo nó é o primeiro
        } else {
            tail.setNext(novo); //Antigo último nó passa a apontar para o novo
        }
        tail = novo ; //Atualiza o último nó da lista
        size++; //Aumenta a quantidade de elementos
    }

    @Override
    public E removeFirst(){
        if (isEmpty()){ //Verifica se a lista está vazia
            throw new IllegalStateException();
        }

        E elemento = head.getElement(); //Guarda o elemento que será removido
        head = head.getNext(); //Segundo nó passa a ser o primeiro
        size--; //Diminui a quantidade de elementos
        if (size == 0){ //Se a lista ficou vazia, o último também passa a ser null
            tail = null;
        }
        return elemento; //Retorna o elemento que foi removido
    }
}
