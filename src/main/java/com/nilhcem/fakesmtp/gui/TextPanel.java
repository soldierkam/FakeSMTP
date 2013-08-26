/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nilhcem.fakesmtp.gui;

import com.nilhcem.fakesmtp.model.EmailModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

/**
 *
 * @author soldier
 */
public class TextPanel {

    private final Text plainText;

    public TextPanel(TabFolder tabs, TabItem tab) {
        plainText = new Text(tabs, SWT.WRAP | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        plainText.setEditable(false);
        tab.setControl(plainText);
    }

    public void update(EmailModel model) {
        if (model.getPlainTextEmail() != null) {
            plainText.setText(model.getPlainTextEmail().getText());
        } else {
            plainText.setText("");
        }
    }
}
