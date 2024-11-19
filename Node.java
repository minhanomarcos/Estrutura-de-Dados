
public class Node {
    private Palavra palavra;
    private Node left, right, parent;

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

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}