package service;

import estruturas.MyQueue;
import estruturas.CircularArrayQueue;
import estruturas.MyStack;
import estruturas.ArrayStack;
import model.Solicitacao;
import model.Operacao;

public class CentralAtendimento {
    public static final String CADASTRO = "CADASTRO";
    public static final String ATENDIMENTO = "ATENDIMENTO";

    public static final String AGUARDANDO = "AGUARDANDO";
    public static final String CONCLUIDA = "CONCLUÍDA";

    private static final int CAPACIDADE_FILA = 10;

    private MyQueue<Solicitacao> filaDeEspera;
    private MyStack<Operacao> historico;

    public CentralAtendimento(){
        filaDeEspera = new CircularArrayQueue<>();
        historico = new ArrayStack<>();
    }

    //CASE 1
    public Solicitacao cadastrarSolicitacao(int codigo,String solicitante,String descricao,String categoria,int prioridade){
        if (filaEstaCheia()){
            return null;
        }

        Solicitacao nova = new Solicitacao(codigo,solicitante,descricao,categoria,prioridade);
        filaDeEspera.enqueue(nova);
        historico.push(new Operacao(CADASTRO, nova));
        return nova;
    }

    //CASE 2
    public Solicitacao consultarProxima(){
        if (filaDeEspera.isEmpty()){
            return null;
        }
        return filaDeEspera.front();
    }

    //CASE 3
    public Solicitacao atenderProxima(){
        if (filaDeEspera.isEmpty()){
            return null;
        }

        Solicitacao atendida = filaDeEspera.dequeue();
        atendida.setStatus(CONCLUIDA);
        historico.push(new Operacao(ATENDIMENTO, atendida));

        return atendida;
    }

    //CASE 4
    public String listarFila(){
        if (filaDeEspera.isEmpty()){
            return "A fila de espera está vazia";
        }

        String texto = "\n--- Fila de Solicitações (Aguardando) ---\n";
        int tamanho = filaDeEspera.size();

        for (int i = 0; i < tamanho; i++){
            Solicitacao atual = filaDeEspera.dequeue();
            texto += (i + 1) + "° da fila: " + atual + "\n";
            filaDeEspera.enqueue(atual);
        }
        return texto;
    }

    //CASE 5
    public int getQuantidadeAguardando(){
        return filaDeEspera.size();
    }

    //CASE 6
    public Operacao consultarUltimaOperacao(){
        if (historico.isEmpty()){
            return null;
        }

        return historico.top();
    }

    //CASE 7
    public String listarHistorico(){
        if (historico.isEmpty()){
            return "Nenbuma operação registrada até o momento.";
        }

        String texto = "\n--- Histórico de Operações ---\n";

        MyStack<Operacao> auxiliar = new ArrayStack<>();

        while(!historico.isEmpty()){
            Operacao op = historico.pop();
            texto += op + "\n";
            auxiliar.push(op);
        }

        while(!auxiliar.isEmpty()){
            historico.push(auxiliar.pop());
        }

        return texto;
    }

    //CASE 8
    public String desfazerUltimaOperacao(){
        if (historico.isEmpty()){
            return null;
        }
        Operacao ultima = historico.pop();
        Solicitacao alvo =ultima.getSolicitacao();

        if (CADASTRO.equals(ultima.getTipo())){
            removerDaFila(alvo.getCodigo());
            return "Cadastro da Solicitação " + alvo.getCodigo() + " desfeito";
        }

        if (ATENDIMENTO.equals(ultima.getTipo())){
            alvo.setStatus(AGUARDANDO);
            devolverParaFrenteDaFila(alvo);
            return "Atendimento da Solicitação " + alvo.getCodigo() + " desfeito";
        }
        return "Operação não reconhecida";
    }

    //Remover da fila
    private void removerDaFila(int codigo){
        MyQueue<Solicitacao> novaFila = new CircularArrayQueue<>();
        int tamanho = filaDeEspera.size();
        for( int i = 0; i < tamanho; i++){
            Solicitacao atual = filaDeEspera.dequeue();

            if (atual.getCodigo() != codigo){
                novaFila.enqueue(atual);
            }
        }
        filaDeEspera = novaFila;
    }

    //Colocar na frente da fila
    private void devolverParaFrenteDaFila(Solicitacao solicitacao){
        MyQueue<Solicitacao> novaFila = new CircularArrayQueue<>();
        novaFila.enqueue(solicitacao);

        while(!filaDeEspera.isEmpty()){
            novaFila.enqueue(filaDeEspera.dequeue());
        }
        filaDeEspera = novaFila;
    }

    //Usados na Main
    public boolean filaEstaVazia(){
        return filaDeEspera.isEmpty();
    }

    public boolean filaEstaCheia(){
        return filaDeEspera.size() == CAPACIDADE_FILA;
    }

    public int getCapacidadeFila(){
        return CAPACIDADE_FILA;
    }

    public boolean historicoEstaVazio(){
        return historico.isEmpty();
    }

    public int getQuantidadeOperacoes(){
        return historico.size();
    }
}