package model;

import java.util.ArrayList;
import java.util.List;

public class Partida {

    private int placarJogador;
    private int placarMaquina;
    private int rodadaAtual;
    private List<Rodada> historico;

    public Partida() {
        this.placarJogador = 0;
        this.placarMaquina = 0;
        this.rodadaAtual   = 0;
        this.historico     = new ArrayList<>();
    }

    public void registrarRodada(Rodada rodada) {
        historico.add(rodada);
        placarJogador += rodada.getPontosJogador();
        placarMaquina += rodada.getPontosMaquina();
        rodadaAtual++;
    }

    public String getVencedor() {
        if (placarJogador > placarMaquina) return "JOGADOR";
        if (placarMaquina > placarJogador) return "MAQUINA";
        return "EMPATE";
    }

    public void resetar() {
        placarJogador = 0;
        placarMaquina = 0;
        rodadaAtual   = 0;
        historico.clear();
    }

    public int getPlacarJogador() { return placarJogador; }
    public int getPlacarMaquina() { return placarMaquina; }
    public int getRodadaAtual()   { return rodadaAtual; }
    public List<Rodada> getHistorico() { return historico; }
}