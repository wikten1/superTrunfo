package controller;

import model.Baralho;
import model.Carta;
import model.Rodada;

public class RodadaController {

    private Baralho baralho;

    public RodadaController(Baralho baralho) {
        this.baralho = baralho;
    }

    public Rodada executarRodada(Carta cartaJogador) {
        // Sorteia carta da máquina excluindo a carta do jogador
        Carta cartaMaquina = baralho.sortearCarta(cartaJogador);

        if (cartaMaquina == null) {
            return null;
        }

        // Marca ambas como usadas
        baralho.marcarUsada(cartaJogador);
        baralho.marcarUsada(cartaMaquina);

        // Cria e retorna a rodada com os resultados calculados
        return new Rodada(cartaJogador, cartaMaquina);
    }

    public boolean partidaEncerrada() {
        return !baralho.temCartasDisponiveis();
    }
}