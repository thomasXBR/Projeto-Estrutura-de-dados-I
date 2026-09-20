package app;

import java.util.Scanner;

import model.Solicitacao;
import model.Operacao;
import service.CentralAtendimento; // Chamada de funções da central nos cases da main

public class Main
{
    public static void main(String[] args)
    {
        CentralAtendimento central = new CentralAtendimento();
        Scanner s = new Scanner(System.in);
        int resp;

        do {
            // MENU
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
                    // Confere o limite antes de pedir os dados
                    if (central.filaEstaCheia()) {
                        System.out.println("Aviso: a fila atingiu o limite de "
                                + central.getCapacidadeFila() + " solicitações.");
                        break;
                    }

                    // Pede que o usuário preencha os dados da solicitação (codigo, nome, descrição, categoria e prioridade)
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

                    // Cria nova solicitação
                    Solicitacao nova = central.cadastrarSolicitacao(codigo, nome, descricao, categoria, prioridade);

                    if (nova == null)
                        System.out.println("Aviso: não foi possível cadastrar.");
                    else
                        System.out.println("Solicitação cadastrada! " + nova);
                    break;

                case 2:
                    // Retorna o front() da fila
                    Solicitacao proxima = central.consultarProxima();

                    if (proxima == null)
                        System.out.println("Aviso: nenhuma solicitação foi cadastrada na fila!");
                    else
                        System.out.println("Próxima solicitação: " + proxima);
                    break;

                case 3:
                    // Atende solicitação  (retira da fila e adiciona no histórico)
                    Solicitacao atendida = central.atenderProxima();

                    if (atendida == null)
                        System.out.println("Aviso: não há nenhuma solicitação aguardando atendimento.");
                    else
                        System.out.println("Solicitação " + atendida.getCodigo() + " atendida com sucesso!");
                    break;

                case 4:
                    // Lista fila de solicitações pendentes
                    System.out.println(central.listarFila());
                    break;

                case 5:
                    // Retorna a quantidade de solicitações
                    if (central.filaEstaVazia())
                        System.out.println("Não há nenhuma solicitação");
                    else
                        System.out.println("Quantidade de solicitações: " + central.getQuantidadeAguardando());
                    break;

                case 6:
                    // Retorna a última operação
                    Operacao ultima = central.consultarUltimaOperacao();

                    if (ultima == null)
                        System.out.println("Nenhuma operação foi realizada ainda.");
                    else
                        System.out.println("Última operação: " + ultima);
                    break;

                case 7:
                    // Lista o histórico (operações e solicitações)
                    System.out.println(central.listarHistorico());
                    break;

                case 8:
                    // Chama a função removerDaFila() da Central
                    String resultado = central.desfazerUltimaOperacao();

                    if (resultado == null)
                        System.out.println("Não há operação para desfazer.");
                    else
                        System.out.println(resultado);
                    break;

                case 0:
                    // Encerra o programa
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    // Mensagem seguida pelo menu caso o usuário digite um número fora do intervalo [0,8]
                    System.out.println("Por favor, digite uma opção válida.");
                    break;
            }

        } while (resp != 0);

        s.close();
    }
}
