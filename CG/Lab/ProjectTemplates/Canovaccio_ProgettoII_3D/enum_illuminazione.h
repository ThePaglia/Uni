typedef enum {
	GOURAUD,
	PHONG,
	BLINN,
	TOON,
	PASS_THROUGH
} ShadingType;
typedef enum {
	RED_PLASTIC,
	EMERALD,
	BRASS,
	SLATE,
	YELLOW,
	NO_MATERIAL
} MaterialType;

typedef enum {
	NONE,
	GOURAD_SHADING,
	PHONG_SHADING,
	NO_TEXTURE,
	WAVE
	
} ShaderOption;
typedef enum {
	WIRE_FRAME,
	FACE_FILL,
	CULLING_ON,
	CULLING_OFF,
}MenuOption;