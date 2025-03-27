package com.tasklist.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tarefa {
    private int id;
    private String titulo;
    private String descricao;
    private LocalDate dataLimite;
    private LocalDate dataExecucao;
    private Status status;
    private LocalDateTime createdAt;

    private static int proximoId = 1;
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Tarefa(String titulo, String descricao, LocalDate dataLimite) {
        if (titulo == null || titulo.trim().isEmpty() || titulo.length() > 20) {
            throw new IllegalArgumentException("O título não pode ser vazio ou ter mais de 20 caracteres.");
        }
        if (dataLimite == null || dataLimite.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data limite não pode ser no passado.");
        }
        this.id = proximoId++;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataLimite = dataLimite;
        this.status = Status.PENDENTE;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty() || titulo.length() > 20) {
            throw new IllegalArgumentException("O título não pode ser vazio ou ter mais de 20 caracteres.");
        }
        this.titulo = titulo;
    }

    public String getDescricao () {
        return null;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        if (dataLimite == null || dataLimite.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data limite não pode ser no passado.");
        }
        this.dataLimite = dataLimite;
    }

    public LocalDate getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(LocalDate dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setProximoId(int proximoId) {
        Tarefa.proximoId = proximoId;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Título: " + titulo +
                ", Descrição: " + descricao +
                ", Data Limite: " + dataLimite.format(DATE_FORMATTER) +
                ", Data de Execução: " + (dataExecucao != null ? dataExecucao.format(DATE_FORMATTER) : "N/A") +
                ", Status: " + status +
                ", Criada em: " + createdAt.format(DATE_TIME_FORMATTER);
    }

    public void setCreatedAt ( LocalDateTime createdAt ) {
    }
}
