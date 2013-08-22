package com.nilhcem.fakesmtp.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author soldier
 */
public class MultipartHanlderFactory {

    private final Map<String, MultipartHandler.Builder> builders = new HashMap<String, MultipartHandler.Builder>();
    private final static MultipartHanlderFactory INSTANCE = new MultipartHanlderFactory();

    private MultipartHanlderFactory() {
        add("multipart/alternative", new AlternativeHandler.AlternativeBuilder());
        add("multipart/related", new RelatedHandler.RelatedBuilder());
        add("multipart/mixed", new MixedHandler.MixedBuilder());
    }

    public static MultipartHanlderFactory getInstance() {
        return INSTANCE;
    }

    private void add(String mime, MultipartHandler.Builder b) {
        builders.put(mime, b);
    }

    public MultipartHandler getHandler(MimeMessage multipart) throws MessagingException, IOException {
        MultipartHandler.Builder builder = getHandlerBuilder(multipart.getContentType());
        return builder == null ? null : builder.build((MimeMultipart) multipart.getContent());
    }

    public MultipartHandler getHandler(MimeBodyPart bodyPart) throws MessagingException, IOException {
        MultipartHandler.Builder builder = getHandlerBuilder(bodyPart.getContentType());
        return builder == null ? null : builder.build((MimeMultipart) bodyPart.getContent());
    }

    private MultipartHandler.Builder getHandlerBuilder(String contentType) {
        int lastIdx = contentType.contains(";") ? contentType.indexOf(';') : contentType.length();
        return builders.get(contentType.substring(0, lastIdx));
    }
}
