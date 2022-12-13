package gui;

import java.util.EventListener;

public interface AccountListener extends EventListener {

	public void accountEventOccurred(AccountEvent e);
	
}
