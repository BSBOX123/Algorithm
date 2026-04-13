import java.util.ArrayList;

public class SequentioalSearchST <K, V>{
    private Node<K, V> first; // establish the node
    int N; // number of node

    public V get(K key) {
        for(Node<K, V> x = first; x != null; x = x.next)
            if(key.equals(x.key))
                return x.value;
        return null;
    }

    public void put(K key, V value) {
        for(Node <K, V> x = first; x != null; x = x.next)
            if(key.equals(x.key)) {
                x.value = value;
                return;
            }
        first = new Node<K, V>(key, value, first);//key가 없다면 제일 앞에 추가
        N++;
    }

    public void delete(K key) {
        if(key.equals(first.key)) {
            first = first.next; N--;
            return;
        }

        //delete같은 경우 삭제 할 노드 전, 후의 노드를 이어야하기에 x.next != null을 사용
        for(Node<K, V> x = first; x.next != null; x = x.next) {
            if(key.equals(x.next.key)) {
                x.next = x.next.next; N--;
                return;
            }
        }
    }

    public Iterable<K> keys() {
        ArrayList<K> keyList = new ArrayList<K>(N); //동적할당 크기지정 중요
        for(Node <K,V> x = first; x != null; x = x.next)
            keyList.add(x.key);
        return keyList;
    }

}
