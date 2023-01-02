package gui;

import java.util.EventListener;
/**
 * Listener interface for receiving account events.
 * 
 * @author Simone
 */
public interface AccountListener extends EventListener {
	/**
     * Invoked when an account event occurs.
     * 
     * @param e the account event
     */
	public void accountEventOccurred(AccountEvent e);
}
