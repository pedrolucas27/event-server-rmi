package br.edu.ufrn.server.model;

import br.edu.ufrn.client.model.Client;
import br.edu.ufrn.client.model.Event;
import br.edu.ufrn.server.view.UI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImpl extends UnicastRemoteObject implements Server {

    private List<Event> events = new ArrayList<>();
    private Map<Integer, List<Client>> topicClientsMap = new ConcurrentHashMap<>();

    public ServerImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean registerEvent(Event event) throws RemoteException {
        if(Objects.isNull(event)) throw new RemoteException("Event is null.");
        events.add(event);

        int eventCode = event.getEventType().getCode();

        if(topicClientsMap.containsKey(eventCode)){
            topicClientsMap.get(eventCode).forEach(client -> {
                try {
                    client.notifyEvent(event);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return true;
    }

    @Override
    public boolean registerInterest(Client callBack, List<Integer> topics) throws RemoteException {
        if(Objects.isNull(callBack)) throw new RemoteException("Client is null.");

        if(topics.isEmpty()) throw new RemoteException("List of interested topics empty.");

        topics.forEach(topic -> {
            if(topicClientsMap.containsKey(topic)){
                topicClientsMap.get(topic).add(callBack);
            }else{
                topicClientsMap.put(topic, new ArrayList<>(List.of(callBack)));
            }
        });

        UI.log("Interesse registrado com sucesso em ["+ZonedDateTime.now()+"]");

        return true;
    }



}
