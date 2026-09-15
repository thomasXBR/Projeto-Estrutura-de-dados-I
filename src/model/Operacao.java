package model;

public class Operacao {
    private String tipo; //tipo da operação
<<<<<<< HEAD
    private Solicitacao solicitacao; //guarda solicitação 
=======
    private Solicitacao solicitacao; //guarda solicitação
>>>>>>> 2761991d0af8238eae4b6f6ee704b8995ad04092

    public Operacao(String tipo,Solicitacao solicitacao){
        this.tipo = tipo;
        this.solicitacao = solicitacao;
    }

    public String getTipo(){ //retorna o tipo da operação
        return tipo;
    }

    public Solicitacao getSolicitacao(){ //retorna a solicitação relacionada a operação
        return solicitacao;
    }

    @Override //Define a maneira que a operação será exibida
    public String toString(){
        return tipo + "-" + solicitacao;
    }
}