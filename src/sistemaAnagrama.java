import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class sistemaAnagrama {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<String> palavras = new ArrayList<>();

    public static void main(String[] args) {

        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    cadastrarPalavra();
                    break;

                case 2:
                    verificarAnagrama();
                    break;

                case 3:
                    listarPalavras();
                    break;

                case 4:
                    buscarAnagramas();
                    break;

                case 0:
                    System.out.println("\nEncerrando sistema...");
                    break;

                default:
                    System.out.println("\nOpção inválida!");
            }

            if (opcao != 0) {
                System.out.println("\nPressione ENTER para continuar...");
                sc.nextLine();
            }

        } while (opcao != 0);

        sc.close();
    }

    public static void exibirMenu() {
        System.out.println("\n====================================");
        System.out.println("      SISTEMA DE ANAGRAMAS");
        System.out.println("====================================");
        System.out.println("1 - Cadastrar palavra");
        System.out.println("2 - Verificar anagrama");
        System.out.println("3 - Listar palavras");
        System.out.println("4 - Buscar anagramas cadastrados");
        System.out.println("0 - Sair");
        System.out.println("====================================");
        System.out.print("Escolha uma opção: ");
    }

    public static int lerInteiro() {
        while (!sc.hasNextInt()) {
            System.out.print("Entrada inválida. Digite um número válido: ");
            sc.nextLine(); // Consome a linha inteira para evitar loop infinito de buffer
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    public static void cadastrarPalavra() {
        System.out.print("\nDigite a palavra: ");
        String palavra = sc.nextLine().trim(); // .trim() remove espaços antes e depois

        if (palavra.isEmpty()) {
            System.out.println("Erro: A palavra não pode ser vazia!");
            return;
        }

        for (String p : palavras) {
            if (p.equalsIgnoreCase(palavra)) {
                System.out.println("Erro: Esta palavra já está cadastrada!");
                return;
            }
        }

        palavras.add(palavra);
        System.out.println("Palavra cadastrada com sucesso!");
    }

    public static void verificarAnagrama() {
        System.out.print("\nPrimeira palavra: ");
        String p1 = sc.nextLine();

        System.out.print("Segunda palavra: ");
        String p2 = sc.nextLine();

        if (ehAnagrama(p1, p2)) {
            System.out.println("\nSão anagramas!");
        } else {
            System.out.println("\nNão são anagramas!");
        }
    }

    public static void listarPalavras() {
        System.out.println("\n=== PALAVRAS CADASTRADAS ===");

        if (palavras.isEmpty()) {
            System.out.println("Nenhuma palavra cadastrada.");
            return;
        }

        for (int i = 0; i < palavras.size(); i++) {
            System.out.println((i + 1) + " - " + palavras.get(i));
        }
    }

    public static void buscarAnagramas() {
        if (palavras.isEmpty()) {
            System.out.println("\nA lista está vazia. Cadastre palavras primeiro.");
            return;
        }

        System.out.print("\nDigite uma palavra para pesquisar: ");
        String palavra = sc.nextLine();

        boolean encontrou = false;
        System.out.println("\n=== RESULTADOS ===");

        for (String p : palavras) {
            if (!p.equalsIgnoreCase(palavra) && ehAnagrama(palavra, p)) {
                System.out.println("Anagrama encontrado: " + p);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum anagrama encontrado.");
        }
    }

    public static boolean ehAnagrama(String a, String b) {
        a = removerAcentos(a.replaceAll("\\s+", "")).toLowerCase();
        b = removerAcentos(b.replaceAll("\\s+", "")).toLowerCase();

        if (a.length() != b.length()) {
            return false;
        }

        char[] vetorA = a.toCharArray();
        char[] vetorB = b.toCharArray();

        Arrays.sort(vetorA);
        Arrays.sort(vetorB);

        return Arrays.equals(vetorA, vetorB);
    }

    private static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }
}
