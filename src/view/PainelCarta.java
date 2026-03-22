package view;

import model.Carta;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;

public class PainelCarta extends JPanel {

    private JLabel lblTitulo;
    private JLabel lblGrupo;
    private JLabel lblSuperTrunfo;
    private JLabel lblImagem;
    private JLabel lblComprimento;
    private JLabel lblPeso;
    private JLabel lblVelocidade;
    private JLabel lblLongevidade;
    private JLabel lblEnvergadura;
    private JLabel lblPopulacao;

    public PainelCarta(String titulo) {
        setLayout(new BorderLayout(2, 2));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(34, 100, 34), 2),
            titulo,
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 13),
            new Color(34, 100, 34)
        ));
        setBackground(new Color(240, 255, 240));
        setPreferredSize(new Dimension(200, 420));

        // === TOPO: nome + grupo + super trunfo ===
        JPanel painelTopo = new JPanel(new GridLayout(3, 1, 1, 1));
        painelTopo.setBackground(new Color(240, 255, 240));

        lblTitulo      = criarLabel("Selecione uma carta", Color.DARK_GRAY, true, 12);
        lblGrupo       = criarLabel("Grupo: -", Color.GRAY, false, 11);
        lblSuperTrunfo = criarLabel("", new Color(180, 120, 0), true, 11);

        painelTopo.add(lblTitulo);
        painelTopo.add(lblGrupo);
        painelTopo.add(lblSuperTrunfo);

        // === CENTRO: imagem ===
        lblImagem = new JLabel("", SwingConstants.CENTER);
        lblImagem.setPreferredSize(new Dimension(150, 130));
        lblImagem.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagem.setVerticalAlignment(SwingConstants.CENTER);

        JPanel painelImagem = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 4));
        painelImagem.setBackground(new Color(240, 255, 240));
        painelImagem.add(lblImagem);

        // === RODAPÉ: atributos ===
        JPanel painelAtributos = new JPanel(new GridLayout(6, 1, 1, 2));
        painelAtributos.setBackground(new Color(240, 255, 240));

        lblComprimento = criarLabel("Comprimento: -", Color.BLACK, false, 11);
        lblPeso        = criarLabel("Peso: -",        Color.BLACK, false, 11);
        lblVelocidade  = criarLabel("Velocidade: -",  Color.BLACK, false, 11);
        lblLongevidade = criarLabel("Longevidade: -", Color.BLACK, false, 11);
        lblEnvergadura = criarLabel("Envergadura: -", Color.BLACK, false, 11);
        lblPopulacao   = criarLabel("Populacao: -",   Color.BLACK, false, 11);

        painelAtributos.add(lblComprimento);
        painelAtributos.add(lblPeso);
        painelAtributos.add(lblVelocidade);
        painelAtributos.add(lblLongevidade);
        painelAtributos.add(lblEnvergadura);
        painelAtributos.add(lblPopulacao);

        add(painelTopo,      BorderLayout.NORTH);
        add(painelImagem,    BorderLayout.CENTER);
        add(painelAtributos, BorderLayout.SOUTH);
    }

    private JLabel criarLabel(String texto, Color cor, boolean negrito, int tamanho) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("Arial", negrito ? Font.BOLD : Font.PLAIN, tamanho));
        label.setForeground(cor);
        return label;
    }

    public void exibirCarta(Carta carta) {
        if (carta == null) { limpar(); return; }

        lblTitulo.setText(carta.getNome());
        lblGrupo.setText("Grupo: " + carta.getGrupo());
        lblSuperTrunfo.setText(carta.isSuperTrunfo() ? "*** SUPER TRUNFO ***" : "");
        lblComprimento.setText(String.format("Comprimento: %.2f mm",  carta.getComprimento_mm()));
        lblPeso.setText(String.format("Peso: %.3f mg",                carta.getPeso_mg()));
        lblVelocidade.setText(String.format("Velocidade: %.2f km/h",  carta.getVelocidade_kmh()));
        lblLongevidade.setText(String.format("Longevidade: %d dias",  carta.getLongevidade_dias()));
        lblEnvergadura.setText(String.format("Envergadura: %.2f mm",  carta.getEnvergadura_mm()));
        lblPopulacao.setText(String.format("Populacao: %.3f bi",      carta.getPopulacao_bilhoes()));

        carregarImagem(carta.getImagemPath());
    }

    private void carregarImagem(String imagemPath) {
        if (imagemPath == null || imagemPath.isEmpty()) {
            lblImagem.setIcon(null);
            lblImagem.setText("[sem imagem]");
            return;
        }
        try {
            URL url = getClass().getClassLoader().getResource(imagemPath);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(130, 110, Image.SCALE_SMOOTH);
                lblImagem.setIcon(new ImageIcon(img));
                lblImagem.setText("");
            } else {
                lblImagem.setIcon(null);
                lblImagem.setText("[imagem nao encontrada]");
            }
        } catch (Exception e) {
            lblImagem.setIcon(null);
            lblImagem.setText("[erro ao carregar]");
        }
    }

    public void limpar() {
        lblTitulo.setText("Selecione uma carta");
        lblGrupo.setText("Grupo: -");
        lblSuperTrunfo.setText("");
        lblImagem.setIcon(null);
        lblImagem.setText("");
        lblComprimento.setText("Comprimento: -");
        lblPeso.setText("Peso: -");
        lblVelocidade.setText("Velocidade: -");
        lblLongevidade.setText("Longevidade: -");
        lblEnvergadura.setText("Envergadura: -");
        lblPopulacao.setText("Populacao: -");
    }
}