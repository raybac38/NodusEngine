package test3;

import engine.ecs.component.Script;
import engine.ecs.entity.Entity;
import utils.vector.Vector3;

public class CameraScript extends Script {
	private final float offset = 5;
	private int tickNumber = 0;

	public CameraScript(Entity owner) {
		super(owner);
	}

	@Override
	public void update() {
		tickNumber += 1;
		float cos = (float) Math.cos(Math.toRadians(tickNumber));
		float sin = (float) Math.sin(Math.toRadians(tickNumber));
		owner.transform.setPosition(new Vector3(cos * offset, 0f, sin * offset));
		owner.transform.setRotation(new Vector3(-cos * 360, 0, -sin * 360));

	}
}
