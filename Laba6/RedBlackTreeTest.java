import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RedBlackTreeTest {
    private RedBlackTree tree;

    @BeforeEach
    public void setUp() {
        tree = new RedBlackTree();
    }

    @Test
    public void testInsertAndContains() {
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);

        assertTrue(tree.contains(10));
        assertTrue(tree.contains(20));
        assertTrue(tree.contains(5));
        assertFalse(tree.contains(99));
    }

    @Test
    public void testRootIsAlwaysBlack() {
        tree.insert(15);
        assertEquals(Color.BLACK, tree.getRoot().getColor());

        tree.insert(10);
        tree.insert(20);
        assertEquals(Color.BLACK, tree.getRoot().getColor());
    }

    @Test
    public void testInOrderTraversalIsSorted() {
        tree.insert(50);
        tree.insert(10);
        tree.insert(80);
        tree.insert(30);
        tree.insert(20);

        List<Integer> expected = List.of(10, 20, 30, 50, 80);
        assertEquals(expected, tree.getInOrderTraversal());
    }

    @Test
    public void testDeleteExistingElement() {
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);

        assertTrue(tree.delete(20));
        assertFalse(tree.contains(20));
        assertEquals(List.of(10, 30), tree.getInOrderTraversal());
    }

    @Test
    public void testDeleteNonExistingElement() {
        tree.insert(10);
        tree.insert(20);

        assertFalse(tree.delete(100));
        assertEquals(List.of(10, 20), tree.getInOrderTraversal());
    }

    @Test
    public void testRedBlackPropertiesAfterMultipleOperations() {
        int[] values = {10, 85, 15, 70, 20, 60, 30, 50, 65, 80, 90, 40, 5, 55};
        for (int v : values) {
            tree.insert(v);
        }

        assertEquals(Color.BLACK, tree.getRoot().getColor());
        assertTrue(validateNoRedRedParentChild(tree.getRoot(), tree.getTnull()));

        tree.delete(70);
        tree.delete(15);
        tree.delete(85);

        assertEquals(Color.BLACK, tree.getRoot().getColor());
        assertTrue(validateNoRedRedParentChild(tree.getRoot(), tree.getTnull()));
    }

    private boolean validateNoRedRedParentChild(Node node, Node tnull) {
        if (node == tnull) {
            return true;
        }
        if (node.getColor() == Color.RED) {
            if (node.left.getColor() == Color.RED || node.right.getColor() == Color.RED) {
                return false;
            }
        }
        return validateNoRedRedParentChild(node.left, tnull) && validateNoRedRedParentChild(node.right, tnull);
    }
}