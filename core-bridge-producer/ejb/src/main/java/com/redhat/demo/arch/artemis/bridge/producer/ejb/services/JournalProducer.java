/*
 *
 */
package com.redhat.demo.arch.artemis.bridge.producer.ejb.services;

public interface JournalProducer {

    boolean produce(String messageContent);

}