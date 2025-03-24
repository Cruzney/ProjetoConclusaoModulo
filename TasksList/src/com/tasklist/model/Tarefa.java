package com.tasklist.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Tarefa {
    private int id;
    private String titulo;
    private String descricao;
    private LocalDate dataLimite;
    private LocalDate dataExecucao;
    private Status status;
    private LocalDateTime createdAt;

    private static int proximoId = 1;

    public Tarefa(String titulo, String descricao, LocalDate dataLimite) {
        if (titulo == null || titulo.trim ().isEmpty () || titulo.length () > 20) {
            throw new IllegalArgumentException ( "O título não pode ser vazio ou ter mais de 20 caracteres." );
        }
        if (dataLimite == null || dataLimite.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException ( "A data limite não pode ser no passado." );
        }

        this.id = proximoId++;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataLimite = dataLimite;
        this.status = Status.PENDENTE;
        this.createdAt = LocalDateTime.now();
    }

    public int getId () {
        return id;
    }

    public String getTitulo () {
        return titulo;
    }

    public void setTitulo ( String titulo ) {
        if (titulo == null || titulo.trim ().isEmpty () || titulo.length () > 20) {
            throw new IllegalArgumentException ( "O título não pode ser vazio ou ter mais de 20 caracteres." );
        }
        this.titulo = titulo;
    }

    public String getDescricao () {
        return descricao;
    }

    public void setDescricao ( String descricao ) {
        this.descricao = descricao;
    }

    public LocalDate getDataLimite () {
        return dataLimite;
    }

    public void setDataLimite ( LocalDate dataLimite ) {
        if (dataLimite == null || dataLimite.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException ( "A data limite não pode ser no passado." );
        }
        this.dataLimite = dataLimite;
    }

    public LocalDate getDataExecucao () {
        return dataExecucao;
    }

    public void setDataExecucao ( LocalDate dataExecucao ) {
        this.dataExecucao = dataExecucao;
    }

    public Status getStatus () {
        return status;
    }

    public void setStatus ( Status status ) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt () {
        return createdAt;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Título: " + titulo +
                ", Descrição: " + descricao +
                ", Data Limite: " + dataLimite +
                ", Data Execução: " + (dataExecucao != null ? dataExecucao: "N/A") +
                ", Status: " + status +
                ", Criado em: " + createdAt;
    }
}
