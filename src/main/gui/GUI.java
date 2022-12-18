package main.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionEvent;

import main.controller.Controller;
import main.exceptions.InvalidNameException;

/**
 * Main user interface
 * @author AgusF
 */
public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	protected Controller controller;
	protected JComboBox<String> loadComboBox;
	protected JButton loadButton;
	private JComboBox<String> saveComboBox;
	
	/**
	 * Creates a new GUI
	 * @param controller The logic controller
	 */
	public GUI(Controller controller) {
		super("Elden Ring Save Manager");
		this.controller = controller;
		this.setSize(450, 160);
		this.setLocationRelativeTo(null);
		
		populate();
	}

	/**
	 * Creates the components for the GUI
	 */
	protected void populate() {
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[]{0, 0, 0};
		layout.rowHeights = new int[]{0, 0, 0, 0};
		layout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		layout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(layout);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		GridBagConstraints gbc_saveButton = new GridBagConstraints();
		gbc_saveButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_saveButton.insets = new Insets(10, 10, 10, 10);
		gbc_saveButton.gridx = 0;
		gbc_saveButton.gridy = 0;
		getContentPane().add(saveButton, gbc_saveButton);
		
		loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
		GridBagConstraints gbc_loadButton = new GridBagConstraints();
		gbc_loadButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_loadButton.insets = new Insets(0, 10, 10, 10);
		gbc_loadButton.gridx = 0;
		gbc_loadButton.gridy = 1;
		getContentPane().add(loadButton, gbc_loadButton);
		
		saveComboBox = new JComboBox<>();
		saveComboBox.setEditable(true);
		GridBagConstraints gbc_saveComboBox = new GridBagConstraints();
		gbc_saveComboBox.insets = new Insets(10, 10, 10, 10);
		gbc_saveComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_saveComboBox.gridx = 1;
		gbc_saveComboBox.gridy = 0;
		getContentPane().add(saveComboBox, gbc_saveComboBox);
		
		loadComboBox = new JComboBox<>();
		GridBagConstraints gbc_loadComboBox = new GridBagConstraints();
		gbc_loadComboBox.insets = new Insets(0, 10, 10, 10);
		gbc_loadComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_loadComboBox.gridx = 1;
		gbc_loadComboBox.gridy = 1;
		getContentPane().add(loadComboBox, gbc_loadComboBox);
		loadSaveNames();
		
		JButton backupButton = new JButton("Backup");
		backupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backup();
			}
		});
		GridBagConstraints gbc_backupButton = new GridBagConstraints();
		gbc_backupButton.insets = new Insets(0, 10, 10, 10);
		gbc_backupButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_backupButton.gridwidth = 2;
		gbc_backupButton.gridx = 0;
		gbc_backupButton.gridy = 2;
		getContentPane().add(backupButton, gbc_backupButton);
	}
	
	/**
	 * Saves the current save file with the given name in the textField
	 */
	protected void save() {
		try {
			controller.save(saveComboBox.getSelectedItem().toString());
			loadSaveNames();
			JOptionPane.showMessageDialog(this, "Save completed", "Success", JOptionPane.PLAIN_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Save error", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (InvalidNameException e) {
			JOptionPane.showMessageDialog(this, "Invalid save name", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * Loads the selected save file in the comboBox
	 */
	protected void load() {
		try {
			controller.load(String.valueOf(loadComboBox.getSelectedItem()));
			JOptionPane.showMessageDialog(this, "Load completed", "Success", JOptionPane.PLAIN_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Load error", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Creates a backup of all the saves including the current one
	 */
	protected void backup() {
		try {
			controller.backup();
			JOptionPane.showMessageDialog(this, "Backup completed", "Success", JOptionPane.PLAIN_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Backup error", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Updates the list of saves
	 */
	protected void loadSaveNames() {
		List<String> saveNames = controller.getSaveNames();
		if (saveNames.isEmpty()) {
			loadComboBox.setEnabled(false);
			loadButton.setEnabled(false);
			saveComboBox.removeAllItems();
		} else {
			loadComboBox.setEnabled(true);
			loadButton.setEnabled(true);
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(saveNames.toArray(new String[0]));
			loadComboBox.setModel(model);
			model = new DefaultComboBoxModel<>(saveNames.toArray(new String[0]));
			saveComboBox.setModel(model);
			saveComboBox.setSelectedItem("");
		}
	}
	
}
