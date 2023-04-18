package br.edu.ufrn.client.view;

import br.edu.ufrn.client.model.ClientImpl;
import br.edu.ufrn.client.model.Event;
import br.edu.ufrn.client.model.EventType;
import br.edu.ufrn.server.model.Server;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class UI {

    private Server server;

    private JFrame jFrame;
    private DefaultListModel<String> listModel;

    private final Integer CLIENT_CODE = new Random().nextInt(1000);

    private final List<EventType> topics = List.of(EventType.POLICE_OFFICER, EventType.FIRE_DEPARTMENT, EventType.CIVIL_DEFENSE);

    public UI(Server server) {
        this.server = server;

        this.jFrame = new JFrame();
        this.listModel = new DefaultListModel<>();
    }

    public void registerInterestClient(List<Integer> options) {
        try {
            boolean result = server.registerInterest(new ClientImpl(listModel), options);
            if(result) {
                JOptionPane.showMessageDialog(null, "Interesse registrado com sucesso!");
                jFrame.setVisible(Boolean.FALSE);
                openEventCenter();
            }
        }catch (RemoteException ex){
            JOptionPane.showMessageDialog(null, "Erro no registro de interesse do cliente. Tente novamente!");
        }
    }

    public void registerEvent(EventType eventType, String description){
        try {
            boolean result = server.registerEvent(new Event(description, ZonedDateTime.now(), eventType));
            if(result) JOptionPane.showMessageDialog(null, "Evento registrado.");
        }catch (RemoteException ex){
            JOptionPane.showMessageDialog(null, "Erro no envio do evento para o servidor. Tente novamente!");
        }
    }

    private void configureWindow(JFrame jFrame) {
        jFrame.setTitle("Cliente - "+CLIENT_CODE);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(700, 300);
        jFrame.setLocationRelativeTo(null);
    }

    public void registrationInterest(){
        configureWindow(jFrame);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JLabel title = new JLabel("Registre seu interesse de acordo com as seguintes opções:");
        title.setFont(new Font("Arial", Font.BOLD, 17));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(title);

        List<JCheckBox> checkboxes = new ArrayList<>();

        topics.forEach(it -> {
            JCheckBox checkbox = new JCheckBox(it.getName());
            checkbox.setFont(new Font("Arial", Font.PLAIN, 14));
            checkbox.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(checkbox);
            checkboxes.add(checkbox);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(buttonPanel);

        JButton submitButton = new JButton("Enviar");
        submitButton.addActionListener(e -> {
            List<Integer> codeTopicsInterest = new ArrayList<>();

            if (checkboxes.get(0).isSelected()) codeTopicsInterest.add(1);
            if (checkboxes.get(1).isSelected()) codeTopicsInterest.add(2);
            if (checkboxes.get(2).isSelected()) codeTopicsInterest.add(3);

            if (codeTopicsInterest.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Selecione ao menos uma opção para prosseguir.");
            }else {
                registerInterestClient(codeTopicsInterest);
            }
        });

        buttonPanel.add(submitButton);
        jFrame.add(mainPanel);
        jFrame.setVisible(Boolean.TRUE);
    }

    public void openEventCenter(){
        this.jFrame = new JFrame();
        configureWindow(jFrame);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Registrar Evento", getTabEventRegister());
        tabbedPane.addTab("Eventos de meu interesse", getEventList());

        mainPanel.add(tabbedPane);


        Font tabFont = tabbedPane.getFont().deriveFont(Font.PLAIN, 18f);
        tabbedPane.setFont(tabFont);

        jFrame.add(mainPanel);
        jFrame.setVisible(true);
    }

    private JPanel getTabEventRegister(){
        JPanel tab1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tab1.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        ButtonGroup btnGroup = new ButtonGroup();

        List<EventType> options = List.of(EventType.POLICE_OFFICER, EventType.FIRE_DEPARTMENT, EventType.CIVIL_DEFENSE);
        List<JRadioButton> jRadioButtons = new ArrayList<>();

        options.forEach(it -> {
            JRadioButton jr = new JRadioButton(it.getName());
            jr.setFont(new Font("Arial", Font.PLAIN, 14));
            btnGroup.add(jr);
            tab1.add(jr);
            jRadioButtons.add(jr);
        });


        JTextField textField = new JTextField(50);
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setHorizontalAlignment(JTextField.LEFT);
        tab1.add(textField);

        JButton button = new JButton("Enviar");

        AtomicInteger option = new AtomicInteger();

        button.addActionListener(e -> {
            if(jRadioButtons.get(0).isSelected())  option.set(1);
            else if(jRadioButtons.get(1).isSelected()) option.set(2);
            else if(jRadioButtons.get(2).isSelected()) option.set(3);
            else JOptionPane.showMessageDialog(null, "Selecione o tópico em que deseja cadastrar a notícia.");

            if(textField.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Preencha o input com a notícia.");
            }else{
                registerEvent(EventType.getEventTypeByCode(option.get()), textField.getText());
            }

        });

        tab1.add(button);

        return tab1;
    }

    public JPanel getEventList(){
        JPanel tab2 = new JPanel(new FlowLayout());

        JList<String> list = new JList<>(listModel);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(650, 200));

        tab2.add(scrollPane);

        return tab2;
    }

    public void start(){
        registrationInterest();
    }

}
