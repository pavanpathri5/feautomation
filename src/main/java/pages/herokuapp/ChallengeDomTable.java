package pages.herokuapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.SeleniumUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChallengeDomTable {
    private static final By TABLE = By.xpath("//table");
    private static final By HEADERS = By.cssSelector("thead th");
    private static final By ROWS = By.cssSelector("tbody tr");

    /**
     * Extracts the table data as a List of HashMaps (column name -> cell value)
     */
    public List<HashMap<String, String>> getTableData() {
        List<HashMap<String, String>> tableData = new ArrayList<>();
        WebElement table = SeleniumUtils.find(TABLE);
        List<WebElement> headers = table.findElements(HEADERS);
        List<String> headerNames = headers.stream().map(WebElement::getText).toList();
        List<WebElement> rows = table.findElements(ROWS);
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            HashMap<String, String> rowData = new HashMap<>();
            for (int i = 0; i < headerNames.size() && i < cells.size(); i++) {
                rowData.put(headerNames.get(i), cells.get(i).getText());
            }
            tableData.add(rowData);
        }
        return tableData;
    }
}
