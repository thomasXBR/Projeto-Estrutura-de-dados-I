package estruturas;

public class MyDLinkedList<E> implements MyLinkedList<E>{
    private int size; //Guarda a quantidade de elementos existentes na lista
    private DNode<E> header; //Nó sentinela do início (não armazena elemento válido)
    private DNode<E> trailer; //Nó sentinela do final (não armazena elemento válido)

    public MyDLinkedList(){ //Construtor da lista
        //Cria os dois nós sentinelas (lista não possui elementos no começo)
        header = new DNode<>(null, null, null);
        trailer = new DNode<>(null, header,null);
        //Faz o primeiro nó apontar para o último (lista vazia com dois sentinelas)
        header.setNext(trailer);
        size = 0;
    }

    @Override //Retorna a quantidade de elementos da lista
    public int size() {
        return size;
    }

    @Override //Verifica se a lista está vazia
    public boolean isEmpty(){
        return size == 0;
    }

    @Override //Consulta o primeiro elemento da lista
    public E first(){
        if (isEmpty()) //Não existe primeiro se a lista estiver vazia
            throw new IllegalStateException();
        return header.getNext().getElement(); //Primeiro elemento verdadeiro está depois do header
    }

    @Override //Consulta o último elemento da lista
    public E last(){
        if (isEmpty()) //Não existe último se a lista estiver vazia
            throw new IllegalStateException();
        return trailer.getPrev().getElement(); //O último elemento verdadeiro está antes do trailer
    }

    private void addBetween(E e, DNode<E> pred, DNode<E> succ) { //Método auxiliar para inserir um elemento entre dois nós já existentes
        DNode<E> novo = new DNode<>(e, pred, succ); //Cria um novo nó ligado aos dois nós recebidos
        pred.setNext(novo); //Faz o nó anterior apontar para o novo
        succ.setPrev(novo); //Faz o próximo nó apontar de volta para o novo
        size++; // Atualiza a quantidade de elementos
    }

    @Override //Insere um elemento no início da lista
    public void addFirst(E element){
        addBetween(element, header, header.getNext()); //Insere entre o header e o primeiro nó atual( se a lista estiver vazia o header.getNext() será o trailer)
    }

    @Override //Insere um elemento no final da lista
    public void addLast(E element){
        addBetween(element, trailer.getPrev(), trailer); //Insere entre o último nó atual e o trailer
    }

    private E remove(DNode<E> node){ //Método auxiliar para remover um nó específico 
        DNode<E> pred = node.getPrev(); //Guarda o nó anterior
        DNode<E> succ = node.getNext(); //Guarda o próximo nó
        //Liga diretamente o anterior ao próximo, retirando o intermediário da lista
        pred.setNext(succ);
        succ.setPrev(pred);
        size--; //Atualiza a quantidade de elementos
        //Desliga o nó removido da lista
        node.setNext(null);
        node.setPrev(null);
        return node.getElement(); //Retorna o elemento que estava armazenado
    }

    @Override
    public E removeFirst(){ //Remove e retorna o primeiro elemento da lista
        if (isEmpty()) { //Verifica se existe algum elemento para remover
            throw new IllegalStateException("Lista vazia");
        }
        return remove(header.getNext());//Remove o nó que está logo depois do header
    }

    public E removeLast(){ //Remove e retorna o último elemento da lista
        if (isEmpty()){ //Verifica se a lista está vazia
            throw new IllegalStateException("Lista vazia");
        }
        return remove(trailer.getPrev()); //Remove o nó que está logo antes do trailer
    }

    public void reverse(){ //Inverte a ordem dos elementos da lista
        DNode<E> no = header; //Começa no header e percorre todos os nós
        DNode<E> temp; //Variável temporária para guardar uma referência
        while (no != null) {
            temp = no.getNext(); //Guarda o próximo nó antes de trocar as referências
            //Inverte as referências (next aponta para prev e prev aponta para next)
            no.setNext(no.getPrev());
            no.setPrev(temp);
            no = temp; //Avança utilizando a referência original next
        }
        //Header antigo passa a ser o trailer e o trailer antigo passa a ser o header
        temp = header;
        header = trailer;
        trailer = temp;
    }

    public void imprime(){ //Exibe todos os elementos da lista, do início ao fim
        DNode<E> no = header.getNext(); //Começa pelo primeiro elemento verdadeiro
        while (no.getNext() != null) { //Continua enquanto não chegar ao trailer
            System.out.print(no.getElement() + " "); //Mostra o elemento armazenado
            no = no.getNext(); //Avança para o próximo nó
        }
        System.out.println();
    }
}