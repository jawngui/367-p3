/////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Fall 2017 
// PROJECT:          p3
// FILE:             MedianStream.java
//
// TEAM:    p3 pair 2
// Authors: Jasper Nelson, Matt P'ng
// Author1: Jasper Nelson, jnelson27@wisc.edu, jnelson27, 002
// Author2: Matt P'ng, mpng@wisc.edu, mpng, 002
// ---------------- OTHER ASSISTANCE CREDITS 
// Persons:
// Online sources: javadocs
//////////////////////////// 80 columns wide //////////////////////////////////
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The MedianStream is an object that includes instances of the two heaps and also
 * tracks the median of each instance of Median Stream.  In addition, MedianStream
 * includes code to handle command line input of files, and then find the median of each 
 * individual file, and finally, write each median to a new final.  Additionally, it has 
 * code to process input from the console and calculates a running median of all user inputs.  
 * 
 * @author Jasper Nelson, Matt P'ng
 *
 */



public class MedianStream
{

    private static final String PROMPT_NEXT_VALUE = "Enter next value or q to quit: ";
    private static final String MEDIAN = "Current median: ";
    private static final String EXIT_MESSAGE = "That wasn't a double or 'q'. Goodbye!";
    private static final String FNF_MESSAGE = " not found.";

    /**
     * Use this format to ensure that double values are formatted correctly.
     * Double doubleValue = 1412.1221132
     * System.out.printf(DOUBLE_FORMAT, doubleValue);
     */
    private static final String DOUBLE_FORMAT = "%8.3f\n";

    private Double currentMedian;
    private MaxPQ<Double> maxHeap;
    private MinPQ<Double> minHeap;

    /**
     * Override Default Constructor
     *
     *  Initialize the currentMedian = 0.0
     *  Create a new MaxPQ and MinPQ.
     */
    public MedianStream()
    {
        this.currentMedian = 0.0;
        this.maxHeap = new MaxPQ<Double>();
        this.minHeap = new MinPQ<Double>();
    }

    /**
     * This method is called if the user passes NO command line arguments.
     * The method prompts the user for a double value on each iteration.
     *
     * If the input received is a double, the current median is updated.
     * After each iteration, print the new current median using MEDIAN string
     * as declared and initialized with the data members above.
     *
     * If the input is the character 'q', return from the method.
     *
     * If the input is anything else, then you print an error using EXIT_MESSAGE
     * string as declared and initialized with the data members above and
     * then return from the method.
     *
     * For the purposes of calculating the median, every input received since
     * the beginning of the method execution is part of the same stream.
     */
    private static void runInteractiveMode()
    {
       Scanner stdin = new Scanner(System.in);
       MedianStream temps = new MedianStream();
       boolean quit = false;
       while(!quit)
       {
    	   System.out.print(PROMPT_NEXT_VALUE);
    	   String nonDub;
    	   Double input;
    	   if(stdin.hasNextDouble()) //If format is correct, this block is taken
    	   {
    		   input = stdin.nextDouble(); 
    		   temps.currentMedian = temps.getMedian(input); //update PQs and Median
    		   System.out.println(MEDIAN + temps.currentMedian); //print output
    	   }
    	   else if(stdin.hasNext()) //taken if input format is incorrect
    	   {
    		   nonDub = stdin.next().trim();
    		   if(nonDub.equalsIgnoreCase("q")) //quit without error
    		   {
    			   quit = true;
    		   }
    		   else //quit with error message displayed
    		   {
    			   System.out.println(EXIT_MESSAGE);
    			   break;
    		   }
    	   }
       }
       
       stdin.close();
    }

