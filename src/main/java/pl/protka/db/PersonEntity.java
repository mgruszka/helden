package pl.protka.db;

public class PersonEntity {
	
	private long ID = -1;
	private String name = null;
	private String birthDate = null;
	private String deathDate = null;
	private String birthPlace = null;
	private String deathPlace = null;
	private String fields = null;
	private String britURL = null;
	
	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getDeathDate() {
		return deathDate;
	}
	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	public String getDeathPlace() {
		return deathPlace;
	}
	public void setDeathPlace(String deathPlace) {
		this.deathPlace = deathPlace;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public String getBritURL() {
		return britURL;
	}
	public void setBritURL(String britURL) {
		this.britURL = britURL;
	}

	
	
}
