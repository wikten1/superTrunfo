package view;

import controller.JogoController;
import model.Carta;
import model.Rodada;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelaPrincipal extends JFrame {

    // Controller
    private JogoController controller;

    // Painel das cartas
    private PainelCarta painelJogador;
    private PainelCarta painelMaquina;

    // Seleção de carta
    private JComboBox<String> comboCartas;
    private Map<String, Boolean> cartasDesabilitadas;

    // Tabela de resultados
    private JLabel[] lblAtributos;
    private JLabel[] lblValoresJogador;
    private JLabel[] lblResultados;
    private JLabel[] lblValoresMaquina;

    // Placar
    private JTextField txtPlacarRodadaJogador;
    private JTextField txtPlacarRodadaMaquina;
    private JTextField txtPlacarGeralJogador;
    private JTextField txtPlacarGeralMaquina;
    private JTextField txtVencedor;

    // Botões
    private JButton btnJogar;
    private JButton btnNova;

    // Nomes dos atributos
    private static final String[] ATRIBUTOS = {
        "Comprimento (mm)", "Peso (mg)", "Velocidade (km/h)",
        "Longevidade (dias)", "Envergadura (mm)", "População (bi)"
    };

    public TelaPrincipal() {
        super("🦋 Super Trunfo — Insetos 🦋");
        cartasDesabilitadas = new HashMap<>();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 680);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarComponentes() {
        // Painéis das cartas
        painelJogador = new PainelCarta("Sua Carta");
        painelMaquina = new PainelCarta("Carta da Máquina");

        // ComboBox
        comboCartas = new JComboBox<>();
        comboCartas.setFont(new Font("Arial", Font.PLAIN, 12));
        comboCartas.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                if (value != null && cartasDesabilitadas.getOrDefault(value.toString(), false)) {
                    label.setForeground(Color.LIGHT_GRAY);
                    label.setEnabled(false);
                }
                return label;
            }
        });

        // Tabela de resultados
        int n = ATRIBUTOS.length;
        lblAtributos       = new JLabel[n];
        lblValoresJogador  = new JLabel[n];
        lblResultados      = new JLabel[n];
        lblValoresMaquina  = new JLabel[n];

        for (int i = 0; i < n; i++) {
            lblAtributos[i]      = new JLabel(ATRIBUTOS[i], SwingConstants.CENTER);
            lblValoresJogador[i] = new JLabel("-", SwingConstants.CENTER);
            lblResultados[i]     = new JLabel("-", SwingConstants.CENTER);
            lblValoresMaquina[i] = new JLabel("-", SwingConstants.CENTER);

            lblAtributos[i].setFont(new Font("Arial", Font.BOLD, 12));
            lblValoresJogador[i].setFont(new Font("Arial", Font.PLAIN, 12));
            lblResultados[i].setFont(new Font("Arial", Font.BOLD, 16));
            lblValoresMaquina[i].setFont(new Font("Arial", Font.PLAIN, 12));
        }

        // Placares
        txtPlacarRodadaJogador = criarCampoTexto();
        txtPlacarRodadaMaquina = criarCampoTexto();
        txtPlacarGeralJogador  = criarCampoTexto();
        txtPlacarGeralMaquina  = criarCampoTexto();
        txtVencedor = new JTextField("", 20);
        txtVencedor.setEditable(false);
        txtVencedor.setHorizontalAlignment(JTextField.CENTER);
        txtVencedor.setFont(new Font("Arial", Font.BOLD, 14));

        // Botões
        btnJogar = new JButton("JOGAR");
        btnJogar.setFont(new Font("Arial", Font.BOLD, 14));
        btnJogar.setBackground(new Color(34, 139, 34));
        btnJogar.setForeground(Color.WHITE);
        btnJogar.setFocusPainted(false);
        btnJogar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnJogar.setOpaque(true);
        btnJogar.setBorderPainted(false);
        btnJogar.setPreferredSize(new Dimension(140, 35));

        btnNova = new JButton("NOVA PARTIDA");
        btnNova.setFont(new Font("Arial", Font.BOLD, 12));
        btnNova.setBackground(new Color(70, 130, 180));
        btnNova.setForeground(Color.WHITE);
        btnNova.setFocusPainted(false);
        btnNova.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNova.setOpaque(true);
        btnNova.setBorderPainted(false);
        btnNova.setPreferredSize(new Dimension(160, 35));
    }

    private void configurarLayout() {
        getContentPane().setBackground(new Color(245, 255, 245));
        setLayout(new BorderLayout(10, 10));

        // === TOPO: título ===
        JLabel lblTitulo = new JLabel("** SUPER TRUNFO — INSETOS **", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(34, 100, 34));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // === CENTRO: cartas + tabela ===
        JPanel painelCentro = new JPanel(new BorderLayout(10, 10));
        painelCentro.setBackground(new Color(245, 255, 245));

        // Cartas laterais
        JPanel painelCartas = new JPanel(new BorderLayout(10, 0));
        painelCartas.setBackground(new Color(245, 255, 245));

     // Painel esquerdo: label + combo + carta
        JPanel painelEsquerdo = new JPanel(new BorderLayout(5, 5));
        painelEsquerdo.setBackground(new Color(245, 255, 245));

        // Topo: label + combo agrupados
        JPanel painelSelecao = new JPanel(new BorderLayout(3, 3));
        painelSelecao.setBackground(new Color(245, 255, 245));
        JLabel lblEscolha = new JLabel("Escolha sua carta:", SwingConstants.CENTER);
        lblEscolha.setFont(new Font("Arial", Font.BOLD, 12));
        painelSelecao.add(lblEscolha, BorderLayout.NORTH);
        painelSelecao.add(comboCartas, BorderLayout.CENTER);

        painelEsquerdo.add(painelSelecao, BorderLayout.NORTH);
        painelEsquerdo.add(painelJogador, BorderLayout.CENTER);

        painelCartas.add(painelEsquerdo, BorderLayout.WEST);
        painelCartas.add(painelMaquina, BorderLayout.EAST);

        // Tabela de resultados (centro)
        JPanel painelTabela = criarPainelTabela();
        painelCartas.add(painelTabela, BorderLayout.CENTER);

        painelCentro.add(painelCartas, BorderLayout.CENTER);
        add(painelCentro, BorderLayout.CENTER);

        // === RODAPÉ: placar + botões ===
        JPanel painelRodape = new JPanel(new GridBagLayout());
        painelRodape.setBackground(new Color(245, 255, 245));
        painelRodape.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);

        // Linha 1: Pontuação da Rodada
        gbc.gridx = 0; gbc.gridy = 0;
        painelRodape.add(rotulo("Pontuação da Rodada:"), gbc);
        gbc.gridx = 1;
        painelRodape.add(rotuloAzul("Você"), gbc);
        gbc.gridx = 2;
        painelRodape.add(txtPlacarRodadaJogador, gbc);
        gbc.gridx = 3;
        painelRodape.add(txtPlacarRodadaMaquina, gbc);
        gbc.gridx = 4;
        painelRodape.add(rotuloVermelho("Máquina"), gbc);

        // Linha 2: Pontuação Geral
        gbc.gridx = 0; gbc.gridy = 1;
        painelRodape.add(rotulo("Pontuação Geral:"), gbc);
        gbc.gridx = 1;
        painelRodape.add(rotuloAzul("Você"), gbc);
        gbc.gridx = 2;
        painelRodape.add(txtPlacarGeralJogador, gbc);
        gbc.gridx = 3;
        painelRodape.add(txtPlacarGeralMaquina, gbc);
        gbc.gridx = 4;
        painelRodape.add(rotuloVermelho("Máquina"), gbc);

        // Linha 3: Vencedor
        gbc.gridx = 0; gbc.gridy = 2;
        painelRodape.add(rotulo("Vencedor da Partida:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 4;
        painelRodape.add(txtVencedor, gbc);
        gbc.gridwidth = 1;

        // Linha 4: Botões
     // Linha 4: Botões
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painelRodape.add(btnJogar, gbc);
        gbc.gridx = 3;
        gbc.gridwidth = 2;
        painelRodape.add(btnNova, gbc);
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        add(painelRodape, BorderLayout.SOUTH);
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(34, 100, 34), 1),
            "Comparação de Atributos",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12),
            new Color(34, 100, 34)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 6, 3, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Cabeçalho
        gbc.gridy = 0;
        gbc.gridx = 0; painel.add(cabecalho("Atributo"), gbc);
        gbc.gridx = 1; painel.add(cabecalho("Você"), gbc);
        gbc.gridx = 2; painel.add(cabecalho(""), gbc);
        gbc.gridx = 3; painel.add(cabecalho("Máquina"), gbc);

        // Linhas de atributos
        for (int i = 0; i < ATRIBUTOS.length; i++) {
            gbc.gridy = i + 1;
            gbc.gridx = 0; painel.add(lblAtributos[i], gbc);
            gbc.gridx = 1; painel.add(lblValoresJogador[i], gbc);
            gbc.gridx = 2; painel.add(lblResultados[i], gbc);
            gbc.gridx = 3; painel.add(lblValoresMaquina[i], gbc);
        }

        return painel;
    }

    private void configurarEventos() {
        btnJogar.addActionListener(e -> {
            String selecionado = (String) comboCartas.getSelectedItem();
            if (selecionado == null || cartasDesabilitadas.getOrDefault(selecionado, false)) {
                JOptionPane.showMessageDialog(this,
                    "Esta carta já foi usada! Escolha outra.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controller.executarRodada(selecionado);
        });

        btnNova.addActionListener(e -> {
            int opcao = JOptionPane.showConfirmDialog(this,
                "Deseja iniciar uma nova partida?",
                "Nova Partida", JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                controller.novaPartida();
            }
        });
    }

    // ===================== Métodos chamados pelo Controller =====================

    public void inicializarCartas(List<Carta> cartas) {
        comboCartas.removeAllItems();
        cartasDesabilitadas.clear();
        for (Carta c : cartas) {
            comboCartas.addItem(c.getNome());
            cartasDesabilitadas.put(c.getNome(), false);
        }
        painelJogador.limpar();
        painelMaquina.limpar();

        // Atualiza painel ao selecionar carta no combo
        comboCartas.addActionListener(e -> {
            String nome = (String) comboCartas.getSelectedItem();
            if (nome == null) return;
            for (Carta c : cartas) {
                if (c.getNome().equals(nome)) {
                    painelJogador.exibirCarta(c);
                    break;
                }
            }
        });
    }

    public void exibirResultadoRodada(Rodada rodada) {
        // Exibe carta da máquina
        painelMaquina.exibirCarta(rodada.getCartaMaquina());
        painelJogador.exibirCarta(rodada.getCartaJogador());

        // Atributos do jogador
        lblValoresJogador[0].setText(String.format("%.2f", rodada.getCartaJogador().getComprimento_mm()));
        lblValoresJogador[1].setText(String.format("%.3f", rodada.getCartaJogador().getPeso_mg()));
        lblValoresJogador[2].setText(String.format("%.2f", rodada.getCartaJogador().getVelocidade_kmh()));
        lblValoresJogador[3].setText(String.valueOf(rodada.getCartaJogador().getLongevidade_dias()));
        lblValoresJogador[4].setText(String.format("%.2f", rodada.getCartaJogador().getEnvergadura_mm()));
        lblValoresJogador[5].setText(String.format("%.3f", rodada.getCartaJogador().getPopulacao_bilhoes()));

        // Atributos da máquina
        lblValoresMaquina[0].setText(String.format("%.2f", rodada.getCartaMaquina().getComprimento_mm()));
        lblValoresMaquina[1].setText(String.format("%.3f", rodada.getCartaMaquina().getPeso_mg()));
        lblValoresMaquina[2].setText(String.format("%.2f", rodada.getCartaMaquina().getVelocidade_kmh()));
        lblValoresMaquina[3].setText(String.valueOf(rodada.getCartaMaquina().getLongevidade_dias()));
        lblValoresMaquina[4].setText(String.format("%.2f", rodada.getCartaMaquina().getEnvergadura_mm()));
        lblValoresMaquina[5].setText(String.format("%.3f", rodada.getCartaMaquina().getPopulacao_bilhoes()));

        // Ícones de resultado
        int[] resultados = {
            rodada.getResComprimento(), rodada.getResPeso(),
            rodada.getResVelocidade(),  rodada.getResLongevidade(),
            rodada.getResEnvergadura(), rodada.getResPopulacao()
        };

        for (int i = 0; i < resultados.length; i++) {
            if (resultados[i] == 1) {
                lblResultados[i].setText("✅");
                lblResultados[i].setForeground(new Color(0, 150, 0));
            } else if (resultados[i] == -1) {
                lblResultados[i].setText("❌");
                lblResultados[i].setForeground(Color.RED);
            } else {
                lblResultados[i].setText("➖");
                lblResultados[i].setForeground(Color.GRAY);
            }
        }
    }

    public void atualizarPlacar(int ptRodadaJ, int ptRodadaM, int ptGeralJ, int ptGeralM) {
        txtPlacarRodadaJogador.setText(String.valueOf(ptRodadaJ));
        txtPlacarRodadaMaquina.setText(String.valueOf(ptRodadaM));
        txtPlacarGeralJogador.setText(String.valueOf(ptGeralJ));
        txtPlacarGeralMaquina.setText(String.valueOf(ptGeralM));

        // Mostra lider parcial durante a partida
        if (ptGeralJ > ptGeralM) {
            txtVencedor.setForeground(new Color(0, 0, 180));
        } else if (ptGeralM > ptGeralJ) {
            txtVencedor.setText("Liderando: MAQUINA  (" + ptGeralJ + " x " + ptGeralM + ")");
            txtVencedor.setForeground(Color.RED);
        } else {
            txtVencedor.setText("Empatado  (" + ptGeralJ + " x " + ptGeralM + ")");
            txtVencedor.setForeground(Color.DARK_GRAY);
        }
    }

    public void desabilitarCarta(String nome) {
        cartasDesabilitadas.put(nome, true);
    }

    public void exibirVencedorFinal(String vencedor, int ptJ, int ptM) {
        String msg;
        String textoVencedor;
        if (vencedor.equals("JOGADOR")) {
            textoVencedor = "🏆 VOCÊ VENCEU!";
            msg = String.format("Parabéns! Você venceu a partida!\nSua pontuação: %d  |  Máquina: %d", ptJ, ptM);
        } else if (vencedor.equals("MAQUINA")) {
            textoVencedor = "🤖 MÁQUINA VENCEU!";
            msg = String.format("A máquina venceu desta vez!\nSua pontuação: %d  |  Máquina: %d", ptJ, ptM);
        } else {
            textoVencedor = "🤝 EMPATE!";
            msg = String.format("A partida terminou em empate!\nSua pontuação: %d  |  Máquina: %d", ptJ, ptM);
        }
        txtVencedor.setText(textoVencedor);
        btnJogar.setEnabled(false);
        JOptionPane.showMessageDialog(this, msg, "Fim de Partida", JOptionPane.INFORMATION_MESSAGE);
    }

    public void resetarPlacar() {
        txtPlacarRodadaJogador.setText("0");
        txtPlacarRodadaMaquina.setText("0");
        txtPlacarGeralJogador.setText("0");
        txtPlacarGeralMaquina.setText("0");
        txtVencedor.setText("");
        btnJogar.setEnabled(true);
    }

    public void limparResultados() {
        for (int i = 0; i < ATRIBUTOS.length; i++) {
            lblValoresJogador[i].setText("-");
            lblResultados[i].setText("-");
            lblValoresMaquina[i].setText("-");
        }
    }

    public void setController(JogoController controller) {
        this.controller = controller;
    }

    // ===================== Helpers visuais =====================

    private JTextField criarCampoTexto() {
        JTextField tf = new JTextField("0", 5);
        tf.setEditable(false);
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setFont(new Font("Arial", Font.BOLD, 13));
        return tf;
    }

    private JLabel rotulo(String texto) {
        JLabel l = new JLabel(texto, SwingConstants.RIGHT);
        l.setFont(new Font("Arial", Font.BOLD, 12));
        return l;
    }

    private JLabel rotuloAzul(String texto) {
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setFont(new Font("Arial", Font.BOLD, 13));
        l.setForeground(new Color(0, 0, 180));
        return l;
    }

    private JLabel rotuloVermelho(String texto) {
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setFont(new Font("Arial", Font.BOLD, 13));
        l.setForeground(Color.RED);
        return l;
    }

    private JLabel cabecalho(String texto) {
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setFont(new Font("Arial", Font.BOLD, 12));
        l.setForeground(new Color(34, 100, 34));
        return l;
    }
}