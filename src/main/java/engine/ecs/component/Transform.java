package engine.ecs.component;

import engine.ecs.entity.Entity;
import utils.vector.Vector3;

public class Transform extends BaseComponent {
	private Vector3 rotation = new Vector3();
	private Vector3 position = new Vector3();

	public Transform(Entity owner) {
		super(owner);
	}

	public Vector3 getPosition() {
		return this.position;
	}

	public void setPosition(Vector3 newPosition) {
		this.position = newPosition;
	}

	public Vector3 getRotation() {
		return this.rotation;
	}

	public void setRotation(Vector3 newRotation) {
		this.rotation = newRotation;
	}

	public void translate(Vector3 translation) {
		this.position = position.add(translation);
	}
	
}
