package com.nilhcem.fakesmtp.gui;

import com.nilhcem.fakesmtp.core.I18n;
import com.nilhcem.fakesmtp.model.UIModel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Provides the main window of the application.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class MainFrame {

    private final MenuBar menu;
    private final MainPanel mainPanel;
    
    /**
     * Creates the main window and make it visible.
     * <p>
     * First, assigns the main panel to the default uncaught exception handler
     * to display exceptions in this panel.<br /><br />
     * To create the main window, the application will have to set some
     * elements, such as:
     * <ul>
     * <li>The minimum and default size;</li>
     * <li>The menu bar and the main panel;</li>
     * <li>An icon image;</li>
     * <li>A shutdown hook to stop the server, once the main window is
     * closed.</li>
     * </ul><br />
     * The icon of the application is a modified version from the one provided
     * in "{@code WebAppers.com}"
     * <i>(Creative Commons Attribution 3.0 License)</i>.
     * </p>
     */
    public MainFrame() {
        final Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        shell.setText(I18n.INSTANCE.get("window.main.title"));
        menu = new MenuBar(shell);
        mainPanel = new MainPanel(shell);
        menu.getClearList().addObserver(mainPanel.getTable());
        
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
        UIModel.INSTANCE.stopServer();
    }
}
