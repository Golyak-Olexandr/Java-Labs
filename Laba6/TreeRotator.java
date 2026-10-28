public class TreeRotator {
    public static void leftRotate(RedBlackTree tree, Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != tree.getTnull()) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            tree.setRoot(y);
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    public static void rightRotate(RedBlackTree tree, Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != tree.getTnull()) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            tree.setRoot(y);
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }
}