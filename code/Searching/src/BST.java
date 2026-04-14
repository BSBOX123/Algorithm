import java.lang.reflect.Array;
import java.util.ArrayList;

public class BST<K extends Comparable<K>, V> {
    protected Node<K, V> root;
    public int size() {return (root != null) ? root.N : 0;}

    protected Node<K, V> treeSearch(K key) {
        Node<K, V> x = root;
        while (true) {
            int cmp = key.compareTo(x.key);
            if(cmp == 0) return x;
            else if(cmp < 0) {
                if(x.left == null) return x;
                else x = x.left;
            }
            else {
                if(x.right == null) return x;
                else x= x.right;
            }
        }
    }

    public V get(K key) {
        if(root == null) return null;
        Node<K, V> x = treeSearch(key);
        if(key.equals(x.key))
            return x.value;
        else
            return null;
    }

    public void put(K key, V val) {
        if(root == null) {root = new Node<K,V>(key, val); return;}
        Node<K, V> x = treeSearch(key);
        int cmp = key.compareTo(x.key);
        if(cmp == 0) x.value = val;
        else {
            Node<K,V> newNode = new Node<K,V>(key, val);
            if(cmp < 0) x.left = newNode;
            else x.right = newNode;
            newNode.parent = x;
            rebalanceInsert(newNode);
        }
    }

    protected void rebalanceInsert(Node<K, V> x) {
        resetSize(x.parent, 1);
    }

    protected void rebalanceDelete(Node<K, V> p, Node <K, V> deleted) {
        resetSize(p, -1);
    }

    private void resetSize(Node<K, V> x, int value) {
        for(; x != null; x = x.parent)
            x.N += value;
    }

    public Iterable<K> keys() {
        if(root == null) return null;
        ArrayList<K> keyList = new ArrayList<K>(size());
        inorder(root, keyList);
        return keyList;
    }

    private void inorder(Node<K, V> x, ArrayList<K> keyList) { //중위
        if( x != null ) {
            inorder(x.left, keyList);
            keyList.add(x.key);
            inorder(x.right, keyList);
        }
    }

    public void delete(K key) {
        if(root == null) return;
        Node<K,V> x, y, p;
        x = treeSearch(key);

        if(!key.equals(x.key))
            return;
        if(x == root || isTwoNode(x)) {
            if(isLeaf(x)) { root = null; return;}
            else if(!isTwoNode(x)) {
                root = (x.right == null) ? x.left : x.right;
                root.parent = null;
                return;
            }
            else {
                y = min(x.right);
                x.key = y.key;
                x.value = y.value;
                p = y.parent;
                relink(p, y.right, y == p.left);
                rebalanceDelete(p, y);
            }
        }
        else {
            p = x.parent;
            if(x.right == null)
                relink(p, x.left, x==p.left);
            else if(x.left == null)
                relink(p, x.right, x == p.left);
            rebalanceDelete(p, x);
        }
    }
    public boolean contains(K key) { return get(key) != null; }
    public boolean isEmpty() { return root == null; }

    protected boolean isLeaf(Node<K, V> x) {
        return x.left == null && x.right == null;
    }

    protected boolean isTwoNode(Node<K, V> x) {
        return x.left != null && x.right != null;
    }

    protected void relink(Node<K, V> parent, Node<K, V> child, boolean makeLeft) {

    }
}
