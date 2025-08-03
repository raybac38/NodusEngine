package test3;

import engine.ecs.component.Script;
import engine.ecs.entity.Entity;
import utils.vector.Vector3;

public class CameraScript extends Script {
	private final float offset = 5;
	private final int tickNumber = 0;

	public CameraScript(Entity owner) {
		super(owner);
	}

	@Override
	public void onUpdate() {
		owner.transform.setPosition(new Vector3(0, 0, offset));
	}

	@Override
	public void onStart() {

	}

	@Override
	public void onStop() {

	}
}
