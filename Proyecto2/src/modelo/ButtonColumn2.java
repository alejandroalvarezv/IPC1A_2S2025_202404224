package modelo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class ButtonColumn2 extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor, ActionListener {

    private final JTable table;
    private final JButton renderButton;
    private final JButton editButton;
    private String text;
    private final ActionListener actionListener;

    public ButtonColumn2(JTable table, int column, ActionListener actionListener) {
        this.table = table;
        this.actionListener = actionListener;

        renderButton = new JButton();
        renderButton.setBackground(new Color(0, 153, 255));
        renderButton.setForeground(Color.WHITE);
        renderButton.setFocusPainted(false);
        renderButton.setBorderPainted(false);

        editButton = new JButton();
        editButton.setBackground(new Color(0, 153, 255));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setBorderPainted(false);
        editButton.addActionListener(this);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        renderButton.setText((value == null) ? "" : value.toString());
        return renderButton;
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        text = (value == null) ? "" : value.toString();
        editButton.setText(text);
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        return text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
        int row = table.getSelectedRow();
        if (actionListener != null) {
            actionListener.actionPerformed(
                new ActionEvent(table, ActionEvent.ACTION_PERFORMED, String.valueOf(row))
            );
        }
    }
}