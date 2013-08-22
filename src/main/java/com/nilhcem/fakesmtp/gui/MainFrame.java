package com.nilhcem.fakesmtp.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.core.exception.UncaughtExceptionHandler;
import com.nilhcem.fakesmtp.model.UIModel;
import com.nilhcem.fakesmtp.server.SMTPServerHandler;
import java.awt.Frame;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
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
    private final SwtMainPanel mainPanel;
    
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
        menu = new MenuBar(shell);
        mainPanel = new SwtMainPanel(shell);
//        mainFrame = SWT_AWT.new_Frame(composite);
//        mainFrame.setTitle(Configuration.INSTANCE.get("application.title"));
//        mainFrame.setMinimumSize(new Dimension(300, 300));
//        mainFrame.setVisible(true);
//
//        ((UncaughtExceptionHandler) Thread.getDefaultUncaughtExceptionHandler()).setParentComponent(panel.get());
//        Dimension frameSize = new Dimension(Integer.parseInt(Configuration.INSTANCE.get("application.min.width")),
//                Integer.parseInt(Configuration.INSTANCE.get("application.min.height")));
//
//        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainFrame.setSize(frameSize);
//        mainFrame.setMinimumSize(frameSize);
//
//        mainFrame.setMenuBar(menu.get());
//        mainFrame.getContentPane().add(panel.get());
//        mainFrame.setLocationRelativeTo(null); // Center main frame
//        mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().
//                getResource(Configuration.INSTANCE.get("application.icon.path"))));
//
//        // Add shutdown hook to stop server if enabled
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            public void run() {
//                SMTPServerHandler.INSTANCE.stopServer();
//            }
//        ;
//        });
//
//		mainFrame.setVisible(true);
        
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
        UIModel.INSTANCE.dispose();
    }
}
