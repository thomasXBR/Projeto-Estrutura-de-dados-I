package service;

import estruturas.MyQueue;
import estruturas.MyLinkedQueue;
import estruturas.MyStack;
import estruturas.MyLinkedStack;
import estruturas.MyDLinkedList;
import model.Solicitacao;
import model.Operacao;
import model.Atendimento;

public class CentralAtendimento {
    // Tipos de operação
    public static final String CADASTRO = "CADASTRO";
    public static final String ATENDIMENTO = "ATENDIMENTO";

    // Status de solicitação
    public static final String AGUARDANDO = "AGUARDANDO";
    public static final String CONCLUIDA = "CONCLUÍDA";

    private MyQueue<Solicitacao> filaDeEspera;
    private MyStack<Operacao> historico;
    private MyDLinkedList<Solicitacao> todasSolicitacoes;
    private MyDLinkedList<Atendimento> historicoAtendimentos;

    public CentralAtendimento() {
        filaDeEspera = new MyLinkedQueue<>();
        historico = new MyLinkedStack<>();
        todasSolicitacoes = new MyDLinkedList<>();
        historicoAtendimentos = new MyDLinkedList<>();
    }

    // CASE 1
    public Solicitacao cadastrarSolicitacao(int codigo, String solicitante, String descricao, String categoria, int prioridade) {
        // Evita duplicidade de código no cadastro geral
        if (!todasSolicitacoes.isEmpty() && todasSolicitacoes.buscarPorCodigo(codigo) != null) {
            return null;
        }

        Solicitacao nova = new Solicitacao(codigo, solicitante, descricao, categoria, prioridade);
        filaDeEspera.enqueue(nova);
        todasSolicitacoes.addLast(nova);
        historico.push(new Operacao(CADASTRO, nova));
        return nova;
    }

    // CASE 2
    public Solicitacao consultarProxima() {
        if (filaDeEspera.isEmpty()) {
            return null;
        }
        return filaDeEspera.front();
    }

    // CASE 3
    public Solicitacao atenderProxima() {
        if (filaDeEspera.isEmpty()) {
            return null;
        }

        Solicitacao atendida = filaDeEspera.dequeue();
        atendida.setStatus(CONCLUIDA);
        historico.push(new Operacao(ATENDIMENTO, atendida));
        historicoAtendimentos.addLast(new Atendimento(atendida));

        return atendida;
    }

    // CASE 4
    public String listarFila() {
        if (filaDeEspera.isEmpty()) {
            return "A fila de espera está vazia.";
        }

        String texto = "\n--- Fila de Solicitações (Aguardando) ---\n";
        int tamanho = filaDeEspera.size();

        for (int i = 0; i < tamanho; i++) {
            Solicitacao atual = filaDeEspera.dequeue();
            texto += (i + 1) + "° da fila: " + atual + "\n";
            filaDeEspera.enqueue(atual);
        }
        return texto;
    }

    // CASE 5
    public int getQuantidadeAguardando() {
        return filaDeEspera.size();
    }

    // CASE 6
    public Operacao consultarUltimaOperacao() {
        if (historico.isEmpty()) {
            return null;
        }
        return historico.top();
    }

    // CASE 7
    public String listarHistorico() {
        if (historico.isEmpty()) {
            return "Nenhuma operação registrada até o momento.";
        }

        String texto = "\n--- Histórico de Operações ---\n";
        MyStack<Operacao> auxiliar = new MyLinkedStack<>();

        while (!historico.isEmpty()) {
            Operacao op = historico.pop();
            texto += op + "\n";
            auxiliar.push(op);
        }

        while (!auxiliar.isEmpty()) {
            historico.push(auxiliar.pop());
        }

        return texto;
    }

    // CASE 8
    public String desfazerUltimaOperacao() {
        if (historico.isEmpty()) {
            return null;
        }

        Operacao ultima = historico.pop();
        Solicitacao alvo = ultima.getSolicitacao();

        if (CADASTRO.equals(ultima.getTipo())) {
            removerDaFila(alvo.getCodigo());
            todasSolicitacoes.removerPorCodigo(alvo.getCodigo());
            return "Cadastro da Solicitação " + alvo.getCodigo() + " desfeito.";
        }

        if (ATENDIMENTO.equals(ultima.getTipo())) {
            alvo.setStatus(AGUARDANDO);
            devolverParaFrenteDaFila(alvo);
            if (!historicoAtendimentos.isEmpty()) {
                historicoAtendimentos.removeLast();
            }
            return "Atendimento da Solicitação " + alvo.getCodigo() + " desfeito.";
        }

        return "Operação não reconhecida.";
    }

    // CASE 9
    public void exibirTodasSolicitacoes() {
        if (todasSolicitacoes.isEmpty()) {
            System.out.println("Nenhuma solicitação cadastrada no sistema.");
            return;
        }
        System.out.println("\n--- Todas as Solicitações (Cadastro Geral) ---");
        todasSolicitacoes.imprime();
    }

