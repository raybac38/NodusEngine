package engine.core;

import engine.core.window.Window;
import engine.ecs.system.RenderSystem;

public class SceneManager {
	private final Window window;
	protected Scene currentScene;

	public SceneManager(Window window) {
		this.window = window;
	}

	public Window getWindow() {
		return window;
	}

	public void loadScene(String sceneName) {
		RenderSystem renderSystem = new RenderSystem();
		this.currentScene = new Scene(this, renderSystem);
	}

	public void update() {
		currentScene.update();
		window.update();
	}
}
