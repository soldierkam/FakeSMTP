package com.nilhcem.fakesmtp.model;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A model representing a received email.
 * <p>
 * This object will be created and sent to observers by the {@code MailSaver}
 * object.<br />
 * It contains useful data such as the content of the email and its path in the
 * file system.
 * </p>
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class EmailModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailModel.class);
    private final Date receivedDate;
    private final byte[] rawData;
    private final String from;
    private final String to;
    private final String subject;
    private final PlainTextEmail plainTextEmail;
    private final HtmlMail htmlMail;
    private final List<Attachment> attachments;

    public EmailModel(String from, String to, InputStream is) throws IOException, MessagingException {
        this.receivedDate = new Date();
        this.from = from;
        this.to = to;
        this.rawData = IOUtils.toByteArray(is);
        final MimeMessage message = getMessage(new ByteArrayInputStream(rawData));
        this.subject = message.getSubject();
        MainHandler mainHandler = new MainHandler(message);
        plainTextEmail = mainHandler.getPlain();
        htmlMail = mainHandler.getHtml();
        attachments = mainHandler.getAttachments();
    }

    public PlainTextEmail getPlainTextEmail() {
        return plainTextEmail;
    }

    public HtmlMail getHtmlMail() {
        return htmlMail;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public MimeMessage getMessage(InputStream is) throws IOException, MessagingException {
        Session s = Session.getDefaultInstance(new Properties());
        return new MimeMessage(s, is);
    }

    /**
     * Converts an {@code InputStream} into a {@code String} object.
     * <p>
     * The method will not copy the first 4 lines of the input stream.<br />
     * These 4 lines are SubEtha SMTP additional information.
     * </p>
     *
     * @param is the inputstream to be converted.
     * @return the converted string object, containing data from the inputstream
     * passed in parameters.
     */
    private String convertStreamToString(InputStream is) {
        final long lineNbToStartCopy = 4; // Do not copy the first 4 lines (received part)
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        long lineNb = 0;
        try {
            while ((line = reader.readLine()) != null) {
                if (++lineNb > lineNbToStartCopy) {
                    sb.append(line).append(System.getProperty("line.separator"));
                }
            }
        } catch (IOException e) {
            LOGGER.error("", e);
        }
        return sb.toString();
    }

    private String decodeString(String str) {
        try {
            return MimeUtility.decodeText(MimeUtility.unfold(str));
        } catch (UnsupportedEncodingException ex) {
            return str;
        }
    }
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyhhmmssSSS");
}
