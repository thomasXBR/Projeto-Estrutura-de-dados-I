package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Atendimento {
    private Solicitacao solicitacao;
    private String dataHoraAtendimento;

    // Construtor padrão (caso não informe o responsável)
    public Atendimento(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.dataHoraAtendimento = LocalDateTime.now().format(formatter);
    }

    // Construtor complementar (repassa o responsável para a Solicitação)
    public Atendimento(Solicitacao solicitacao, String responsavel) {
        this(solicitacao);
        if (responsavel != null && !responsavel.trim().isEmpty()) {
            this.solicitacao.setResponsavelAtendimento(responsavel);
        }
    }

    // GETTERS & SETTERS

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    public int getCodigo() {
        return solicitacao.getCodigo();
    }

    public String getSolicitante() {
        return solicitacao.getSolicitante();
    }

    public String getStatus() {
        return solicitacao.getStatus();
    }

    public String getResponsavelAtendimento() {
        return solicitacao.getResponsavelAtendimento();
    }

    public String getDataHoraAtendimento() {
        return dataHoraAtendimento;
    }

    @Override
    public String toString() {
        return solicitacao.toString() + " | Atendido em: " + dataHoraAtendimento;
    }
}