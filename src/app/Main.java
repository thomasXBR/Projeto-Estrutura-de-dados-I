
package app;

import java.util.Scanner;
import model.Atendimento;
import model.Solicitacao;
import model.Operacao;
import service.CentralAtendimento;

public class Main {
    public static void main(String[] args) {
        CentralAtendimento central = new CentralAtendimento();
        Scanner s = new Scanner(System.in);
        int resp;

        do {
            System.out.println("===============================\n" +
                    "CENTRAL DE ATENDIMENTO E SERVIÇOS" +
                    "\n===============================" +
                    "\n\n" +
                    "ATENDIMENTO\n" +
                    "1 - Cadastrar nova solicitação\n" +
                    "2 - Consultar próxima solicitação\n" +
                    "3 - Atender próxima solicitação\n" +
                    "4 - Exibir fila de solicitações\n" +
                    "5 - Exibir quantidade de solicitações\n" +
                    "OPERAÇÕES\n" +
                    "6 - Consultar última operação realizada\n" +
                    "7 - Exibir histórico de operações\n" +
                    "8 - Desfazer última operação\n" +
                    "CADASTRO GERAL\n" +
                    "9 - Exibir todas as solicitações\n" +
                    "10 - Pesquisar solicitação por código\n" +
                    "11 - Remover solicitação\n" +
                    "12 - Exibir solicitações por status\n" +
                    "13 - Exibir solicitações por categoria\n" +
                    "HISTÓRICO DE ATENDIMENTOS\n" +
                    "14 - Exibir do primeiro ao último\n" +
                    "15 - Exibir do último ao primeiro\n" +
                    "16 - Consultar e navegar pelo histórico\n" +
                    "17 - Pesquisar atendimento\n" +
                    "RELATÓRIOS\n" +
                    "18 - Exibir resumo do sistema\n" +
                    "0 - Encerrar\n");

            // Lê a opção do menu tratando entradas que não sejam números
            resp = lerInteiro(s, ">> ");

            switch (resp) {
                case 1:
                    int codigo = lerInteiro(s, "Digite o código da solicitação: ");

                    System.out.print("Nome do solicitante: ");
                    String nome = s.nextLine();

                    System.out.print("Descrição do problema: ");
                    String descricao = s.nextLine();

                    System.out.print("Categoria: ");
                    String categoria = s.nextLine();

                    int prioridade = lerInteiro(s, "Prioridade: ");

                    Solicitacao nova = central.cadastrarSolicitacao(codigo, nome, descricao, categoria, prioridade);

                    if (nova == null)
                        System.out.println("Aviso: não foi possível cadastrar (código já existente ou inválido).");
                    else
                        System.out.println("Solicitação cadastrada! " + nova);

                    break;

                case 2:
                    Solicitacao proxima = central.consultarProxima();

                    if (proxima == null)
                        System.out.println("Aviso: nenhuma solicitação foi cadastrada na fila!");
                    else
                        System.out.println("Próxima solicitação: " + proxima);

                    break;

                case 3:
                    System.out.print("Nome do responsável pelo atendimento: ");
                    String responsavel = s.nextLine();

                    if (responsavel.trim().isEmpty()) {
                        responsavel = "Não informado";
                    }

                    Solicitacao atendida = central.atenderProxima(responsavel);

                    if (atendida == null)
                        System.out.println("Aviso: não há nenhuma solicitação aguardando atendimento.");
                    else
                        System.out.println("Solicitação " + atendida.getCodigo() + " atendida com sucesso! " + atendida);

                    break;

                case 4:
                    System.out.println(central.listarFila());
                    break;

                case 5:
                    if (central.filaEstaVazia())
                        System.out.println("Não há nenhuma solicitação aguardando na fila.");
                    else
                        System.out.println("Quantidade de solicitações aguardando: " + central.getQuantidadeAguardando());

                    break;

                case 6:
                    Operacao ultima = central.consultarUltimaOperacao();

                    if (ultima == null)
                        System.out.println("Nenhuma operação foi realizada ainda.");
                    else
                        System.out.println("Última operação: " + ultima);

                    break;

                case 7:
                    System.out.println(central.listarHistorico());
                    break;

                case 8:
                    String resultado = central.desfazerUltimaOperacao();

                    if (resultado == null)
                        System.out.println("Não há operação para desfazer.");
                    else
                        System.out.println(resultado);

                    break;

                case 9:
                    central.exibirTodasSolicitacoes();
                    break;

                case 10:
                    int codBusca = lerInteiro(s, "Insira o código da solicitação que deseja buscar >> ");

                    Solicitacao valida = central.pesquisarSolicitacaoPorCodigo(codBusca);

                    if (valida != null)
                        System.out.println("Solicitação encontrada: " + valida);
                    else
                        System.out.println("Solicitação de ID " + codBusca + " não existe.");

                    break;

                case 11:
                    int codBuscaRem = lerInteiro(s, "Insira o código da solicitação que deseja remover >> ");

                    Solicitacao removida = central.removerSolicitacaoPorCodigo(codBuscaRem);

                    if (removida != null) {
                        System.out.println("Solicitação #" + codBuscaRem + " removida com sucesso!");
                    } else {
                        Solicitacao existente = central.pesquisarSolicitacaoPorCodigo(codBuscaRem);

                        if (existente != null && "CONCLUÍDA".equalsIgnoreCase(existente.getStatus())) {
                            System.out.println("Não é possível remover uma solicitação já concluída, pois ela pertence ao histórico de atendimentos.");
                        } else {
                            System.out.println("Solicitação de ID " + codBuscaRem + " não existe.");
                        }
                    }

                    break;

                case 12:
                    System.out.print("Digite o status (AGUARDANDO / CONCLUÍDA): ");
                    String statusBusca = s.nextLine();

                    System.out.println(central.buscarPorStatus(statusBusca));

                    break;

                case 13:
                    System.out.print("Digite a categoria da solicitação: ");
                    String categoriaBusca = s.nextLine();

                    System.out.println(central.pesquisarSolicitacaoPorCategoria(categoriaBusca));

                    break;

                case 14:
                    central.exibirAtendimentosPrimeiroAoUltimo();
                    break;

                case 15:
                    central.exibirAtendimentosUltimoAoPrimeiro();
                    break;

                case 16:
                    menuHistoricoAtendimentos(s, central);
                    break;

                case 17:
                    int codAtend = lerInteiro(s, "Digite o código do atendimento (solicitação): ");

                    Atendimento atendEncontrado = central.pesquisarAtendimentoPorCodigo(codAtend);

                    if (atendEncontrado != null)
                        System.out.println("Atendimento encontrado: " + atendEncontrado);
                    else
                        System.out.println("Nenhum atendimento encontrado com o código #" + codAtend);

                    break;

                case 18:
                    System.out.println(central.gerarResumoSistema());
                    break;

                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Por favor, digite uma opção válida (0 a 18).");
                    break;
            }

        } while (resp != 0);

        s.close();
    }

