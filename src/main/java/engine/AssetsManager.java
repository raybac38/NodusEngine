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

			List<float[]> objectVertices = meshData.getVertices();
			List<float[]> objectNormals = meshData.getNormal();
			List<float[]> objectUvs = meshData.getUV();

			List<FaceData> faceDataList = meshData.getFaces();
			int verticesNumber = faceDataList.size() * 3;

			int meshVerticesInsertionIndex = 0;
			Vector3[] meshVertices = new Vector3[verticesNumber];
			int meshNormalsInsertionIndex = 0;
			Vector3[] meshNormals = new Vector3[verticesNumber];
			int meshUvsInsertionIndex = 0;
			Vector3[] meshUvs = new Vector3[verticesNumber];

			for (FaceData faceData : faceDataList) {
				/// Vertices
				int[] objectVerticesIndex = faceData.getVerticesIndex();
				float[] coordinateA = objectVertices.get(objectVerticesIndex[0] - 1);
				meshVertices[meshVerticesInsertionIndex++] = new Vector3(coordinateA);
				float[] coordinateB = objectVertices.get(objectVerticesIndex[1] - 1);
				meshVertices[meshVerticesInsertionIndex++] = new Vector3(coordinateB);
				float[] coordinateC = objectVertices.get(objectVerticesIndex[2] - 1);
				meshVertices[meshVerticesInsertionIndex++] = new Vector3(coordinateC);

				/// Normals
				int[] objectNormalsIndex = faceData.getNormalIndex();
				float[] normalsA = objectNormals.get(objectNormalsIndex[0] - 1);
				meshNormals[meshNormalsInsertionIndex++] = new Vector3(normalsA);
				float[] normalsB = objectNormals.get(objectNormalsIndex[1] - 1);
				meshNormals[meshNormalsInsertionIndex++] = new Vector3(normalsB);
				float[] normalsC = objectNormals.get(objectNormalsIndex[2] - 1);
				meshNormals[meshNormalsInsertionIndex++] = new Vector3(normalsC);

				/// Uvs
				int[] objectUvsIndex = faceData.getUVIndex();
				float[] uvA = objectUvs.get(objectUvsIndex[0] - 1);
				meshUvs[meshUvsInsertionIndex++] = new Vector3(uvA);
				float[] uvB = objectUvs.get(objectUvsIndex[1] - 1);
				meshUvs[meshUvsInsertionIndex++] = new Vector3(uvB);
				float[] uvC = objectUvs.get(objectUvsIndex[2] - 1);
				meshUvs[meshUvsInsertionIndex++] = new Vector3(uvC);
			}

			targetMesh.vertices = meshVertices;
			targetMesh.normals = meshNormals;
			targetMesh.uvs = meshUvs;

			int[] triangles = new int[verticesNumber];
			for (int i = 0; i < verticesNumber; i++) {
				triangles[i] = i;
			}
			targetMesh.triangles = triangles;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
