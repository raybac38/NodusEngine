package test1;

import engine.core.Core;
import engine.core.SceneManager;
import engine.core.window.GlfwWindow;
import engine.core.window.Window;

public class Test1 {
	public static void main(String[] args) {
		Window window = new GlfwWindow();
		SceneManager sceneManager = new SceneManager1(window);
		sceneManager.loadScene("f");

		Core core = new Core(sceneManager, window);

		while (!window.isKilled()) {
			sceneManager.update();
			window.update();
		}
		window.terminate();
	}


}
