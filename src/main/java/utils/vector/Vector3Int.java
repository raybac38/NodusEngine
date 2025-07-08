package utils.vector;

public class Vector3Int {
	public int x, y, z;

	public Vector3Int() {
		x = 0;
		y = 0;
		z = 0;
	}

	public Vector3Int(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3Int add(Vector3Int vector3Int) {
		return new Vector3Int(x + vector3Int.x, y + vector3Int.y, z + vector3Int.z);
	}

	public Vector3Int sub(Vector3Int vector3Int) {
		return new Vector3Int(x - vector3Int.x, y - vector3Int.y, z - vector3Int.z);
	}

	public Vector3Int mul(Vector3Int vector3Int) {
		return new Vector3Int(x * vector3Int.x, y * vector3Int.y, z * vector3Int.z);
	}

	public Vector3Int div(Vector3Int vector3Int) {
		return new Vector3Int(x / vector3Int.x, y / vector3Int.y, z / vector3Int.z);
	}

	public Vector3Int scale(int factor) {
		return new Vector3Int(x * factor, y * factor, z * factor);
	}
}
