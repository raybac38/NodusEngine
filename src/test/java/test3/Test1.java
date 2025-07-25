package test3;

import engine.core.Core;
import engine.core.SceneManager;
import engine.core.window.GlfwWindow;
import engine.core.window.Window;

public class Test1 {
	public static void main(String[] args) throws InterruptedException {
		Window window = new GlfwWindow();
		SceneManager sceneManager = new SceneManager1(window);
		sceneManager.loadScene("f");

		Core core = new Core(sceneManager, window);

		while (!window.isKilled()) {
			sceneManager.update();
			window.update();
			Thread.sleep(10);
		}
		window.terminate();
	}
}
