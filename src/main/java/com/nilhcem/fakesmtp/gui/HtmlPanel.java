/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nilhcem.fakesmtp.gui;

import com.nilhcem.fakesmtp.model.EmailModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

/**
 *
 * @author soldier
 */
public class HtmlPanel {

    private final Browser browser;

    public HtmlPanel(TabFolder tabs, TabItem tab) {
        browser = new Browser(tabs, SWT.NONE);
        browser.setJavascriptEnabled(false);
        tab.setControl(browser);
    }

    public void update(EmailModel model) {
        if (model.getHtmlMail() != null) {
            browser.setText(model.getHtmlMail().getHtml());
        } else {
            browser.setText("");
        }
    }
}
