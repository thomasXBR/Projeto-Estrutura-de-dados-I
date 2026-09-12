package app;

import java.util.Scanner;

import estruturas.MyQueue;
import estruturas.CircularArrayQueue;
import model.Solicitacao;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        // Fila de espera de solicitações do CRM
        MyQueue<Solicitacao> filaDeEspera = new CircularArrayQueue<>();
        Scanner s = new Scanner(System.in);
        int resp;

        do {
            System.out.println("===============================\n" +
                    "CENTRAL DE ATENDIMENTO" +
                    "\n===============================" +
                    "\n\n" +
                    "1 - Cadastrar nova solicitação\n" +
                    "2 - Consultar próxima solicitação\n" +
                    "3 - Atender próxima solicitação\n" +
                    "4 - Exibir fila de solicitações\n" +
                    "5 - Exibir quantidade de solicitações\n" +
                    "6 - Consultar última operação realizada\n" +
                    "7 - Exibir histórico de operações\n" +
                    "8 - Desfazer última operação\n" +
                    "0 - Encerrar\n");
            System.out.print(">> ");
            resp = s.nextInt();

            switch (resp)
            {
                case 1:
                    System.out.print("Digite o código da solicitação: ");
                    int codigo = s.nextInt();
                    s.nextLine();

                    System.out.print("Nome do solicitante: ");
                    String nome = s.nextLine();

                    System.out.print("Descrição do problema: ");
                    String descricao = s.nextLine();

                    System.out.print("Categoria: ");
                    String categoria = s.nextLine();

                    System.out.print("Prioridade: ");
                    int prioridade = s.nextInt();

                    Solicitacao novaSolicitacao = new Solicitacao(codigo, nome, descricao, categoria, prioridade);
                    filaDeEspera.enqueue(novaSolicitacao);

                    System.out.println("Solicitação cadastrada!");
                    break;

                case 2:
                    // Checa se a fila de solicitalções está fazia antes de mostrar a próxima solicitação
                    if (!filaDeEspera.isEmpty()) {
                        System.out.println("Próxima solicitação: " + filaDeEspera.front());
                    } else {
                        System.out.println("Aviso: Nenhuma solicitação foi cadastrada na fila!");
                    }
                    break;

                case 3:
                    break;

                case 4:
                    if (filaDeEspera.isEmpty()) {
                        System.out.println("A fila de espera está vazia!");
                    } else {
                        System.out.println("\n--- Fila de Solicitações (Aguardando) ---");

                        int tamanho = filaDeEspera.size();

                        for (int i = 0; i < tamanho; i++) {
                            // 1. Remove o primeiro da fila
                            Solicitacao atual = filaDeEspera.dequeue();

                            // 2. Imprime o ticket na tela
                            System.out.println((i + 1) + "º da fila: " + atual);

                            // 3. Coloca o ticket de volta no final da fila
                            filaDeEspera.enqueue(atual);
                        }
                    }
                    break;

                case 5:
                    if (filaDeEspera.isEmpty())
                        System.out.println("Não há nenhuma solicitação");
                    else
                        System.out.println("Quantidade de solicitações: " + filaDeEspera.size());
                case 6:
                    break;

                case 7:
                    break;

                case 8:
                    break;

                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Por favor, digite uma opção válida.");
                    break;
            }

        } while (resp != 0);

        s.close();
    }
}