/*
 *
 */
package com.redhat.demo.arch.artemis.bridge.consumer.commons.dto;

public class MessageReceivedDTO {
    private final String message;

    public MessageReceivedDTO(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageReceivedEvent [\n    message=");
        builder.append(message);
        builder.append("\n]");
        return builder.toString();
    }

}
