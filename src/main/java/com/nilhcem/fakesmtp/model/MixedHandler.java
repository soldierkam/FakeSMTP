/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nilhcem.fakesmtp.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.io.IOUtils;
import org.subethamail.smtp.util.Base64;

/**
 *
 * @author soldier
 */
public class MixedHandler extends AbstractMultipartHandler {

    public static class MixedBuilder implements MultipartHandler.Builder {

        @Override
        public MultipartHandler build(MimeMultipart multipart) throws IOException, MessagingException {
            return new MixedHandler(multipart);
        }
    }

    public MixedHandler(MimeMultipart mimeMultipart) throws IOException, MessagingException {
        for (int relatedBodyPartIdx = 0; relatedBodyPartIdx < mimeMultipart.getCount(); relatedBodyPartIdx++) {
            MimeBodyPart bodyPart = (MimeBodyPart) mimeMultipart.getBodyPart(relatedBodyPartIdx);
            if (bodyPart.isMimeType("text/html")) {
                htmlMail = new HtmlMail(bodyPart, mimeMultipart);
            } else {
                handleMultipart(bodyPart);
            }
        }
    }

    @Override
    protected void handleUknownContentType(MimeBodyPart bodyPart) throws MessagingException, IOException {
        if (attachments == null) {
            attachments = new LinkedList<Attachment>();
        }
        final Object content = bodyPart.getContent();
        if (content instanceof InputStream) {
            attachments.add(new Attachment((InputStream) content, bodyPart.getContentType(), bodyPart.getDisposition(), bodyPart.getFileName()));
        } else {
            super.handleUknownContentType(bodyPart);
        }
    }
}
