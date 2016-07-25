/*
 *
 */
package com.redhat.demo.arch.artemis.bridge.consumer.web.socket.endpoints;

import java.util.Set;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;

import com.redhat.demo.arch.artemis.bridge.consumer.commons.dto.MessageReceivedDTO;
import com.redhat.demo.arch.artemis.bridge.consumer.web.socket.dto.SessionContainer;

@ServerEndpoint("/websocket/consumer")
public class ConsumerEndpoint {

    /** The log. */
    @Inject
    private Logger LOG;

    @Inject
    private SessionContainer sessionContainer;

    // store the session once that it's opened
    /**
     * On open.
     *
     * @param session
     *            the session
     */
    @OnOpen
    public void onOpen(Session session) {
        LOG.info("New websocket session opened: " + session.getId());
        Set<Session> clients = sessionContainer.getClients();
        synchronized (clients) {
            clients.add(session);
        }

    }

    // remove the session after it's closed
    /**
     * On close.
     *
     * @param session
     *            the session
     */
    @OnClose
    public void onClose(Session session) {
        LOG.info("Websoket session closed: " + session.getId());
        Set<Session> clients = sessionContainer.getClients();
        synchronized (clients) {
            clients.remove(session);
        }
    }

    // Exception handling
    /**
     * Error.
     *
     * @param session
     *            the session
     * @param t
     *            the t
     */
    @OnError
    public void error(Session session, Throwable t) {
        t.printStackTrace();
    }

    /**
     * @param event
     *            the event
     */
    public void onJournalMessage(@Observes MessageReceivedDTO event) {
        String message = event.getMessage();
        LOG.info("Received Journal message :\"{}\"", message);
        Set<Session> clients = sessionContainer.getClients();
        synchronized (clients) {
            for (Session s : clients) {
                try {
                    s.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    LOG.debug("Session id {} is dirty", s.getId());
                }
            }
        }

    }
}
