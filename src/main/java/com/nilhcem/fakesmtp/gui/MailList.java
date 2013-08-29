package com.nilhcem.fakesmtp.gui;

import com.nilhcem.fakesmtp.core.I18n;
import com.nilhcem.fakesmtp.model.EmailModel;
import com.nilhcem.fakesmtp.server.MailSaver;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 *
 * @author soldier
 */
public class MailList extends Observable implements Observer {

    final private Table table;
    private EmailModel selected;

    public MailList(SashForm sashForm) {
        Composite tableWrapper = new Composite(sashForm, SWT.NONE);
        table = new Table(tableWrapper, SWT.BORDER);

        TableColumn tc1 = new TableColumn(table, SWT.LEFT);
        TableColumn tc2 = new TableColumn(table, SWT.LEFT);
        TableColumn tc3 = new TableColumn(table, SWT.LEFT);
        tc1.setText(I18n.INSTANCE.get("email.list.date.column"));
        tc2.setText(I18n.INSTANCE.get("email.list.from.column"));
        tc3.setText(I18n.INSTANCE.get("email.list.subject.column"));
        table.setHeaderVisible(true);
        tableWrapper.addControlListener(new TableResizeControl(tableWrapper, table, new ColumnWidth(tc1, 200), new ColumnWidth(tc2, 200), new ColumnWidth(tc3, 1d)));
        table.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                TableItem item = table.getSelection()[0];
                selected = (EmailModel) item.getData();
                setChanged();
                notifyObservers(selected);
            }
        });
    }

    public void clear() {
        table.removeAll();
    }

    public EmailModel getSelected() {
        return selected;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MailSaver) {
            final EmailModel emailModel = (EmailModel) arg;
            Display.getDefault().asyncExec(new Runnable() {
                @Override
                public void run() {
                    TableItem tableItem = new TableItem(table, SWT.NONE);
                    tableItem.setData(emailModel);
                    SimpleDateFormat sdf = new SimpleDateFormat();
                    tableItem.setText(new String[]{sdf.format(emailModel.getReceivedDate()), emailModel.getFrom(), emailModel.getSubject()});
                }
            });
        } else if (o instanceof ClearListMenuItem) {
            clear();
        } else {
            throw new IllegalArgumentException("Unknown " + o + " " + arg);
        }
    }
}
