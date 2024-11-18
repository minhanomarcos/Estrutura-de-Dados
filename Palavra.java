public class Palavra {
    private String texto;
    private int ocorrencias;

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
    public String getTexto() {
        return this.texto;
    }

    public int getOcorrencias(){
        return this.ocorrencias;
    }

}