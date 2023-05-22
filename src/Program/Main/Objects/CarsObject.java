package Program.Main.Objects;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class CarsObject {
	private int ID;
	private String brand;
	private String model;
	private String color;
	private String category;
	private int nbOfDoors;
	private float price;
	private int discount;
	private String availability;

	private Date dateOfSale;
	public CarsObject(int ID, String brand, String model, String color, String category, int nbOfDoors, float price, int discount, String availability) {
		this.ID = ID;
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.category = category;
		this.nbOfDoors = nbOfDoors;
		this.price = price;
		this.discount = discount;
		this.availability = availability;
	}

	public CarsObject(int ID, String brand, String model, String color, String category, int nbOfDoors, float price, int discount, Date dateOfSale) {
		this.ID = ID;
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.category = category;
		this.nbOfDoors = nbOfDoors;
		this.price = price;
		this.discount = discount;
		this.dateOfSale = dateOfSale;
	}

	public SimpleStringProperty getDate() {
		return new SimpleStringProperty(String.valueOf(dateOfSale));
	}

	public SimpleStringProperty getID() {
		return new SimpleStringProperty(String.valueOf(ID));
	}

	public void setID(int id) {
		ID = id;
	}

	public SimpleStringProperty getBrand() {
		return new SimpleStringProperty(brand);
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public SimpleStringProperty getModel() {
		return new SimpleStringProperty(model);
	}

	public void setModel(String model) {
		this.model = model;
	}

	public SimpleStringProperty getColor() {
		return new SimpleStringProperty(color);
	}

	public void setColor(String color) {
		this.color = color;
	}

	public SimpleStringProperty getCategory() {
		return new SimpleStringProperty(category);
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public SimpleStringProperty getNbOfDoors() {
		return new SimpleStringProperty(String.valueOf(nbOfDoors));
	}

	public void setNbOfDoors(int nbOfDoors) {
		this.nbOfDoors = nbOfDoors;
	}

	public SimpleStringProperty getPrice() {
		return new SimpleStringProperty("$ " + String.format("%,.2f", price));
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public SimpleStringProperty getDiscount() {
		return new SimpleStringProperty(discount + " %");
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public SimpleStringProperty getAvailability() {
		return new SimpleStringProperty(availability);
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public SimpleStringProperty getUnformattedPrice() {
		return new SimpleStringProperty(String.valueOf(price));
	}

	public SimpleStringProperty getUnformattedDiscount() {
		return new SimpleStringProperty(String.valueOf(discount));
	}

	public int getNormalizedId() {
		return this.ID;
	}
}
