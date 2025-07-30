package utils.vector;

public class Vector3 {
	public float x, y, z;

	public Vector3() {
		x = 0;
		y = 0;
		z = 0;
	}

	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3(float[] coordinate) {
		assert (coordinate != null);
		switch (coordinate.length) {
			case 3:
				this.z = coordinate[2];
			case 2:
				this.y = coordinate[1];
			case 1:
				this.x = coordinate[0];
				break;
			default:
				throw new RuntimeException("Vector3 constructor : coordinate out of bound");
		}
	}

	public Vector3 add(Vector3 vector3) {
		return new Vector3(x + vector3.x, y + vector3.y, z + vector3.z);
	}

	public Vector3 sub(Vector3 vector3) {
		return new Vector3(x - vector3.x, y - vector3.y, z - vector3.z);
	}

	public Vector3 mul(Vector3 vector3) {
		return new Vector3(x * vector3.x, y * vector3.y, z * vector3.z);
	}

	public Vector3 div(Vector3 vector3) {
		return new Vector3(x / vector3.x, y / vector3.y, z / vector3.z);
	}

	public Vector3 scale(int factor) {
		return new Vector3(x * factor, y * factor, z * factor);
	}
}
