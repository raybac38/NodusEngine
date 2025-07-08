package utils.vector;

public class Vector4 {
	public int x, y, z, w;

	public Vector4() {
		x = 0;
		y = 0;
		z = 0;
		w = 0;
	}

	public Vector4(int x, int y, int z, int w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vector4 add(Vector4 vector4) {
		return new Vector4(x + vector4.x, y + vector4.y, z + vector4.z, w + vector4.w);
	}

	public Vector4 sub(Vector4 vector4) {
		return new Vector4(x - vector4.x, y - vector4.y, z - vector4.z, w - vector4.w);
	}

	public Vector4 mul(Vector4 vector4) {
		return new Vector4(x * vector4.x, y * vector4.y, z * vector4.z, w * vector4.w);
	}

	public Vector4 div(Vector4 vector4) {
		return new Vector4(x / vector4.x, y / vector4.y, z / vector4.z, w / vector4.w);
	}

	public Vector4 scale(int factor) {
		return new Vector4(x * factor, y * factor, z * factor, w * factor);
	}
}
