package com.nilhcem.fakesmtp.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

/**
 *
 * @author soldier
 */
public class TableResizeControl extends ControlAdapter {

    private final Composite comp;
    private final Table table;
    private final ColumnWidth columns[];

    public TableResizeControl(Composite comp, Table table, ColumnWidth... columns) {
        this.comp = comp;
        this.table = table;
        this.columns = columns;
    }

    @Override
    public void controlResized(ControlEvent e) {
        Rectangle area = comp.getClientArea();
        Point preferredSize = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        int width = area.width - 2 * table.getBorderWidth();
        if (preferredSize.y > area.height + table.getHeaderHeight()) {
            // Subtract the scrollbar width from the total column width
            // if a vertical scrollbar will be required
            Point vBarSize = table.getVerticalBar().getSize();
            width -= vBarSize.x;
        }
        Point oldSize = table.getSize();
        if (oldSize.x > area.width) {
            // table is getting smaller so make the columns 
            // smaller first and then resize the table to
            // match the client area width
            setWidth(width);
            table.setSize(area.width, area.height);
        } else {
            // table is getting bigger so make the table 
            // bigger first and then make the columns wider
            // to match the client area width
            table.setSize(area.width, area.height);
            setWidth(width);
        }
    }

    private void setWidth(int width) {
        double div = columns.length;
        int sum = 0;
        final ColumnWidth lastColumn = columns[columns.length - 1];
        final double scaleSum = sumScale();
        final int fixedSum = sumFixed();
        int widthForResizable = width - fixedSum;
        for (ColumnWidth column : columns) {
            int w = column.isResizable() ? (int) (column.getScale() / scaleSum * widthForResizable) : column.getFixedWidth();
            if (column.equals(lastColumn)) {
                column.setWidth(width - sum);
            } else {
                column.setWidth(w);
            }
            sum += w;
        }
    }

    private double sumScale() {
        double sum = 0;
        for (ColumnWidth c : columns) {
            if (c.isResizable()) {
                sum += c.getScale();
            }
        }
        return sum;
    }
    
    private int sumFixed(){
        int sum = 0;
        for (ColumnWidth c : columns) {
            if (!c.isResizable()) {
                sum += c.getFixedWidth();
            }
        }
        return sum;
    }
}
