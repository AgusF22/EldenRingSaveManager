package main.controller;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.List;

import main.data_access.SaveFileManager;
import main.exceptions.InvalidNameException;

/**
 * Main logic controller
 * @author AgusF
 */
public class Controller {
	
	protected SaveFileManager saveFileManager;
	
	/**
	 * Creates a new logic controller
	 */
	public Controller() {
		saveFileManager = new SaveFileManager();
	}

	/**
	 * Creates a backup of all the saves
	 * @throws IOException If an error occurs during the backup
	 */
	public void backup() throws IOException {
		saveFileManager.backup();
	}
	
	/**
	 * Loads the save with the given name
	 * @param name The name of the save to load
	 * @throws IOException If an error occurs during the loading
	 */
	public void load(String name) throws IOException {
		saveFileManager.load(name);
	}
	
	/**
	 * Saves the current active save with the given name
	 * @param name The name for the new save
	 * @throws IOException If an error occurs during the saving
	 * @throws InvalidNameException If the given name is invalid
	 */
	public void save(String name) throws IOException, InvalidNameException {
		final String exceptionMsg = "Invalid save file name";
		try {
			Paths.get(name);
		} catch (InvalidPathException ex) {
			throw new InvalidNameException(exceptionMsg);
		}
		if (name.equals("")) {
			throw new InvalidNameException(exceptionMsg);
		}
		saveFileManager.save(name);
	}
	
	/**
	 * Returns a list with all the saved files names
	 * @return A list with all the saved files names
	 */
	public List<String> getSaveNames() {
		return saveFileManager.getSaveNames();
	}
	
}
