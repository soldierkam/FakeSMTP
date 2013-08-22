package com.nilhcem.fakesmtp;

import com.nilhcem.fakesmtp.core.Configuration;
import com.nilhcem.fakesmtp.core.exception.UncaughtExceptionHandler;
import com.nilhcem.fakesmtp.gui.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Entry point of the application.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class FakeSMTP {
	private static final Logger LOGGER = LoggerFactory.getLogger(FakeSMTP.class);

	private FakeSMTP() {
	}

	/**
	 * Sets some specific properties, and runs the main window.
	 * <p>
	 * Before opening the main window, this method will:
	 * <ul>
	 *   <li>set a default uncaught exception handler to intercept every uncaught exception;</li>
	 *   <li>set a property for Mac OS X to take the menu bar off the JFrame;</li>
	 *   <li>set a property for Mac OS X to set the name of the application menu item;</li>
	 *   <li>turn off the bold font in all components for swing default theme;</li>
	 *   <li>use the platform look and feel.</li>
	 * </ul>
	 * </p>
	 *
	 * @param args a list of parameters.
	 */
	public static void main(String[] args) {
        System.setProperty ( "sun.awt.xembedserver", "true" );
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				System.setProperty("apple.laf.useScreenMenuBar", "true");
				System.setProperty("com.apple.mrj.application.apple.menu.about.name", Configuration.INSTANCE.get("application.name"));
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					LOGGER.error("", e);
				}
				new MainFrame();
			}
		});
	}
}
