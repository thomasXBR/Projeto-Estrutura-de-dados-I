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
                    "16 - Navegar pelo histórico\n" +
                    "17 - Pesquisar atendimento\n" +
                    "RELATÓRIOS\n" +
                    "18 - Exibir resumo do sistema\n" +
                    "0 - Encerrar\n");
            System.out.print(">> ");
            resp = s.nextInt();

            switch (resp) {
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
                    Solicitacao atendida = central.atenderProxima();

                    if (atendida == null)
                        System.out.println("Aviso: não há nenhuma solicitação aguardando atendimento.");
                    else
                        System.out.println("Solicitação " + atendida.getCodigo() + " atendida com sucesso!");
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
                    System.out.print("Insira o código da solicitação que deseja buscar >> ");
                    int codBusca = s.nextInt();

                    Solicitacao valida = central.pesquisarSolicitacaoPorCodigo(codBusca);
                    if (valida != null)
                        System.out.println("Solicitação encontrada: " + valida);
                    else
                        System.out.println("Solicitação de ID " + codBusca + " não existe.");
                    break;

                case 11:
                    System.out.print("Insira o código da solicitação que deseja remover >> ");
                    int codBuscaRem = s.nextInt();

                    Solicitacao removida = central.removerSolicitacaoPorCodigo(codBuscaRem);
                    if (removida != null)
                        System.out.println("Solicitação #" + codBuscaRem + " removida com sucesso!");
                    else
                        System.out.println("Solicitação de ID " + codBuscaRem + " não existe.");
                    break;

                case 12:
                    s.nextLine();
                    System.out.print("Digite o status (AGUARDANDO / CONCLUÍDA): ");
                    String statusBusca = s.nextLine();

                    System.out.println(central.buscarPorStatus(statusBusca));
                    break;

                case 13:
                    s.nextLine();
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
                    if (central.historicoAtendimentosVazio()) {
                        System.out.println("Nenhum atendimento registrado para navegar.");
                    } else {
                        Atendimento atualNav = central.iniciarNavegacaoAtendimentos();
                        int opNav;
                        do {
                            System.out.println("\nAtendimento atual: " + atualNav);
                            System.out.println("1 - Próximo | 2 - Anterior | 0 - Voltar ao menu");
                            System.out.print(">> ");
                            opNav = s.nextInt();

                            if (opNav == 1) {
                                atualNav = central.proximoAtendimento();
                            } else if (opNav == 2) {
                                atualNav = central.atendimentoAnterior();
                            } else if (opNav != 0) {
                                System.out.println("Opção de navegação inválida.");
                            }
                        } while (opNav != 0);
                    }
                    break;

                case 17:
                    System.out.print("Digite o código do atendimento (solicitação): ");
                    int codAtend = s.nextInt();

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
}