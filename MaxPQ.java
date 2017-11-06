/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Fall 2017 
// PROJECT:          p3
// FILE:             MaxPQ.java
//
// TEAM:    p3 pair 2
// Authors: Jasper Nelson, Matt P'ng
// Author1: Jasper Nelson, jnelson27@wisc.edu, jnelson27, 002
// Author2: Matt P'ng, mpng@wisc.edu, mpng, 002
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons:
// Online sources: javadocs
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.Arrays;

/**
 * MaxPQ represents the priority queue where the maximum value is the highest
 * priority. This priority queue is implemented using an array-based heap and
 * stores the values less than the median.
 *
 * <p>Bugs: NA
 *
 * @author Matt P'ng, Jasper Nelson
 */

public class MaxPQ<E extends Comparable<E>> implements PriorityQueueADT<E>
{
    private E[] items;
    private static final int INITIAL_SIZE = 10;
    private int numItems = 0;
    
    //ADD MORE DATA PRIVATE DATA FIELDS AS YOU NEED.

    public MaxPQ()
    {
        this.items = (E[]) new Comparable[INITIAL_SIZE];
        // TO-DO: Complete the constructor for any private data fields that you add.
    }
    
    /**
     * Checks if the priority queue has any
     * elements and returns true if no elements,
     * false otherwise.
     *
     * @return true if no elements in queue, false otherwise.
     */
    public boolean isEmpty()
    {
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
     public void insert(E item)
     {
    	 boolean done = false;
    	 E tmp;

    	 if (item == null ) 
    		 throw new IllegalArgumentException();
    	 /*
    	  * if numItems + 1 equals the length of the array, you must expand
    	  * the array before adding to it so there is room.
    	  */
    	 if (numItems + 1 == items.length) 
    	 {
    		items = Arrays.copyOf(items, items.length *2);
    	 }
    	 
    	 items[numItems+1] = item; //set item to the last index in the heap
    	 int index = numItems+1;   //the index of item
    	 int check = index/2;	   //the index of item's parent
    	 while (!done)
    	 {
    		 
	 		if (index == 1) //item is at the top of the heap
	 		{
	 			done = true;
	 			break;
	 		}
	 		
	 	    // if item is greater than its parent, you swap the two items 
	 		if (item.compareTo(items[check]) > 0)
	 		{
	 			items[index] = items[check];
	 			items[check] = item;
	     		index = check; //update item's position
	     		check = index/2; //update item's parent's position
	 		}
	 		else
	 		{
	 			done = true;
	 		}	
	 	}
	 	
	 	numItems++;
	 	
	 }
    /**
     * Returns the highest priority item in the priority queue.
     *
     * MinPriorityQueue => it will return the smallest valued element.
     * MaxPriorityQueue => it will return the largest valued element.
     *
     * @return the highest priority item in the priority queue.
     * @throws EmptyQueueException if priority queue is empty.
     */
     public E getMax() throws EmptyQueueException
     {
     	if (isEmpty())
     		throw new EmptyQueueException();
     	
     	return items[1];
     }

    /**
     * Returns and removes the highest priority item in the priority queue.
     * Reorders all the other data items in the
     * queue accordingly.
     *
     * MinPriorityQueue => it will return and remove the smallest valued element.
     * MaxPriorityQueue => it will return and remove the largest valued element.
     *
     * @return the highest priority item in the priority queue.
     * @throws EmptyQueueException if priority queue is empty.
     */
     public E removeMax() throws EmptyQueueException
     {
    	 if (isEmpty() == true){throw new EmptyQueueException();}
      	E tmp = items[1]; // tmp is removed maximum value that will be returned
      	E tmp2 = null;
      	boolean done = false; 
      	items[1] = items[numItems]; // Set root of tree equal to last item in PQ
      	
      	int index=1; // Index of position checked against its children, one being swapIndex
      	int swapIndex = 0; // Index of position of a child we will swap with 
      	
      	while(!done){
      	// If index or index*2 is greater than numItems, no more swaps are possible.
      		if (index*2+1 > numItems){
      			done=true;
      			break;
      		}
      		
      		// If either items[index*2] or items[index*2+1] is greater than tmp, swap with the 
      		// greater priority of the two.
      		if (tmp.compareTo(items[index*2]) > 0 || tmp.compareTo(items[index*2+1]) > 0 ){
      			if (items[index*2].compareTo(items[index*2+1]) >= 0){
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
    public int size()
    {
		return numItems;
    }
}
