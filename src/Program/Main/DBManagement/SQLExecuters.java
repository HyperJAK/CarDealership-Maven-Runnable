package Program.Main.DBManagement;

import Program.Main.Guis.CarsGui;
import Program.Main.Guis.ClientGui;
import Program.Main.Guis.EmployeeGui;
import Program.Main.Guis.RightClick.ClientsRightClickGui;
import Program.Main.App;
import Program.Main.Objects.CarsObject;
import Program.Main.Objects.ClientsObject;
import Program.Main.Objects.EmployeesObject;

import java.sql.*;
import java.time.LocalDateTime;

public class SQLExecuters {
	// This Class should contains all the possible SQL commands that this
	// program could execute

	public Connection connection;

	public SQLExecuters() {
		connection = InitConnection.getConnection();
		if(InitConnection.isConnectionStarted()){
			refreshCars();
			refreshEmployees();
			refreshClients();
		}

	}
	// Later use ?
	/*   public void executeChange(String tableName, String databaseOptionToChange, String changeTo){
	    String querry = "ALTER TABLE "+tableName+"("+databaseOptionToChange+") VALUE (" + changeTo+");";
	}*/

	public void tryConnection() {
		if(!InitConnection.isConnectionStarted()){
			connection = InitConnection.getConnection();
		}

	}

	public void insertCar(String manufacturer, String model, String color, String category, int nbDoors, float price) throws Exception {
		tryConnection();

		String values = "(\"" + manufacturer + "\",\"" + model + "\",\"" + color + "\",\"" + category + "\"," + nbDoors + "," + price + ");";
		String querry = "INSERT INTO CAR(manufacturer, model, color, category, nbdoors, price) VALUES " + values;

		Statement statement = connection.createStatement();

		statement.executeUpdate(querry);

		// Closes the connection between IDE and database as well as statements
		// created and any resultsets
		statement.close();

	}

	public void updateCar(int carID, String manufacturer, String model, String color, String category, int nbDoors, float price, int discount, String availability)
			throws Exception {
		tryConnection();

		String querry =
				"UPDATE CAR SET \n" + "manufacturer = \'" + manufacturer + "\',\n" + "model = \'" + model + "\',\n" + "color = \'" + color + "\',\n" + "category = \'"
						+ category + "\',\n" + "nbdoors = " + nbDoors + ",\n" + "price = " + price + ",\n" + "sold = \'" + availability + "\',\n" + "Discount = "
						+ discount + ",\n" + "sold = \'" + availability + "\'\n" + "WHERE idcar = " + carID + ";";
		Statement statement = connection.createStatement();

		statement.executeUpdate(querry);

		// Closes the connection between IDE and database as well as statements
		// created and any resultsets
		statement.close();
	}

	public void deleteCar(int carID) throws Exception {
		tryConnection();

		String querryDelCar = "update car set deleted = \"" + true + "\" where idcar = " + carID + ";";

		Statement statementDel = connection.createStatement();

		statementDel.executeUpdate(querryDelCar);

		// Closes the connection between IDE and database as well as statements
		// created and any resultsets

		statementDel.close();
	}

