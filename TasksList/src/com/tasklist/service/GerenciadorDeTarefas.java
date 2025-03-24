package com.tasklist.service;

import com.tasklist.model.Status;
import com.tasklist.model.Tarefa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorDeTarefas {
    private List<Tarefa> tarefas;

    public GerenciadorDeTarefas() {
        this.tarefas = new ArrayList<> ();
    }

    public void cadastrarTarefa( String titulo, String descricao, LocalDate dataLimite) {
        Tarefa novatarefa = new Tarefa(titulo, descricao, dataLimite);
        tarefas.add(novatarefa);
        System.out.println ("Tarefa cadastrada com sucesso. ID: " + novatarefa.getId());
    }

    public List<Tarefa> ListarTarefas(String filtroTitulo, LocalDate filtroDataVencimento) {
        return this.tarefas.stream()
                .filter(tarefa -> filtroTitulo == null || tarefa.getTitulo ().toLowerCase (  ).contains ( filtroTitulo.toLowerCase (  ) ))
                .filter(tarefa -> filtroDataVencimento == null || tarefa.getDataLimite ().equals ( filtroDataVencimento ))
                .sorted( Comparator.comparing ( Tarefa::getDataLimite ).reversed ())
                .collect( Collectors.toList ( ));
    }

    public Tarefa obterTarefaPorId(int id) {
        return this.tarefas.stream ()
                .filter(tarefa -> tarefa.getId() == id)
                .findFirst ()
                .orElse(null);
    }

    public void editarTarefa( int id, String novoTitulo, String novaDescricao, LocalDate novaDataLimite, LocalDate novaDataExecucao, Status novoStatus ) {
        Tarefa tarefaExistente = obterTarefaPorId ( id );

        if (tarefaExistente != null) {
            if ( novoTitulo != null ) {
                tarefaExistente.setTitulo ( novoTitulo );
            }
            if ( novaDescricao != null ) {
                tarefaExistente.setDescricao ( novaDescricao );
            }
            if ( novaDataLimite != null ) {
                tarefaExistente.setDataLimite ( novaDataLimite );
            }
            if ( novaDataExecucao != null ) {
                tarefaExistente.setDataExecucao ( novaDataExecucao );
            }
            if ( novoStatus != null ) {
                tarefaExistente.setStatus ( novoStatus );
            }
            System.out.println ( "Tarefa com ID " + id + " Editada com sucesso!" );
        } else {
                System.out.println ("Tarefa com ID " + id + " Não localizda :-(" );
            }
        }

        public void deletarTarefa(int id) {
            Tarefa tarefaParaRemover = obterTarefaPorId ( id );
            if (tarefaParaRemover != null) {
                this.tarefas.remove ( tarefaParaRemover );
                System.out.println ("Tarefa com ID " + id + " Deletada com sucesso !" );
            } else {
                System.out.println ("Tarefa com ID " + id + "Não localizada ;-( " );
            }
        }

        public void consultarTarefasEmVencimento() {
        LocalDate hoje = LocalDate.now ();
        List<Tarefa> tarefasVencendoHoje = this.tarefas.stream()
                .filter(tarefa -> tarefa.getDataLimite().equals ( hoje ) && tarefa.getStatus () != Status.CONCLUIDO)
                .collect(Collectors.toList ());

            if (! tarefasVencendoHoje.isEmpty ()) {
                System.out.println ("nNotificações : As seguintes tarefas vencem hoje:" );
                tarefasVencendoHoje.forEach ( tarefa -> System.out.println (" - " + tarefa.getTitulo () ) );
                // To DO:  Implementar a lógica para enviar um e-mail ou outra forma de notificação
                // Isso poderia ser feito utilizando programação assíncrona para não bloquear a aplicação principal.
                // Por exemplo, utilizando um ScheduledExecutorService para executar essa verificação periodicamente.
                System.out.println ("Será implementada solução para envio de e-mail: " );
            } else {
                System.out.println ("Não há tarefas vencendo hoje. " );
            }

        }
    }
