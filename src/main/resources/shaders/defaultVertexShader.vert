#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aNor;
layout (location = 2) in vec2 aUV;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

out vec2 UV;
out vec3 normal;

void main()
{
    UV = aUV;
    normal = (model * vec4(aNor, 1.0)).xyz;
    gl_Position = projection * view * model * vec4(aPos, 1.0);
}