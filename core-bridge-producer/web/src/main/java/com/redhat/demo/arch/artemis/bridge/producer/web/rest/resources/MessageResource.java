/*
 *
 */
package com.redhat.demo.arch.artemis.bridge.producer.web.rest.resources;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.redhat.demo.arch.artemis.bridge.producer.ejb.services.JournalProducer;

@Path("message")
public class MessageResource {

    @EJB
    private JournalProducer journalProducerService;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String sendMessage(
            @QueryParam("messageContent") String messageContent) {
        return journalProducerService.produce(messageContent) ? "OK" : "KO";
    }
}
