package main;

import view.TelaPrincipal;
import controller.JogoController;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            TelaPrincipal tela = new TelaPrincipal();
            JogoController controller = new JogoController(tela);
            tela.setController(controller);
            tela.setVisible(true);
        });
    }
}