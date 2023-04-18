package br.edu.ufrn.client.model;

import javax.swing.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.format.DateTimeFormatter;

public class ClientImpl extends UnicastRemoteObject implements Serializable, Client {

    private DefaultListModel<String> listModel;

    public ClientImpl(DefaultListModel<String> listModel) throws RemoteException {
        super();
        this.listModel = listModel;
    }

    @Override
    public void notifyEvent(Event event) throws RemoteException{
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String strDate = dtf.format(event.getDate());

        listModel.addElement(event.getEventType().getName()+ " - " + event.getDescription() + " - " + strDate);
    }

}
