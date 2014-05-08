package lmp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Part C
 * Simulate for a given value of Lambda, the average number of slot times needed
 * before a successful transmission, called the contention interval.
 * @author T-CAPS
 */
public class LMP2 {
    private static final int NUM_OF_NODES = 5; // the # of stations to transmit between
    private static final int TIMES_TO_RUN = 100; // how times to simulate transmission between the # of stations
    //private static final double LAMBDA = 20.00; //MAX Lambda size
    
    private static ArrayList<Node2> nodes;
    private static ArrayList<Node2> sortedNodes;
    //private static LinkedList<Node2> collidingNodes;
        
    public static double simulate(double lambda)
    {
        nodes = new ArrayList<Node2>();
    	
        for (int i = 0; i < NUM_OF_NODES; i++)
        {  

        	nodes.add(new Node2(lambda));

        }
        
        //copy of created nodes
        sortedNodes = nodes;
        //sorts the nodes in ascending order of time
        Collections.sort(sortedNodes);

        //difference of two contention intervals
        double difference;

        double time1, time2;
                
        //lets run the simulation...
        //while there's a collision
        boolean collision = true;
        // the collision count so far...
        int collisionCount = 0;
        double totalTime = 0;
        
        while(collision)
        {

            for (int j = 0; j < sortedNodes.size(); j++) 
            {
            	time1 = sortedNodes.get(j).getTime();
                
                for (int k = j + 1; k < sortedNodes.size(); k++)
                {
                	time2 = sortedNodes.get(k).getTime();
                	difference =  time2 - time1;
                	
                	// if the nodes collide...
                	if (difference <= 1) 
                    {

                		collisionCount++;
                		//set nodes that collided
                		sortedNodes.get(j).didCollided();
                		sortedNodes.get(k).didCollided();
                    }
                	
                	

            if (collisionCount == 0)
            {
            	//totalTime = 0;
            	for (int i = 0; i < sortedNodes.size(); i++)
            	{
            		totalTime = totalTime + sortedNodes.get(i).getTime();
            	}
            	
            	break;
            }
            //otherwise....continue
            else
            {
            	//reset the collisions seen so far
            	collisionCount = 0;
            	
            	//go through sorted node and...
            	for (int i = 0; i < sortedNodes.size(); i++)
            	{
            		//go generate next contention time interval
            		//for each node that collided
            		//and unset node to not collided
            		if (sortedNodes.get(i).isCollided())
            		{
            			sortedNodes.get(i).send(lambda);
            			sortedNodes.get(i).notCollided();
            		}
            		//if the node did not collide,transmission successful, remove from
            		//sortedNode...I guess...
            		else
            		{
            			//add up total successful transmission of stations
            			totalTime = totalTime + sortedNodes.get(i).getTime();
            			sortedNodes.remove(i);
            		}
            		
            		
            	}
            	

                Collections.sort(sortedNodes);
            }
            
        }//end of while loop

         */
        return totalTime;
    }
   

    
    public static void main(String[] args) {
    	int i;
        double sum;
        double lambda;
        
        for(lambda = 2.0; lambda <= 20.00; lambda += 2.0)
        {
            sum = 0;
            for(i = 0; i < TIMES_TO_RUN; i++)
            {
                sum += simulate(lambda);
            }
            System.out.println("Lambda\t" +  lambda + "\tsum\t" +  sum/TIMES_TO_RUN);
        }
        
    }
    	
}