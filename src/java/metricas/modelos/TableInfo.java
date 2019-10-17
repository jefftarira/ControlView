package metricas.modelos;

public class TableInfo {

  private String name;
  private int rows;

  public TableInfo() {
  }

  public TableInfo(String name, int rows) {
    this.name = name;
    this.rows = rows;
  }

  public TableInfo(String name) {
    this.name = name;
    this.rows = name.trim().toLowerCase().equals("california") ? 914684 : 24865658;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getRows() {
    return rows;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

}
