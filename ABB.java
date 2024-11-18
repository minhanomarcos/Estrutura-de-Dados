import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ABB {
    private Node root = null;

    public boolean isEmpty() {
        return this.root == null;
    }

    public Node root() {
        return this.root;
    }

    public boolean isLeaf(Node n) {
        return n.left == null && n.right == null;
    }

    public void executaPreOrdem(Node no) {
        if (no != null) {
            no.mostraNo();
            this.executaPreOrdem(no.left);
            this.executaPreOrdem(no.right);
        }
    }

    public void executaInOrdem(Node no) {
        if (no != null) {
            this.executaInOrdem(no.left);
            no.mostraNo();
            this.executaInOrdem(no.right);
        }
    }

    public void executaPosOrdem(Node no) {
        if (no != null) {
            this.executaPosOrdem(no.left);
            this.executaPosOrdem(no.right);
            no.mostraNo();
        }
    }

    public void insere(Palavra palavra) {
        root = insereRecursivo(root, null, palavra);
    }

    private Node insereRecursivo(Node atual, Node parent, Palavra palavra) {
        if (atual == null) {
            Node novo = new Node(palavra);
            novo.parent = parent;
            return novo;
        }

        int comparacao = palavra.texto.compareTo(atual.palavra.texto);
        if (comparacao < 0) {
            atual.left = insereRecursivo(atual.left, atual, palavra);
        } else if (comparacao > 0) {
            atual.right = insereRecursivo(atual.right, atual, palavra);
        } else {
            atual.palavra.incrementarOcorrencias();
        }

        return atual;
    }

    public Node busca(String texto) {
        return buscaRecursiva(root, texto);
    }

    private Node buscaRecursiva(Node atual, String texto) {
        if (atual == null || atual.palavra.texto.equals(texto)) {
            return atual;
        }

        if (texto.compareTo(atual.palavra.texto) < 0) {
            return buscaRecursiva(atual.left, texto);
        } else {
            return buscaRecursiva(atual.right, texto);
        }
    }
    public void carregarTexto(String caminho) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] palavras = linha.replaceAll("[^a-zA-Zá-úÁ-Ú]", " ").toLowerCase().split("\\s+");
                for (String palavra : palavras) {
                    if (!palavra.isEmpty()) {
                        this.insere(new Palavra(palavra));
                    }
                }
            }
        System.out.println("TEXTO CARREGADO!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exibeEstatisticas() {
        int[] estatisticas = calcularEstatisticas(root);
        System.out.println("Total de palavras: " + estatisticas[0]);
        System.out.println("Palavras distintas: " + estatisticas[1]);
        System.out.println("Palavra mais longa: " + estatisticas[2] + " caracteres");
    }

    private int[] calcularEstatisticas(Node atual) {
        if (atual == null) return new int[]{0, 0, 0};

        int[] esquerda = calcularEstatisticas(atual.left);
        int[] direita = calcularEstatisticas(atual.right);

        int totalPalavras = esquerda[0] + direita[0] + atual.palavra.ocorrencias;
        int palavrasDistintas = esquerda[1] + direita[1] + 1;
        int palavraMaisLonga = Math.max(Math.max(esquerda[2], direita[2]), atual.palavra.texto.length());

        return new int[]{totalPalavras, palavrasDistintas, palavraMaisLonga};
    }
   public void contarLetras() {
        if (isEmpty()) {
            System.out.println("A árvore está vazia. Carregue um texto primeiro.");
            return;
        }

        Map<Integer, Integer> contagemLetras = new HashMap<>();
        calcularContagemLetras(this.root, contagemLetras);

        System.out.println("Contagem de letras nas palavras:");
        for (Map.Entry<Integer, Integer> entrada : contagemLetras.entrySet()) {
            System.out.println("Palavras com " + entrada.getKey() + " letras: " + entrada.getValue());
        }
    }

    private void calcularContagemLetras(Node no, Map<Integer, Integer> contagemLetras) {
        if (no != null) {
            int numeroDeLetras = no.getPalavra().getPalavra().length();
            contagemLetras.put(numeroDeLetras, contagemLetras.getOrDefault(numeroDeLetras, 0) + 1);

            calcularContagemLetras(no.left, contagemLetras);
            calcularContagemLetras(no.right, contagemLetras);
        }
    }
    public List<Map.Entry<String, Integer>> obterMaisFrequentes() {
        Map<String, Integer> frequenciaPalavras = new HashMap<>();
        contarFrequencia(root, frequenciaPalavras);

        // Ordenar por frequência em ordem decrescente
        List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(frequenciaPalavras.entrySet());
        listaOrdenada.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        return listaOrdenada;
    }

    
    private void contarFrequencia(Node no, Map<String, Integer> frequenciaPalavras) {
        if (no != null) {
            String palavra = no.getPalavra().getPalavra(); // Obtém a palavra do nó
            frequenciaPalavras.put(palavra, frequenciaPalavras.getOrDefault(palavra, 0) + 1);

            contarFrequencia(no.left, frequenciaPalavras);
            contarFrequencia(no.right, frequenciaPalavras);
        }
    }

    
    public void exibirMaisFrequentes() {
        List<Map.Entry<String, Integer>> maisFrequentes = obterMaisFrequentes();
        System.out.println("Palavras mais frequentes (ordenadas por frequência):");
        for (Map.Entry<String, Integer> entrada : maisFrequentes) {
            System.out.println(entrada.getKey() + " - " + entrada.getValue() + " ocorrência(s)");
        }
    }
}