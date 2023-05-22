package Program.Main.Objects;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.time.LocalDate;

public class EmployeesObject {
	private int id;
	private String fName;
	private String lName;
	private String gender;
	private String address;
	private String telephone;
	private String position;
	private float salary;
	private String email;

	private Date dateOfBirth;

	public EmployeesObject(int id, Date dateOfBirth, String fName, String lName, String gender, String position, float salary, String address, String telephone,
			String email) {
		this.id = id;
		this.dateOfBirth = dateOfBirth;
		this.fName = fName;
		this.lName = lName;
		this.gender = gender;
		this.position = position;
		this.salary = salary;
		this.address = address;
		this.telephone = telephone;
		this.email = email;
	}

	public SimpleStringProperty getId() {
		return new SimpleStringProperty(String.valueOf(id));
	}

	public void setId(int id) {
		this.id = id;
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

	public SimpleStringProperty getDateOfBirth() {
		return new SimpleStringProperty(String.valueOf(dateOfBirth));
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getDateOfBirthLocalDate() {
		return this.dateOfBirth.toLocalDate();
	}

	public SimpleStringProperty getEmail() {
		return new SimpleStringProperty(email);
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SimpleStringProperty getTelephone() {
		return new SimpleStringProperty("+" + String.valueOf(telephone).replaceFirst("(\\d{2})(\\d{3})(\\d+)", "$1 $2 $3")); // google,
		// please
		// dont
		// ask
		// how
		// it
		// works
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public SimpleStringProperty getUnformattedTelephone() {
		return new SimpleStringProperty(String.valueOf(telephone));
	}
	public SimpleStringProperty getID() {
		return new SimpleStringProperty(String.valueOf(id));
	}

	public SimpleStringProperty getFname() {
		return new SimpleStringProperty(fName);
	}

	public SimpleStringProperty getLname() {
		return new SimpleStringProperty(lName);
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

	public SimpleStringProperty getPosition() {

		return new SimpleStringProperty(position);
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public SimpleStringProperty getSalary() {
		return new SimpleStringProperty("$ " + String.format("%,.2f", salary));
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public SimpleStringProperty getUnformattedSalary() {
		return new SimpleStringProperty(String.valueOf(salary));
	}
	public int getNormalizedId() {
		return this.id;
	}
}
