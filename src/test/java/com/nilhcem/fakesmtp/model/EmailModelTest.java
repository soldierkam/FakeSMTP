package com.nilhcem.fakesmtp.model;

import java.io.IOException;
import java.io.InputStream;
import javax.mail.MessagingException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author soldier
 */
public class EmailModelTest {

    private EmailModel build(String fileName) throws IOException, MessagingException {
        InputStream stream = getClass().getResourceAsStream(fileName);
        if (stream == null) {
            throw new RuntimeException("Does not exists: '" + fileName + "'");
        }
        return new EmailModel("from@example.com", "to@example.com", stream);
    }

    @Test
    public void testHtmlWithImg() throws IOException, MessagingException {
        EmailModel model = build("html_with_img.mail");
        Assert.assertNull(model.getPlainTextEmail());
        Assert.assertNotNull(model.getHtmlMail());
        Assert.assertNotNull(model.getAttachments());
        Assert.assertEquals(0, model.getAttachments().size());
    }

    @Test
    public void testPlainPlusHtml() throws IOException, MessagingException {
        EmailModel model = build("plain_plus_html.mail");
        Assert.assertNotNull(model.getPlainTextEmail());
        Assert.assertNotNull(model.getHtmlMail());
        Assert.assertNotNull(model.getAttachments());
        Assert.assertEquals(0, model.getAttachments().size());
    }

    @Test
    public void testPlainText() throws IOException, MessagingException {
        EmailModel model = build("plain_text.mail");
        Assert.assertNotNull(model.getPlainTextEmail());
        Assert.assertNull(model.getHtmlMail());
        Assert.assertNotNull(model.getAttachments());
        Assert.assertEquals(0, model.getAttachments().size());
    }

    @Test
    public void testSimpleHtml() throws IOException, MessagingException {
        EmailModel model = build("simple_html.mail");
        Assert.assertNull(model.getPlainTextEmail());
        Assert.assertNotNull(model.getHtmlMail());
        Assert.assertNotNull(model.getAttachments());
        Assert.assertEquals(0, model.getAttachments().size());
    }

    @Test
    public void testSimplePlainPlusHtml() throws IOException, MessagingException {
        EmailModel model = build("simple_plain_plus_html.mail");
        Assert.assertNotNull(model.getPlainTextEmail());
        Assert.assertNotNull(model.getHtmlMail());
        Assert.assertNotNull(model.getAttachments());
        Assert.assertEquals(0, model.getAttachments().size());
    }

    @Test
    public void testAttachment() throws IOException, MessagingException {
        EmailModel model = build("with_attachment.mail");
        Assert.assertNotNull(model.getPlainTextEmail());
        Assert.assertNotNull(model.getHtmlMail());
        Assert.assertNotNull(model.getAttachments());
        Assert.assertEquals(1, model.getAttachments().size());
    }
}
