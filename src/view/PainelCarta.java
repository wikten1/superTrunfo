package view;

import model.Carta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class PainelCarta extends JPanel {

	private JLabel lblImagem;
	private String imagemPadrao;
	private int largura;
	private int altura;

	public PainelCarta(String imagemPadrao, int largura, int altura) {
		this.imagemPadrao = imagemPadrao;
		this.largura = largura;
		this.altura = altura;

		setLayout(new BorderLayout());
		setOpaque(false); // transparente — mostra o fundo da interface
		setBorder(new EmptyBorder(2, 2, 2, 2));
		setPreferredSize(new Dimension(largura, altura));

		lblImagem = new JLabel();
		lblImagem.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagem.setVerticalAlignment(SwingConstants.CENTER);
		lblImagem.setOpaque(false);

		add(lblImagem, BorderLayout.CENTER);
		limpar();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Mesmo gradiente do PainelPergaminho
		GradientPaint gp = new GradientPaint(0, 0, new Color(0xF2E4BC), getWidth(), getHeight(), new Color(0xDDC98A));
		g2.setPaint(gp);
		g2.fillRect(0, 0, getWidth(), getHeight());

		// Mesma textura de ruído do PainelPergaminho
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.04f));
		java.util.Random rand = new java.util.Random(42); // seed fixo = textura consistente
		for (int i = 0; i < getWidth(); i += 3) {
			for (int j = 0; j < getHeight(); j += 3) {
				int v = rand.nextInt(80);
				g2.setColor(new Color(v, v, 0));
				g2.fillRect(i, j, 2, 2);
			}
		}

		g2.dispose();
	}

	public void exibirCarta(Carta carta) {
		if (carta == null) {
			limpar();
			return;
		}
		carregarImagem(carta.getImagemPath());
	}

	private void carregarImagem(String imagemPath) {
		if (imagemPath == null || imagemPath.isEmpty()) {
			limpar();
			return;
		}
		try {
			URL url = getClass().getClassLoader().getResource(imagemPath);
			if (url != null) {
				ImageIcon icon = new ImageIcon(url);
				Dimension d = calcularDimensao();
				Image img = icon.getImage().getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH);
				lblImagem.setIcon(new ImageIcon(img));
			} else {
				limpar();
			}
		} catch (Exception e) {
			limpar();
		}
	}

	public void limpar() {
		try {
			URL url = getClass().getClassLoader().getResource(imagemPadrao);
			if (url != null) {
				ImageIcon icon = new ImageIcon(url);
				Dimension d = calcularDimensao();
				Image img = icon.getImage().getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH);
				lblImagem.setIcon(new ImageIcon(img));
			} else {
				lblImagem.setIcon(null);
				lblImagem.setText("[ carta ]");
			}
		} catch (Exception e) {
			lblImagem.setIcon(null);
		}
	}

	private Dimension calcularDimensao() {
		int maxW = largura - 4;
		int maxH = altura - 4;
		int w = (int) (maxH * 1023.0 / 1537.0);
		int h = maxH;
		if (w > maxW) {
			w = maxW;
			h = (int) (maxW * 1537.0 / 1023.0);
		}
		return new Dimension(w, h);
	}
}