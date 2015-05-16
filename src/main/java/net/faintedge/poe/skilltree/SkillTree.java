package net.faintedge.poe.skilltree;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class SkillTree {

  private List<Node> nodes;

  private Supplier<Map<Integer, Node>> idToNode = Suppliers.memoize(
    new Supplier<Map<Integer, Node>>() {
      public Map<Integer, Node> get() {
        Map<Integer, Node> map = Maps.newHashMap();
        for (Node node : nodes) {
          map.put(node.getId(), node);
        }
        return map;
      }
    }
  );

  private Supplier<Multimap<Integer, Node>> edges = Suppliers.memoize(
    new Supplier<Multimap<Integer, Node>>() {
      public Multimap<Integer, Node> get() {
        Multimap<Integer, Node> result = HashMultimap.create();
        for (Node node : nodes) {
          for (int outId : node.getOut()) {
            Node outNode = getNode(outId);
            result.put(node.getId(), outNode);
            result.put(outNode.getId(), node);
          }
        }
        return result;
      }
    }
  );

  public List<Node> getNodes() {
    return nodes;
  }

  public Node getNode(int id) {
    Node result = idToNode.get().get(id);
    Preconditions.checkArgument(result != null);
    return result;
  }

  public Iterable<Node> getEdges(int nodeId) {
    return edges.get().get(nodeId);
  }

  public Iterable<Node> getEdges(Node node) {
    return getEdges(node.getId());
  }

  public boolean hasNode(int nodeId) {
    return idToNode.get().containsKey(nodeId);
  }
}
