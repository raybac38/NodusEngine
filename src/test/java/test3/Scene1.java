package test3;

import engine.core.Scene;
import engine.core.SceneManager;
import engine.ecs.component.Camera;
import engine.ecs.entity.Entity;
import engine.ecs.system.RenderSystem;

public class Scene1 extends Scene {
	public Scene1(SceneManager sceneManager, RenderSystem renderSystem) {
		super(sceneManager, renderSystem);

		Entity entity = new Entity(this);
		entity.addComponent(new Mesh1(entity));
		entity.addComponent(new Script1(entity));

		Entity camera = new Entity(this);
		camera.addComponent(new Camera(camera));
		camera.addComponent(new CameraScript(camera));
	}
}
