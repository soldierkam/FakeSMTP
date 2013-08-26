/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nilhcem.fakesmtp.gui;

import com.nilhcem.fakesmtp.core.I18n;
import com.nilhcem.fakesmtp.model.Attachment;
import com.nilhcem.fakesmtp.model.EmailModel;
import java.text.SimpleDateFormat;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 *
 * @author soldier
 */
public class AttachmentPanel {

    final private Table table;

    public AttachmentPanel(TabFolder tabs, TabItem tab) {
        table = new Table(tabs, SWT.NONE);
        tab.setControl(table);

        TableColumn tc1 = new TableColumn(table, SWT.LEFT);
        TableColumn tc2 = new TableColumn(table, SWT.LEFT);
        TableColumn tc3 = new TableColumn(table, SWT.LEFT);
        tc1.setText(I18n.INSTANCE.get("attachment.list.filename"));
        tc2.setText(I18n.INSTANCE.get("attachment.list.mime"));
        tc3.setText(I18n.INSTANCE.get("attachment.list.size"));
        tc1.setWidth(400);
        tc2.setWidth(100);
        tc3.setWidth(100);
        table.setHeaderVisible(true);
    }

    public void update(EmailModel email) {
        table.clearAll();
        for (Attachment attachment : email.getAttachments()) {
            TableItem tableItem = new TableItem(table, SWT.NONE);
            tableItem.setData(attachment);
            tableItem.setText(new String[]{attachment.getFilename(), attachment.getMime(), getUserFriendlySize(attachment.getData().length)});
        }
    }

    private String getUserFriendlySize(int sizeInBytes) {
        return sizeInBytes / 1024 + " KB";
    }
}
