package com.nilhcem.fakesmtp.model;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author soldier
 */
public class MainHandler extends AbstractMultipartHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainHandler.class);

    public MainHandler(MimeMessage message) throws IOException, MessagingException, IllegalArgumentException {
        if (message.isMimeType("text/plain")) {
            plainTextEmail = new PlainTextEmail(message);
            htmlMail = null;
            attachments = Collections.EMPTY_LIST;
        } else if (message.isMimeType("text/html")) {
            Object content = message.getContent();
            plainTextEmail = null;
            htmlMail = new HtmlMail((String) content);
            attachments = Collections.EMPTY_LIST;
        } else if (message.getContentType().startsWith("multipart/")) {
            final MultipartHandler handler = MultipartHanlderFactory.getInstance().getHandler(message);
            mergerHandlerParseResults(handler);
        } else {
            LOGGER.warn("Unknown body part " + message + " " + message.getContentType());
        }
        if (attachments == null) {
            attachments = Collections.EMPTY_LIST;
        }
    }
}
