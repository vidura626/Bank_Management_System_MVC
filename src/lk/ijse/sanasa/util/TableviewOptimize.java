package lk.ijse.sanasa.util;

import javafx.scene.control.TableColumn;

public class TableviewOptimize {
    public static TableColumn[] addColumns(String...columnNames){
        TableColumn[] tableColumns=new TableColumn[columnNames.length];
        for (int i = 0; i < tableColumns.length; i++) {
            tableColumns[i]=new TableColumn(columnNames[i]);
            if(i== tableColumns.length-1){
                return tableColumns;
            }
        }
        return null;
    }
}
