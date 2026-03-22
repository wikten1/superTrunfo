package controller;

import dao.CartaDAO;
import model.Baralho;
import model.Carta;
import model.Partida;
import model.Rodada;
import view.TelaPrincipal;

import javax.swing.*;
import java.util.List;

public class JogoController {

    private Partida partida;
    private Baralho baralho;
    private RodadaController rodadaController;
    private TelaPrincipal view;
    private CartaDAO cartaDAO;

    public JogoController(TelaPrincipal view) {
        this.view     = view;
        this.cartaDAO = new CartaDAO();
        iniciarJogo();
    }

    public void iniciarJogo() {
        // Carrega cartas do banco e inicializa os objetos
        List<Carta> cartas = cartaDAO.buscarTodas();

        if (cartas.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                "Nenhuma carta encontrada no banco de dados!\n" +
                "Verifique a conexão com o MySQL e os dados inseridos.",
                "Erro ao carregar cartas",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        baralho          = new Baralho();
        baralho.setCartas(cartas);
        partida          = new Partida();
        rodadaController = new RodadaController(baralho);

        // Atualiza a view com as cartas disponíveis
        view.inicializarCartas(baralho.getCartasDisponiveis());
        view.resetarPlacar();
        view.limparResultados();
    }

    public void executarRodada(String nomeCartaSelecionada) {
        // Busca a carta escolhida pelo jogador
        Carta cartaJogador = null;
        for (Carta c : baralho.getCartasDisponiveis()) {
            if (c.getNome().equals(nomeCartaSelecionada)) {
                cartaJogador = c;
                break;
            }
        }

        if (cartaJogador == null) {
            JOptionPane.showMessageDialog(view,
                "Selecione uma carta válida antes de jogar!",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Executa a rodada
        Rodada rodada = rodadaController.executarRodada(cartaJogador);

        if (rodada == null) {
            JOptionPane.showMessageDialog(view,
                "Não há cartas suficientes para continuar!",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Registra no histórico e atualiza placar
        partida.registrarRodada(rodada);

        // Atualiza a view com os resultados
        view.exibirResultadoRodada(rodada);
        view.atualizarPlacar(
            rodada.getPontosJogador(),
            rodada.getPontosMaquina(),
            partida.getPlacarJogador(),
            partida.getPlacarMaquina()
        );

        // Desabilita as cartas usadas na rodada
        view.desabilitarCarta(rodada.getCartaJogador().getNome());
        view.desabilitarCarta(rodada.getCartaMaquina().getNome());

        // Verifica se a partida encerrou
        if (rodadaController.partidaEncerrada()) {
            String vencedor = partida.getVencedor();
            view.exibirVencedorFinal(vencedor,
                partida.getPlacarJogador(),
                partida.getPlacarMaquina());
        }
    }

    public void novaPartida() {
        baralho.resetar();
        partida.resetar();
        iniciarJogo();
    }
}