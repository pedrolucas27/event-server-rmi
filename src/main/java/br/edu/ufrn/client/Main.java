package br.edu.ufrn.client;

import br.edu.ufrn.client.view.UI;
import br.edu.ufrn.server.model.Server;

import javax.swing.*;
import java.rmi.Naming;

public class Main {

    public static void main(String[] args) {
        try {
            Server server = (Server) Naming.lookup("rmi://localhost:1098/EventServer");
            new UI(server).start();
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Erro de comunicação com o Servidor!");
        }

    }
}
