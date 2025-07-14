package test2;

import engine.ecs.component.Mesh;
import engine.ecs.entity.Entity;
import utils.vector.Vector3;

public class Mesh1 extends Mesh {
	public Mesh1(Entity owner) {
		super(owner);

		this.vertices = new Vector3[]{
				new Vector3(0f, 0f, 1f),
				new Vector3(1f, 0f, 1f),
				new Vector3(0f, 1f, 1f)
		};

		this.triangles = new int[]{
				0, 1, 2
		};
	}
}
