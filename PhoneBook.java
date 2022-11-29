import javafx.stage.FileChooser;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class PhoneBook {
	private Map<String, String> book;

	public PhoneBook() throws IOException {
		book = new HashMap<>();
	}

	public PhoneBook(Map<String, String> map) throws IOException {
		book = new HashMap<>(map);
	}

	public Map<String, String> getBook() {
		return book;
	}

	public void setBook(Map<String, String> book) {
		this.book = book;
	}

	public void addContact(String name, String phone) throws IllegalArgumentException {
		if (name.equals("") || phone.equals("")) {
			throw new IllegalArgumentException("Your fields is empty");
		}
		if (isExist(name)) {
			JOptionPane.showMessageDialog(null, "This name is exist");
			throw new IllegalArgumentException("This name is exist");
		}
		if (phone.matches("[0-9]+")) {
			book.put(name, phone);
		} else {
			throw new IllegalArgumentException("Invalid phone number");
		}
	}

	public void deleteContact(String name) {
		book.remove(name);
	}

	public void editContact(String name, String phone) throws IllegalArgumentException {
		if (name.equals("") || phone.equals("")) {
			throw new IllegalArgumentException("Your fields is empty");
		}
		book.replace(name, phone);
	}

	public boolean isExist(String name) {
		return this.book.containsKey(name);
	}

	public String search(String query) {
		return isExist(query) ? (query + " " + book.get(query).toString()) : (query + " is not exist");
	}

	public void saveToFile(PhoneBook book) throws IOException, FileNotFoundException {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Add all", " "));
		fc.setTitle("Save file");
		File file = fc.showSaveDialog(null);
		try {
			FileOutputStream fOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("fo"));
			out.writeObject(book.getBook());
			out.flush();
			out.close();
			fOut.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public Map<String, String> load() throws IOException, IllegalArgumentException {
		this.book = new HashMap<>();
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File("."));
		File file = fc.showOpenDialog(null);
		StringBuilder sb;
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNext()) {
				sb = new StringBuilder(scan.nextLine());
				int index = sb.indexOf(" ");
				String name = sb.substring(0, index);
				String number = sb.substring(index + 1, sb.length());
				book.put(name.trim(), number.trim());
			}
			scan.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return book;
	}
}
