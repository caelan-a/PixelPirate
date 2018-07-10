#ifdef GL_ES
    precision mediump float;
#endif

uniform sampler2D u_texture;
uniform sampler2D u_texture2;

varying vec4 v_color;
varying vec2 v_texCoords;

uniform float u_time;
uniform float u_speed;

const float frequency = 12.0;
const float amplitude = 0.02;

void main(){
	vec2 displacement = texture2D(u_texture2, v_texCoords/6.0).xy;
	float ty = v_texCoords.y + displacement.y * 0.1 - 0.15 + (sin(v_texCoords.x * frequency - u_time) * amplitude); 
	gl_FragColor = texture2D(u_texture, vec2(v_texCoords.x,ty));
}