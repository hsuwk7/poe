package net.faintedge.poe.build;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Shorts;
import net.faintedge.poe.skilltree.Node;
import net.faintedge.poe.skilltree.SkillTree;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class BuildParser {

  private static final Map<Integer, String> CHAR_TYPE_TO_NAME = ImmutableMap.<Integer, String>builder()
    .put(0, "SEVEN")
    .put(1, "MARAUDER")
    .put(2, "RANGER")
    .put(3, "WITCH")
    .put(4, "DUELIST")
    .put(5, "TEMPLAR")
    .put(6, "SIX")
    .build();

  public Build parseBuild(SkillTree tree, String buildString) {
    int si = buildString.indexOf("skill-tree/");
    buildString = buildString.substring((si>0)?si+11:0);
    buildString = buildString.replace("-", "+").replace("_", "/");
    byte[] bytes = BaseEncoding.base64().decode(buildString);
    short charType = bytes[4];

    List<Short> nodeIds = Lists.newArrayList();
    for (int i = 6; i < bytes.length; i += 2) {
      byte[] nodeIdBytes = new byte[] { bytes[i + 1], bytes[i] };
      short nodeId = Shorts.fromByteArray(nodeIdBytes);
      if (tree.hasNode(nodeId)) {
        nodeIds.add(nodeId);
      }
    }

    // move character node ID to beginning of nodeIds
    int charNodeIndex = -1;
    for (int i = 0; i < nodeIds.size(); i++) {
      short nodeId = nodeIds.get(i);
      if (isCharNode(tree.getNode(nodeId), charType)) {
        charNodeIndex = i;
        break;
      }
    }
    Preconditions.checkState(charNodeIndex >= 0);
    short charNodeId = nodeIds.remove(charNodeIndex);
    nodeIds.add(0, charNodeId);

    return new Build(charType, nodeIds);
  }

  public String toBuildString(SkillTree tree, Build build) {
    List<Short> nodeIds = build.getNodes();
    short charType = build.getCharacterType();

    byte[] bytes = new byte[6 + (nodeIds.size() - 1) * 2];
    byte[] two = Shorts.toByteArray((short) 2);
    bytes[0] = two[3];
    bytes[1] = two[2];
    bytes[2] = two[1];
    bytes[3] = two[0];
    bytes[4] = (byte) charType;

    int i = 6;
    for (short nodeId : nodeIds) {
      if (!isCharNode(tree.getNode(nodeId), charType)) {
        byte[] nodeIdBytes = Shorts.toByteArray(nodeId);
        bytes[i++] = nodeIdBytes[1];
        bytes[i++] = nodeIdBytes[0];
      }
    }
    return "http://www.pathofexile.com/passive-skill-tree/"+BaseEncoding.base64().encode(bytes).replace("/", "_").replace("+", "-");
  }

  private boolean isCharNode(Node node, int charType) {
    return CHAR_TYPE_TO_NAME.get(charType).equalsIgnoreCase(node.getDescription());
  }
}
