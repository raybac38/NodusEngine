package test3;

import engine.ecs.component.Script;
import engine.ecs.entity.Entity;
import utils.vector.Vector3;

public class CameraScript extends Script {
	private final float zFixedPosition = 4;
	private int tickNumber = 0;

	public CameraScript(Entity owner) {
		super(owner);
	}

	@Override
	public void update() {
		tickNumber += 1;
		owner.transform.setPosition(new Vector3(0f, 0f, (float) (zFixedPosition + Math.sin(Math.toRadians(tickNumber)))));
	}
}
