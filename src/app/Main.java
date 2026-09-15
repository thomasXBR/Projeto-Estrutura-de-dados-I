package app;

import java.util.Scanner;

import estruturas.MyQueue;
import estruturas.CircularArrayQueue;
import model.Solicitacao;
import estruturas.MyStack;
import estruturas.ArrayStack;
import model.Operacao;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        // Fila de espera de solicitações do CRM e Pilha de Operações
        MyQueue<Solicitacao> filaDeEspera = new CircularArrayQueue<>();
        ArrayStack<Operacao> historico = new ArrayStack<>();

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
                    // Checa se a fila de solicitações está fazia antes de mostrar a próxima solicitação
                    if (!filaDeEspera.isEmpty()) {
                        System.out.println("Próxima solicitação: " + filaDeEspera.front());
                    } else {
                        System.out.println("Aviso: Nenhuma solicitação foi cadastrada na fila!");
                    }
                    break;

                case 3:
                    // Verifica apenas se a fila tem clientes aguardando
                    if (!filaDeEspera.isEmpty())
                    {

                        // 1. Remove o cliente do início da fila
                        Solicitacao atendida = filaDeEspera.dequeue();

                        atendida.setStatus("CONCLUÍDA");

                        // 2. Empacota a solicitação dentro de uma nova "Operacao"
                        Operacao opAtendimento = new Operacao("ATENDIMENTO", atendida);

                        // 3. Registra a operação no topo do histórico
                        historico.push(opAtendimento);

                        System.out.println("Solicitação " + atendida.getCodigo() + " atendida com sucesso!");

                    } else {
                        System.out.println("Aviso: Não há nenhuma solicitação aguardando atendimento.");
                    }
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
                    break;

                case 6:
                    if (historico.isEmpty())
                        System.out.println("Nenhuma operação foi realizada ainda.");
                    else
                        System.out.println("Última operação: " + historico.top());
                    break;

                case 7:
                    if (historico.isEmpty())
                        System.out.println("Nenhuma operação registrada até o momento.");
                    else
                    {
                        System.out.println("\n--- Histórico de Operações (LIFO) ---");
                        MyStack<Operacao> auxiliar = new ArrayStack<>();

                        while (!historico.isEmpty())
                        {
                            // 1. Tira do topo, que já sai da mais recente para a mais Operacao op = historico.pop() antiga
                            // 2. Imprime a operação
                            Operacao op = historico.pop();
                            System.out.println(op);
                            auxiliar.push(op);
                        }
                        // 3. Guarda na auxiliar para não perder o histórico
                        while (!auxiliar.isEmpty())
                            historico.push(auxiliar.pop());
                    }
                    break;

                case 8:
                    if (historico.isEmpty())
                        System.out.println("Não há operação para desfazer.");
                    else
                    {
                        Operacao ultima = historico.pop();
                        Solicitacao alvo = ultima.getSolicitacao();
                        if (ultima.getTipo().equals("CADASTRO"))
                        {
                        // Refaz a fila sem a solicitação que tinha sido cadastrada
                            MyQueue<Solicitacao> novaFila = new CircularArrayQueue<>();
                            int total = filaDeEspera.size();
                            for (int i = 0; i < total; i++)
                            {
                                Solicitacao atual = filaDeEspera.dequeue();
                                if (atual.getCodigo() != alvo.getCodigo())
                                    novaFila.enqueue(atual);
                            }
                            filaDeEspera = novaFila;
                            System.out.println("Cadastro da solicitação " + alvo.getCodigo());
                        }
                        else
                        {
                            // Volta o status e coloca a solicitação na frente da fila alvo.setStatus("AGUARDANDO");
                            alvo.setStatus("AGUARDANDO");
                            MyQueue<Solicitacao> novaFila = new CircularArrayQueue<>();
                            novaFila.enqueue(alvo);
                            while (!filaDeEspera.isEmpty())
                                novaFila.enqueue(filaDeEspera.dequeue());
                            filaDeEspera = novaFila;
                            System.out.println("Atendimento da solicitação " + alvo.getCodigo());
                        }
                    }
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