    /**
     * This method is called if the user passes command line arguments.
     * The method is called once for every filename passed by the user.
     *
     * The method reads values from the given file and writes the new median
     * after reading each new double value to the output file.
     *
     * The name of the output file follows a format specified in the write-up.
     *
     * If the input file contains a non-double value, the program SHOULD NOT
     * throw an exception, instead it should just read the values up to that
     * point, write medians after each value up to that point and then
     * return from the method.
     *
     * If a FileNotFoundException occurs, just print the string FNF_MESSAGE
     * as declared and initialized with the data members above.
     */
    private static void findMedianForFile(String filename)
    {
    	try
		{
    		String[] parse = filename.split("\\."); //get filename w/o extension
    		MedianStream temps = new MedianStream();
			File dataSet = new File(filename);
			File output = new File(parse[0] + "_out.txt"); //creates output file with
														   //correctly formatted name
			Scanner read = new Scanner(dataSet);		   
			PrintWriter toFile = new PrintWriter(output);
			while(read.hasNext())
			{
				if(read.hasNextDouble()) //if input format is correct take this block
				{
					Double toAdd = read.nextDouble();
					temps.currentMedian = temps.getMedian(toAdd);
					toFile.printf(DOUBLE_FORMAT, temps.currentMedian);
				}
				else //else, close scannner and PW and quit
				{
					read.close();
					toFile.close();
					return;
				}
			}
			read.close();
			toFile.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(FNF_MESSAGE);
		}

    }

    /**
     * YOU ARE NOT COMPULSORILY REQUIRED TO IMPLEMENT THIS METHOD.
     *
     * That said, we found it useful to implement.
     *
     * Adds the new temperature reading to the corresponding
     * maxPQ or minPQ depending upon the current state.
     *
     * Then calculates and returns the updated median.
     *
     * @param newReading - the new reading to be added.
     * @return the updated median.
     */
    private Double getMedian(Double newReading)
    {
    	if(newReading >= currentMedian)
    	{
    		if(minHeap.size() == maxHeap.size())
    		{
    			minHeap.insert(newReading);
    			return minHeap.getMax();
    		}
    		else if(minHeap.size() == maxHeap.size() - 1)
    		{
    			minHeap.insert(newReading);
    			Double maxP = maxHeap.getMax();
    			Double minP = minHeap.getMax();
    			Double avg = (maxP + minP)/2;
    			return avg;
    		}
    		else if(minHeap.size() == maxHeap.size() + 1)
    		{
    			Double str = minHeap.removeMax();
    			maxHeap.insert(str);
    			minHeap.insert(newReading);
    			Double maxP = maxHeap.getMax();
    			Double minP = minHeap.getMax();
    			Double avg = (maxP + minP)/2;
    			return avg;
    		}
    	}
    	else if(newReading < currentMedian)
    	{
    		if(minHeap.size() == maxHeap.size())
    		{
    			maxHeap.insert(newReading);
    			return maxHeap.getMax();
    		}
    		else if(maxHeap.size() == minHeap.size() - 1)
    		{
    			maxHeap.insert(newReading);
    			Double maxP = maxHeap.getMax();
    			Double minP = minHeap.getMax();
    			Double avg = (maxP + minP)/2;
    			return avg;
    		}
    		else if(maxHeap.size() == minHeap.size() + 1)
    		{
    			Double str = maxHeap.removeMax();
    			minHeap.insert(str);
    			maxHeap.insert(newReading);
    			Double maxP = maxHeap.getMax();
    			Double minP = minHeap.getMax();
    			Double avg = (maxP + minP)/2;
    			return avg;
    		}
    	}
    	
    	return null;    	
    }

    // DO NOT EDIT THE main METHOD.
    public static void main(String[] args)
    {
        // Check if files have been passed in the command line.
        // If no files are passed, run an infinite interactive loop taking a double
        // input each time until "q" is entered by the user.
        // After each iteration of the loop, update and display the new median.
        if ( args.length == 0 )
        {
            runInteractiveMode();
        }

        // If files are passed in the command line, open each file.
        // For each file, iterate over all the double values in the file.
        // After reading each new double value, write the new median to the
        // corresponding output file whose name will be inputFilename_out.txt
        // Stop reading the file at the moment a non-double value is detected.
        else
        {
            for ( int i=0 ; i < args.length ; i++ )
            {
                findMedianForFile(args[i]);
            }
        }
    }
}
