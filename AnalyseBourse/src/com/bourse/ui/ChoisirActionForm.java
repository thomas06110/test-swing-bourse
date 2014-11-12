package com.bourse.ui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;

import com.bourse.Manager;
import java.awt.Color;

public class ChoisirActionForm extends JPanel {
	private final JButton btnValider = new JButton("Valider");
	public ChoisirActionForm(final Manager manager) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 200, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblChoisissezVotreAction = new JLabel("Choisissez votre action :");
		GridBagConstraints gbc_lblChoisissezVotreAction = new GridBagConstraints();
		gbc_lblChoisissezVotreAction.anchor = GridBagConstraints.WEST;
		gbc_lblChoisissezVotreAction.gridwidth = 3;
		gbc_lblChoisissezVotreAction.insets = new Insets(0, 0, 5, 0);
		gbc_lblChoisissezVotreAction.gridx = 0;
		gbc_lblChoisissezVotreAction.gridy = 0;
		add(lblChoisissezVotreAction, gbc_lblChoisissezVotreAction);
		
		JLabel lblAction = new JLabel("Action");
		lblAction.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblAction = new GridBagConstraints();
		gbc_lblAction.ipadx = 5;
		gbc_lblAction.ipady = 8;
		gbc_lblAction.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblAction.insets = new Insets(0, 0, 0, 5);
		gbc_lblAction.gridx = 0;
		gbc_lblAction.gridy = 1;
		add(lblAction, gbc_lblAction);
		
		String[] lstAction={"COX", "NEO", "AF"};
		
		final JComboBox comboBox = new JComboBox(lstAction);
		comboBox.setBackground(Color.WHITE);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.NORTH;
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		add(comboBox, gbc_comboBox);

		GridBagConstraints gbc_btnValider = new GridBagConstraints();
		gbc_btnValider.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnValider.gridx = 2;
		gbc_btnValider.gridy = 1;
		btnValider.setSize(50, 15);
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				manager.sendGetActionTransaction(comboBox.getSelectedItem().toString());
			}
		});
		add(btnValider, gbc_btnValider);
	}

		
}
