package app;

import java.util.Scanner;

import estruturas.MyQueue;
import estruturas.CircularArrayQueue;
import model.Solicitacao;

public class Main
{
    public static void main(String[] args)
    {
        // Fila de espera de solicitações do CRM
        MyQueue<Solicitacao> filaDeEspera = new CircularArrayQueue<>();
        Scanner s = new Scanner(System.in);

        do {
            System.out.println("===============================\nCENTRAL DE ATENDIMENTO\n===============================\n\n1 - Cadastrar nova solicitação\n2 - Consultar próxima solicitação\n4 - Exibir fila de solicitações");
            int resp = s.nextInt();
        } while (true);

        /*
        Solicitacao ticket1 = new Solicitacao(101, "Thomas", "Banco de dados caiu", "Infra", 1);
        Solicitacao ticket2 = new Solicitacao(102, "Victor", "Autenticação falhou", "Auth", 2);
        Solicitacao ticket3 = new Solicitacao(103, "Enzo", "Política de RLS fraca", "Segurança", 3);
        filaDeEspera.enqueue(ticket1);
        filaDeEspera.enqueue(ticket2);
        filaDeEspera.enqueue(ticket3);

        int n = filaDeEspera.size();
        for (int i = 0; i < n; i++)
            System.out.println(filaDeEspera.dequeue());
         */
    }
}