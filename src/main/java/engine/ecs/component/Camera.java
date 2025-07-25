package engine.ecs.component;

import engine.ecs.entity.Entity;

public class Camera extends BaseComponent {
	public float fov = (float) Math.toRadians(45);
	public float near = 0.01f;
	public float far = 100f;

	public Camera(Entity owner) {
		super(owner);
	}
}
