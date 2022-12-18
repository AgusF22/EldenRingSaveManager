package main.data_access;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Main files controller
 * @author AgusF
 */
public class SaveFileManager {
	
	protected static final String SAVE_FILE_PATH = "/AppData/Roaming/EldenRing/76561198113163023/ER0000.sl2";
	
	/**
	 * Creates a backup of all the saves
	 * @throws IOException If an error occurs during the backup
	 */
	public void backup() throws IOException {
		String bkpName = "EldenRingBackup" + java.time.LocalDateTime.now().toString().replaceAll(":", ".");
		Files.createDirectories(Paths.get("backups/" + bkpName));
		Files.copy(Paths.get(getSaveFilePath()), Paths.get("backups/" + bkpName + "/current"));

		if (Files.exists(Paths.get("saves"))) {
			Files.walk(Paths.get("saves")).forEach(source -> {
				Path destination = Paths.get("backups/" + bkpName + "/saves", source.toString().substring("saves".length()));
				try {
					Files.copy(source, destination);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	/**
	 * Loads the save with the given name
	 * @param name The name of the save to load
	 * @throws IOException If an error occurs during the loading
	 */
	public void load(String name) throws IOException {
		Files.copy(	Paths.get("saves/" + name),
					Paths.get(getSaveFilePath()),
					StandardCopyOption.REPLACE_EXISTING);
	}

	/**
	 * Saves the current active save with the given name
	 * @param name The name for the new save
	 * @throws IOException If an error occurs during the saving
	 */
	public void save(String name) throws IOException {
		Files.createDirectories(Paths.get("saves/"));
		Files.copy(	Paths.get(getSaveFilePath()),
					Paths.get("saves/" + name),
					StandardCopyOption.REPLACE_EXISTING);
	}

	/**
	 * Returns the path to the Elden Ring save file
	 * @return The path to the Elden Ring save file
	 */
	protected String getSaveFilePath() {
		return System.getProperty("user.home") + SAVE_FILE_PATH;
	}
	
	/**
	 * Returns a list with all the saved files names
	 * @return A list with all the saved files names
	 */
	public List<String> getSaveNames() {
		List<String> results = new ArrayList<String>();
		File[] files = new File("saves").listFiles();
		if (files != null) {
			for (File file : files) {
			    if (file.isFile()) {
			        results.add(file.getName());
			    }
			}
		}
		return results;
	}
	
}
