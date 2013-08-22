/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nilhcem.fakesmtp.model;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author soldier
 */
public class RelatedHandler extends AbstractMultipartHandler {

    public static class RelatedBuilder implements MultipartHandler.Builder {

        @Override
        public MultipartHandler build(MimeMultipart multipart) throws IOException, MessagingException {
            return new RelatedHandler(multipart);
        }
    }

    public RelatedHandler(MimeMultipart mimeMultipart) throws IOException, MessagingException {
        for (int relatedBodyPartIdx = 0; relatedBodyPartIdx < mimeMultipart.getCount(); relatedBodyPartIdx++) {
            MimeBodyPart bodyPart = (MimeBodyPart) mimeMultipart.getBodyPart(relatedBodyPartIdx);
            if (bodyPart.isMimeType("text/html")) {
                htmlMail = new HtmlMail(bodyPart, mimeMultipart);
            } else {
                handleMultipart(bodyPart);
            }
        }
    }
}