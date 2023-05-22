package Program.Main.Objects;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.time.LocalDate;

public class ClientsObject {

	private int id;
	private String fName;
	private String lName;
	private String gender;
	private String address;
	private String telephone;
	private String email;

	private Date dateOfBirth;

	private int carsOwned;

	public ClientsObject(int id, Date dateOfBirth, int carsOwned, String fName, String lName, String gender, String address, String telephone, String email) {
		this.id = id;
		this.dateOfBirth = dateOfBirth;
		this.carsOwned = carsOwned;
		this.fName = fName;
		this.lName = lName;
		this.gender = gender;
		this.address = address;
		this.telephone = telephone;
		this.email = email;

	}

	public SimpleStringProperty getDateOfBirth() {
		return new SimpleStringProperty(String.valueOf(dateOfBirth));
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public LocalDate getDateOfBirthLocalDate() {
		return this.dateOfBirth.toLocalDate();
	}
	public SimpleStringProperty getCarsOwned() {
		return new SimpleStringProperty(String.valueOf(carsOwned));
	}

	public void setCarsOwned(int carsOwned) {
		this.carsOwned = carsOwned;
	}

	public SimpleStringProperty getId() {
		return new SimpleStringProperty(String.valueOf(id));
	}

	public void setId(int id) {
		this.id = id;
	}

	// This returns the id as integer for usage in RightClickGui class // its
	// almost like we can't cast
	public int getNormalizedId() {
		return this.id;
	}

	public SimpleStringProperty getfName() {
		return new SimpleStringProperty(fName);
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public SimpleStringProperty getlName() {
		return new SimpleStringProperty(lName);
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public SimpleStringProperty getGender() {
		return new SimpleStringProperty(gender);
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public SimpleStringProperty getAddress() {
		return new SimpleStringProperty(address);
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public SimpleStringProperty getTelephone() {
		return new SimpleStringProperty("+" + String.valueOf(telephone).replaceFirst("(\\d{2})(\\d{3})(\\d+)", "$1 $2 $3"));
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public SimpleStringProperty getUnformattedTelephone() {
		return new SimpleStringProperty(String.valueOf(telephone));
	}
	public SimpleStringProperty getEmail() {
		return new SimpleStringProperty(email);
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
