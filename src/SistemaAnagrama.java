
import javax.swing.*;
import java.awt.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;

public class SistemaAnagrama extends JFrame {

    private JTextField txtCadastro;
    private JTextField txtP1;
    private JTextField txtP2;
    private JTextArea resultado;

    private final ArrayList<String> palavras = new ArrayList<>();

    public SistemaAnagrama() {

        setTitle("Sistema de Anagramas");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(0, 1, 8, 8));

        txtCadastro = new JTextField();
        txtP1 = new JTextField();
        txtP2 = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar Palavra");
        JButton btnVerificar = new JButton("Verificar Anagrama");
        JButton btnListar = new JButton("Listar Palavras");
        JButton btnBuscar = new JButton("Buscar Anagramas");

        painel.add(new JLabel("Palavra para cadastro:"));
        painel.add(txtCadastro);
        painel.add(btnCadastrar);

        painel.add(new JLabel("Primeira palavra:"));
        painel.add(txtP1);

        painel.add(new JLabel("Segunda palavra:"));
        painel.add(txtP2);

        painel.add(btnVerificar);
        painel.add(btnListar);
        painel.add(btnBuscar);

        resultado = new JTextArea();
        resultado.setEditable(false);

        add(painel, BorderLayout.NORTH);
        add(new JScrollPane(resultado), BorderLayout.CENTER);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnVerificar.addActionListener(e -> verificar());
        btnListar.addActionListener(e -> listar());
        btnBuscar.addActionListener(e -> buscar());
    }

    private void cadastrar() {
        String palavra = txtCadastro.getText().trim();

        if (palavra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite uma palavra.");
            return;
        }

        for (String p : palavras) {
            if (p.equalsIgnoreCase(palavra)) {
                JOptionPane.showMessageDialog(this, "Palavra já cadastrada.");
                return;
            }
        }

        palavras.add(palavra);
        txtCadastro.setText("");
        JOptionPane.showMessageDialog(this, "Palavra cadastrada com sucesso!");
    }

    private void verificar() {
        if (ehAnagrama(txtP1.getText(), txtP2.getText())) {
            resultado.setText("São anagramas!");
        } else {
            resultado.setText("Não são anagramas!");
        }
    }

    private void listar() {
        StringBuilder sb = new StringBuilder();

        for (String p : palavras) {
            sb.append(p).append("\n");
        }

        resultado.setText(sb.length() == 0 ? "Nenhuma palavra cadastrada." : sb.toString());
    }

    // Método responsável por buscar anagramas da palavra informada pelo usuário
    private void buscar() {

        // Exibe uma caixa de diálogo para o usuário digitar uma palavra
        String palavra = JOptionPane.showInputDialog(this, "Digite a palavra para pesquisar:");

        // Se o usuário cancelar ou não digitar nada, encerra o método
        if (palavra == null || palavra.isBlank()) return;

        // Objeto utilizado para armazenar os anagramas encontrados
        StringBuilder sb = new StringBuilder();

        // Percorre todas as palavras cadastradas na coleção "palavras"
        for (String p : palavras) {

            // Verifica se não é a mesma palavra
            // e se ela é um anagrama da palavra pesquisada
            if (!p.equalsIgnoreCase(palavra) && ehAnagrama(palavra, p)) {

                // Adiciona a palavra encontrada ao resultado
                sb.append(p).append("\n");
            }
        }

        // Exibe os anagramas encontrados ou uma mensagem caso não encontre nenhum
        resultado.setText(
                sb.length() == 0
                        ? "Nenhum anagrama encontrado."
                        : sb.toString()
        );
    }

    // Método que verifica se duas palavras são anagramas
    public static boolean ehAnagrama(String a, String b) {

        // Remove espaços, acentos e converte para minúsculas
        a = removerAcentos(a.replaceAll("\\s+", "")).toLowerCase();
        b = removerAcentos(b.replaceAll("\\s+", "")).toLowerCase();

        // Se os tamanhos forem diferentes, não podem ser anagramas
        if (a.length() != b.length()) return false;

        // Converte as strings para vetores de caracteres
        char[] va = a.toCharArray();
        char[] vb = b.toCharArray();

        // Ordena os caracteres em ordem alfabética
        Arrays.sort(va);
        Arrays.sort(vb);

        // Compara os vetores ordenados
        // Se forem iguais, as palavras são anagramas
        return Arrays.equals(va, vb);
    }

    private static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaAnagrama().setVisible(true));
    }
}
