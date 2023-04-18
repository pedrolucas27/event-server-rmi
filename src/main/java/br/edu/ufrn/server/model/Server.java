package br.edu.ufrn.server.model;

import br.edu.ufrn.client.model.Client;
import br.edu.ufrn.client.model.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Server extends Remote {

    boolean registerEvent(Event event) throws RemoteException;

    boolean registerInterest(Client client, List<Integer> topics) throws RemoteException;

}