    // Menu para consultar o primeiro/último atendimento ou navegar pelo histórico
    private static void menuHistoricoAtendimentos(Scanner s, CentralAtendimento central) {
        if (central.historicoAtendimentosVazio()) {
            System.out.println("Nenhum atendimento registrado até o momento.");
            return;
        }

        int opcao;

        do {
            System.out.println("\n--- Consultar Histórico de Atendimentos ---");
            System.out.println("1 - Consultar primeiro atendimento");
            System.out.println("2 - Consultar último atendimento");
            System.out.println("3 - Navegar pelo histórico");
            System.out.println("0 - Voltar ao menu principal");

            opcao = lerInteiro(s, ">> ");

            if (opcao == 1) {
                Atendimento primeiro = central.consultarPrimeiroAtendimento();
                System.out.println("Primeiro atendimento: " + primeiro);

            } else if (opcao == 2) {
                Atendimento ultimo = central.consultarUltimoAtendimento();
                System.out.println("Último atendimento: " + ultimo);

            } else if (opcao == 3) {
                navegarHistorico(s, central);

            } else if (opcao != 0) {
                System.out.println("Opção de histórico inválida.");
            }

        } while (opcao != 0);
    }

    // Permite avançar e voltar pelos atendimentos
    private static void navegarHistorico(Scanner s, CentralAtendimento central) {
        Atendimento atual = central.iniciarNavegacaoAtendimentos();
        int opcao;

        do {
            System.out.println("\nAtendimento atual: " + atual);
            System.out.println("1 - Próximo | 2 - Anterior | 0 - Voltar");

            opcao = lerInteiro(s, ">> ");

            if (opcao == 1) {
                atual = central.proximoAtendimento();

            } else if (opcao == 2) {
                atual = central.atendimentoAnterior();

            } else if (opcao != 0) {
                System.out.println("Opção de navegação inválida.");
            }

        } while (opcao != 0);
    }

    // Lê um número inteiro sem encerrar o programa quando o usuário digita letras
    private static int lerInteiro(Scanner s, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = s.nextLine();

            try {
                return Integer.parseInt(entrada.trim());

            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }
}