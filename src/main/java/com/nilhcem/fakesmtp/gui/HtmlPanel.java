package com.nilhcem.fakesmtp.gui;

import com.nilhcem.fakesmtp.model.EmailModel;
import java.util.Observable;
import java.util.Observer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;

/**
 *
 * @author soldier
 */
public class HtmlPanel implements Observer {

    private final Browser browser;

    public HtmlPanel(Composite parent) {
        browser = new Browser(parent, SWT.NONE);
        browser.setJavascriptEnabled(false);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof SwtTable) {
            final SwtTable table = (SwtTable) o;
            EmailModel model = table.getSelected();
            if (model.getHtmlMail() != null) {
                browser.setText(model.getHtmlMail().getHtml());
            } else {
                browser.setText(model.getPlainTextEmail().getText());
            }
        } else {
            throw new IllegalArgumentException("Unknown " + o + " " + arg);
        }
    }
}
