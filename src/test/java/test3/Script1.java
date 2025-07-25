package test3;

import engine.AssetsManager;
import engine.ecs.component.Mesh;
import engine.ecs.component.Script;
import engine.ecs.entity.Entity;

public class Script1 extends Script {
	private Mesh mesh;
	private boolean triggered = false;

	public Script1(Entity owner) {
		super(owner);
	}
	
	@Override
	public void update() {
		if (!triggered) {
			triggered = true;
			mesh = owner.getComponent(Mesh.class);
			AssetsManager.loadMesh(mesh, "src/test/resources/obj/cube.obj");
			System.out.println("loading effectuer");
		}
	}
}