    // CASE 10
    public Solicitacao pesquisarSolicitacaoPorCodigo(int codigo) {
        if (todasSolicitacoes.isEmpty()) {
            return null;
        }
        return todasSolicitacoes.buscarPorCodigo(codigo);
    }

    // CASE 11
    public Solicitacao removerSolicitacaoPorCodigo(int codigo) {
        if (todasSolicitacoes.isEmpty()) {
            return null;
        }
        removerDaFila(codigo);
        return todasSolicitacoes.removerPorCodigo(codigo);
    }

    // CASE 12
    public String buscarPorStatus(String statusBuscado) {
        if (todasSolicitacoes.isEmpty()) {
            return "Nenhuma solicitação cadastrada no sistema.";
        }

        String texto = "\n--- Solicitações com status: " + statusBuscado.toUpperCase() + " ---\n";
        boolean encontrou = false;

        for (int i = 0; i < todasSolicitacoes.size(); i++) {
            Solicitacao s = todasSolicitacoes.get(i);
            if (s.getStatus().equalsIgnoreCase(statusBuscado)) {
                texto += s + "\n";
                encontrou = true;
            }
        }

        if (!encontrou) {
            return "Nenhuma solicitação encontrada com o status: " + statusBuscado;
        }

        return texto;
    }

    // CASE 13
    public String pesquisarSolicitacaoPorCategoria(String categoria) {
        if (todasSolicitacoes.isEmpty()) {
            return "Nenhuma solicitação cadastrada no sistema.";
        }

        String encontrados = todasSolicitacoes.buscarPorCategoria(categoria);

        if (encontrados.isEmpty()) {
            return "Nenhuma solicitação encontrada para a categoria: " + categoria;
        }

        return "\n--- Solicitações na categoria: " + categoria.toUpperCase() + " ---\n" + encontrados;
    }

    // CASE 14
    public void exibirAtendimentosPrimeiroAoUltimo() {
        if (historicoAtendimentos.isEmpty()) {
            System.out.println("Nenhum atendimento realizado até o momento.");
            return;
        }
        System.out.println("\n--- Histórico de Atendimentos (Primeiro ao Último) ---");
        historicoAtendimentos.imprime();
    }

    // CASE 15
    public void exibirAtendimentosUltimoAoPrimeiro() {
        if (historicoAtendimentos.isEmpty()) {
            System.out.println("Nenhum atendimento realizado até o momento.");
            return;
        }
        System.out.println("\n--- Histórico de Atendimentos (Último ao Primeiro) ---");
        historicoAtendimentos.imprimeReverso();
    }

    // CASE 16 (Apoio à navegação)
    public boolean historicoAtendimentosVazio() {
        return historicoAtendimentos.isEmpty();
    }

    public Atendimento iniciarNavegacaoAtendimentos() {
        return historicoAtendimentos.iniciarNavegacao();
    }

    public Atendimento proximoAtendimento() {
        return historicoAtendimentos.navegarProximo();
    }

    public Atendimento atendimentoAnterior() {
        return historicoAtendimentos.navegarAnterior();
    }

    // CASE 17
    public Atendimento pesquisarAtendimentoPorCodigo(int codigo) {
        if (historicoAtendimentos.isEmpty()) {
            return null;
        }
        return historicoAtendimentos.buscarPorCodigo(codigo);
    }

    // CASE 18
    public String gerarResumoSistema() {
        return "\n===============================" +
                "\nRESUMO DO SISTEMA" +
                "\n===============================" +
                "\nTotal de solicitações cadastradas: " + todasSolicitacoes.size() +
                "\nSolicitações aguardando na fila:  " + filaDeEspera.size() +
                "\nAtendimentos concluídos:          " + historicoAtendimentos.size() +
                "\nOperações na pilha de histórico:  " + historico.size() +
                "\n===============================\n";
    }

    // Métodos auxiliares privados da fila
    private void removerDaFila(int codigo) {
        MyQueue<Solicitacao> novaFila = new MyLinkedQueue<>();
        int tamanho = filaDeEspera.size();
        for (int i = 0; i < tamanho; i++) {
            Solicitacao atual = filaDeEspera.dequeue();
            if (atual.getCodigo() != codigo) {
                novaFila.enqueue(atual);
            }
        }
        filaDeEspera = novaFila;
    }

    private void devolverParaFrenteDaFila(Solicitacao solicitacao) {
        MyQueue<Solicitacao> novaFila = new MyLinkedQueue<>();
        novaFila.enqueue(solicitacao);

        while (!filaDeEspera.isEmpty()) {
            novaFila.enqueue(filaDeEspera.dequeue());
        }
        filaDeEspera = novaFila;
    }

    // Usados na Main
    public boolean filaEstaVazia() {
        return filaDeEspera.isEmpty();
    }

    public boolean historicoEstaVazio() {
        return historico.isEmpty();
    }

    public int getQuantidadeOperacoes() {
        return historico.size();
    }
}