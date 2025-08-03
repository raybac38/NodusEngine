package engine.core;

import engine.core.window.Window;

public class SceneManager {
	private final Window window;
	protected Scene currentScene;

	public SceneManager(Window window) {
		this.window = window;
	}

	public Window getWindow() {
		return window;
	}

	public void loadScene(Scene scene) {
		scene.sceneManager = this;
		scene.isActivated = true;
		this.currentScene = scene;
	}

	public void update() {
		currentScene.update();
	}
}
