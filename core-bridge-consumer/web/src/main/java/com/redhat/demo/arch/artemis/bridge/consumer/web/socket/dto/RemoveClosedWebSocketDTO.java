/*
 * 
 */
package com.redhat.demo.arch.artemis.bridge.consumer.web.socket.dto;

import javax.websocket.Session;

public class RemoveClosedWebSocketDTO {
    private final Session closedSession;

    public RemoveClosedWebSocketDTO(Session closedSession) {
        this.closedSession = closedSession;
    }

    public javax.websocket.Session getClosedSession() {
        return closedSession;
    }

}
