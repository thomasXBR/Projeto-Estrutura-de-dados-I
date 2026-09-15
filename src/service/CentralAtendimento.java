package service;

import estruturas.MyQueue;
import estruturas.CircularArrayQueue;
import estruturas.MyStack;
import estruturas.ArrayStack;
import model.Solicitacao;
import model.Operacao;

public class CentralAtendimento {
    private MyQueue<Solicitacao> filadeEspera;

    Solicitacao novaSolicitacao = new Solicitacao(codigo, nome, descricao, categoria, prioridade);
    filaDeEspera.enqueue(novaSolicitacao);

    // Cria uma cópia exata do ticket para mandar pro histórico com o status "CADASTRO"
    Solicitacao copiaCadastro = new Solicitacao(codigo, nome, descricao, categoria, prioridade);
    Operacao opCadastro = new Operacao("CADASTRO", copiaCadastro);

    // Insere a operação com a cópia no topo da pilha
    historico.push(opCadastro);


}
