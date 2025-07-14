package test2;

import engine.ecs.component.Mesh;
import engine.ecs.component.Script;
import engine.ecs.entity.Entity;

public class Script1 extends Script {
	private Mesh mesh;

	public Script1(Entity owner) {
		super(owner);
	}

	@Override
	public void update() {
		mesh = owner.getComponent(Mesh.class);
		mesh.vertices[2].y *= -1;
	}
}
