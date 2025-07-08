package engine.component;

import engine.entity.Entity;
import utils.vector.Vector3;

public class Transform extends BaseComponent {
	private Vector3 position = new Vector3();

	public Transform(Entity owner) {
		super(owner);
	}

	public void translate(Vector3 translation) {
		this.position = position.add(translation);
	}

	public void setPosition(Vector3 newPosition) {
		this.position = newPosition;
	}
}
