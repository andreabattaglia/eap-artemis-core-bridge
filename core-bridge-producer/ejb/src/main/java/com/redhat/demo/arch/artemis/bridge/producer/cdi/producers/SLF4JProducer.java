/*
 *
 */
package com.redhat.demo.arch.artemis.bridge.producer.cdi.producers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SLF4JProducer.
 */
@ApplicationScoped
public class SLF4JProducer {

    /**
     * Produce.
     *
     * @param ip the ip
     * @return the logger
     */
    @Produces
    public Logger produce(InjectionPoint ip) {
        return LoggerFactory.getLogger(ip.getMember().getDeclaringClass()
                .getName());
    }
}
