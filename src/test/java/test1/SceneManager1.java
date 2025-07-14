package test1;

import engine.core.SceneManager;
import engine.core.window.Window;
import engine.ecs.system.RenderSystem;

public class SceneManager1 extends SceneManager {
	public SceneManager1(Window window) {
		super(window);
	}

	@Override
	public void loadScene(String sceneName) {
		RenderSystem renderSystem = new RenderSystem();
		this.currentScene = new Scene1(this, renderSystem);
	}
}
