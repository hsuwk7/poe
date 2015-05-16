package net.faintedge.poe.skilltree;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 */
public class Node {

  private int id;

  @SerializedName("dn")
  private String description;

  private String icon;

  @SerializedName("out")
  private List<Integer> out;

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public String getIcon() {
    return icon;
  }

  public List<Integer> getOut() {
    return out;
  }
}
