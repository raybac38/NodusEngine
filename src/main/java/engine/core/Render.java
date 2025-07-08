package engine.core;

import engine.component.Mesh;
import engine.core.window.Window;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Render {
	private final List<Mesh> meshs = new ArrayList<>();
	private final long window;
	private int defaultShaderProgram;


	public Render(Window window) {
		GL.createCapabilities();
		createDefaultShaderProgram();
		this.window = window.getWindow();
	}

	/*
		Create the defaultShaderProgram saved in this variable
	 */
	private void createDefaultShaderProgram() {
		String vertexShaderCode = """
				      #version 330 core      \s
				      layout (location = 0) in vec3 aPos;
				      void main()      \s
				      {         \s
					   gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);
				}""";

		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, vertexShaderCode);
		glCompileShader(vertexShader);

		String fragmentShaderCode = """
				#version 330 core      \s
				out vec4 FragColor;             \s
				void main() \s
				{          \s
				 FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);      \s
				}""";
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, fragmentShaderCode);
		glCompileShader(fragmentShader);

		/// Création d'un shader programme
		defaultShaderProgram = glCreateProgram();

		glAttachShader(defaultShaderProgram, vertexShader);
		glAttachShader(defaultShaderProgram, fragmentShader);
		glLinkProgram(defaultShaderProgram);

		/// Clean after creation
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
	}

	public void addMesh(Mesh mesh) {
		meshs.add(mesh);
	}

	public void removeMesh(Mesh mesh) {
		meshs.remove(mesh);
	}

	public void render() {
		glClearColor(0.2f, 0.3f, 1f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glUseProgram(defaultShaderProgram);
		for (Mesh mesh : meshs) {
			if (mesh.vertices == null) return;
			int vbo = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vbo);
			float[] vertices = new float[mesh.vertices.length];
			for (int i = 0; i < vertices.length; i++) {
				vertices[i * 3] = mesh.vertices[i].x;
				vertices[i * 3] = mesh.vertices[i].y;
				vertices[i * 3 + 1] = mesh.vertices[i].z;
			}
			glBufferData(GL_ARRAY_BUFFER, vertices, GL_STREAM_DRAW);

			int vao = glGenVertexArrays();

			glBindVertexArray(vao);
			glVertexAttribPointer(0, mesh.vertices.length, GL_FLOAT, false, 0, 0L);
			glEnableVertexAttribArray(0);

			///  Dessin du vao
			glBindVertexArray(vao);
			glDrawArrays(GL_TRIANGLES, 0, 3);
		}
	}
}
