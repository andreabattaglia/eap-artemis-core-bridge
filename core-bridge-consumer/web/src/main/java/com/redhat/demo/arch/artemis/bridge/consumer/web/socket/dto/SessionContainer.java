/*
 *
 */
package com.redhat.demo.arch.artemis.bridge.consumer.web.socket.dto;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

@ApplicationScoped
public class SessionContainer {
    private Set<Session> clients = Collections
            .synchronizedSet(new HashSet<Session>());

    public Set<Session> getClients() {
        return clients;
    }

}
