package lmp;

import java.util.ArrayList;

/**
 *
 * @author T-CAPS
 */
public class LMP {
    private static final int NUM_OF_NODES = 5;
    private static final int TIMES_TO_RUN = 100;

    
    public static ArrayList simulate(int nodes)
    {
        ArrayList<Node> network = new ArrayList();
        ArrayList<Integer> results = new ArrayList();
        int t = 0; // initial time t is 0
            
        for(int i = 0; i < nodes; i++)
        {
           network.add(new Node());
           network.get(i).clear();
        

        while(results.size()<nodes)
        {
            int count = 0;
            //int j = -1;

            for (int i = 0; i < nodes; i++)
            {

                if(network.get(i).transmit(t))
                {
                    ++count;

                }
            }
            //System.out.println("count is " + count);
            if(count == 1)
            {
                //System.out.println("t is " + t);
                // return t;
                results.add(t);
            }
            else if(count > 1)
            {
                for(int i = 0; i < nodes; i++)
                {

                	
                    if(network.get(i).transmit(t))
                    {

                        network.get(i).collide();
                    }
                }
            }
            
            ++t;

        }
        return results;
    }
    

    public static double avg(ArrayList arrivalTimes)
    {
        double total = 0;
        for(int i = 0;i <= arrivalTimes.size()-1; i++)
        {
            total =  total + (Integer) arrivalTimes.get(i);
        }
        return total / arrivalTimes.size(); // returns the average
    }
    
    public static void main(String[] args) {
    ;

    	System.out.format("%-3s\t%-3s\t%-3s\t%-3s\t%-3s\t\n", "#"+1,"#"+2,"#"+3,"#"+4,"#"+5);
    	
    	for(int i = 0; i < TIMES_TO_RUN; i++)
    	{
    		ArrayList oneRun = simulate(NUM_OF_NODES);
    		
    		//simulated += simulate(1);
    		//System.out.println("Attempt #" + i + " simulate is " + oneRun);
    		//avg.add(simulate(5));
    		for(int j=0;j<oneRun.size();j++)
    		{
    			//System.out.print(" Arrival "+(j+1)+", value: "+oneRun.get(j));
    			System.out.format("%-3d\t", oneRun.get(j));
    		}
    		System.out.println("");

    	}
    	System.out.println("=SUM(A2:A101)/100\t=SUM(B2:B101)/100\t=SUM(C2:C101)/100\t=SUM(D2:D101)/100\t=SUM(E2:E101)/100");

    }
}