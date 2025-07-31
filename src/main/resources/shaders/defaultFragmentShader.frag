#version 330 core
in vec2 UV;
in vec3 normal;

out vec4 FragColor;
void main()
{
    float ambiantLight = 0.1;
    vec3 light = vec3(0., -1., -1.);
    float brightness = max(dot(normal, -light), 0.0);
    FragColor = min(1.0, brightness + ambiantLight) *  vec4(UV.xy, 0.7, 1.0);
}