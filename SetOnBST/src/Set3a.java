import java.util.Iterator;

import components.binarytree.BinaryTree;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code BinaryTree} (maintained as a binary
 * search tree) of elements with implementations of primary methods.
 *
 * @param <T>
 *            type of {@code Set} elements
 * @mathdefinitions <pre>
 * IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]
 * </pre>
 * @convention IS_BST($this.tree)
 * @correspondence this = labels($this.tree)
 *
 * @author Nicholas McCracken and Jack Mikesell
 *
 */
public class Set3a<T extends Comparable<T>> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private BinaryTree<T> tree;

    /**
     * Returns whether {@code x} is in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)
     * @ensures isInTree = (x is in labels(t))
     */
    private static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        boolean isInTree = false;
        BinaryTree<T> left = t.newInstance(), right = t.newInstance();

        /*
         * If the root node exists, then check if it is the label x.
         */
        if (t.size() > 0) {
            T root = t.disassemble(left, right);

            /*
             * Base case is when the root is the label x, otherwise make a
             * recursive call down the left or right tree depending on the value
             * of the label x relative to the root node.
             */
            if (root.equals(x)) {
                isInTree = true;
            } else if (x.compareTo(root) < 0) {
                isInTree = isInTree(left, x);
            } else {
                isInTree = isInTree(right, x);
            }

            t.assemble(root, left, right);
        }

        /*
         * If the root node did not exist, that means the boolean flag isInTree
         * is never changed, and there is no more trees to check, therefore the
         * default false value is returned. Otherwise, the value may have been
         * reset through recursion if the root node did exist in the current
         * call.
         */
        return isInTree;
    }

    /**
     * Inserts {@code x} in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be inserted
     * @aliases reference {@code x}
     * @updates t
     * @requires IS_BST(t) and x is not in labels(t)
     * @ensures IS_BST(t) and labels(t) = labels(#t) union {x}
     */
    private static <T extends Comparable<T>> void insertInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        BinaryTree<T> left = t.newInstance(), right = t.newInstance();

        /*
         * If the root node exists, continue traveling down the tree to it's
         * proper place of insertion.
         */
        if (t.size() > 0) {
            T root = t.disassemble(left, right);

            /*
             * Make a recursive call down the left or right tree depending on
             * the value of the label x relative to the root node.
             */
            if (x.compareTo(root) < 0) {
                insertInTree(left, x);
            } else {
                insertInTree(right, x);
            }

            t.assemble(root, left, right);
        } else {
            /*
             * Once the root node no longer exists, the proper insertion place
             * of the label x has been found, thus the tree can be reconstructed
             * with the newly added root node x.
             */
            t.assemble(x, left, right);
        }

    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates t
     * @requires IS_BST(t) and |t| > 0
     * @ensures <pre>
     * IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}
     * </pre>
     */
    private static <T> T removeSmallest(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";
        assert t.size() > 0 : "Violation of: |t| > 0";

        BinaryTree<T> left = t.newInstance(), right = t.newInstance();

        /*
         * Assume the smallest node is the root node.
         */
        T root = t.disassemble(left, right), smallest = root;

        /*
         * Reassign the smallest node with a recursive call if the root node has
         * a left child, meaning it cannot be the smallest since there exists a
         * tree with nodes smaller than it.
         */
        if (left.size() > 0) {
            smallest = removeSmallest(left);
            t.assemble(root, left, right);
        } else {
            /*
             * If the root node is the smallest node, it cannot have a left tree
             * since there are no nodes smaller than it. Thus, the right tree
             * can reaplce the root node to reassemble the tree without the
             * smallest node.
             */
            t.transferFrom(right);
        }

        return smallest;
    }

    /**
     * Finds label {@code x} in {@code t}, removes it from {@code t}, and
     * returns it.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove label {@code x}
     * @param x
     *            the label to be removed
     * @return the removed label
     * @updates t
     * @requires IS_BST(t) and x is in labels(t)
     * @ensures <pre>
     * IS_BST(t)  and  removeFromTree = x  and
     *  labels(t) = labels(#t) \ {x}
     * </pre>
     */
    private static <T extends Comparable<T>> T removeFromTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        assert t.size() > 0 : "Violation of: x is in labels(t)";

        BinaryTree<T> left = t.newInstance(), right = t.newInstance();

        /*
         * Assume the root node is the label x.
         */
        T root = t.disassemble(left, right), label = root;

        /*
         * If the root node is a different value than the label x, make a
         * recursive call down the left or right tree depending on the value of
         * the label x relative to the root node to reassign the label value.
         */
        if (x.compareTo(root) < 0) {
            label = removeFromTree(left, x);
            t.assemble(root, left, right);

        } else if (x.compareTo(root) > 0) {
            label = removeFromTree(right, x);
            t.assemble(root, left, right);

        } else {
            /*
             * If the root node is the label x, then it must be replaced with
             * the smallest element from it's right child, since this element is
             * smaller than everything in the right tree while remaining larger
             * than everything in the left tree if it exists. If the right tree
             * does not exist, then the root node can be replaced by it's left
             * child.
             */
            if (right.size() > 0) {
                t.assemble(removeSmallest(right), left, right);
            } else {
                t.transferFrom(left);
            }

        }

        return label;
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        // TODO - fill in body

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Set3a() {

        // TODO - fill in body

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set3a<?> : ""
                + "Violation of: source is of dynamic type Set3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set3a<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set3a<T> localSource = (Set3a<T>) source;
        this.tree = localSource.tree;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";

        // TODO - fill in body

    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";

        // TODO - fill in body

        // This line added just to make the component compilable.
        return null;
    }

    @Override
    public final T removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";

        // TODO - fill in body

        // This line added just to make the component compilable.
        return null;
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";

        // TODO - fill in body

        // This line added just to make the component compilable.
        return false;
    }

    @Override
    public final int size() {

        // TODO - fill in body

        // This line added just to make the component compilable.
        return 0;
    }

    @Override
    public final Iterator<T> iterator() {
        return this.tree.iterator();
    }

}
