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
public class HtmlPanel implements Observer {

    private final TabItem htmlItem;
    private final TabItem plainTextItem;
    private final TabItem attachmentsItem;
    private final Browser browser;
    private final Text plainText;
    
    public HtmlPanel(Composite parent) {
        TabFolder tabs = new TabFolder(parent, SWT.NONE);
        htmlItem = new TabItem(tabs, SWT.NONE);
        htmlItem.setText(I18n.INSTANCE.get("mail.panel.html.tab"));
        plainTextItem = new TabItem(tabs, SWT.NONE);
        plainTextItem.setText(I18n.INSTANCE.get("mail.panel.plaintext.tab"));
        attachmentsItem = new TabItem(tabs, SWT.NONE);
        attachmentsItem.setText(I18n.INSTANCE.get("mail.panel.attachments.tab"));
        browser = new Browser(tabs, SWT.NONE);
        browser.setJavascriptEnabled(false);
        htmlItem.setControl(browser);
        plainText = new Text(tabs, SWT.NONE);        
        plainText.setEditable(false);
        plainTextItem.setControl(plainText);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof SwtTable) {
            final SwtTable table = (SwtTable) o;
            EmailModel model = table.getSelected();
            if (model.getHtmlMail() != null) {
                browser.setText(model.getHtmlMail().getHtml());
            } else {
                browser.setText("");
            }
            if(model.getPlainTextEmail() != null){
                plainText.setText(model.getPlainTextEmail().getText());
            }else{
                plainText.setText("");
            }
        } else {
            throw new IllegalArgumentException("Unknown " + o + " " + arg);
        }
    }
}
