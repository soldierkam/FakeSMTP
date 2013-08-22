/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nilhcem.fakesmtp.model;

import java.io.IOException;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimePart;

/**
 *
 * @author soldier
 */
public class PlainTextEmail {

    private final String text;

    public PlainTextEmail(MimePart message) throws IOException, MessagingException {
        this.text = (String) message.getContent();
    }

    public String getText() {
        return text;
    }
}
