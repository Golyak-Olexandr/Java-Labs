public class TreePrinter {
    public static void printTree(RedBlackTree tree) {
        if (tree.getRoot() == tree.getTnull()) {
            System.out.println("Дерево порожнє.");
            return;
        }
        printHelper(tree.getRoot(), tree.getTnull(), "", true);
    }

    private static void printHelper(Node root, Node tnull, String indent, boolean last) {
        if (root != tnull) {
            System.out.print(indent);
            if (last) {
                System.out.print("└── ");
                indent += "    ";
            } else {
                System.out.print("├── ");
                indent += "│   ";
            }
            String colorStr = root.getColor() == Color.RED ? "RED" : "BLACK";
            System.out.println(root.getData() + " (" + colorStr + ")");
            printHelper(root.left, tnull, indent, false);
            printHelper(root.right, tnull, indent, true);
        }
    }
}