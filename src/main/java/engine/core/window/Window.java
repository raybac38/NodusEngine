package engine.core.window;

public interface Window {
	long getWindow();

	void setWindowKeyListener(WindowKeyListener windowKeyListener);

	void setWindowMouseListener(WindowMouseListener windowMouseListener);

	/*
		Kill the current windows
	 */
	void kill();

	/*
		Does the current windows have been killed
	 */
	boolean isKilled();

	/*
		Terminate the current window context
	 */
	void terminate();


	/*
	Update what the windows show
	 */
	void update();

	interface WindowKeyListener {
		void keyPressed(Window window, int key);

		void keyRelease(Window window, int key);
	}

	interface WindowMouseListener {
		void mouseMoved(Window window, double offsetX, double offsetY);
	}
	
}
