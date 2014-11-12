package com.bourse.ui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import com.bourse.Manager;
import com.bourse.Transaction;

public class ListTransactionsTable extends JPanel implements TableModelListener {
	private JTable table;
	private String[]comboData={"item1", "item2", "item3", "item4"};

	/**
	 * @wbp.parser.constructor
	 */
	public ListTransactionsTable(final Manager manager) {
		setSize(800, 500);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{600, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 400, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblAction = new JLabel("Transactions de :"+manager.getCurrentAction());
		GridBagConstraints gbc_lblAction = new GridBagConstraints();
		gbc_lblAction.insets = new Insets(0, 0, 5, 0);
		gbc_lblAction.gridx = 0;
		gbc_lblAction.gridy = 0;
		add(lblAction, gbc_lblAction);
		
		
		String title[]={"Heure", "Valeur", "Qte", "BTN TEST", "Combo TEST"};
		Object[][] data=new Object[manager.getListTransactions().size()][5];
		//initialiser les datas
		int i=0;
		for(Transaction tr:manager.getListTransactions()){
			data[i][0]=tr.getHeure().toString();
			data[i][1]=String.valueOf(tr.getMontant());
			data[i][2]=String.valueOf(tr.getNombre());
			data[i][3]=new String("test"+i);
			data[i][4]=comboData[1];
			i++;
		}
		JButton btnRetour = new JButton("retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				manager.displayScreen("RechercheAction");
			}
		});
		GridBagConstraints gbc_btnRetour = new GridBagConstraints();
		gbc_btnRetour.anchor = GridBagConstraints.WEST;
		gbc_btnRetour.insets = new Insets(0, 0, 5, 0);
		gbc_btnRetour.gridx = 0;
		gbc_btnRetour.gridy = 1;
		add(btnRetour, gbc_btnRetour);
		
