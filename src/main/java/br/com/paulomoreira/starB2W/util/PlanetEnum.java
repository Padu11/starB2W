package br.com.paulomoreira.starB2W.util;

public enum PlanetEnum {

	ID("id"), PLANET("name"), TERRAIN("terrain"), CLIMATE("climate");

	private String planetEnum;

	PlanetEnum(String planetEnum) {
		if (planetEnum == null) {
			this.planetEnum = "id";
		}
		this.planetEnum = planetEnum;
	}

	public String getPlanetEnum() {

		return planetEnum;
	}

}
