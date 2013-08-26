package com.nilhcem.fakesmtp.gui;

import com.nilhcem.fakesmtp.core.I18n;
import java.util.Observable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 *
 * @author soldier
 */
public class ClearListMenuItem extends Observable {

    private final MenuItem clearList;

    public ClearListMenuItem(Menu editMenu) {
        clearList = new MenuItem(editMenu, SWT.PUSH);
        clearList.setText(I18n.INSTANCE.get("menubar.messages.clear.list"));
        clearList.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                setChanged();
                notifyObservers(clearList);
            }
        });
    }
}
