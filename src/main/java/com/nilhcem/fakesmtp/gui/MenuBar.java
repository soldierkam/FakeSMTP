package com.nilhcem.fakesmtp.gui;

import com.nilhcem.fakesmtp.core.Configuration;
import java.util.Observable;
import com.nilhcem.fakesmtp.core.I18n;
import com.nilhcem.fakesmtp.model.UIModel;
import java.util.Observer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * Provides the menu bar of the application.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class MenuBar extends Observable implements Observer {

    private final static I18n i18n = I18n.INSTANCE;
    private final Menu menuBar;
    private MenuItem exit;
    private MenuItem startServer;
    private MenuItem stopServer;
    private ClearListMenuItem clearList;

    /**
     * Creates the menu bar and the different menus (file / edit / help).
     */
    public MenuBar(Shell shell) {
        menuBar = new Menu(shell, SWT.BAR);
        createFileMenu(shell, menuBar);
        createEditMenu(shell, menuBar);
        createHelpMenu(shell, menuBar);
        shell.setMenuBar(menuBar);
    }

    /**
     * Returns the JMenuBar object.
     *
     * @return the JMenuBar object.
     */
    public Menu get() {
        return menuBar;
    }

    /**
     * Creates the file menu.
     * <p>
     * The file menu contains an "Exit" item, to quit the application.
     * </p>
     *
     * @return the newly created file menu.
     */
    private void createFileMenu(Shell shell, Menu menuBar) {
        MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        fileMenuHeader.setMenu(fileMenu);
        fileMenuHeader.setText(i18n.get("menubar.file"));
        //fileMenu.setMnemonic(i18n.get("menubar.mnemo.file").charAt(0));

        exit = new MenuItem(fileMenu, SWT.PUSH);
        exit.setText(i18n.get("menubar.exit"));
        //exit.setMnemonic(i18n.get("menubar.mnemo.exit").charAt(0));
        exit.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Creates the edit menu.
     * <p>
     * The edit menu contains a "Messages location" item, to define the location
     * of the incoming mails.
     * </p>
     *
     * @return the newly created edit menu.
     */
    private void createEditMenu(final Shell shell, Menu menuBar) {
        MenuItem editMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        editMenuHeader.setText(i18n.get("menubar.edit"));
        //editMenu.setMnemonic(i18n.get("menubar.mnemo.edit").charAt(0));
        Menu editMenu = new Menu(shell, SWT.DROP_DOWN);
        editMenuHeader.setMenu(editMenu);

        final ServerStartDialog dialog = new ServerStartDialog(shell);
        dialog.addObserver(this);

        startServer = new MenuItem(editMenu, SWT.PUSH);
        startServer.setText(i18n.get("menubar.messages.start.server"));
        //mailsLocation.setMnemonic(i18n.get("menubar.mnemo.msglocation").charAt(0));
        startServer.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                dialog.show();        
            }
        });

        stopServer = new MenuItem(editMenu, SWT.PUSH);
        stopServer.setEnabled(false);
        stopServer.setText(i18n.get("menubar.messages.stop.server"));
        //mailsLocation.setMnemonic(i18n.get("menubar.mnemo.msglocation").charAt(0));
        stopServer.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                UIModel.INSTANCE.stopServer();
                setMenuStartStop(false);
            }
        });
        clearList = new ClearListMenuItem(editMenu);
    }

    private void setMenuStartStop(boolean isRunning) {
        stopServer.setEnabled(isRunning);
        startServer.setEnabled(!isRunning);
    }

    private void displayError(String msg) {
        //TODO
    }

    public ClearListMenuItem getClearList() {
        return clearList;
    }

    /**
     * Creates the help menu.
     * <p>
     * The help menu contains an "About" item, to display some software
     * information.
     * </p>
     *
     * @return the newly created help menu.
     */
    private void createHelpMenu(final Shell shell, Menu menuBar) {
        MenuItem helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
        helpMenuHeader.setText(i18n.get("menubar.help"));
        helpMenuHeader.setMenu(helpMenu);
        //helpMenu.setMnemonic(i18n.get("menubar.mnemo.help").charAt(0));

        MenuItem about = new MenuItem(helpMenu, SWT.PUSH);
        about.setText(i18n.get("menubar.about"));
        //about.setMnemonic(i18n.get("menubar.mnemo.about").charAt(0));
        about.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK);
                dialog.setText(String.format(i18n.get("menubar.about.title"), Configuration.INSTANCE.get("application.name")));
                dialog.setMessage(String.format(i18n.get("menubar.about.dialog")));
                dialog.open();
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ServerStartDialog) {
            setMenuStartStop(true);
        } else {
            throw new IllegalArgumentException("Unknown " + o + " " + arg);
        }

    }
}
