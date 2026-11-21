package model;

public class Solicitacao {
    // Todos privados
    private int codigo;
    private String solicitante;
    private String descricao;
    private String categoria;
    private int prioridade;
    private String status;

    // Construtor
    public Solicitacao(int codigo, String solicitante, String descricao, String categoria, int prioridade) {
        this.codigo = codigo;
        this.solicitante = solicitante;
        this.descricao = descricao;
        this.categoria = categoria;
        this.prioridade = prioridade;
        this.status = "AGUARDANDO"; // Todo ticket novo começa aguardando
    }

    // Getters e Setters
    public int getCodigo() { return codigo; }
    public String getSolicitante() { return solicitante; }
    public String getStatus() { return status; }
    public String getCategoria() { return categoria; }
    public void setStatus(String status) { this.status = status; }

    // Função que exibe no console formatado
    @Override
    public String toString() {
        return "[Ticket #" + codigo + "] " + solicitante + " - " + categoria + " (" + status + ")";
    }
}