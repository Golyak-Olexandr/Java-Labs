public class TreeFixer {
    public static void fixInsert(RedBlackTree tree, Node k) {
        Node u;
        while (k.parent != null && k.parent.color == Color.RED) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u.color == Color.RED) {
                    u.color = Color.BLACK;
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        TreeRotator.rightRotate(tree, k);
                    }
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    TreeRotator.leftRotate(tree, k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;
                if (u.color == Color.RED) {
                    u.color = Color.BLACK;
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        TreeRotator.leftRotate(tree, k);
                    }
                    k.parent.color = Color.BLACK;
                    k.parent.parent.color = Color.RED;
                    TreeRotator.rightRotate(tree, k.parent.parent);
                }
            }
            if (k == tree.getRoot()) {
                break;
            }
        }
        tree.getRoot().color = Color.BLACK;
    }

    public static void fixDelete(RedBlackTree tree, Node x) {
        Node s;
        while (x != tree.getRoot() && x.color == Color.BLACK) {
            if (x == x.parent.left) {
                s = x.parent.right;
                if (s.color == Color.RED) {
                    s.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    TreeRotator.leftRotate(tree, x.parent);
                    s = x.parent.right;
                }

                if (s.left.color == Color.BLACK && s.right.color == Color.BLACK) {
                    s.color = Color.RED;
                    x = x.parent;
                } else {
                    if (s.right.color == Color.BLACK) {
                        s.left.color = Color.BLACK;
                        s.color = Color.RED;
                        TreeRotator.rightRotate(tree, s);
                        s = x.parent.right;
                    }

                    s.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    s.right.color = Color.BLACK;
                    TreeRotator.leftRotate(tree, x.parent);
                    x = tree.getRoot();
                }
            } else {
                s = x.parent.left;
                if (s.color == Color.RED) {
                    s.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    TreeRotator.rightRotate(tree, x.parent);
                    s = x.parent.left;
                }

                if (s.right.color == Color.BLACK && s.left.color == Color.BLACK) {
                    s.color = Color.RED;
                    x = x.parent;
                } else {
                    if (s.left.color == Color.BLACK) {
                        s.right.color = Color.BLACK;
                        s.color = Color.RED;
                        TreeRotator.leftRotate(tree, s);
                        s = x.parent.left;
                    }

                    s.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    s.left.color = Color.BLACK;
                    TreeRotator.rightRotate(tree, x.parent);
                    x = tree.getRoot();
                }
            }
        }
        x.color = Color.BLACK;
    }

    public static void rbTransplant(RedBlackTree tree, Node u, Node v) {
        if (u.parent == null) {
            tree.setRoot(v);
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }
}