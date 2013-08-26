package com.nilhcem.fakesmtp.gui;

import com.nilhcem.fakesmtp.core.I18n;
import com.nilhcem.fakesmtp.model.EmailModel;
import java.util.Observable;
import java.util.Observer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

/**
 *
 * @author soldier
 */
public class MailPanel implements Observer {
    
    private final TabItem htmlItem;
    private final TabItem plainTextItem;
    private final TabItem attachmentsItem;
    private final TextPanel textPanel;
    private final HtmlPanel htmlPanel;
    private final AttachmentPanel attachmentPanel;
    
    public MailPanel(Composite parent) {
        TabFolder tabs = new TabFolder(parent, SWT.NONE);
        htmlItem = new TabItem(tabs, SWT.NONE);
        htmlItem.setText(I18n.INSTANCE.get("mail.panel.html.tab"));
        plainTextItem = new TabItem(tabs, SWT.NONE);
        plainTextItem.setText(I18n.INSTANCE.get("mail.panel.plaintext.tab"));
        attachmentsItem = new TabItem(tabs, SWT.NONE);
        attachmentsItem.setText(I18n.INSTANCE.get("mail.panel.attachments.tab"));
        
        htmlPanel = new HtmlPanel(tabs, htmlItem);
        textPanel = new TextPanel(tabs, plainTextItem);
        attachmentPanel = new AttachmentPanel(tabs, attachmentsItem);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MailList) {
            final MailList table = (MailList) o;
            EmailModel model = table.getSelected();
            htmlPanel.update(model);
            textPanel.update(model);
            attachmentPanel.update(model);
        } else {
            throw new IllegalArgumentException("Unknown " + o + " " + arg);
        }
    }
}
