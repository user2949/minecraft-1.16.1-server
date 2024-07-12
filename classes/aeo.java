import com.mojang.datafixers.DSL.TypeReference;

public enum aeo {
	LEVEL(ajb.a),
	PLAYER(ajb.b),
	CHUNK(ajb.c),
	HOTBAR(ajb.d),
	OPTIONS(ajb.e),
	STRUCTURE(ajb.f),
	STATS(ajb.g),
	SAVED_DATA(ajb.h),
	ADVANCEMENTS(ajb.i),
	POI_CHUNK(ajb.j),
	WORLD_GEN_SETTINGS(ajb.y);

	private final TypeReference l;

	private aeo(TypeReference typeReference) {
		this.l = typeReference;
	}

	public TypeReference a() {
		return this.l;
	}
}
