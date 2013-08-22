package com.nilhcem.fakesmtp.server;

import com.nilhcem.fakesmtp.model.EmailModel;
import com.nilhcem.fakesmtp.model.UIModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;
import java.util.Observable;

/**
 * Saves emails and notifies components so they can refresh their views with new
 * data.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class MailSaver extends Observable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailSaver.class);

    /**
     * Saves incoming email in file system and notifies observers.
     *
     * @param from the user who send the email.
     * @param to the recipient of the email.
     * @param data an inputstream object containing the email.
     * @see com.nilhcem.fakesmtp.gui.MainPanel#addObservers to see which
     * observers will be notified
     */
    public void saveEmailAndNotify(String from, String to, InputStream data) {
        synchronized (getLock()) {
            try {
                EmailModel model = new EmailModel(from, to, data);
                setChanged();
                notifyObservers(model);
            } catch (Exception exc) {
                throw new RuntimeException(exc);
            }
        }
    }

    /**
     * Deletes all received emails from file system.
     */
    public void deleteEmails() {
        Map<Integer, String> mails = UIModel.INSTANCE.getListMailsMap();

        for (String value : mails.values()) {
            File file = new File(value);
            if (file.exists()) {
                try {
                    if (!file.delete()) {
                        LOGGER.error("Impossible to delete file {}", value);
                    }
                } catch (SecurityException e) {
                    LOGGER.error("", e);
                }
            }
        }
    }

    /**
     * Returns a lock object.
     * <p>
     * This lock will be used to make the application thread-safe, and avoid
     * receiving and deleting emails in the same time.
     * </p>
     *
     * @return a lock object <i>(which is actually the current instance of the
     * {@code MailSaver} object)</i>.
     */
    public Object getLock() {
        return this;
    }
}
