import dao.CentralDeInformacoes;
import dao.Persistencia;
import panes.TelaCadastroADM;

public class Main {
    public static void main(String[] args) {

        Persistencia persistencia = new Persistencia();
        String nomeArquivo = "central.xml";
        CentralDeInformacoes central = persistencia.recuperarCentral(nomeArquivo);
        TelaCadastroADM cadastro = new TelaCadastroADM(central);

        cadastro.setVisible(true);

//        Scanner sc = new Scanner(System.in);
//
//        String op = "";
//
//        while (!op.equals("S")){
//
//            System.out.println("\n=== MENU CENTRAL DE INFORMAÇÕES ===\n");
//            System.out.println("1 - Novo jogador");
//            System.out.println("2 - Listar todos os jogadores");
//            System.out.println("3 - Exibir informações de um jogador específico");
//            System.out.println("4 - Salvar palavras a partir de um arquivo CSV");
//            System.out.println("5 - Listar todas as palavras salvas na central");
//            System.out.println("6 - Geração do relatório em PDF");
//            System.out.println("7 - Enviar uma mensagem para todos os jogadores");
//            System.out.println("S - Sair");
//            System.out.print("\nEscolha uma opção: ");
//            op = sc.nextLine();
//
//            switch (op) {
//                case "1":
//                    System.out.println("\n--- Novo model.Jogador ---\n");
//                    System.out.print("Nome: ");
//                    String nome = sc.nextLine();
//
//                    System.out.print("CPF: ");
//                    String cpf = sc.nextLine();
//
//                    System.out.print("E-mail: ");
//                    String email = sc.nextLine();
//
//                    System.out.print("enums.Sexo (M/F/OUTROS): ");
//                    String sexoInput = sc.nextLine();
//
//                    Sexo sexo;
//                    if (sexoInput.equals("M")) {
//                        sexo = Sexo.MASCULINO;
//                    } else if (sexoInput.equals("F")) {
//                        sexo = Sexo.FEMININO;
//                    } else {
//                        sexo = Sexo.OUTROS;
//                    }
//
//                    Jogador novoJogador = new Jogador(nome, sexo, cpf, email);
//
//                    boolean adc = central.adicionarJogador(novoJogador);
//
//                    if (adc) {
//                        persistencia.salvarCentral(central, nomeArquivo);
//                    }
//
//                    break;
//
//                case "2":
//                    System.out.println("\n--- Lista de Jogadores ---\n");
//                    ArrayList<Jogador> jogadores = central.getTodosOsJogadores();
//
//                    if (jogadores.isEmpty()) {
//                        System.out.println("Nenhum jogador cadastrado.");
//                    } else {
//                        for (Jogador j : jogadores) {
//                            System.out.println("Nome: " + j.getNome() + " | CPF: " + j.getCPF() + " | Email: " + j.getEmail());
//                        }
//                    }
//
//                    break;
//
//
//                case "3":
//                    System.out.println("\n--- Exibir model.Jogador Específico ---\n");
//                    System.out.print("Digite o CPF do jogador: ");
//                    String cpfBusca = sc.nextLine();
//
//                    Jogador jogadorEncontrado = central.recuperarJogadorPorCPF(cpfBusca);
//
//                    if (jogadorEncontrado != null) {
//                        System.out.println("\nmodel.Jogador Encontrado:");
//                        System.out.println("Nome: " + jogadorEncontrado.getNome());
//                        System.out.println("CPF: " + jogadorEncontrado.getCPF());
//                        System.out.println("E-mail: " + jogadorEncontrado.getEmail());
//                        System.out.println("enums.Sexo: " + jogadorEncontrado.getSexo());
//                    } else {
//                        System.out.println("Nenhum jogador encontrado com o CPF informado.");
//                    }
//                    break;
//
//                case "4":
//                    System.out.println("\n--- Salvar Palavras a partir de CSV ---\n");
//                    System.out.print("Informe o nome do arquivo CSV: ");
//                    String caminhoCSV = sc.nextLine();
//
//                    ArrayList<Palavra> palavrasExtraidas = ExtratorPalavrasCSV.extrairPalavras(caminhoCSV);
//
//                    if (palavrasExtraidas == null) {
//                        System.out.println("Erro ao ler o arquivo CSV. Verifique se o nome está correto");
//                    } else if (palavrasExtraidas.isEmpty()) {
//                        System.out.println("Nenhuma palavra encontrada no arquivo CSV");
//                    } else {
//                        boolean algumaAdicionada = false;
//                        for (Palavra p : palavrasExtraidas) {
//                            if (central.adicionarPalavra(p)) {
//                                algumaAdicionada = true;
//                            }
//                        }
//
//                        if (algumaAdicionada) {
//                            persistencia.salvarCentral(central, nomeArquivo);
//                        }
//                    }
//                    break;
//
//                case "5":
//                    System.out.println("\n--- Lista de Palavras ---\n");
//                    ArrayList<Palavra> palavras = central.getTodasAsPalavras();
//
//                    if (palavras.isEmpty()) {
//                        System.out.println("Nenhuma palavra encontrada.");
//                    } else {
//                        for (Palavra p : palavras) {
//                            System.out.println("model.Palavra: " + p.getPalavra() + " | Dica: " + p.getDica() + "enums.Dificuldade: " + p.getNivelDificuldade());
//                        }
//                    }
//                    break;
//
//                case "6":
//                    System.out.println("\n--- Gerar Relatório em PDF ---\n");
//                    GeradorDeRelatorios.gerarRelatorio(central);
//                    break;
//
//                case "7":
//                    System.out.println("\n--- Enviar Mensagem para Todos os Jogadores ---\n");
//                    ArrayList<Jogador> listaJogadores = central.getTodosOsJogadores();
//
//                    if (listaJogadores.isEmpty()) {
//                        System.out.println("Nenhuma jogador cadastrado para receber mensagem.");
//                    } else {
//                        System.out.print("Digite o assunto da mensagem: ");
//                        String assunto = sc.nextLine();
//
//                        System.out.print("Digite o texto da mensagem: ");
//                        String mensagemTexto = sc.nextLine();
//
//                        for (Jogador j : listaJogadores) {
//                            Mensageiro.enviarMensagem(j.getEmail(), assunto, mensagemTexto);
//                        }
//                    }
//                    break;
//
//                case "S":
//                    System.out.println("Saindo do programa...");
//                    break;
//
//                default:
//                    System.out.println("Opção inválida! Tente novamente.");
//                    break;
//
//            }

        }



}

