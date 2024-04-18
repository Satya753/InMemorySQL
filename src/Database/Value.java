package Database;

public class Value <T>{
    public T item;

    public Value(T item) {
        this.item = item;
    }

    public void setItem(T value){
        this.item = value;
    }
    public T getItem(){
        return this.item;
    }

}
