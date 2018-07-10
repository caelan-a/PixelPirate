#ifdef GL_ES
    precision mediump float;
#endif

uniform sampler2D u_texture;

varying vec4 v_color;
varying vec2 v_texCoords;

uniform float u_time;

void main(){
	gl_FragColor = vec4(1.0, 1.0, 1.0, u_time) * texture2D(u_texture, v_texCoords);
}