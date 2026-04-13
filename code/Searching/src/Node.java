public class Node <K, V>{
    K key; V value;
    Node<K, V> next, left, right, parent;
    int N, aux;

    public Node(K key, V value, Node<K, V> next) {
        this.key = key; this.value = value; this.next = next;
    }

    public Node(K key, V val) {
        this.key = key; this.value = val;
        this.N  = 1;
    }

    public int getAux() { return aux; }
    public void setAux(int value) { aux = value; }
}


