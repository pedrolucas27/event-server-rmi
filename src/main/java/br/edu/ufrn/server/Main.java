package br.edu.ufrn.server;

import br.edu.ufrn.server.model.ServerImpl;
import br.edu.ufrn.server.view.UI;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1098);
            registry.bind("EventServer", new ServerImpl());

            System.out.println("Servidor Eventos OK.");
        }catch (RemoteException | AlreadyBoundException exception){
            UI.log("Erro na inicialização do servidor: " + exception.getMessage());
        }

    }
}