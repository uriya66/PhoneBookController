import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.io.*;

public class PhoneBookController {

	@FXML
	private TextField resultText, exsistNameText, nameText, phoneNumberTextAdd, phoneNumberTextUpdate, searchText;

	@FXML
	private Button addButton, loadButton, removeButton, saveButton, searchButton, updateButton;

	@FXML
	private TableColumn<Map.Entry<String, String>, String> nameColumn;

	@FXML
	private TableColumn<Map.Entry<String, String>, String> phoneColumn;

	@FXML
	private TableView<Map.Entry<String, String>> table;

	private PhoneBook phoneBook;
	private HashMap<String, String> book;

	public void initialize() throws IOException, IllegalArgumentException {
		try {
			book = new HashMap<String, String>();
			phoneBook = new PhoneBook(book);
			nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
			phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue()));
			PhoneBookShow();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	void loadPressed(ActionEvent event) throws IOException, IllegalArgumentException, ClassNotFoundException {
		try {
			phoneBook.setBook(phoneBook.load());
			PhoneBookShow();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	void addPressed(ActionEvent event) {
		try {
			String name = nameText.getText();
			String phone = phoneNumberTextAdd.getText();
			phoneBook.addContact(name, phone);
			PhoneBookShow();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	void removePressed(ActionEvent event) {
		try {
			if (phoneBook.isExist(searchText.getText())) {
				phoneBook.deleteContact(searchText.getText());
				PhoneBookShow();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	void savePressed(ActionEvent event) throws IOException {
		phoneBook.saveToFile(phoneBook);
	}

	@FXML
	void searchPressed(ActionEvent event) {
		resultText.setText(phoneBook.search(searchText.getText()));
	}

	@FXML
	void updatePressed(ActionEvent event) {
		try {
			if (phoneBook.isExist(exsistNameText.getText())) {
				phoneBook.editContact(exsistNameText.getText(), phoneNumberTextUpdate.getText());
				PhoneBookShow();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void PhoneBookShow() {
		table.getItems().clear();
		table.getItems().addAll(phoneBook.getBook().entrySet());
		table.sort();
	}
}
