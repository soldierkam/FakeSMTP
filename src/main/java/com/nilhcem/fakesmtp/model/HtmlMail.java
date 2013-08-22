/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nilhcem.fakesmtp.model;

import com.nilhcem.fakesmtp.server.MailSaver;
import com.sun.mail.util.BASE64DecoderStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.util.Base64;

/**
 *
 * @author soldier
 */
public class HtmlMail {

    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlMail.class);
    private final String html;

    public HtmlMail(String html) {
        this.html = html;
    }

    public HtmlMail(BodyPart htmlPart) throws IOException, MessagingException {
        html = (String) htmlPart.getContent();
    }

    public HtmlMail(BodyPart htmlPart, MimeMultipart message) throws IOException, MessagingException {
        final String originalHtml = (String) htmlPart.getContent();
        final Document htmlDoc = Jsoup.parse(originalHtml);
        final Elements elements = htmlDoc.getElementsByAttribute("src");
        for (Element element : elements) {
            for (Attribute attribute : element.attributes()) {
                if (attribute.getKey().equalsIgnoreCase("src") && attribute.getValue().startsWith("cid:")) {
                    try {
                        URI uri = new URI(attribute.getValue());
                        BodyPart bodyPart = message.getBodyPart('<' + uri.getSchemeSpecificPart() + '>');
                        attribute.setValue(decodeBodyPart(bodyPart));
                    } catch (URISyntaxException exc) {
                        LOGGER.warn("Wrong URI in attribute " + attribute, exc);
                    } catch (MessagingException exc) {
                        LOGGER.warn("Cannot get part ref by " + attribute, exc);
                    }
                }
            }
        }
        html = htmlDoc.toString();
    }

    private static String decodeBodyPart(BodyPart bodyPart) throws MessagingException, IOException {
        //data:<MIMETYPE>;base64,<BASE64_ENCODED_IMAGE>
        final int dataSize = bodyPart.getSize() * 2;
        StringBuilder sb = new StringBuilder(40 + dataSize);
        sb.append("data:").append(bodyPart.getContentType()).append(";base64,");
        Object content = bodyPart.getContent();
        if (content instanceof InputStream) {
            sb.append(Base64.encodeToString(IOUtils.toByteArray((InputStream) content), false));
        } else {
            LOGGER.warn("Unknown data " + content);
        }
        return sb.toString();
    }

    public String getHtml() {
        return html;
    }
}
