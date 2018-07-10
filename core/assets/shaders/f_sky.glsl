#ifdef GL_ES
    precision mediump float;
#endif

uniform sampler2D u_texture;

varying vec4 v_color;
varying vec2 v_texCoords;

uniform float u_time;

const float frequency = 200.0;
const float amplitude = 0.005;

void main(){
	float tx = v_texCoords.x + (sin(u_time * 5) * amplitude); 
	gl_FragColor = texture2D(u_texture, vec2(tx, v_texCoords.y));
}