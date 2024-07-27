package dev.neeraj;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Initiating Conway's Game of Life Simulation....\n\n");
        int sleepTime = 1000/2;
        int dimension = 20;
        if(args.length > 0){
            try{
                sleepTime = Integer.parseInt(args[0]);
                dimension = Integer.parseInt(args[1]);
            } catch (NumberFormatException e){
                System.out.println("Invalid arguments. Using default values.");
            }
        }

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CellularGrid cellularGrid = new CellularGrid(dimension);

        int i=0;
        while(i<1000){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Generation: " + i);
            cellularGrid.printGrid();
            cellularGrid.nextGeneration();
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }

    }
}