package test3;

import engine.AssetsManager;
import engine.ecs.component.Mesh;
import engine.ecs.component.Script;
import engine.ecs.entity.Entity;
import utils.vector.Vector3;

public class Script1 extends Script {
	private Mesh mesh;
	private boolean triggered = false;
	private int tick;

	public Script1(Entity owner) {
		super(owner);
	}

	@Override
	public void update() {
		if (!triggered) {
			triggered = true;
			mesh = owner.getComponent(Mesh.class);
			AssetsManager.loadMesh(mesh, "src/test/resources/obj/ico.obj");
			System.out.println("loading effectuer");
		}

		tick++;

		owner.transform.setRotation(new Vector3(tick, tick, 0));
	}
}
