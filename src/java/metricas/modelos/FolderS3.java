package metricas.modelos;

import java.math.BigInteger;

public class FolderS3 {

  private String name;
  private int archivos;
  private long size;

  public FolderS3() {
  }

  public FolderS3(String name, int archivos, long size) {
    this.name = name;
    this.archivos = archivos;
    this.size = size;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getArchivos() {
    return archivos;
  }

  public void setArchivos(int archivos) {
    this.archivos = archivos;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

}
