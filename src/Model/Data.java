package Model;

import java.util.Arrays;
import java.util.Vector;

public abstract class Data {
    protected String[] columnNameStrings = { "條碼", "名稱", "價格", "數量" };
    protected Vector<String> columnName = new Vector<String>(Arrays.asList(columnNameStrings));

    Data(){

    }
}
