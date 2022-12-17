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
import javax.swing.JTextField;

import main.controller.Controller;
import main.exceptions.InvalidNameException;

/**
 * Main user interface
 * @author AgusF
 */
public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	protected Controller controller;
	
	protected JTextField textField;
	protected JComboBox<String> comboBox;
	protected JButton loadButton;
	
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
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(10, 10, 10, 10);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
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
		
		comboBox = new JComboBox<>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 10, 10, 10);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		getContentPane().add(comboBox, gbc_comboBox);
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
			controller.save(textField.getText());
			textField.setText("");
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
			controller.load(String.valueOf(comboBox.getSelectedItem()));
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
			comboBox.setEnabled(false);
			loadButton.setEnabled(false);
		} else {
			comboBox.setEnabled(true);
			loadButton.setEnabled(true);
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(saveNames.toArray(new String[0]));
			comboBox.setModel(model);
		}
	}
	
}
