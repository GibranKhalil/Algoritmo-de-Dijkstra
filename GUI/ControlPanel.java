package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    public ControlPanel(MapPanel mapPanel) {
        setLayout(new BorderLayout());
        setBackground(null);
        Border topBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK);
        setBorder(topBorder);

        // Painel para labels e comboboxes (disposição vertical)
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.Y_AXIS)); // 2 linhas, 2 colunas
        comboBoxPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        comboBoxPanel.setBackground(null);

        JPanel originPanel = new JPanel();
        originPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));

        JLabel originLabel = new JLabel("Origem:");
        originLabel.setForeground(Color.red);
        originPanel.add(originLabel);
        JComboBox<String> originComboBox = new JComboBox<>(getCities());
        originComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.setOrigemSelecionada((String) originComboBox.getSelectedItem());
            }
        });
        originPanel.add(originComboBox);

        JPanel destinationPanel = new JPanel();
        destinationPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));

        JLabel destinationLabel = new JLabel("Destino:");
        destinationLabel.setForeground(new Color(61, 99, 221));

        destinationPanel.add(destinationLabel);

        JComboBox<String> destinationComboBox = new JComboBox<>(getCities());
        destinationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.setDestinoSelecionado((String) destinationComboBox.getSelectedItem());
            }
        });
        destinationPanel.add(destinationComboBox);

        comboBoxPanel.add(originPanel);
        comboBoxPanel.add(destinationPanel);

        add(comboBoxPanel, BorderLayout.WEST); // Posiciona comboBoxPanel à esquerda

        // Painel para os botões (disposição horizontal)
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(null);
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton clearButton = new JButton("Limpar Mapa");
        clearButton.setForeground(Color.RED);
        clearButton.setBackground(null);
        clearButton.setBorderPainted(false);
        clearButton.setContentAreaFilled(false);
        buttonPanel.add(clearButton);


        JButton searchButton = new JButton("Buscar");
        searchButton.setBackground(new Color(61, 99, 221));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String origem = (String) originComboBox.getSelectedItem();
                String destino = (String) destinationComboBox.getSelectedItem();
            }
        });
        buttonPanel.add(searchButton);

        add(buttonPanel, BorderLayout.EAST); // Posiciona buttonPanel à direita
    }

    private String[] getCities() {
        return new String[] {
                "São José do Rio Preto", "Araraquara", "Ribeirão Preto",
                "Araçatuba", "Piracicaba", "Campinas", "São José dos Campos",
                "Marília", "Bauru", "Sorocaba", "Presidente Prudente",
                "São Paulo", "Santos"
        };
    }
}
