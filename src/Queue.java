import java.util.ArrayList;


public class Queue<T> {
    private ArrayList<T> list;
    //some basic methods
    public Queue(){
        list=new ArrayList<>();
    }
    public boolean isEmpty() {
        return list.size() == 0;
    }
    public int size() {
        return list.size();
    }
    //we are adding the item as a last element
    public void enqueue(T item) {
        list.add(item);
    }
    //we are removing the first one
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return list.remove(0);
    }
}
