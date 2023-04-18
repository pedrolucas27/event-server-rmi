package br.edu.ufrn.client.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

@FunctionalInterface
public interface Client extends Remote {

    void notifyEvent(Event event) throws RemoteException;
}
