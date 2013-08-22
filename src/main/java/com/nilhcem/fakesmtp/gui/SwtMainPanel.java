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
public class SwtMainPanel {

    private final SwtTable table;
    private final HtmlPanel htmlPanel;

    public SwtMainPanel(Shell shell) {
        SashForm form = new SashForm(shell, SWT.HORIZONTAL);
        form.setLayout(new FillLayout());
        table = new SwtTable(form);
        SMTPServerHandler.INSTANCE.getMailSaver().addObserver(table);
        htmlPanel = new HtmlPanel(form);
        table.addObserver(htmlPanel);
    }
}
