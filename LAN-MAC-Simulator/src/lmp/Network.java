package lmp;

import java.util.ArrayList;
import java.util.Collections;

public class Network 
{
    private static ArrayList<Double> xVal = new ArrayList();
    private static Node node;
    private static int numOfStations = 20;
    private static int MAX_TIME_SLOT = 20000;
    private static Double min = 0.0;
    private static Packet minPacket, collidedPacket;
    
    private static int numOfSuccessfulPackets;
    private static int timeSuccessful;
    
    private static ArrayList<Node> nodes = new ArrayList();
    private static ArrayList<Packet> packets = new ArrayList();
    private static ArrayList<Packet> collidedPackets = new ArrayList();
    private static Double currentTime = 0.0;

    private static int indexOfMin = 0;

    public static void generatePackets(double alambda) 
    {
        for (int i = 0; i < numOfStations; i++) 
        {
        	// creates new stations of type node
            node = new Node(i, alambda);
            // add stations to arraylist
            nodes.add(node);
       n
            nodes.get(i).send(alambda);
        }
    }


    public static void simulate(double lambda) 
    {
    	// We'll go ahead and initialize some variables
        //int collisionCount = 0;
        numOfSuccessfulPackets = 0;
        timeSuccessful = 0;
        //Packet currentPacket, packetToBeTransmitted, successfulPacket;
        
        /* clear this list of nodes breh
       	   need to clear because the arraylists are static */
        nodes.clear();
        packets.clear();
        
        /* we'll go ahead and make a temporary variable to hold the
           difference of the contention time of two stations to see
           if they collide or not between
           t-1 & t+1 */
        double difference;
        
        /* creating individual stations and storing them into
           a arraylist with each of their own contention time... */
        //for (int i = 0; i < numOfStations; i++) 
        //{
            //node = new Node(i, lambda);
            //nodes.add(node); //Add nodes into the ArrayList
            //nodes.get(i).send(lambda); //generate contention time
        
            generatePackets(lambda); //<--takes care of what we WERE doing with the loop before.
            						 // And lets go ahead and generate these packets for stations.
            
        //}

        packets = getSortedPacketList(); //get all of the packets that are waiting to be transmitted sorted  
        minPacket = packets.get(0); // the packet with the minimum contention time, the first element of the sorted list
        
        /* Let's go ahead and start simulating!
         * When the current time reaches the max time slots, STOP!
         */
        while (currentTime <= MAX_TIME_SLOT) 
        {
            boolean noCollision = true; // No collisions so far because we're just started...
            
            //Go through loop and check whether there is collision at the particular j
            for (int j = 0; j < numOfStations; j++) 
            {
            	// storing the difference
                difference = packets.get(j).getContentionInterval() - minPacket.getContentionInterval();
                
                // checking if stations collides, if it does then...
                if(difference <= 1 && j != minPacket.getStationName())
                {
                    noCollision = false;
                    collidedPacket = packets.get(j);
                    break;
            }// end of for loop
            
            // if no stations collide with one another....
            if (noCollision)
            {
                currentTime += 8;
                numOfSuccessfulPackets++;
                packets.get(0).setStationTime(currentTime);
            } 

            else 
            {
                currentTime++;
                
                minPacket.incrementCollision();
                collidedPacket.incrementCollision();
                
                // apply backoff to collided packets
                nodes.get(minPacket.getStationName()).backoff(minPacket.getCollisionCount(), minPacket.getContentionInterval()); //backoff
                nodes.get(collidedPacket.getStationName()).backoff(collidedPacket.getCollisionCount(), collidedPacket.getContentionInterval()); //backoff
                
                //packets.set(minPacket.getStationName(), minPacket);
                //packets.set(collidedPacket.getStationName(),collidedPacket);
                
                packets = getSortedPacketList(); //get all of the packets that are waiting to be transmitted sorted
                minPacket = packets.get(0); //min
                //minPacket = getMinPacket(packets);
                
                
                /**
                 * Need to add waiting packets into list....Need to generate new packets per station 
                 */
            }
            
            generatePackets(lambda); //generates new packets after checking and dealing with collisions...need to continue doing this until <= 20,000
        }
    }
    
    
    
    /**
     * Sorts and returns the packets in order
     * @return the sorted list of packets from ascending order
     */
    public static ArrayList<Packet> getSortedPacketList()
    {
        ArrayList<Packet> sortedList = new ArrayList();
        
        for(Node n: nodes)
        {
            sortedList.add(n.getNextPacketToBeTransmitted());
        }
        
        Collections.sort(sortedList);
        return sortedList;
    }
    
    

        
    

    public static void main(String[] args) 
    {


        Double sum = 0.0;
        Double lambda = 0.0;
        double throughput = 0.0;
        ArrayList<Packet> resultPackets = new ArrayList();
        
        for (lambda = 20.0; lambda >= 4.0; lambda -= 2.0) 
        {
            for (int i = 0; i < MAX_TIME_SLOT; i++) 
            {
                simulate(lambda);
                //System.out.println(" " + resultPackets.get(i).toString());
                
            }
            
                System.out.println("lambda " + lambda + " current time is " + currentTime);
                

            throughput = (numOfSuccessfulPackets * 8 * 512) / (MAX_TIME_SLOT * 51.2 * Math.pow(10, -6));
            
            sum = 0.0;
        }
        
    }
}