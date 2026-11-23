package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // Formatador do Java automático para datas

public class Solicitacao {
    private int codigo;
    private String solicitante;
    private String descricao;
    private String categoria;
    private int prioridade;
    private String status;
    private String dataHoraAbertura;
    private String responsavelAtendimento;

    public Solicitacao(int codigo, String solicitante, String descricao, String categoria, int prioridade) {
        this.codigo = codigo;
        this.solicitante = solicitante;
        this.descricao = descricao;
        this.categoria = categoria;
        this.prioridade = prioridade;
        this.status = "AGUARDANDO"; // Por default

        // Captura e formata a data/hora exata da criação
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.dataHoraAbertura = LocalDateTime.now().format(formatter);

        // Inicia sem responsável atribuído
        this.responsavelAtendimento = "Não atribuído";
    }

    // GETTERS & SETTERS

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataHoraAbertura() {
        return dataHoraAbertura;
    }

    public void setDataHoraAbertura(String dataHoraAbertura) {
        this.dataHoraAbertura = dataHoraAbertura;
    }

    public String getResponsavelAtendimento() {
        return responsavelAtendimento;
    }

    public void setResponsavelAtendimento(String responsavelAtendimento) {
        this.responsavelAtendimento = responsavelAtendimento;
    }

    // Formatação

    @Override
    public String toString() {
        return "[Ticket #" + codigo + "] " + solicitante +
                " | Problema: " + descricao +
                " | Categoria: " + categoria +
                " | Prioridade: " + prioridade +
                " | Status: " + status +
                " | Aberto em: " + dataHoraAbertura +
                " | Responsável: " + responsavelAtendimento;
    }
}