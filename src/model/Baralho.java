package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Baralho {

    private List<Carta> cartas;
    private List<Carta> cartasUsadas;

    public Baralho() {
        this.cartas = new ArrayList<>();
        this.cartasUsadas = new ArrayList<>();
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = new ArrayList<>(cartas);
        Collections.shuffle(this.cartas);
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public List<Carta> getCartasDisponiveis() {
        List<Carta> disponiveis = new ArrayList<>(cartas);
        disponiveis.removeAll(cartasUsadas);
        return disponiveis;
    }

    public Carta sortearCarta(Carta excluir) {
        List<Carta> disponiveis = getCartasDisponiveis();
        disponiveis.remove(excluir);
        if (disponiveis.isEmpty()) return null;
        Random rand = new Random();
        return disponiveis.get(rand.nextInt(disponiveis.size()));
    }

    public void marcarUsada(Carta carta) {
        if (!cartasUsadas.contains(carta)) {
            cartasUsadas.add(carta);
        }
    }

    public boolean temCartasDisponiveis() {
        return getCartasDisponiveis().size() >= 2;
    }

    public void resetar() {
        cartasUsadas.clear();
    }
}