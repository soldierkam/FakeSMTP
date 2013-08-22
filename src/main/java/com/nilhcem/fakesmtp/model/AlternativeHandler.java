/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nilhcem.fakesmtp.model;

import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author soldier
 */
public class AlternativeHandler extends AbstractMultipartHandler {

    public static class AlternativeBuilder implements MultipartHandler.Builder {

        @Override
        public MultipartHandler build(MimeMultipart multipart) throws IOException, MessagingException {
            return new AlternativeHandler(multipart);
        }
    }

    public AlternativeHandler(MimeMultipart mimeMultipart) throws IOException, MessagingException {
        for (int bodyPartIdx = 0; bodyPartIdx < mimeMultipart.getCount(); bodyPartIdx++) {
            final MimeBodyPart bodyPart = (MimeBodyPart) mimeMultipart.getBodyPart(bodyPartIdx);
            if (bodyPart.isMimeType("text/plain")) {
                plainTextEmail = new PlainTextEmail(bodyPart);
            } else if (bodyPart.isMimeType("text/html")) {
                htmlMail = new HtmlMail(bodyPart);
            } else {
                handleMultipart(bodyPart);
            }
        }
    }
}
