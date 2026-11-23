package estruturas;

import model.Solicitacao;

public class MySLinkedList<E> implements MyLinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public MySLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E first() {
        if (isEmpty()) {
            throw new IllegalStateException("Lista vazia");
        }
        return head.getElement();
    }

    @Override
    public E last() {
        if (isEmpty()) {
            throw new IllegalStateException("Lista vazia");
        }
        return tail.getElement();
    }

    @Override
    public void addFirst(E element) {
        head = new Node<>(element, head);
        if (size == 0) {
            tail = head;
        }
        size++;
    }

    @Override
    public void addLast(E element) {
        Node<E> novo = new Node<>(element, null);
        if (isEmpty()) {
            head = novo;
        } else {
            tail.setNext(novo);
        }
        tail = novo;
        size++;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Lista vazia");
        }
        E removido = head.getElement();
        head = head.getNext();
        size--;
        if (size == 0) {
            tail = null;
        }
        return removido;
    }

    // Exibe todos os elementos do início ao fim
    public void imprime() {
        Node<E> atual = head;
        while (atual != null) {
            System.out.println(atual.getElement());
            atual = atual.getNext();
        }
        System.out.println();
    }

    public E buscarPorIndice(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice inválido: " + index);
        }

        Node<E> atual = head;
        for (int i = 0; i < index; i++) {
            atual = atual.getNext();
        }
        return atual.getElement();
    }

    // Busca solicitação por código
    public E buscarPorCodigo(int codigo) {
        Node<E> atual = head;
        while (atual != null) {
            if (atual.getElement() instanceof Solicitacao) {
                Solicitacao s = (Solicitacao) atual.getElement();
                if (s.getCodigo() == codigo) {
                    return atual.getElement();
                }
            }
            atual = atual.getNext();
        }
        return null;
    }

    // Remove solicitação por código (início, meio ou fim)
    public E removerPorCodigo(int codigo) {
        if (isEmpty()) {
            return null;
        }

        Node<E> anterior = null;
        Node<E> atual = head;

        while (atual != null) {
            if (atual.getElement() instanceof Solicitacao) {
                Solicitacao s = (Solicitacao) atual.getElement();
                if (s.getCodigo() == codigo) {
                    E removido = atual.getElement();

                    // Caso 1: Remoção do primeiro elemento (head)
                    if (anterior == null) {
                        head = atual.getNext();
                        if (head == null) {
                            tail = null; // Lista ficou vazia
                        }
                    } else {
                        // Caso 2: Remoção de intermediário ou último
                        anterior.setNext(atual.getNext());
                        // Caso 3: Se removeu o último, atualiza tail
                        if (atual == tail) {
                            tail = anterior;
                        }
                    }

                    atual.setNext(null);
                    size--;
                    return removido;
                }
            }
            anterior = atual;
            atual = atual.getNext();
        }

        return null;
    }
}