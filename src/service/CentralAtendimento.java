package service;

import estruturas.MyQueue;
import estruturas.CircularArrayQueue;
import estruturas.MyStack;
import estruturas.ArrayStack;
import model.Solicitacao;
import model.Operacao;

public class CentralAtendimento {
    // Tipos de operação
    public static final String CADASTRO = "CADASTRO";
    public static final String ATENDIMENTO = "ATENDIMENTO";

    // Status de solicitação
    public static final String AGUARDANDO = "AGUARDANDO";
    public static final String CONCLUIDA = "CONCLUÍDA";

    private static final int CAPACIDADE_FILA = 10;

    private MyQueue<Solicitacao> filaDeEspera; // fila guarda as solicitações que estão aguardando atendimento
    private MyStack<Operacao> historico;       // pilha guarda as operações realizadas

    public CentralAtendimento() {
        filaDeEspera = new CircularArrayQueue<>(); // Cria a fila que será usada pelo sistema
        historico = new ArrayStack<>();            // Cria a pilha que será usada pelo histórico
    }

    // CASE 1
    // Recebe os dados digitados pelo usuário na main
    public Solicitacao cadastrarSolicitacao(int codigo, String solicitante, String descricao, String categoria, int prioridade) {
        if (filaEstaCheia()) { // verifica se a fila está cheia
            return null;
        }

        // Cria a solicitação com os dados recebidos
        Solicitacao nova = new Solicitacao(codigo, solicitante, descricao, categoria, prioridade);
        filaDeEspera.enqueue(nova);                   // coloca a nova solicitação no final da fila
        historico.push(new Operacao(CADASTRO, nova)); // Registra no histórico
        return nova;                                  // Retorna a solicitação criada
    }

    // CASE 2
    public Solicitacao consultarProxima() { // Retorna a primeira solicitação da fila sem remover
        if (filaDeEspera.isEmpty()) {      // verifica se a fila está vazia
            return null;
        }
        return filaDeEspera.front();       // consulta o primeiro sem remover
    }

    // CASE 3
    public Solicitacao atenderProxima() { // Retira a primeira solicitação da fila
        if (filaDeEspera.isEmpty()) {     // Verifica se tem alguém para atender
            return null;
        }

        Solicitacao atendida = filaDeEspera.dequeue();        // Remove o primeiro elemento da fila
        atendida.setStatus(CONCLUIDA);                        // Muda o status após ser atendida
        historico.push(new Operacao(ATENDIMENTO, atendida));  // Registra no histórico

        return atendida; // retorna a solicitação atendida
    }

    // CASE 4
    public String listarFila() { // Retorna todas as solicitações que aguardam atendimento
        if (filaDeEspera.isEmpty()) {
            return "A fila de espera está vazia";
        }

        String texto = "\n--- Fila de Solicitações (Aguardando) ---\n";
        int tamanho = filaDeEspera.size(); // Guarda o tamanho

        for (int i = 0; i < tamanho; i++) { // Percorre a fila
            Solicitacao atual = filaDeEspera.dequeue(); // Retira o primeiro elemento
            texto += (i + 1) + "° da fila: " + atual + "\n"; // Coloca a solicitação no texto
            filaDeEspera.enqueue(atual); // Coloca o elemento novamente no final
        }
        return texto; // Retorna todo o texto
    }

    // CASE 5
    public int getQuantidadeAguardando() { // Retorna a quantidade de solicitações aguardando na fila
        return filaDeEspera.size();
    }

    // CASE 6
    public Operacao consultarUltimaOperacao() { // Retorna a operação no topo da pilha sem remover
        if (historico.isEmpty()) {
            return null;
        }
        return historico.top();
    }

    // CASE 7
    public String listarHistorico() { // Mostra as operações realizadas
        if (historico.isEmpty()) {
            return "Nenhuma operação registrada até o momento.";
        }

        String texto = "\n--- Histórico de Operações ---\n";
        MyStack<Operacao> auxiliar = new ArrayStack<>(); // Segunda pilha para auxiliar

        while (!historico.isEmpty()) { // Enquanto houver operações
            Operacao op = historico.pop(); // Retira a operação do topo
            texto += op + "\n";
            auxiliar.push(op); // Guarda na pilha auxiliar
        }

        while (!auxiliar.isEmpty()) { // Devolve para a pilha original
            historico.push(auxiliar.pop());
        }

        return texto;
    }

    // CASE 8
    public String desfazerUltimaOperacao() { // Desfaz a operação no topo da pilha
        if (historico.isEmpty()) {
            return null;
        }

        Operacao ultima = historico.pop();
        Solicitacao alvo = ultima.getSolicitacao();

        if (CADASTRO.equals(ultima.getTipo())) {
            removerDaFila(alvo.getCodigo());
            return "Cadastro da Solicitação " + alvo.getCodigo() + " desfeito";
        }

        if (ATENDIMENTO.equals(ultima.getTipo())) {
            alvo.setStatus(AGUARDANDO);
            devolverParaFrenteDaFila(alvo);
            return "Atendimento da Solicitação " + alvo.getCodigo() + " desfeito";
        }

        return "Operação não reconhecida";
    }

    // Remover da fila
    private void removerDaFila(int codigo) {
        MyQueue<Solicitacao> novaFila = new CircularArrayQueue<>(); // Cria uma nova fila vazia
        int tamanho = filaDeEspera.size();
        for (int i = 0; i < tamanho; i++) {
            Solicitacao atual = filaDeEspera.dequeue();
            if (atual.getCodigo() != codigo) {
                novaFila.enqueue(atual);
            }
        }
        filaDeEspera = novaFila; // Atualiza a referência
    }

    // Colocar na frente da fila
    private void devolverParaFrenteDaFila(Solicitacao solicitacao) {
        MyQueue<Solicitacao> novaFila = new CircularArrayQueue<>();
        novaFila.enqueue(solicitacao); // Primeiro a solicitação revertida

        while (!filaDeEspera.isEmpty()) { // Coloca os outros elementos atrás dela
            novaFila.enqueue(filaDeEspera.dequeue());
        }
        filaDeEspera = novaFila;
    }

    // Usados na Main
    public boolean filaEstaVazia() {
        return filaDeEspera.isEmpty();
    }

    public boolean filaEstaCheia() {
        return filaDeEspera.size() == CAPACIDADE_FILA;
    }

    public int getCapacidadeFila() {
        return CAPACIDADE_FILA;
    }

    public boolean historicoEstaVazio() {
        return historico.isEmpty();
    }

    public int getQuantidadeOperacoes() {
        return historico.size();
    }
}