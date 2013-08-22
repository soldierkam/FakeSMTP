/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nilhcem.fakesmtp.model;

import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author soldier
 */
public interface MultipartHandler {

    PlainTextEmail getPlain();

    HtmlMail getHtml();

    List<Attachment> getAttachments();

    interface Builder {

        MultipartHandler build(MimeMultipart multipart) throws IOException, MessagingException;
    }
}