	public void refreshCars() {
		try {
			tryConnection();
			App.dataListCars.clear();

			String querry = "SELECT * FROM car where deleted = \"" + false + "\";";
			PreparedStatement preparedStatement = connection.prepareStatement(querry);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				App.dataListCars.add(
						new CarsObject(resultSet.getInt("idcar"), resultSet.getString("manufacturer"), resultSet.getString("model"), resultSet.getString("color"),
								resultSet.getString("category"), resultSet.getInt("nbDoors"), resultSet.getFloat("price"), resultSet.getInt("Discount"),
								resultSet.getString("sold")));
				// CarsGui.displayVehicle_Table.setItems(Main.dataListCars);
				CarsGui.initSearchFilter();

			}
		} catch (Exception ex) {

		}
	}
	public void updateCategoryWithBrandAndDoors(String brand, int nbDoors) throws SQLException {
		tryConnection();

		CarsGui.vehicleCategories_List.clear();

		String querryCategories = "SELECT category FROM default_car_info where (nbDoors = 0 or nbDoors = " + nbDoors + ") and brand = \"" + brand
				+ "\" group by category order by category;";
		PreparedStatement categories_Statement = connection.prepareStatement(querryCategories);
		ResultSet categories_ResultSet = categories_Statement.executeQuery();

		try {
			while (categories_ResultSet.next()) {
				CarsGui.vehicleCategories_List.add(categories_ResultSet.getString("category"));
			}
			CarsGui.resetComboBoxes("categories");
		} catch (Exception ignored) {

		}
	}

	/*public void updateCategoryWithBrand(String brand) throws SQLException {
		CarsGui.vehicleCategories_List.clear();

		String querryCategories = "SELECT category FROM default_car_info  where brand = \""
				+ brand + "\" group by category order by category;";
		PreparedStatement categories_Statement = connection
				.prepareStatement(querryCategories);
		ResultSet categories_ResultSet = categories_Statement.executeQuery();

		try {
			while (categories_ResultSet.next()) {
				CarsGui.vehicleCategories_List
						.add(categories_ResultSet.getString("category"));
			}
			CarsGui.resetComboBoxes("categories");
		} catch (Exception ignored) {
			System.out.println("#Exceptawetra");
		}
	}*/

	public void insertDefaultVehicleBrands() {
		try {
			tryConnection();
			CarsGui.vehicleBrands_List.clear();
			CarsGui.vehicleCategories_List.clear();
			CarsGui.vehicleModels_List.clear();

			String querryBrands = "SELECT brand FROM default_car_info group by brand order by brand;";
			PreparedStatement brands_Statement = connection.prepareStatement(querryBrands);
			ResultSet brands_ResultSet = brands_Statement.executeQuery();

			String querryModels = "SELECT model FROM default_car_info group by model order by model;";
			PreparedStatement models_Statement = connection.prepareStatement(querryModels);
			ResultSet models_ResultSet = models_Statement.executeQuery();

			String querryCategories = "SELECT category FROM default_car_info group by category order by category;";
			PreparedStatement categories_Statement = connection.prepareStatement(querryCategories);
			ResultSet categories_ResultSet = categories_Statement.executeQuery();

			try {
				while (brands_ResultSet.next()) {
					CarsGui.vehicleBrands_List.add(brands_ResultSet.getString("brand"));

				}

				while (models_ResultSet.next()) {
					CarsGui.vehicleModels_List.add(models_ResultSet.getString("model"));

				}

				while (categories_ResultSet.next()) {
					CarsGui.vehicleCategories_List.add(categories_ResultSet.getString("category"));

				}
			} catch (Exception ignored) {

			}
		} catch (Exception ignored) {

		}
	}

	public void updateVehicleModelInfo(String brand, int nbDoors) {
		try {
			tryConnection();
			try {
				CarsGui.vehicleModels_List.clear();

			} catch (Exception ignored) {
			}

			String querryModels =
					"SELECT model FROM default_car_info where (nbDoors = 0 or nbDoors = " + nbDoors + ") and brand = \"" + brand + "\" group by model order by model;";
			PreparedStatement model_Statement = connection.prepareStatement(querryModels);
			ResultSet models_ResultSet = model_Statement.executeQuery();

			try {

				while (models_ResultSet.next()) {
					CarsGui.vehicleModels_List.add(models_ResultSet.getString("model"));

				}

				CarsGui.resetComboBoxes("models");
				// System.out.println(CarsGui.vehicleModels_List);
			} catch (Exception test) {
				System.out.println("Exception on while loop");
			}
		} catch (Exception ex) {
			System.out.println("Exception on sql");
		}

	}

	public void insertClient(String fName, String lName, String gender, String address, String telephone, String email, Date dateOfBirth) throws Exception {
		tryConnection();

		String values = "(\"" + fName + "\",\"" + lName + "\",\"" + gender + "\",\"" + address + "\",\"" + telephone + "\",\"" + email + "\",\"" + dateOfBirth + "\");";

		String querry = "INSERT INTO CLIENT(firstName, lastName, gender, address, phoneNumber, email, dateOfBirth) VALUES" + values;

		Statement statement = connection.createStatement();
		statement.executeUpdate(querry);

		statement.close();

	}

	public void updateClient(int clientID, String fName, String lName, String gender, String address, String telephone, String email, Date dateOfBirth, int carsOwned)
			throws Exception {
		tryConnection();

		String querry =
				"UPDATE CLIENT SET \n" + "firstName = \'" + fName + "\',\n" + "lastName = \'" + lName + "\',\n" + "gender = \'" + gender + "\',\n" + "address = \'"
						+ address + "\',\n" + "phoneNumber = \'" + telephone + "\',\n" + "email = \'" + email + "\',\n" + "dateOfBirth = \'" + dateOfBirth + "\',\n"
						+ "carsOwned = " + carsOwned + "\n" + "WHERE idclient = " + clientID + ";";
		Statement statement = connection.createStatement();

		statement.executeUpdate(querry);

		// Closes the connection between IDE and database as well as statements
		// created and any resultsets
		statement.close();
	}

	public void deleteClient(int clientID) throws Exception {
		tryConnection();

		String deleteClient = "delete from client where idclient = " + clientID + ";";

		String deleteClients_Cars = "update car set deleted = \"" + true + "\" " + "where idcar in(" + "select hasCar.car_idcar " + "from client_has_car hasCar "
				+ "where hasCar.car_idcar = idcar and hasCar.client_idclient = " + clientID + ");";

		Statement statement = connection.createStatement();
		Statement statement2 = connection.createStatement();

		statement.executeUpdate(deleteClient);
		statement2.executeUpdate(deleteClients_Cars);

		// Closes the connection between IDE and database as well as statements
		// created and any resultsets
		statement.close();
		refreshCars();
	}

	public void refreshClients() {
		try {
			tryConnection();
			App.dataListClients.clear();

			String querry = "SELECT * FROM client;";
			PreparedStatement preparedStatement = connection.prepareStatement(querry);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				App.dataListClients.add(
						new ClientsObject(resultSet.getInt("idclient"), resultSet.getDate("dateOfBirth"), resultSet.getInt("carsOwned"), resultSet.getString("firstName"),
								resultSet.getString("lastName"), resultSet.getString("gender"), resultSet.getString("address"), resultSet.getString("phoneNumber"),
								resultSet.getString("email")));

				// ClientGui.displayClients_Table.setItems(Main.dataListClients);
				ClientGui.initSearchFilter();
			}
		} catch (Exception ignored) {

		}
	}

	public void refreshSpecificClientCars(int clientID) {
		try {
			tryConnection();
			ClientsRightClickGui.dataListCarsOwned.clear();

			String querry = "select car.idcar, car.manufacturer, car.model, car.color, car.category, car.nbDoors, car.price, car.discount, hasCar.dateOfSale"
					+ " from car car, client_has_car hasCar" + " where (hasCar.car_idcar = car.idcar and hasCar.client_idclient = " + clientID + ")"
					+ " and car.deleted = \"" + false + "\";";

			PreparedStatement preparedStatement = connection.prepareStatement(querry);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ClientsRightClickGui.dataListCarsOwned.add(
						new CarsObject(resultSet.getInt("idcar"), resultSet.getString("manufacturer"), resultSet.getString("model"), resultSet.getString("color"),
								resultSet.getString("category"), resultSet.getInt("nbDoors"), resultSet.getFloat("price"), resultSet.getInt("Discount"),
								resultSet.getDate("dateOfSale")));
				// CarsGui.displayVehicle_Table.setItems(Main.dataListCars);
			}
		} catch (Exception ex) {

		}
	}

	public void addCarToClient(ClientsObject client, CarsObject car) {
		try {
			tryConnection();
			LocalDateTime now = LocalDateTime.now();

			String addCarToClient_querry =
					"INSERT INTO client_has_car(client_idclient, car_idcar, dateOfSale) " + "VALUES(" + client.getNormalizedId() + "," + car.getNormalizedId() + ",\""
							+ now + "\") ;";

			String updateClientCounter_querry =
					"UPDATE CLIENT SET " + "carsOwned = " + (Integer.valueOf(client.getCarsOwned().get()) + 1) + " " + "WHERE idclient = " + client.getNormalizedId()
							+ ";";
			String updateCarStatus_querry = "UPDATE CAR SET\n" + "sold = " + "\'true\'\n" + "WHERE idcar = " + car.getNormalizedId() + ";";

			Statement statement1 = connection.createStatement();
			statement1.executeUpdate(addCarToClient_querry);
			Statement statement2 = connection.createStatement();
			statement2.executeUpdate(updateClientCounter_querry);
			Statement statement3 = connection.createStatement();
			statement3.executeUpdate(updateCarStatus_querry);

			statement1.close();
			statement2.close();
			statement3.close();

			refreshClients();
			refreshCars();
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}
	}

	public void insertEmployee(String fName, String lName, String gender, String position, float salary, String address, String telephone, String email, Date dateOfBirth)
			throws Exception {
		tryConnection();

		String values =
				"(\"" + fName + "\",\"" + lName + "\",\"" + gender + "\",\"" + position + "\",\"" + salary + "\",\"" + address + "\",\"" + telephone + "\",\"" + email
						+ "\",\"" + dateOfBirth + "\");";

		String querry = "INSERT INTO EMPLOYEE(firstName, lastName, gender, position, salary, address, phoneNumber, email, dateOfBirth) VALUES" + values;

		Statement statement = connection.createStatement();
		statement.executeUpdate(querry);

		statement.close();

	}

	public void updateEmployee(int employeeID, String fName, String lName, String gender, String position, float salary, String address, String telephone, String email,
			Date dateOfBirth) throws Exception {
		tryConnection();

		String querry =
				"UPDATE EMPLOYEE SET \n" + "firstName = \'" + fName + "\',\n" + "lastName = \'" + lName + "\',\n" + "gender = \'" + gender + "\',\n" + "position = \'"
						+ position + "\',\n" + "salary = " + salary + ",\n" + "address = \'" + address + "\',\n" + "phoneNumber = \'" + telephone + "\',\n" + "email = \'"
						+ email + "\',\n" + "dateOfBirth = \'" + dateOfBirth + "\'\n" + "WHERE idemployee = " + employeeID + ";";
		Statement statement = connection.createStatement();

		statement.executeUpdate(querry);

		// Closes the connection between IDE and database as well as statements
		// created and any resultsets
		statement.close();
	}

	public void deleteEmployee(int employeeID) throws Exception {
		tryConnection();

		String deleteEmployee_querry = "delete from employee where idemployee = " + employeeID + ";";

		Statement statement = connection.createStatement();

		statement.executeUpdate(deleteEmployee_querry);

		// Closes the connection between IDE and database as well as statements
		// created and any resultsets
		statement.close();
	}

	public void refreshEmployees() {
		try {
			tryConnection();
			App.dataListEmployees.clear();

			String querry = "SELECT * FROM employee;";
			PreparedStatement preparedStatement = connection.prepareStatement(querry);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				App.dataListEmployees.add(new EmployeesObject(resultSet.getInt("idemployee"), resultSet.getDate("dateOfBirth"), resultSet.getString("firstName"),
						resultSet.getString("lastName"), resultSet.getString("gender"), resultSet.getString("position"), resultSet.getFloat("salary"),
						resultSet.getString("address"), resultSet.getString("phoneNumber"), resultSet.getString("email")));

				/*EmployeeGui.displayEmployees_Table
						.setItems(Main.dataListEmployees);*/
				EmployeeGui.initSearchFilter();
			}
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}
	}

}
