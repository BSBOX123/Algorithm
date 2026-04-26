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
                relink(p, x.left, x == p.left);
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
        if(child != null) child.parent = parent;
        if(makeLeft) parent.left = child;
        else parent.right = child;
    }

    protected Node<K, V> min(Node<K, V> x) {while (x.left != null) x = x.left; return x;}

    public K min() {
        if(root == null) return null;
        Node<K, V> x = root;
        while (x.left != null)
            x = x.left;
        return x.key;
    }

    public K max() {
        if(root == null) return null;
        Node<K, V> x = root;
        while (x.right != null)
            x = x.right;
        return x.key;
    }

    public K flood(K key) {
        if(root == null || key == null) return null;
        Node<K,V> x = flood(root, key);
        if( x == null) return null;
        else return x.key;
    }

    private Node<K, V> flood(Node<K, V> x, K key) {
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0) return x;
        if(cmp < 0) return flood(x.left, key);
        Node<K,V> t = flood(x.right, key);
        if( t != null ) return t;
        else return x;
    }

    public int rank(K key) {
        if(root == null || key == null) return 0;
        Node<K, V> x = root;
        int num = 0;

        while(x != null) {
            int cmp = key.compareTo(x.key);
            if(cmp < 0) x = x.left;
            else if(cmp > 0) {
                num += 1 + size(x.left);
                x = x.right;
            }
            else {
                num += size(x.left); break;
            }
        }
        return num;
    }

    private int size(Node<K,V> x) {return (x != null) ? x.N : 0;}

    public K select(int rank) {
        if( root == null || rank < 0 || rank >= size())
            return null;
        Node<K, V> x = root;
        while (true) {
            int t = size(x.left);
            if (rank < t)
                x = x.left;
            else if( rank > t) {
                rank = rank - t - 1;
                x = x.right;
            }
            else
                return x.key;
        }
    }
}
