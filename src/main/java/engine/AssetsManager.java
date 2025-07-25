package engine;

import engine.ecs.component.Mesh;
import objcc.api.FaceData;
import objcc.api.MeshData;
import objcc.parser.ObjectParser;
import utils.vector.Vector3;

import java.io.FileInputStream;
import java.util.List;

public class AssetsManager {

	/*
		Load a geometry file into the targeted Mesh
	 */
	public static void loadMesh(Mesh targetMesh, String filename) {
		try {
			MeshData meshData = ObjectParser.parse(new FileInputStream(filename));

			/// Transform List<float[]> into Vector3[]
			List<float[]> vertices = meshData.getVertices();
			int verticesLength = vertices.size();
			targetMesh.vertices = new Vector3[verticesLength];
			for (int i = 0; i < verticesLength; i++) {
				float[] point = vertices.get(i);
				targetMesh.vertices[i] = new Vector3(point[0], point[1], point[2]);
			}

			///  adding triangles
			List<FaceData> faces = meshData.getFaces();
			int facesLength = faces.size();
			targetMesh.triangles = new int[facesLength * 3];
			for (int i = 0; i < facesLength; i++) {
				int[] face = faces.get(i).getVerticesIndex();
				targetMesh.triangles[i * 3] = face[0] - 1;
				targetMesh.triangles[i * 3 + 1] = face[1] - 1;
				targetMesh.triangles[i * 3 + 2] = face[2] - 1;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
