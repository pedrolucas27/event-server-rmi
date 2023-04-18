package br.edu.ufrn.client;

import br.edu.ufrn.client.view.UI;
import br.edu.ufrn.server.model.Server;

import java.rmi.Naming;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = (Server) Naming.lookup("rmi://localhost:1098/EventServer");
        new UI(server).start();
    }
}
