package Program.Main.Guis.RightClick;
import Program.Main.CustomClass.CText;
import Program.Main.CustomClass.CTextField;
import Program.Main.Guis.CarsGui;
import Program.Main.App;
import Program.Main.Objects.CarsObject;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
public class CarsRightClickGui extends RightClickGui {

	public CTextField carDiscount_Field = new CTextField("Discount", "discount-field");
	public CTextField carPrice_Field = new CTextField("Price", "price-field");
	public CText carDiscount_CText = new CText();
	public CText carPrice_CText = new CText();
	public CText carColor_CText = new CText();

	public CarsRightClickGui() {

	}

	@Override
	public void deleteObject(int ID) {
		rightClickStage.setScene(delete_Scene);
		confirmation_text.setText("Are you sure you want to delete this Car ?");
		rightClickStage.show();

		yes_btn.setOnAction(e -> {

			try {
				App.sql.deleteCar(ID);
				App.sql.refreshCars();
				rightClickStage.close();
			} catch (Exception ignored) {

			}
		});

		no_btn.setOnAction(e -> {
			rightClickStage.close();
		});
	}

	public void initCarEdit(CarsObject car) {

		GridPane edit_GridPane = new GridPane();

		carDiscount_Field.setText(car.getUnformattedDiscount().get());
		carPrice_Field.setText(car.getUnformattedPrice().get());
		CarsGui.vehicleColors_ComboBox.setValue(car.getColor().get());

		carDiscount_CText.setText("Discount: ");
		carPrice_CText.setText("Price: ");
		carColor_CText.setText("Color: ");

		edit_GridPane.add(carDiscount_CText, 0, 0);
		edit_GridPane.add(carPrice_CText, 0, 1);
		edit_GridPane.add(carColor_CText, 0, 2);

		edit_GridPane.add(carDiscount_Field, 1, 0);
		edit_GridPane.add(carPrice_Field, 1, 1);
		edit_GridPane.add(CarsGui.vehicleColors_ComboBox, 1, 2);

		carPrice_Field.addEventFilter(KeyEvent.KEY_TYPED, e1 -> {
			if (!"1234567890".contains(e1.getCharacter())) {
				e1.consume();
			}
			if (carPrice_Field.getLength() >= 10) { // we got
				// remember
				// to put
				// limiters
				// so the
				// database
				// doesn't
				// explode
				e1.consume();
			}
		});

		carDiscount_Field.addEventFilter(KeyEvent.KEY_TYPED, e2 -> {
			if (!"1234567890".contains(e2.getCharacter())) {
				e2.consume();
			}
			if (carDiscount_Field.getLength() > 1) {
				e2.consume();
			}
		});

		edit_GridPane.add(save_btn, 1, 4);
		edit_GridPane.setAlignment(Pos.CENTER);
		edit_GridPane.setHgap(10);
		edit_GridPane.setVgap(10);
		edit_GridPane.setHalignment(save_btn, HPos.RIGHT);
		edit_GridPane.setId("grid");

		edit_Scene = new Scene(edit_GridPane, 350, 200);

		initCss();

		rightClickStage.setScene(edit_Scene);
		rightClickStage.setResizable(false);
		rightClickStage.show();
		// rightClickStage.setAlwaysOnTop(true); trust me, dont

		// // click the save button stuff // //

		save_btn.setOnAction(e -> {
			try {
				// take his original
				// value, otherwise
				// take this values
				// in the text
				// fields
				App.sql.updateCar(car.getNormalizedId(), car.getBrand().get(), // is
						// it
						// empty?
						// yes?
						car.getModel().get(), CarsGui.vehicleColors_ComboBox.getValue().toString(), car.getCategory().get(), Integer.parseInt(car.getNbOfDoors().get()),
						(isEmptyOrBlank(carPrice_Field.getText()) ? Float.parseFloat(car.getUnformattedPrice().get()) : Float.parseFloat(carPrice_Field.getText())),
						// same
						// as
						(isEmptyOrBlank(carDiscount_Field.getText())
								? Integer.parseInt(car.getUnformattedDiscount().get())
								: Integer.parseInt(carDiscount_Field.getText())), // same
						// as,
						car.getAvailability().get());
				// before but
				// now after
				// checking,
				// does it end
				// with
				// @gmail.com?
				// if yes
				// proceed if
				// not take emp
				// current email
				App.sql.refreshCars();
				rightClickStage.close();
			} catch (Exception ignored) {
				ignored.printStackTrace();
			}
		});

	}

}
