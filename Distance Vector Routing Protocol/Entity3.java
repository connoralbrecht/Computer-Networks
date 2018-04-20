public class Entity3 extends Entity
{
	private int nodenum = 3; //only thing that is changed between entities
	private int[] mincostTemp = new int [NetworkSimulator.NUMENTITIES];
    // Perform any necessary initialization in the constructor
    public Entity3()
    {
		mincostTemp[nodenum] = 0;
		//int min = 0;
		for(int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
		{
			for(int j = 0; j < NetworkSimulator.NUMENTITIES; j++)
			{
				if(i == j){
					this.distanceTable[i][j] = NetworkSimulator.cost[nodenum][j];
				}
				else if(i == nodenum || j == nodenum){
					this.distanceTable[i][j] = 0;
				}
				else if(NetworkSimulator.cost[nodenum][j] == 999){ 
					this.distanceTable[i][j] = 999;
				}
				else{
					this.distanceTable[i][j] = 1000;
				}
			}
		}
		//public Packet(int s, int d, int[] mc)
		//public static void toLayer2(Packet p)
		//NetworkSimulator.toLayer2(nodenum, i, mincostTemp)

		for(int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
		{
			if(i == nodenum){
				continue;
			}
			int min = 1001;
			for(int j = 0; j < NetworkSimulator.NUMENTITIES; j++)
			{
				
				if(j == nodenum){
					continue;
				}
				if(distanceTable[i][j] < 999){
					min = NetworkSimulator.cost[nodenum][i];
					mincostTemp[i] = min;
				}
				else if(min > 1000){
					mincostTemp[i] = 999;
				}
				
			}

		}
		
		for(int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
		{
			if(i == nodenum){
				continue;
			}
			if(mincostTemp[i] == 999){
				continue;
			}
			NetworkSimulator.toLayer2(new Packet(nodenum, i, mincostTemp));
		}

		printDT();
    }
	
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {
		int updated = 0;
        int dest = p.getDest(); //nodenum
        int source = p.getSource();

		//int[] mincostTemp;
		int min = 0;
		for(int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
		{
			if(i == nodenum || i == source){
				continue;
			}
			if((mincostTemp[source] + p.getMincost(i)) < distanceTable[i][source])
			{
				int temp = (mincostTemp[source] + p.getMincost(i));
				this.distanceTable[i][source] = temp;
				if(mincostTemp[i] > temp){
						mincostTemp[i] = temp;
						
						//NetworkSimulator.toLayer2(new Packet(source, i, mincostTemp));
				}
				updated = 1;
			}
		}
		if(updated == 1)
		{			
			for(int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
			{
				if(i == nodenum){
					continue;
				}
				if(mincostTemp[i] == 999){
					continue;
				}
				NetworkSimulator.toLayer2(new Packet(nodenum, i, mincostTemp));
			}
			printDT();
		}
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
    {
        System.out.println("         via");
        System.out.println(" D3 |   0   2");
        System.out.println("----+--------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            if (i == 3)
            {
                continue;
            }
            
            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j += 2)
            {
               
                if (distanceTable[i][j] < 10)
                {    
                    System.out.print("   ");
                }
                else if (distanceTable[i][j] < 100)
                {
                    System.out.print("  ");
                }
                else 
                {
                    System.out.print(" ");
                }
                
                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }
}
