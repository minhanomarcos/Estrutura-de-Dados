import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        return n.getLeft() == null && n.getRight() == null;
    }

    public void executaPreOrdem(Node no) {
        if (no != null) {
            no.mostraNo();
            this.executaPreOrdem(no.getLeft());
            this.executaPreOrdem(no.getRight());
        }
    }

    public void executaInOrdem(Node no) {
        if (no != null) {
            this.executaInOrdem(no.getLeft());
            no.mostraNo();
            this.executaInOrdem(no.getRight());
        }
    }

    public void executaPosOrdem(Node no) {
        if (no != null) {
            this.executaPosOrdem(no.getLeft());
            this.executaPosOrdem(no.getRight());
            no.mostraNo();
        }
    }

    public void insere(Palavra palavra) {
        root = insereRecursivo(root, null, palavra);
    }

    private Node insereRecursivo(Node atual, Node parent, Palavra palavra) {
        if (atual == null) {
            Node novo = new Node(palavra);
            novo.setParent(parent);
            return novo;
        }

        int comparacao = palavra.getTexto().compareTo(atual.getPalavra().getTexto());
        if (comparacao < 0) {
            atual.setLeft(insereRecursivo(atual.getLeft(), atual, palavra));
        } else if (comparacao > 0) {
            atual.setRight(insereRecursivo(atual.getRight(), atual, palavra));
        } else {
            atual.getPalavra().incrementarOcorrencias();
        }

        return atual;
    }

    public Node busca(String texto) {
        return buscaRecursiva(root, texto);
    }

    private Node buscaRecursiva(Node atual, String texto) {
        if (atual == null || atual.getPalavra().getTexto().equals(texto)) {
            return atual;
        }

        if (texto.compareTo(atual.getPalavra().getTexto()) < 0) {
            return buscaRecursiva(atual.getLeft(), texto);
        } else {
            return buscaRecursiva(atual.getRight(), texto);
        }
    }

    public void carregarTexto(String caminho) {
        try {
            Path arq_produtos = Paths.get(caminho);
            String aux[] = Files.readAllLines(arq_produtos).toArray(new String[0]);
            for (String linhas : aux) {
                String[] palavrasLinha = linhas.split(" ");
                    for (String linha : palavrasLinha) {
                    System.out.println(linha);

                    String semPontuacoes = linha.replaceAll("[\\p{Punct}]", "");

                    String minusculas = semPontuacoes.toLowerCase();

                    String numerosPorExtenso = substituirNumeros(minusculas);

                    String[] palavras = numerosPorExtenso.split(" ");
                    StringBuilder resultadoFinal = new StringBuilder();
                    for (String palavra : palavras) {
                        if (palavra.contains("-")) {
                            resultadoFinal.append(palavra.replace("-", " "));
                        } else {
                            resultadoFinal.append(palavra);
                        }
                        resultadoFinal.append(" ");
                    }
                    this.insere(new Palavra(resultadoFinal.toString().trim()));
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

        int[] esquerda = calcularEstatisticas(atual.getLeft());
        int[] direita = calcularEstatisticas(atual.getRight());

        int totalPalavras = esquerda[0] + direita[0] + atual.getPalavra().getOcorrencias();
        int palavrasDistintas = esquerda[1] + direita[1] + 1;
        int palavraMaisLonga = Math.max(Math.max(esquerda[2], direita[2]), atual.getPalavra().getTexto().length());

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
            int numeroDeLetras = no.getPalavra().getTexto().length();
            contagemLetras.put(numeroDeLetras, contagemLetras.getOrDefault(numeroDeLetras, 0) + 1);

            calcularContagemLetras(no.getLeft(), contagemLetras);
            calcularContagemLetras(no.getRight(), contagemLetras);
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
            String palavra = no.getPalavra().getTexto(); // Obtém a palavra do nó
            frequenciaPalavras.put(palavra, frequenciaPalavras.getOrDefault(palavra, 0) + 1);

            contarFrequencia(no.getLeft(), frequenciaPalavras);
            contarFrequencia(no.getRight(), frequenciaPalavras);
        }
    }

    
    public void exibirMaisFrequentes() {
        List<Map.Entry<String, Integer>> maisFrequentes = obterMaisFrequentes();
        System.out.println("Palavras mais frequentes (ordenadas por frequência):");
        for (Map.Entry<String, Integer> entrada : maisFrequentes) {
            System.out.println(entrada.getKey() + " - " + entrada.getValue() + " ocorrência(s)");
        }
    }

    private String substituirNumeros(String texto) {
        String[][] numeros = {
                {"0", "zero"}, {"1", "um"}, {"2", "dois"}, {"3", "três"},
                {"4", "quatro"}, {"5", "cinco"}, {"6", "seis"}, {"7", "sete"},
                {"8", "oito"}, {"9", "nove"}, {"10", "dez"}
        };
        for (String[] numero : numeros) {
            texto = texto.replaceAll("\\b" + numero[0] + "\\b", numero[1]);
        }
        return texto;
    }
}