		ZModel zModel=new ZModel(data, title);
		table = new JTable(zModel);
		//table = new JTable(data, title);
		table.getColumn("BTN TEST").setCellRenderer(new ButtonRenderer()); 
		table.getColumn("BTN TEST").setCellEditor(new ButtonEditor(new JCheckBox()));
		table.getColumn("Combo TEST").setCellRenderer(new ComboRenderer()); 
		table.getColumn("Combo TEST").setCellEditor(new ComboEditor());
		//table.setDefaultRenderer(JComponent.class, new TableComponent());
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		
		table.getModel().addTableModelListener(this);
		
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 2;
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, gbc_table);
	}

	public ListTransactionsTable(LayoutManager layout) {
		super(layout);
	}

	public ListTransactionsTable(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public ListTransactionsTable(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	
	 //Classe modèle personnalisée
	  class ZModel extends AbstractTableModel{
	    private Object[][] data;
	    private String[] title;

	    //Constructeur
	    public ZModel(Object[][] data, String[] title){
	      this.data = data;
	      this.title = title;
	    }

	    //Retourne le nombre de colonnes
	    public int getColumnCount() {
	      return this.title.length;
	    }

	    //Retourne le nombre de lignes
	    public int getRowCount() {
	      return this.data.length;
	    }

	    //Retourne la valeur à l'emplacement spécifié
	    public Object getValueAt(int row, int col) {
	      return this.data[row][col];
	    }
	    
	    /**
	    * Retourne le titre de la colonne à l'indice spécifié
	    */
	    public String getColumnName(int col) {
	      return this.title[col];
	    }
	    
	  //Retourne la classe de la donnée de la colonne
	    public Class getColumnClass(int col){
	      //On retourne le type de la cellule à la colonne demandée
	      //On se moque de la ligne puisque les types de données sont les mêmes quelle que soit la ligne
	      //On choisit donc la première ligne
	      return this.data[0][col].getClass();
	    }
	    
	    public boolean isCellEditable(int row, int col){
	    	return true;
	    }
	    
	    public void setValueAt(Object value, int row, int col){
	    	System.out.println("ZModel:"+row+":"+col+":"+value);
    		data[row][col]=value;
	    	fireTableCellUpdated(row, col);
	    }
	    
	  }
	  /*
	  public class TableComponent extends DefaultTableCellRenderer {

		  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		    if (value instanceof JButton)
		      return (JButton) value;
		    //Lignes ajoutées
		    else if(value instanceof JComboBox)
		      return (JComboBox) value;
		    else
		      return this;
		  }
		}
		*/
	  
	  public class ButtonRenderer extends JButton implements TableCellRenderer{

	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {
	      //On écrit dans le bouton ce que contient la cellule
	      setText((value != null) ? value.toString() : "");
	      //On retourne notre bouton
	      return this;
	    }
	  }
	  
	  public class ComboRenderer extends JComboBox implements TableCellRenderer{

		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {
		    	 for (String val:comboData){
		    		 this.addItem(val);
		    	 }
		    	this.setSelectedItem(value);
		    	
		    //On retourne notre bouton
		      return this;
		    }
		  }

	  public class ComboEditor extends AbstractCellEditor implements TableCellEditor, ActionListener{
		  private String value;
		  int row, column;
		  
		  //Constructeur avec une CheckBox
		    public ComboEditor() {
	    	 System.out.println("ComboEditor");
		    }  
		    
		    public Object getCellEditorValue(){
		    	return(value);
		    }
		     public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
//		    	 combo=new JComboBox(comboData);
//			     combo.addItemListener(itemListener);
		    	 System.out.println("GetCellEditor avec:"+value);
		    	 this.row=row;
		    	 this.column=column;
		    	 JComboBox combo=new JComboBox();
		    	 for (String val:comboData){
		    		 combo.addItem(val);
		    	 }
		    	 combo.setSelectedItem((String)value);
//		    	 ComboItemListener cListener=new ComboItemListener();
//		    	 cListener.setColumn(column);
//		    	 cListener.setRow(row);
//		    	 cListener.setTable(table);
//		    	 combo.addItemListener(cListener);
		    	 combo.addActionListener(this);
			    return combo;
			 }
		     
		     
		     public void actionPerformed(ActionEvent e){
		    	 JComboBox combo=(JComboBox)e.getSource();
		    	 this.value=(String)combo.getSelectedItem();
			     table.getModel().setValueAt(value, row, column);
		     }
		     
		     /*
		     class ComboItemListener implements ItemListener{
		   	      private int column, row;
			      private JTable table;
			      private String value;
			          
			      public void setColumn(int col){this.column = col;}
			      public void setRow(int row){this.row = row;}
			      public void setTable(JTable table){this.table = table;}
			      public void setValue(String value){this.value = value;}
					
					@Override
					public void itemStateChanged(ItemEvent e) {
						if(e.getStateChange()==ItemEvent.SELECTED){
							String item=e.getItem();
							System.out.println("Item selected="+item);
					    	table.getModel().setValueAt(item, row, column);
					    	
							//fireEditingStopped();
						}
					}
				};
				*/
				
		  
	  }

		public class ButtonEditor extends DefaultCellEditor {

	    protected JButton button;
	    private boolean   isPushed;
	    private ButtonListener bListener = new ButtonListener();
	     
	    //Constructeur avec une CheckBox
	    public ButtonEditor(JCheckBox checkBox) {
	      //Par défaut, ce type d'objet travaille avec un JCheckBox
	      super(checkBox);
	      //On crée à nouveau un bouton
	      button = new JButton();
	      button.setOpaque(true);
	      //On lui attribue un listener
	      button.addActionListener(bListener);
	    }

	    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) { 
	      //On précise le numéro de ligne à notre listener
	      bListener.setRow(row);
	      //Idem pour le numéro de colonne
	      bListener.setColumn(column);
	      //On passe aussi le tableau en paramètre pour des actions potentielles
	      bListener.setTable(table);
	        
	      //On réaffecte le libellé au bouton
	      button.setText( (value == null) ? "" : value.toString() );
	      //On renvoie le bouton
	      return button;
	    }
	     
	    //Notre listener pour le bouton
	    class ButtonListener implements ActionListener{        
	      private int column, row;
	      private JTable table;
	      private int nbre = 0;
	          
	      public void setColumn(int col){this.column = col;}
	      public void setRow(int row){this.row = row;}
	      public void setTable(JTable table){this.table = table;}
	          
	      public void actionPerformed(ActionEvent event) {
	        //On affiche un message, mais vous pourriez effectuer les traitements que vous voulez
	        System.out.println("coucou du bouton : " + ((JButton)event.getSource()).getText());
	        //On affecte un nouveau libellé à une autre cellule de la ligne
	        table.setValueAt("New Value " + (++nbre), this.row, (this.column -1));
	      }
	    }     
	  }

		@Override
		public void tableChanged(TableModelEvent e) {
			int row=e.getFirstRow();
			int column=e.getColumn();
			TableModel model=(TableModel)e.getSource();
			Object data=model.getValueAt(row, column);
			System.out.println("TableChanged : "+row+":"+column+":Value="+data);
		}
}
