package model;

public class Rodada {

    private Carta cartaJogador;
    private Carta cartaMaquina;
    private int pontosJogador;
    private int pontosMaquina;

    // Resultados por atributo: 1 = jogador venceu, -1 = máquina venceu, 0 = empate
    private int resComprimento;
    private int resPeso;
    private int resVelocidade;
    private int resLongevidade;
    private int resEnvergadura;
    private int resPopulacao;

    public Rodada(Carta cartaJogador, Carta cartaMaquina) {
        this.cartaJogador = cartaJogador;
        this.cartaMaquina = cartaMaquina;
        calcularResultados();
    }

    private void calcularResultados() {
        // Regra Super Trunfo
        if (cartaJogador.isSuperTrunfo()) {
            resComprimento = resEnvergadura = resPeso =
            resVelocidade = resLongevidade = resPopulacao = 1;
            pontosJogador = 6;
            pontosMaquina = 0;
            return;
        }
        if (cartaMaquina.isSuperTrunfo()) {
            resComprimento = resEnvergadura = resPeso =
            resVelocidade = resLongevidade = resPopulacao = -1;
            pontosJogador = 0;
            pontosMaquina = 6;
            return;
        }

        // Comparações normais
        resComprimento = comparar(cartaJogador.getComprimento_mm(), cartaMaquina.getComprimento_mm());
        resPeso        = comparar(cartaJogador.getPeso_mg(),        cartaMaquina.getPeso_mg());
        resVelocidade  = comparar(cartaJogador.getVelocidade_kmh(), cartaMaquina.getVelocidade_kmh());
        resLongevidade = comparar(cartaJogador.getLongevidade_dias(),cartaMaquina.getLongevidade_dias());
        resEnvergadura = comparar(cartaJogador.getEnvergadura_mm(), cartaMaquina.getEnvergadura_mm());
        resPopulacao   = comparar(cartaJogador.getPopulacao_bilhoes(), cartaMaquina.getPopulacao_bilhoes());

        // Pontuação
        pontosJogador = contarPontos(1);
        pontosMaquina = contarPontos(-1);
    }

    private int comparar(double valJogador, double valMaquina) {
        if (valJogador > valMaquina) return 1;
        if (valJogador < valMaquina) return -1;
        return 0;
    }

    private int contarPontos(int resultado) {
        int pontos = 0;
        int[] resultados = {resComprimento, resPeso, resVelocidade,
                            resLongevidade, resEnvergadura, resPopulacao};
        for (int r : resultados) {
            if (r == resultado) pontos++;
        }
        return pontos;
    }

    // Getters
    public Carta getCartaJogador()  { return cartaJogador; }
    public Carta getCartaMaquina()  { return cartaMaquina; }
    public int getPontosJogador()   { return pontosJogador; }
    public int getPontosMaquina()   { return pontosMaquina; }
    public int getResComprimento()  { return resComprimento; }
    public int getResPeso()         { return resPeso; }
    public int getResVelocidade()   { return resVelocidade; }
    public int getResLongevidade()  { return resLongevidade; }
    public int getResEnvergadura()  { return resEnvergadura; }
    public int getResPopulacao()    { return resPopulacao; }
}