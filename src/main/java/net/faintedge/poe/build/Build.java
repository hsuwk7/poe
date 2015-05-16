package net.faintedge.poe.build;

import java.util.List;

/**
 *
 */
public class Build {

  private final short characterType;
  private final List<Short> nodes;

  public Build(short characterType, List<Short> nodes) {
    this.characterType = characterType;
    this.nodes = nodes;
  }

  public short getCharacterType() {
    return characterType;
  }

  public List<Short> getNodes() {
    return nodes;
  }
}
