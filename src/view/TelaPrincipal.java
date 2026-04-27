package view;

import controller.JogoController;
import model.Carta;
import model.Rodada;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelaPrincipal extends JFrame {

    // === PALETA VINTAGE PERGAMINHO ===
    private static final Color PERG_CLARO    = new Color(0xF5EDD6);
    private static final Color PERG_MEDIO    = new Color(0xE8D5A3);
    private static final Color PERG_ESCURO   = new Color(0xC4A265);
    private static final Color VERDE_ESCURO  = new Color(0x2C4A1E);
    private static final Color VERDE_MEDIO   = new Color(0x3D6B2C);
    private static final Color MARROM_ESCURO = new Color(0x3E2B1A);
    private static final Color MARROM_MEDIO  = new Color(0x6B4226);
    private static final Color OURO          = new Color(0xB8860B);
    private static final Color OURO_CLARO    = new Color(0xDAA520);
    private static final Color VERMELHO_ANT  = new Color(0x8B1A1A);
    private static final Color AZUL_ANT      = new Color(0x1A3A5C);

    // Controller
    private JogoController controller;

    // Painéis das cartas
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

    private static final String[] ATRIBUTOS = {
        "Comprimento (mm)", "Peso (mg)", "Velocidade (km/h)",
        "Longevidade (dias)", "Envergadura (mm)", "Populacao (bi)"
    };

    public TelaPrincipal() {
        super("Super Trunfo — Insetos");
        cartasDesabilitadas = new HashMap<>();
        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 680);
        setMinimumSize(new Dimension(1100, 680));
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarComponentes() {
    	painelJogador = new PainelCarta("images/carta_inicial.png", 190, 350);
    	painelMaquina = new PainelCarta("images/carta_inicial.png", 190, 390);

        // ComboBox estilizado
        comboCartas = new JComboBox<>();
        comboCartas.setFont(new Font("Serif", Font.ITALIC, 13));
        comboCartas.setBackground(PERG_CLARO);
        comboCartas.setForeground(MARROM_ESCURO);
        comboCartas.setBorder(BorderFactory.createLineBorder(MARROM_MEDIO, 1));
        comboCartas.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                label.setBackground(isSelected ? PERG_MEDIO : PERG_CLARO);
                label.setForeground(cartasDesabilitadas.getOrDefault(
                    value != null ? value.toString() : "", false)
                    ? new Color(0xAA9977) : MARROM_ESCURO);
                label.setFont(new Font("Serif", Font.ITALIC, 13));
                label.setBorder(new EmptyBorder(3, 8, 3, 8));
                return label;
            }
        });

        // Tabela de resultados
        int n = ATRIBUTOS.length;
        lblAtributos      = new JLabel[n];
        lblValoresJogador = new JLabel[n];
        lblResultados     = new JLabel[n];
        lblValoresMaquina = new JLabel[n];

        for (int i = 0; i < n; i++) {
            lblAtributos[i]      = new JLabel(ATRIBUTOS[i], SwingConstants.CENTER);
            lblValoresJogador[i] = new JLabel("-", SwingConstants.CENTER);
            lblResultados[i]     = new JLabel("-", SwingConstants.CENTER);
            lblValoresMaquina[i] = new JLabel("-", SwingConstants.CENTER);

            lblAtributos[i].setFont(new Font("Serif", Font.BOLD, 13));
            lblAtributos[i].setForeground(VERDE_ESCURO);
            lblValoresJogador[i].setFont(new Font("Serif", Font.PLAIN, 13));
            lblValoresJogador[i].setForeground(MARROM_ESCURO);
            lblResultados[i].setFont(new Font("Serif", Font.BOLD, 16));
            lblValoresMaquina[i].setFont(new Font("Serif", Font.PLAIN, 13));
            lblValoresMaquina[i].setForeground(MARROM_ESCURO);
        }

        // Placares
        txtPlacarRodadaJogador = criarCampoTexto(4);
        txtPlacarRodadaMaquina = criarCampoTexto(4);
        txtPlacarGeralJogador  = criarCampoTexto(4);
        txtPlacarGeralMaquina  = criarCampoTexto(4);
        txtVencedor            = criarCampoTexto(22);
        txtVencedor.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 14));

        // Botões vintage
        btnJogar = criarBotaoVintage("JOGAR", VERDE_ESCURO);
        btnNova  = criarBotaoVintage("NOVA PARTIDA", MARROM_ESCURO);
    }

    private void configurarLayout() {
        // Fundo geral com textura pergaminho
        JPanel painelFundo = new PainelPergaminho();
        painelFundo.setLayout(new BorderLayout(8, 8));
        painelFundo.setBorder(new EmptyBorder(10, 12, 10, 12));
        setContentPane(painelFundo);

        // === TOPO: título ornamentado ===
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setOpaque(false);
        JLabel lblTitulo = new JLabel("SUPER TRUNFO — INSETOS", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Sombra do texto
                g2.setFont(getFont());
                g2.setColor(new Color(0, 0, 0, 60));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x + 2, y + 2);
                // Texto principal
                g2.setColor(VERDE_ESCURO);
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 28));
        lblTitulo.setForeground(VERDE_ESCURO);
        lblTitulo.setBorder(new EmptyBorder(4, 0, 2, 0));

        // Linha ornamental abaixo do título
        JPanel painelLinha = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cx = getWidth() / 2, cy = getHeight() / 2;
                g2.setColor(OURO);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawLine(50, cy, cx - 40, cy);
                g2.drawLine(cx + 40, cy, getWidth() - 50, cy);
                g2.fillOval(cx - 5, cy - 5, 10, 10);
                g2.fillOval(cx - 30, cy - 3, 6, 6);
                g2.fillOval(cx + 24, cy - 3, 6, 6);
                g2.dispose();
            }
        };
        painelLinha.setOpaque(false);
        painelLinha.setPreferredSize(new Dimension(0, 18));

        painelTopo.add(lblTitulo, BorderLayout.CENTER);
        painelTopo.add(painelLinha, BorderLayout.SOUTH);
        painelFundo.add(painelTopo, BorderLayout.NORTH);

        // === CENTRO: cartas + tabela ===
        JPanel painelCentro = new JPanel(new BorderLayout(8, 0));
        painelCentro.setOpaque(false);

        // --- LADO ESQUERDO: combo + carta jogador ---
        JPanel painelEsquerdo = new JPanel(new BorderLayout(4, 6));
        painelEsquerdo.setOpaque(false);
        painelEsquerdo.setPreferredSize(new Dimension(210, 0));

        JLabel lblEscolha = new JLabel("Escolha sua carta:", SwingConstants.CENTER);
        lblEscolha.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 13));
        lblEscolha.setForeground(VERDE_ESCURO);

        JPanel painelCombo = new JPanel(new BorderLayout(2, 2));
        painelCombo.setOpaque(false);
        painelCombo.add(lblEscolha, BorderLayout.NORTH);
        painelCombo.add(comboCartas, BorderLayout.CENTER);

        painelEsquerdo.add(painelCombo, BorderLayout.NORTH);
        painelEsquerdo.add(painelJogador, BorderLayout.CENTER);

        // --- LADO DIREITO: carta máquina ---
        JPanel painelDireito = new JPanel(new BorderLayout());
        painelDireito.setOpaque(false);
        painelDireito.setPreferredSize(new Dimension(210, 0));

        JLabel lblMaquina = new JLabel("Carta da Maquina", SwingConstants.CENTER);
        lblMaquina.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 13));
        lblMaquina.setForeground(VERDE_ESCURO);
        lblMaquina.setBorder(new EmptyBorder(0, 0, 6, 0));

        painelDireito.add(lblMaquina, BorderLayout.NORTH);
        painelDireito.add(painelMaquina, BorderLayout.CENTER);

        // --- CENTRO: tabela de atributos ---
        JPanel painelTabela = criarPainelTabela();

        painelCentro.add(painelEsquerdo, BorderLayout.WEST);
        painelCentro.add(painelTabela,   BorderLayout.CENTER);
        painelCentro.add(painelDireito,  BorderLayout.EAST);
        painelFundo.add(painelCentro, BorderLayout.CENTER);

        // === RODAPÉ: placar + botões ===
        JPanel painelRodape = criarPainelRodape();
        painelFundo.add(painelRodape, BorderLayout.SOUTH);
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Fundo pergaminho levemente mais escuro
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(0xEEDFB8),
                    getWidth(), getHeight(), new Color(0xE0CB95)
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                // Borda ornamental dupla
                g2.setColor(OURO);
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(3, 3, getWidth()-6, getHeight()-6, 10, 10);
                g2.setColor(OURO_CLARO);
                g2.setStroke(new BasicStroke(0.8f));
                g2.drawRoundRect(7, 7, getWidth()-14, getHeight()-14, 8, 8);
                g2.dispose();
            }
        };
        painel.setOpaque(false);
        painel.setBorder(new EmptyBorder(16, 12, 16, 12));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título da tabela
        gbc.gridy = 0; gbc.gridx = 0; gbc.gridwidth = 4;
        JLabel lblTabelaTitulo = new JLabel("Comparacao de Atributos", SwingConstants.CENTER);
        lblTabelaTitulo.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 15));
        lblTabelaTitulo.setForeground(VERDE_ESCURO);
        lblTabelaTitulo.setBorder(new EmptyBorder(0, 0, 6, 0));
        painel.add(lblTabelaTitulo, gbc);
        gbc.gridwidth = 1;

        // Separador ornamental
        gbc.gridy = 1; gbc.gridx = 0; gbc.gridwidth = 4;
        painel.add(criarSeparadorOuro(), gbc);
        gbc.gridwidth = 1;

        // Cabeçalho
        gbc.gridy = 2;
        gbc.gridx = 0; painel.add(cabecalhoTabela("Atributo"), gbc);
        gbc.gridx = 1; painel.add(cabecalhoTabela("Voce"), gbc);
        gbc.gridx = 2; painel.add(cabecalhoTabela(""), gbc);
        gbc.gridx = 3; painel.add(cabecalhoTabela("Maquina"), gbc);

        // Linhas de atributos com fundo alternado
        for (int i = 0; i < ATRIBUTOS.length; i++) {
            gbc.gridy = i + 3;
            final boolean par = i % 2 == 0;

            gbc.gridx = 0;
            painel.add(wrapComFundo(lblAtributos[i], par), gbc);
            gbc.gridx = 1;
            painel.add(wrapComFundo(lblValoresJogador[i], par), gbc);
            gbc.gridx = 2;
            painel.add(wrapComFundo(lblResultados[i], par), gbc);
            gbc.gridx = 3;
            painel.add(wrapComFundo(lblValoresMaquina[i], par), gbc);
        }

        return painel;
    }

    private JPanel wrapComFundo(JLabel label, boolean par) {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(par
                    ? new Color(0xD4C285, false)  // transparente
                    : new Color(0xC8B575, false));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(3, 6, 3, 6));
        p.add(label, BorderLayout.CENTER);
        return p;
    }

    private JComponent criarSeparadorOuro() {
        JPanel sep = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(OURO);
                g2.setStroke(new BasicStroke(1.2f));
                int cy = getHeight() / 2;
                g2.drawLine(10, cy, getWidth() - 10, cy);
                g2.fillOval(getWidth()/2 - 4, cy - 4, 8, 8);
                g2.dispose();
            }
        };
        sep.setOpaque(false);
        sep.setPreferredSize(new Dimension(0, 14));
        return sep;
    }

    private JPanel criarPainelRodape() {
        JPanel painel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(0xDECE9A),
                    getWidth(), getHeight(), new Color(0xC8B070)
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(OURO);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(2, 2, getWidth()-4, getHeight()-4, 8, 8);
                g2.dispose();
            }
        };
        painel.setOpaque(false);
        painel.setBorder(new EmptyBorder(10, 16, 10, 16));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Linha 1: Pontuação da Rodada
        gbc.gridy = 0; gbc.gridx = 0;
        painel.add(rotuloRodape("Pontuacao da Rodada:"), gbc);
        gbc.gridx = 1; painel.add(rotuloAzul("Voce"), gbc);
        gbc.gridx = 2; painel.add(txtPlacarRodadaJogador, gbc);
        gbc.gridx = 3; painel.add(rotuloSep("x"), gbc);
        gbc.gridx = 4; painel.add(txtPlacarRodadaMaquina, gbc);
        gbc.gridx = 5; painel.add(rotuloVerm("Maquina"), gbc);

        // Linha 2: Pontuação Geral
        gbc.gridy = 1; gbc.gridx = 0;
        painel.add(rotuloRodape("Pontuacao Geral:"), gbc);
        gbc.gridx = 1; painel.add(rotuloAzul("Voce"), gbc);
        gbc.gridx = 2; painel.add(txtPlacarGeralJogador, gbc);
        gbc.gridx = 3; painel.add(rotuloSep("x"), gbc);
        gbc.gridx = 4; painel.add(txtPlacarGeralMaquina, gbc);
        gbc.gridx = 5; painel.add(rotuloVerm("Maquina"), gbc);

        // Linha 3: Vencedor
        gbc.gridy = 2; gbc.gridx = 0;
        painel.add(rotuloRodape("Vencedor da Partida:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 5;
        painel.add(txtVencedor, gbc);
        gbc.gridwidth = 1;

        // Linha 4: Botões
        gbc.gridy = 3; gbc.gridx = 1; gbc.gridwidth = 2;
        painel.add(btnJogar, gbc);
        gbc.gridx = 3; gbc.gridwidth = 2;
        painel.add(btnNova, gbc);
        gbc.gridwidth = 1;

        return painel;
    }

    private void configurarEventos() {
        btnJogar.addActionListener(e -> {
            String selecionado = (String) comboCartas.getSelectedItem();
            if (selecionado == null || cartasDesabilitadas.getOrDefault(selecionado, false)) {
                mostrarAviso("Esta carta ja foi usada! Escolha outra.");
                return;
            }
            controller.executarRodada(selecionado);
        });

        btnNova.addActionListener(e -> {
            int opcao = JOptionPane.showConfirmDialog(this,
                "Deseja iniciar uma nova partida?",
                "Nova Partida", JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) controller.novaPartida();
        });
    }

    // ===================== Métodos chamados pelo Controller =====================

    public void inicializarCartas(List<Carta> cartas) {
        // Remove listeners anteriores para evitar duplicação
        for (var l : comboCartas.getActionListeners()) comboCartas.removeActionListener(l);

        comboCartas.removeAllItems();
        cartasDesabilitadas.clear();
        for (Carta c : cartas) {
            comboCartas.addItem(c.getNome());
            cartasDesabilitadas.put(c.getNome(), false);
        }
        painelJogador.limpar();
        painelMaquina.limpar();

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
        painelMaquina.exibirCarta(rodada.getCartaMaquina());
        painelJogador.exibirCarta(rodada.getCartaJogador());

        lblValoresJogador[0].setText(String.format("%.2f",  rodada.getCartaJogador().getComprimento_mm()));
        lblValoresJogador[1].setText(String.format("%.3f",  rodada.getCartaJogador().getPeso_mg()));
        lblValoresJogador[2].setText(String.format("%.2f",  rodada.getCartaJogador().getVelocidade_kmh()));
        lblValoresJogador[3].setText(String.valueOf(         rodada.getCartaJogador().getLongevidade_dias()));
        lblValoresJogador[4].setText(String.format("%.2f",  rodada.getCartaJogador().getEnvergadura_mm()));
        lblValoresJogador[5].setText(String.format("%.3f",  rodada.getCartaJogador().getPopulacao_bilhoes()));

        lblValoresMaquina[0].setText(String.format("%.2f",  rodada.getCartaMaquina().getComprimento_mm()));
        lblValoresMaquina[1].setText(String.format("%.3f",  rodada.getCartaMaquina().getPeso_mg()));
        lblValoresMaquina[2].setText(String.format("%.2f",  rodada.getCartaMaquina().getVelocidade_kmh()));
        lblValoresMaquina[3].setText(String.valueOf(         rodada.getCartaMaquina().getLongevidade_dias()));
        lblValoresMaquina[4].setText(String.format("%.2f",  rodada.getCartaMaquina().getEnvergadura_mm()));
        lblValoresMaquina[5].setText(String.format("%.3f",  rodada.getCartaMaquina().getPopulacao_bilhoes()));

        int[] resultados = {
            rodada.getResComprimento(), rodada.getResPeso(),
            rodada.getResVelocidade(),  rodada.getResLongevidade(),
            rodada.getResEnvergadura(), rodada.getResPopulacao()
        };

        for (int i = 0; i < resultados.length; i++) {
            if (resultados[i] == 1) {
                lblResultados[i].setText("V");
                lblResultados[i].setForeground(VERDE_MEDIO);
            } else if (resultados[i] == -1) {
                lblResultados[i].setText("X");
                lblResultados[i].setForeground(VERMELHO_ANT);
            } else {
                lblResultados[i].setText("=");
                lblResultados[i].setForeground(MARROM_MEDIO);
            }
        }
    }

    public void atualizarPlacar(int ptRodadaJ, int ptRodadaM, int ptGeralJ, int ptGeralM) {
        txtPlacarRodadaJogador.setText(String.valueOf(ptRodadaJ));
        txtPlacarRodadaMaquina.setText(String.valueOf(ptRodadaM));
        txtPlacarGeralJogador.setText(String.valueOf(ptGeralJ));
        txtPlacarGeralMaquina.setText(String.valueOf(ptGeralM));

        if (ptGeralJ > ptGeralM) {
            txtVencedor.setText("Liderando: VOCE  (" + ptGeralJ + " x " + ptGeralM + ")");
            txtVencedor.setForeground(AZUL_ANT);
        } else if (ptGeralM > ptGeralJ) {
            txtVencedor.setText("Liderando: MAQUINA  (" + ptGeralJ + " x " + ptGeralM + ")");
            txtVencedor.setForeground(VERMELHO_ANT);
        } else {
            txtVencedor.setText("Empate  (" + ptGeralJ + " x " + ptGeralM + ")");
            txtVencedor.setForeground(MARROM_ESCURO);
        }
    }

    public void desabilitarCarta(String nome) {
        cartasDesabilitadas.put(nome, true);
    }

    public void exibirVencedorFinal(String vencedor, int ptJ, int ptM) {
        String texto, msg;
        if (vencedor.equals("JOGADOR")) {
            texto = "VOCE VENCEU A PARTIDA!";
            msg = String.format("Parabens! Voce venceu!\nSua pontuacao: %d  |  Maquina: %d", ptJ, ptM);
            txtVencedor.setForeground(AZUL_ANT);
        } else if (vencedor.equals("MAQUINA")) {
            texto = "MAQUINA VENCEU A PARTIDA!";
            msg = String.format("A maquina venceu desta vez!\nSua pontuacao: %d  |  Maquina: %d", ptJ, ptM);
            txtVencedor.setForeground(VERMELHO_ANT);
        } else {
            texto = "EMPATE!";
            msg = String.format("A partida terminou em empate!\nSua pontuacao: %d  |  Maquina: %d", ptJ, ptM);
            txtVencedor.setForeground(MARROM_ESCURO);
        }
        txtVencedor.setText(texto);
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
            lblResultados[i].setForeground(MARROM_MEDIO);
            lblValoresMaquina[i].setText("-");
        }
    }

    public void setController(JogoController controller) {
        this.controller = controller;
    }

    // ===================== Helpers visuais =====================

    private JTextField criarCampoTexto(int cols) {
        JTextField tf = new JTextField("0", cols) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(PERG_CLARO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        tf.setEditable(false);
        tf.setOpaque(false);
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setFont(new Font("Serif", Font.BOLD, 13));
        tf.setForeground(MARROM_ESCURO);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(OURO, 1),
            new EmptyBorder(2, 4, 2, 4)
        ));
        return tf;
    }

    private JButton criarBotaoVintage(String texto, Color cor) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(cor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(cor.brighter());
                } else {
                    g2.setColor(cor);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                // Borda ouro
                g2.setColor(OURO_CLARO);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 8, 8);
                // Texto
                g2.setColor(PERG_CLARO);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Serif", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(160, 38));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(false);
        return btn;
    }

    private JLabel rotuloRodape(String texto) {
        JLabel l = new JLabel(texto, SwingConstants.RIGHT);
        l.setFont(new Font("Serif", Font.BOLD, 13));
        l.setForeground(MARROM_ESCURO);
        return l;
    }

    private JLabel rotuloAzul(String texto) {
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 13));
        l.setForeground(AZUL_ANT);
        return l;
    }

    private JLabel rotuloVerm(String texto) {
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 13));
        l.setForeground(VERMELHO_ANT);
        return l;
    }

    private JLabel rotuloSep(String texto) {
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setFont(new Font("Serif", Font.BOLD, 14));
        l.setForeground(OURO);
        return l;
    }

    private JLabel cabecalhoTabela(String texto) {
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 13));
        l.setForeground(VERDE_ESCURO);
        l.setBorder(new EmptyBorder(0, 0, 4, 0));
        return l;
    }

    private void mostrarAviso(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    // ===================== Painel com textura pergaminho =====================

    static class PainelPergaminho extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Gradiente base pergaminho
            GradientPaint gp = new GradientPaint(
                0, 0,            new Color(0xF2E4BC),
                getWidth(), getHeight(), new Color(0xDDC98A)
            );
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());

            // Textura de ruído leve
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.04f));
            for (int i = 0; i < getWidth(); i += 3) {
                for (int j = 0; j < getHeight(); j += 3) {
                    int v = (int)(Math.random() * 80);
                    g2.setColor(new Color(v, v, 0));
                    g2.fillRect(i, j, 2, 2);
                }
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            // Borda ornamental externa
            g2.setColor(new Color(0xB8860B));
            g2.setStroke(new BasicStroke(3f));
            g2.drawRect(6, 6, getWidth()-12, getHeight()-12);
            g2.setColor(new Color(0xDAA520, false));
            g2.setStroke(new BasicStroke(1f));
            g2.drawRect(10, 10, getWidth()-20, getHeight()-20);

            g2.dispose();
        }
    }
}