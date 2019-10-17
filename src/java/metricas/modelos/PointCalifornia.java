package metricas.modelos;

import java.sql.Timestamp;

public class PointCalifornia {

  private int id;
  private String folder_name;
  private String file_name;
  private Timestamp unixtime;
  private double latitude;
  private double longitude;
  private double speed;
  private String the_geom;
  private int count;

  public PointCalifornia() {
  }

  public PointCalifornia(String file_name, int count) {
    this.file_name = file_name;
    this.count = count;
  }

  public PointCalifornia(int id, String folder_name, String file_name,
          Timestamp unixtime, double latitude, double longitude,
          double speed, String the_geom, int count) {
    this.id = id;
    this.folder_name = folder_name;
    this.file_name = file_name;
    this.unixtime = unixtime;
    this.latitude = latitude;
    this.longitude = longitude;
    this.speed = speed;
    this.the_geom = the_geom;
    this.count = count;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFolder_name() {
    return folder_name;
  }

  public void setFolder_name(String folder_name) {
    this.folder_name = folder_name;
  }

  public String getFile_name() {
    return file_name;
  }

  public void setFile_name(String file_name) {
    this.file_name = file_name;
  }

  public Timestamp getUnixtime() {
    return unixtime;
  }

  public void setUnixtime(Timestamp unixtime) {
    this.unixtime = unixtime;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public String getThe_geom() {
    return the_geom;
  }

  public void setThe_geom(String the_geom) {
    this.the_geom = the_geom;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

}
