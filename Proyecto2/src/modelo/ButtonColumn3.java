package modelo;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener; 
import javax.swing.AbstractCellEditor; 
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel; 


public class ButtonColumn3 extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener { 
    
    private JTable table;
    private ActionListener actionListener;
    private JButton renderButton;
    private JButton editButton;
    private Object editorValue;
    private int column;
    private boolean isButtonColumnEditor; 


    public ButtonColumn3(JTable table, int column, ActionListener actionListener) {
        this.table = table;
        this.column = column;
        this.actionListener = actionListener;

        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this); 
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);

        table.addMouseListener(this); 
    }


    
    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        renderButton.setText((value == null) ? "Acción" : value.toString());
        return renderButton;
    }

    
    @Override
    public Component getTableCellEditorComponent(
        JTable table, Object value, boolean isSelected, int row, int column) {

        editButton.setText((value == null) ? "Acción" : value.toString());
        this.editorValue = value;
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        return editorValue;
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        int editingRow = table.getEditingRow();
            fireEditingStopped(); 


            
        int row = (editingRow != -1) ? table.convertRowIndexToModel(editingRow) : -1;

        if (row != -1) {
            ActionEvent event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, String.valueOf(row));
            actionListener.actionPerformed(event);
        }
    }
    

    @Override
    public void mousePressed(MouseEvent e) {
        if (table.isEditing()) {
            if (table.getCellEditor() == this) {
                isButtonColumnEditor = true;
            }
        } else {
            int row = table.rowAtPoint(e.getPoint());
            int column = table.columnAtPoint(e.getPoint());

            if (row != -1 && column == this.column) {
                table.editCellAt(row, column);
                
                isButtonColumnEditor = true;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isButtonColumnEditor) {
            isButtonColumnEditor = false;
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}