package estruturas;

public class Node<E> {
    private E element;       // O dado armazenado (ex: Solicitacao ou Operacao)
    private Node<E> next;    // Apontador para o próximo nó

    // Construtor
    public Node(E element, Node<E> next) {
        this.element = element;
        this.next = next;
    }

    // Getters e Setters
    public E getElement() { return element; }
    public void setElement(E element) { this.element = element; }

    public Node<E> getNext() { return next; }
    public void setNext(Node<E> next) { this.next = next; }
}