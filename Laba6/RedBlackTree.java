import java.util.List;

public class RedBlackTree {
    private final Node TNULL;
    private Node root;

    public RedBlackTree() {
        TNULL = new Node(0);
        TNULL.color = Color.BLACK;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node getTnull() {
        return TNULL;
    }

    public void insert(int key) {
        Node node = new Node(key);
        node.parent = null;
        node.left = TNULL;
        node.right = TNULL;
        node.color = Color.RED;

        Node y = null;
        Node x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.data < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data < y.data) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.color = Color.BLACK;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        TreeFixer.fixInsert(this, node);
    }

    public boolean delete(int data) {
        Node z = searchNode(this.root, data);
        if (z == TNULL) {
            return false;
        }

        Node x, y;
        y = z;
        Color yOriginalColor = y.color;
        if (z.left == TNULL) {
            x = z.right;
            TreeFixer.rbTransplant(this, z, z.right);
        } else if (z.right == TNULL) {
            x = z.left;
            TreeFixer.rbTransplant(this, z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                TreeFixer.rbTransplant(this, y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            TreeFixer.rbTransplant(this, z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == Color.BLACK) {
            TreeFixer.fixDelete(this, x);
        }
        return true;
    }

    public Node minimum(Node node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }

    public boolean contains(int key) {
        return searchNode(this.root, key) != TNULL;
    }

    public Node searchNode(Node node, int key) {
        if (node == TNULL || key == node.data) {
            return node;
        }
        if (key < node.data) {
            return searchNode(node.left, key);
        }
        return searchNode(node.right, key);
    }

    public List<Integer> getInOrderTraversal() {
        return TreeTraverser.getInOrder(root, TNULL);
    }

    public List<Integer> getPreOrderTraversal() {
        return TreeTraverser.getPreOrder(root, TNULL);
    }

    public List<Integer> getPostOrderTraversal() {
        return TreeTraverser.getPostOrder(root, TNULL);
    }

    public void printTree() {
        TreePrinter.printTree(this);
    }

    public void clear() {
        this.root = TNULL;
    }
}