package Model;

import org.jfree.data.category.DefaultCategoryDataset;

public class SaleHistoryCategoryDataset extends DefaultCategoryDataset {

    private static final long serialVersionUID = 1L;

    public SaleHistoryCategoryDataset() {
        super();
    }

    public void refreshDataSet(){
        super.fireDatasetChanged();
    }
}
