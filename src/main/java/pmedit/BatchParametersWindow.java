package pmedit;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

public abstract class BatchParametersWindow extends JDialog {

	public BatchOperationParameters parameters;
	Runnable onClose;

	/**
	 * Create the frame.
	 */
	public BatchParametersWindow(BatchOperationParameters parameters, final Frame owner) {
		super(owner, true);
		setLocationRelativeTo(owner);

		this.parameters = Objects.requireNonNullElseGet(parameters, BatchOperationParameters::new);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				BatchParametersWindow.this.windowClosed();
			}
		});
		getRootPane().registerKeyboardAction(e -> {
			setVisible(false);
			BatchParametersWindow.this.windowClosed();
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
	    JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		createContentPane();
	}
	protected abstract void createContentPane();

	public void windowClosed() {
		if(onClose != null){
			onClose.run();
		}		
	}

}