package com.nilhcem.fakesmtp.gui;

import com.nilhcem.fakesmtp.core.I18n;
import com.nilhcem.fakesmtp.model.Attachment;
import com.nilhcem.fakesmtp.model.EmailModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
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
        Composite tableWrapper = new Composite(tabs, SWT.NONE);
        table = new Table(tableWrapper, SWT.NONE);
        tab.setControl(tableWrapper);

        TableColumn tc1 = new TableColumn(table, SWT.LEFT);
        TableColumn tc2 = new TableColumn(table, SWT.LEFT);
        TableColumn tc3 = new TableColumn(table, SWT.LEFT);
        tc1.setText(I18n.INSTANCE.get("attachment.list.filename"));
        tc2.setText(I18n.INSTANCE.get("attachment.list.mime"));
        tc3.setText(I18n.INSTANCE.get("attachment.list.size"));
        tableWrapper.addControlListener(new TableResizeControl(tableWrapper, table, new ColumnWidth(tc1, 1d), new ColumnWidth(tc2, 200), new ColumnWidth(tc3, 100)));
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
