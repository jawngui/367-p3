

import java.util.Arrays;

/**
 * MinPQ represents the priority queue where the minimum value is the highest
 * priority. This priority queue is implemented using an array-based heap and
 * stores the values greater than the median.
 *
 * <p>Bugs: NA
 *
 * @author Matt P'ng
 */

public class MinPQ<E extends Comparable<E>> implements PriorityQueueADT<E>
{
    private E[] items;
    private static final int INITIAL_SIZE = 10;
    private int numItems = 0;

    public MinPQ(){
        this.items = (E[]) new Comparable[INITIAL_SIZE];
    }
    
    
    /**
     * Checks if the priority queue has any
     * elements and returns true if no elements,
     * false otherwise.
     *
     * @return true if no elements in queue, false otherwise.
     */
    public boolean isEmpty(){
    	return numItems==0;
    }
    /**
     * Adds a data item to the priority queue.
     * Reorders all the other data items in the
     * queue accordingly.
     *
     * If the size if equal to the capacity of the
     * priority queue, double the capacity and then
     * add the new item.
     *
     * @param item the item to add
     * @throws IllegalArgumentException if item is null
     */
     public void insert(E item){
    	 boolean done = false;
    	 if (item == null ) 
    		 throw new IllegalArgumentException();
    	 
    	 if (numItems + 1 == items.length) {
    		items = Arrays.copyOf(items, items.length *2);
    	 }
    	 
    	 items[numItems+1] = item;
    	 int index = numItems+1;
    	 int check = index/2;
 	
    	 while (!done){
	 		if (index == 1){
	 			done = true;
	 			break;
	 		}
	 		
	 		if (item.compareTo(items[check]) < 0){
	 			items[index] = items[check];
	 			items[check] = item;
	 			index = check;
	 			check = index/2;		
	 		}else{
	 			done = true;
	 		}	
	 	}
	 	numItems++;
     }
    /**
     * Returns the highest priority item in the priority queue.
     *
     * MinPriorityQueue => it will return the smallest valued element.
     *
     * @return the highest priority item in the priority queue.
     * @throws EmptyQueueException if priority queue is empty.
     */
    public E getMax() throws EmptyQueueException{
    	if (isEmpty()){
    		throw new EmptyQueueException();
    	}
    	return items[1];
    }

    /**
     * Returns and removes the highest priority item in the priority queue.
     * Reorders all the other data items in the
     * queue accordingly.
     *
     * MinPriorityQueue => it will return and remove the smallest valued element.
     *
     * @return the highest priority item in the priority queue.
     * @throws EmptyQueueException if priority queue is empty.
     */
    public E removeMax() throws EmptyQueueException{
    	if (isEmpty() == true){throw new EmptyQueueException();}
    	E tmp = items[1]; // tmp is removed maximum value that will be returned
    	E tmp2 = null;
    	boolean done = false; 
    	items[1] = items[numItems]; // Set root of tree equal to last item in PQ
    	
    	int index=1; // Index of position checked against its children, one being swapIndex
    	int swapIndex = 0; // Index of position of a child we will swap with 
    	
    	while(!done){
    		// If index or index*2 is greater than numItems, no more swaps are possible.
    		if (index*2 > numItems || index*2+1 > numItems){
    			done=true;
    			break;
    		}

    		// If either items[index*2] or items[index*2+1] is less than tmp, swap with the lesser priority
    		// of the two.
    		if (tmp.compareTo(items[index*2]) < 0 || tmp.compareTo(items[index*2+1]) < 0 ){
    			if (items[index*2].compareTo(items[index*2+1]) <= 0){
    				swapIndex = index*2;
    			}
    			else{
    				swapIndex = index*2+1;
    			}

    			// Perform swap
    			tmp2 = items[index];
    			items[index] = items[swapIndex];
    			items[swapIndex] = tmp2;
    			index = swapIndex;	
    		}
    		else{
    			done=true;
    			break;
    		}
    	}
    	numItems--;
    	return tmp;
    }

    /**
     * Returns the number of elements in the priority queue.
     *
     * @return number of elements in the queue.
     */
    public int size(){
		return numItems;
    }
}
