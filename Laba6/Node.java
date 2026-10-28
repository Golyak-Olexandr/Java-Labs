public class Node {
    int data;
    Node parent;
    Node left;
    Node right;
    Color color;

    public Node(int data) {
        this.data = data;
        this.color = Color.RED;
    }

    public int getData() {
        return data;
    }

    public Color getColor() {
        return color;
    }
}