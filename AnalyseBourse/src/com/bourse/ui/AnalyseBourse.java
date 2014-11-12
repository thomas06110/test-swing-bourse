package com.bourse.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;

import com.bourse.Manager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.swing.SwingConstants;

public class AnalyseBourse implements Observer{

	private JFrame frame;
	JPanel choisirActionPanel;
	JPanel mainPanel;
	JLabel lblStatus;
	Manager manager;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnalyseBourse window = new AnalyseBourse();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AnalyseBourse() {
		/* Get actual class name to be printed on */
		Logger log = Logger.getLogger(AnalyseBourse.class.getName());
		log.info("Démarrage de l'application");
		mainPanel=new JPanel();
		manager=new Manager(mainPanel);
		manager.addObserver(this);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		JMenuItem mntmErase = new JMenuItem("Erase");
		mntmErase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				erase();
			}
		});
		mnFile.add(mntmErase);

		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{209, 10, 10, 0};
		gridBagLayout.rowHeights = new int[]{10, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		choisirActionPanel = new ChoisirActionForm(manager);
		mainPanel.add(choisirActionPanel);
		choisirActionPanel.setSize(800, 400);
		GridBagConstraints gbc_mainPanel = new GridBagConstraints();
		gbc_mainPanel.weighty = 0.9;
		gbc_mainPanel.weightx = 1.0;
		gbc_mainPanel.gridwidth = 3;
		gbc_mainPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_mainPanel.insets = new Insets(0, 0, 0, 5);
		gbc_mainPanel.gridx = 0;
		gbc_mainPanel.gridy = 0;
		frame.getContentPane().add(mainPanel, gbc_mainPanel);
		
		manager.setCurrentScreen(choisirActionPanel);
		
		JPanel statusPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) statusPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		statusPanel.setPreferredSize(new Dimension(640, 80));
		GridBagConstraints gbc_statusPanel = new GridBagConstraints();
		gbc_statusPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_statusPanel.gridx = 0;
		gbc_statusPanel.gridy = 1;
		frame.getContentPane().add(statusPanel, gbc_statusPanel);
		lblStatus =new JLabel("Status :");
		lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(lblStatus);
		
	}

	void erase(){
		System.out.println("Erase");
		frame.getContentPane().remove(choisirActionPanel);
		JPanel erasePanel=new JPanel();
		frame.getContentPane().add(erasePanel);
		frame.getContentPane().repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Observable :"+o+ "Status:"+((Manager)o).getStatus()+" Object:"+arg);
		if(o instanceof Manager){
			lblStatus.setText(((Manager)o).getStatus());
			lblStatus.repaint();
		}
	}
}
