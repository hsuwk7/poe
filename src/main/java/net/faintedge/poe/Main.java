package net.faintedge.poe;

import net.faintedge.poe.skilltree.Node;
import net.faintedge.poe.skilltree.SkillTree;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 *
 */
public class Main {

  public static final String DEFAULT_URL = "http://webcdn.pathofexile.com/public/jonathan/passive_tree.json";

  public static void main(String[] args) throws IOException {
    URL url = new URL(System.getProperty("tree.url", DEFAULT_URL));
    SkillTreeParser parser = new SkillTreeParser();
    SkillTree tree = parser.parse(url);
    List<Node> nodes = tree.getNodes();
    Node node = nodes.get(0);
    Iterable<Node> edges = tree.getEdges(nodes.get(0));
    System.out.println("Connected nodes for node " + node.getId() + ":");
    for (Node out : edges) {
      System.out.println(out.getId());
    }
  }
}
