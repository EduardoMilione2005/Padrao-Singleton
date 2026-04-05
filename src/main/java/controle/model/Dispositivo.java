package controle.model;

public class Dispositivo {

    private final String id;
    private String nome;
    private TipoDispositivo tipo;
    private boolean ligado;
    private int nivel;

    public Dispositivo(String id, String nome, TipoDispositivo tipo) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID não pode ser vazio.");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome não pode ser vazio.");
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.ligado = false;
        this.nivel = 0;
    }


    public String getId()            { return id; }
    public String getNome()          { return nome; }
    public TipoDispositivo getTipo() { return tipo; }
    public boolean isLigado()        { return ligado; }
    public int getNivel()            { return nivel; }
    
    public void setNome(String nome)   { this.nome = nome; }
    public void setLigado(boolean b)   { this.ligado = b; }
    public void setNivel(int nivel)    { this.nivel = nivel; }

    @Override
    public String toString() {
        return String.format("Dispositivo[id=%s, nome=%s, tipo=%s, ligado=%s, nivel=%d]",
                id, nome, tipo, ligado, nivel);
    }
}
