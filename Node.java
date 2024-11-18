
public class Node {
    Palavra palavra;
    Node left, right, parent;

    public Node(Palavra palavra) {
        this.palavra = palavra;
        this.left = this.right = this.parent = null;
    }
    public Palavra getPalavra() {
        return this.palavra;
    }

    public void mostraNo() {
        System.out.print(this.palavra + " ");
    }
}