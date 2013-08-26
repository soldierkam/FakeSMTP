package com.nilhcem.fakesmtp.gui;

import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.core.I18n;
import com.nilhcem.fakesmtp.core.exception.BindPortException;
import com.nilhcem.fakesmtp.core.exception.InvalidPortException;
import com.nilhcem.fakesmtp.core.exception.OutOfRangePortException;
import com.nilhcem.fakesmtp.model.UIModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

/**
 *
 * @author soldier
 */
public class ServerStartDialog extends Dialog {

    public ServerStartDialog(Shell parentShell) {
        super(parentShell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        setText(I18n.INSTANCE.get("dialog.server.start.title"));
        createContents();
    }

    private void createContents() {
        Shell parent = getParent();
        final Shell shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

        shell.setLayout(new GridLayout(2, true));

        // Show the message
        Label label = new Label(shell, SWT.NONE);
        label.setText(I18n.INSTANCE.get("dialog.server.port"));
        GridData data = new GridData();
        data.horizontalSpan = 1;
        label.setLayoutData(data);

        // Display the input box
        final Spinner portSpinner = new Spinner(shell, SWT.BORDER);
        portSpinner.setMinimum(0);
        portSpinner.setMaximum(65535);
        portSpinner.setTextLimit(6);
        portSpinner.setSelection(Configuration.INSTANCE.getInt("smtp.default.port"));
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.horizontalSpan = 1;
        portSpinner.setLayoutData(data);

        // Create the OK button and add a handler
        // so that pressing it will set input
        // to the entered value
        Button ok = new Button(shell, SWT.PUSH);
        ok.setText("OK");
        data = new GridData(GridData.FILL_HORIZONTAL);
        ok.setLayoutData(data);
        ok.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                try {UIModel.INSTANCE.setPort(portSpinner.getSelection());
                    UIModel.INSTANCE.toggleButton();
                    //toggleStartServerButton();
                } catch (InvalidPortException ipe) {
                    displayError(String.format(I18n.INSTANCE.get("startsrv.err.invalid")));
                } catch (BindPortException bpe) {
                    displayError(String.format(I18n.INSTANCE.get("startsrv.err.bound"), bpe.getPort()));
                } catch (OutOfRangePortException orpe) {
                    displayError(String.format(I18n.INSTANCE.get("startsrv.err.range"), orpe.getPort()));
                } catch (RuntimeException re) {
                    displayError(String.format(I18n.INSTANCE.get("startsrv.err.default"), re.getMessage()));
                }
                shell.close();
            }
        });

        // Create the cancel button and add a handler
        // so that pressing it will set input to null
        Button cancel = new Button(shell, SWT.PUSH);
        cancel.setText("Cancel");
        data = new GridData(GridData.FILL_HORIZONTAL);
        cancel.setLayoutData(data);
        cancel.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                shell.close();
            }
        });

        // Set the OK button as the default, so
        // user can type input and press Enter
        // to dismiss
        shell.setDefaultButton(ok);
        shell.pack();        
        shell.setVisible(true);                
    }

    private void displayError(String msg) {
        //TODO
    }
}
