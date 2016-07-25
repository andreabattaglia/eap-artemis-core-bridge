/*
 *
 */
package com.redhat.demo.arch.artemis.bridge.producer.ejb.services.impl;

/*
 *
 */

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.demo.arch.artemis.bridge.producer.ejb.services.JournalProducer;

@Stateless
@Local(JournalProducer.class)
public class JournalProducerBean implements JournalProducer {
    /**
     * Logger for this class
     */
    private static final Logger LOG = LoggerFactory.getLogger(JournalProducerBean.class);

//    @Inject
//    private Logger LOG;

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:/jms/queue/JournalQueue")
    private Queue queue;

    /**
     * @throws JMSException
     * @see com.redhat.demo.arch.artemis.bridge.producer.ejb.services.impl.JournalProducer
     *      #produce(java.lang.String)
     */
    @Override
    public boolean produce(String messageContent) {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        TextMessage message = null;

        try {
            // Create an authenticated JMS Connection
            // connection = connectionFactory.createConnection("jmsuser",
            // "Pa$$w0rd.01");
            connection = connectionFactory.createConnection();

            // Create a JMS Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create a JMS Message Producer
            producer = session.createProducer(queue);

            // Create a Text Message
            message = session.createTextMessage(messageContent == null
                    ? "This is a text message" : messageContent);

            LOG.info("Sent message: " + message.getText());

            // Send the Message
            producer.send(message);
        } catch (JMSException e) {
            LOG.error("",e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (producer != null) {
                    producer.close();
                }
                if (session != null) {
                    session.close();
                }
                if (connection != null) {
                    connection.close();
                }

            } catch (JMSException e) {
                LOG.error("",e);
                throw new RuntimeException(e);
            }
        }

        return true;
    }
}