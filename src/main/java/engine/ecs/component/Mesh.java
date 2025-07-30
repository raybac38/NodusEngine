package engine.ecs.component;

import engine.ecs.entity.Entity;
import utils.vector.Vector3;

public class Mesh extends BaseComponent {
	public Vector3[] vertices;
	public Vector3[] normals;
	public Vector3[] uvs;
	public int[] triangles;

	public Mesh(Entity owner) {
		super(owner);
	}
}
