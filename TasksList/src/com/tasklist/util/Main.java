package com.tasklist.util;

import com.tasklist.service.GerenciadorDeTarefas;
import com.tasklist.model.Status;

import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main ( String[] args ) {
        GerenciadorDeTarefas gerenciador = new GerenciadorDeTarefas ();

        // Cadastrar tarefas
        try {
            gerenciador.cadastrarTarefa ( "Comparar mantimentos " , "Leite, pão, ovos " , LocalDate.of(2025,3,22) );
            gerenciador.cadastrarTarefa("Estudar Java", "Revisar conceitos de OOP", LocalDate.of(2025, 3, 25));
            gerenciador.cadastrarTarefa("Pagar contas", "Aluguel e internet", LocalDate.of(2025, 3, 20));
            gerenciador.cadastrarTarefa("Fazer exercícios", "30 minutos de cardio", LocalDate.of(2025, 3, 22));
        } catch (IllegalArgumentException e ){
            System.err.println ( "Erro ao cadstrar tarefa: " + e.getMessage () );
        }

        // Listar tarefas
        System.out.println ("\nListagem de todas as tarefas: " );
        gerenciador.ListarTarefas ( null,null).forEach ( System.out::println );

        // Listar tarefas filtrando por título
        System.out.println("\nListagem de tarefas com título contendo 'Java':");
        gerenciador.ListarTarefas("Java", null).forEach(System.out::println);

        // Listar tarefas filtrando por data de vencimento
        LocalDate dataFiltro = LocalDate.of(2025, 3, 22);
        System.out.println("\nListagem de tarefas com data de vencimento em " + dataFiltro + ":");
        gerenciador.ListarTarefas(null, dataFiltro).forEach(System.out::println);

        // Editar uma tarefa
        gerenciador.editarTarefa(1, "Comprar mantimentos frescos", "Leite, pão integral, ovos caipiras", LocalDate.of(2025, 3, 23), null, Status.EM_ANDAMENTO);

        System.out.println("\nListagem de tarefas após edição:");
        gerenciador.ListarTarefas(null, null).forEach(System.out::println);

        // Consultar tarefas em vencimento hoje
        System.out.println("\nConsultando tarefas em vencimento hoje:");
        gerenciador.consultarTarefasEmVencimento();

        // Deletar uma tarefa
        gerenciador.deletarTarefa(3);

        System.out.println("\nListagem de tarefas após exclusão:");
        gerenciador.ListarTarefas(null, null).forEach(System.out::println);

    }
}