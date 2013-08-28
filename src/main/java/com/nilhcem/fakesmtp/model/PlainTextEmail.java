package com.nilhcem.fakesmtp.model;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.MimePart;

/**
 *
 * @author soldier
 */
public class PlainTextEmail {

    private final CompressedString text;

    public PlainTextEmail(MimePart message) throws IOException, MessagingException {
        this.text = new CompressedString((String) message.getContent());
    }

    public String getText() {
        return text.get();
    }
}
