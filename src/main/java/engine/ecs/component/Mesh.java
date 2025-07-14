package engine.ecs.component;

import engine.ecs.entity.Entity;
import utils.vector.Vector3;

public class Mesh extends BaseComponent {
	public Vector3[] vertices;
	public int[] triangles;

	public Mesh(Entity owner) {
		super(owner);
	}
}
