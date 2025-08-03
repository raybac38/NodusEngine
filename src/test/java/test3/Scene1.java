package test3;

import engine.core.Scene;
import engine.ecs.component.Camera;
import engine.ecs.entity.Entity;

public class Scene1 extends Scene {
	
	@Override
	protected void init() {
		Entity entity = new Entity(this);
		entity.addComponent(new Mesh1(entity));
		entity.addComponent(new Script1(entity));

		Entity camera = new Entity(this);
		camera.addComponent(new Camera(camera));
		camera.addComponent(new CameraScript(camera));
	}
}
