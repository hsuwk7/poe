package net.faintedge.poe.build;

import net.faintedge.poe.skilltree.Node;

import java.util.List;

/**
 *
 */
public class Build {

  private final short characterType;
  private final List<Node> nodes;

  public Build(short characterType, List<Node> n) {
    this.characterType = characterType;
    this.nodes = n;
  }

  public short getCharacterType() {
    return characterType;
  }

  public List<Node> getNodes() {
    return nodes;
  }
}
