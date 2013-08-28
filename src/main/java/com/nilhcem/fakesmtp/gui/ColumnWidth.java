package com.nilhcem.fakesmtp.gui;

import java.util.Objects;
import org.eclipse.swt.widgets.TableColumn;

/**
 *
 * @author soldier
 */
public class ColumnWidth {

    private final TableColumn column;
    private final Integer fixedWidth;
    private final Double scale;
    private final int minWidth;

    public ColumnWidth(TableColumn column, double width) {
        this.column = column;
        this.scale = width;
        this.fixedWidth = null;
        this.minWidth = 30;
    }

    public ColumnWidth(TableColumn column, int fixedWidth) {
        this.column = column;
        this.fixedWidth = fixedWidth;
        this.scale = null;
        this.minWidth = 30;
    }

    public void setWidth(int w){
        column.setWidth(Math.max(w, minWidth));
    }

    public int getFixedWidth() {
        return fixedWidth;
    }

    public double getScale() {
        return scale;
    }

    public boolean isResizable() {
        return scale != null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.column);
        hash = 29 * hash + Objects.hashCode(this.fixedWidth);
        hash = 29 * hash + Objects.hashCode(this.scale);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ColumnWidth other = (ColumnWidth) obj;
        if (!Objects.equals(this.column, other.column)) {
            return false;
        }
        if (!Objects.equals(this.fixedWidth, other.fixedWidth)) {
            return false;
        }
        if (!Objects.equals(this.scale, other.scale)) {
            return false;
        }
        return true;
    }
}
