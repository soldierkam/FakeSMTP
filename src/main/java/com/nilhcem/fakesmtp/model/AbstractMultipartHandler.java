package com.nilhcem.fakesmtp.model;

import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author soldier
 */
abstract class AbstractMultipartHandler implements MultipartHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMultipartHandler.class);
    protected PlainTextEmail plainTextEmail;
    protected HtmlMail htmlMail;
    protected List<Attachment> attachments;

    protected final void handleMultipart(MimeBodyPart bodyPart) throws IOException, MessagingException {
        if (bodyPart.getContentType().startsWith("multipart/")) {
            final MultipartHandler handler = MultipartHanlderFactory.getInstance().getHandler(bodyPart);
            mergerHandlerParseResults(handler);
        } else {
            handleUknownContentType(bodyPart);
        }
    }

    protected void handleUknownContentType(MimeBodyPart bodyPart) throws MessagingException, IOException {
        LOGGER.warn("Unknown body part " + bodyPart + " " + bodyPart.getContentType());
    }

    protected final void mergerHandlerParseResults(MultipartHandler hanlder) {
        if (hanlder == null) {
            return;
        }
        plainTextEmail = firstNotNull(plainTextEmail, hanlder.getPlain());
        htmlMail = firstNotNull(htmlMail, hanlder.getHtml());
        attachments = firstNotNull(attachments, hanlder.getAttachments());
    }

    private <T> T firstNotNull(T o1, T o2) {
        return o1 == null ? o2 : o1;
    }

    @Override
    public PlainTextEmail getPlain() {
        return plainTextEmail;
    }

    @Override
    public HtmlMail getHtml() {
        return htmlMail;
    }

    @Override
    public List<Attachment> getAttachments() {
        return attachments;
    }
}
