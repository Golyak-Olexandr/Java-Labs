import java.util.ArrayList;
import java.util.List;

public class TreeTraverser {
    public static List<Integer> getInOrder(Node root, Node tnull) {
        List<Integer> list = new ArrayList<>();
        inOrderHelper(root, tnull, list);
        return list;
    }

    private static void inOrderHelper(Node node, Node tnull, List<Integer> list) {
        if (node != tnull) {
            inOrderHelper(node.left, tnull, list);
            list.add(node.data);
            inOrderHelper(node.right, tnull, list);
        }
    }

    public static List<Integer> getPreOrder(Node root, Node tnull) {
        List<Integer> list = new ArrayList<>();
        preOrderHelper(root, tnull, list);
        return list;
    }

    private static void preOrderHelper(Node node, Node tnull, List<Integer> list) {
        if (node != tnull) {
            list.add(node.data);
            preOrderHelper(node.left, tnull, list);
            preOrderHelper(node.right, tnull, list);
        }
    }

    public static List<Integer> getPostOrder(Node root, Node tnull) {
        List<Integer> list = new ArrayList<>();
        postOrderHelper(root, tnull, list);
        return list;
    }

    private static void postOrderHelper(Node node, Node tnull, List<Integer> list) {
        if (node != tnull) {
            postOrderHelper(node.left, tnull, list);
            postOrderHelper(node.right, tnull, list);
            list.add(node.data);
        }
    }
}