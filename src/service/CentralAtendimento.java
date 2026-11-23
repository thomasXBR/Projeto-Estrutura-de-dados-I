package service;

import estruturas.MyQueue;
import estruturas.MyLinkedQueue;
import estruturas.MyStack;
import estruturas.MyLinkedStack;
import estruturas.MyDLinkedList;
import estruturas.MySLinkedList;
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
    private MySLinkedList<Solicitacao> todasSolicitacoes;
    private MyDLinkedList<Atendimento> historicoAtendimentos;

    public CentralAtendimento() {
        filaDeEspera = new MyLinkedQueue<>();
        historico = new MyLinkedStack<>();
        todasSolicitacoes = new MySLinkedList<>();
        historicoAtendimentos = new MyDLinkedList<>();
    }

    // CASE 1
    public Solicitacao cadastrarSolicitacao(int codigo, String solicitante, String descricao, String categoria, int prioridade) {
        // Evita duplicidade de código no cadastro geral
        if (pesquisarSolicitacaoPorCodigo(codigo) != null) {
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

    // Mantém a chamada antiga disponível, caso seja utilizada em outro ponto do projeto
    public Solicitacao atenderProxima() {
        return atenderProxima("Não informado");
    }

    // CASE 3
    public Solicitacao atenderProxima(String responsavel) {
        if (filaDeEspera.isEmpty()) {
            return null;
        }

        // Retira a primeira solicitação da fila
        Solicitacao atendida = filaDeEspera.dequeue();

        // Atualiza o status e registra quem realizou o atendimento
        atendida.setStatus(CONCLUIDA);
        atendida.setResponsavelAtendimento(responsavel);

        // Registra a operação para permitir consulta e desfazer
        historico.push(new Operacao(ATENDIMENTO, atendida));

        // Guarda o atendimento concluído na lista duplamente encadeada
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

        // Consulta a última operação sem removê-la ainda
        Operacao ultima = historico.top();
        Solicitacao alvo = ultima.getSolicitacao();

        if (CADASTRO.equals(ultima.getTipo())) {
            historico.pop();
            removerDaFila(alvo.getCodigo());
            todasSolicitacoes.removerPorCodigo(alvo.getCodigo());

            return "Cadastro da Solicitação " + alvo.getCodigo() + " desfeito.";
        }

        if (ATENDIMENTO.equals(ultima.getTipo())) {
            // Não devolve à fila uma solicitação que já foi removida do cadastro geral
            if (buscarIndicePorCodigo(alvo.getCodigo()) < 0) {
                return "Não foi possível desfazer: a solicitação não existe mais no cadastro geral.";
            }

            // Confirma que o último registro do histórico corresponde a esta operação
            if (historicoAtendimentos.isEmpty() ||
                    historicoAtendimentos.last().getCodigo() != alvo.getCodigo()) {
                return "Não foi possível desfazer: o histórico de atendimentos não corresponde à operação.";
            }

            historico.pop();
            alvo.setStatus(AGUARDANDO);
            alvo.setResponsavelAtendimento("Não atribuído");
            devolverParaFrenteDaFila(alvo);
            historicoAtendimentos.removeLast();

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

        // Exibe todos os elementos da lista simplesmente encadeada
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
        Solicitacao solicitacao = pesquisarSolicitacaoPorCodigo(codigo);

        if (solicitacao == null) {
            return null;
        }

        // Não permite apagar uma solicitação que já faz parte do histórico concluído
        if (CONCLUIDA.equalsIgnoreCase(solicitacao.getStatus())) {
            return null;
        }

        // Remove da fila, caso ainda esteja aguardando, e do cadastro geral pelo código
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

        // Percorre a lista simples por índice e compara o status de cada solicitação
        for (int i = 0; i < todasSolicitacoes.size(); i++) {
            Solicitacao solicitacao = todasSolicitacoes.buscarPorIndice(i);

            if (solicitacao.getStatus().equalsIgnoreCase(statusBuscado)) {
                texto += solicitacao + "\n";
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

        String texto = "\n--- Solicitações na categoria: " + categoria.toUpperCase() + " ---\n";
        boolean encontrou = false;

        // Percorre a lista simples por índice e compara a categoria de cada solicitação
        for (int i = 0; i < todasSolicitacoes.size(); i++) {
            Solicitacao solicitacao = todasSolicitacoes.buscarPorIndice(i);

            if (solicitacao.getCategoria().equalsIgnoreCase(categoria)) {
                texto += solicitacao + "\n";
                encontrou = true;
            }
        }

        if (!encontrou) {
            return "Nenhuma solicitação encontrada para a categoria: " + categoria;
        }

        return texto;
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

    // Consulta o primeiro atendimento registrado no histórico
    public Atendimento consultarPrimeiroAtendimento() {
        if (historicoAtendimentos.isEmpty()) {
            return null;
        }

        return historicoAtendimentos.first();
    }

    // Consulta o último atendimento registrado no histórico
    public Atendimento consultarUltimoAtendimento() {
        if (historicoAtendimentos.isEmpty()) {
            return null;
        }

        return historicoAtendimentos.last();
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

    // Procura o índice de uma solicitação pelo código na lista simplesmente encadeada
    // Retorna -1 quando o código não existe
    private int buscarIndicePorCodigo(int codigo) {
        for (int i = 0; i < todasSolicitacoes.size(); i++) {
            if (todasSolicitacoes.buscarPorIndice(i).getCodigo() == codigo) {
                return i;
            }
        }

        return -1;
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