public class Palavra {
    String texto;
    int ocorrencias;

    public Palavra(String texto) {
        this.texto = texto;
        this.ocorrencias = 1;
    }

    public void incrementarOcorrencias() {
        this.ocorrencias++;
    }
    @Override
    public String toString() {
        return texto + " (" + ocorrencias + ")";
    }
    public String getPalavra() {
        return this.texto;
    }

}