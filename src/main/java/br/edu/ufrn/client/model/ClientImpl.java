package br.edu.ufrn.client.model;

import javax.swing.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Serializable, Client {

    private DefaultListModel<String> listModel;

    public ClientImpl(DefaultListModel<String> listModel) throws RemoteException {
        super();
        this.listModel = listModel;
    }

    @Override
    public void notifyEvent(Event event) throws RemoteException{
        listModel.addElement(event.getEventType().getName()+ " - " + event.getDescription());
    }

}
