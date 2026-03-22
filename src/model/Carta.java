package model;

public class Carta {

    private int id;
    private String nome;
    private String grupo;
    private boolean superTrunfo;
    private double comprimento_mm;
    private double peso_mg;
    private double velocidade_kmh;
    private int longevidade_dias;
    private double envergadura_mm;
    private double populacao_bilhoes;
    private String imagemPath;

    public Carta() {}

    public Carta(int id, String nome, String grupo, boolean superTrunfo,
                 double comprimento_mm, double peso_mg, double velocidade_kmh,
                 int longevidade_dias, double envergadura_mm, double populacao_bilhoes,
                 String imagemPath) {
        this.id = id;
        this.nome = nome;
        this.grupo = grupo;
        this.superTrunfo = superTrunfo;
        this.comprimento_mm = comprimento_mm;
        this.peso_mg = peso_mg;
        this.velocidade_kmh = velocidade_kmh;
        this.longevidade_dias = longevidade_dias;
        this.envergadura_mm = envergadura_mm;
        this.populacao_bilhoes = populacao_bilhoes;
        this.imagemPath = imagemPath;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public boolean isSuperTrunfo() { return superTrunfo; }
    public void setSuperTrunfo(boolean superTrunfo) { this.superTrunfo = superTrunfo; }

    public double getComprimento_mm() { return comprimento_mm; }
    public void setComprimento_mm(double comprimento_mm) { this.comprimento_mm = comprimento_mm; }

    public double getPeso_mg() { return peso_mg; }
    public void setPeso_mg(double peso_mg) { this.peso_mg = peso_mg; }

    public double getVelocidade_kmh() { return velocidade_kmh; }
    public void setVelocidade_kmh(double velocidade_kmh) { this.velocidade_kmh = velocidade_kmh; }

    public int getLongevidade_dias() { return longevidade_dias; }
    public void setLongevidade_dias(int longevidade_dias) { this.longevidade_dias = longevidade_dias; }

    public double getEnvergadura_mm() { return envergadura_mm; }
    public void setEnvergadura_mm(double envergadura_mm) { this.envergadura_mm = envergadura_mm; }

    public double getPopulacao_bilhoes() { return populacao_bilhoes; }
    public void setPopulacao_bilhoes(double populacao_bilhoes) { this.populacao_bilhoes = populacao_bilhoes; }

    public String getImagemPath() { return imagemPath; }
    public void setImagemPath(String imagemPath) { this.imagemPath = imagemPath; }

    @Override
    public String toString() { return nome; }
}