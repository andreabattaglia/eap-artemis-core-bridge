/*
 *
 */
package com.redhat.demo.arch.artemis.bridge.consumer.ejb.messagedriven;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.redhat.demo.arch.artemis.bridge.consumer.commons.dto.MessageReceivedDTO;

@MessageDriven(name = "JournalQueueMDB",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType",
                        propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination",
                        propertyValue = "JournalQueue"),
                @ActivationConfigProperty(propertyName = "acknowledgeMode",
                        propertyValue = "Auto-acknowledge") })
public class JournalQueueMDB implements MessageListener {

    private final static Logger LOG = Logger
            .getLogger(JournalQueueMDB.class.toString());

    /** The event. */
    @Inject
    private Event<MessageReceivedDTO> event;

    /**
     * @see MessageListener#onMessage(Message)
     */
    @Override
    public void onMessage(Message rcvMessage) {
        TextMessage msg = null;
        try {
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                LOG.info("Received Message from queue: " + msg.getText());
                String messageContent = msg.getText();
                event.fire(new MessageReceivedDTO(messageContent));
            } else {
                LOG.warning("Message of wrong type: "
                        + rcvMessage.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
