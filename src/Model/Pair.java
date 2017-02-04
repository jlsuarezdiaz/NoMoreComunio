/*
 * Author: Juan Luis Su�rez D�az
 * July, 2016
 * No More Dropbox MSN
 */
package Model;

/**
 *
 * @author Juan Luis
 */
public class Pair<T,S>{
    /**
     * First element of the pair.
     */
    public T first;
    
    /**
     * Second element of the pair.
     */
    public S second;
    
    /**
     * Default constructor.
     */
    public Pair(){
        first = null;
        second = null;
    }
    
    /**
     * Constructor.
     * @param t First element of the pair.
     * @param s Second element of the pair.
     */
    public Pair(T t, S s){
        this.first = t;
        this.second = s;
    }
}
