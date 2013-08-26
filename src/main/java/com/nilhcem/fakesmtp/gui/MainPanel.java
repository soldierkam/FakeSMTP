package com.nilhcem.fakesmtp.gui;

import com.nilhcem.fakesmtp.server.SMTPServerHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;

/**
 *
 * @author soldier
 */
public class MainPanel {

    private final MailList table;
    private final MailPanel htmlPanel;

    public MainPanel(Shell shell) {
        SashForm form = new SashForm(shell, SWT.HORIZONTAL);
        form.setLayout(new FillLayout());
        table = new MailList(form);
        SMTPServerHandler.INSTANCE.getMailSaver().addObserver(table);
        htmlPanel = new MailPanel(form);
        table.addObserver(htmlPanel);
    }

    public MailList getTable() {
        return table;
    }

    public MailPanel getHtmlPanel() {
        return htmlPanel;
    }
}
