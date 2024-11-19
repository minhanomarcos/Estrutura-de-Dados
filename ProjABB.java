import java.util.Scanner;

public class ProjABB {
    public static void main(String[] args) {
        ABB arvore = new ABB();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1 - Carregar o texto");
            System.out.println("2 - Exibir estatísticas");
            System.out.println("3 - Buscar palavra");
            System.out.println("4 - Exibir palavras em ordem alfabética");
            System.out.println("5 - Exibir palavras em ordem alfabética");
            System.out.println("6 - Encerrar");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o caminho do arquivo: ");
                    String caminho = scanner.nextLine();
                    arvore.carregarTexto(caminho);
                break;
                case 2:
                    arvore.exibeEstatisticas();
                    break;
                case 3:
                    System.out.print("Digite a palavra a buscar: ");
                    String busca = scanner.nextLine().toLowerCase();
                    Node resultado = arvore.busca(busca);
                    if (resultado != null) {
                        System.out.println("A palavra '" + busca + "' aparece " + resultado.getPalavra().getOcorrencias() + " vezes.");
                    } else {
                        System.out.println("A palavra '" + busca + "' não foi encontrada.");
                    }
                    break;
                case 4:
                    System.out.println("Palavras em ordem alfabética: ");
                    arvore.executaInOrdem(arvore.root());
                    break;

                case 5:
                    System.out.println("Contagem de letras nas palavras: ");
                    arvore.contarLetras();
                    break;

                case 6:
                    System.out.println("Encerrando...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    
}
