package model;

public class Atendimento {
    private Solicitacao solicitacao; //Guarda a solicitação que foi atendida 
    public Atendimento(Solicitacao solicitacao) { //Construtor da classe Atendimento
        this.solicitacao = solicitacao; //Associa a solicitação recebida ao atendimento
    }

    public Solicitacao getSolicitacao(){ //Retorna a solicitação associada ao atendimento
        return solicitacao;
    }

    public int getCodigo(){ //Retorna o código da solicitação atendida
        return solicitacao.getCodigo();
    }

    public String getSolicitante(){ //Retorna o solicitante da solicitação atendida
        return solicitacao.getSolicitante();
    }

    public String getStatus(){ //Retorna o status da solicitação atendida
        return solicitacao.getStatus();
    }

    @Override
    public String toString(){
        return solicitacao.toString(); //Retorna as informações da solicitação associada
    }
}