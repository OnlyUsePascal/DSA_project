import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {
    int rows;
    int cols;
    String[] map;
    int robotRow;
    int robotCol;
    int steps;

    public Maze() throws FileNotFoundException{
        robotRow = 1;
        robotCol = 1;
        File file = new File("src/Maze.txt");
        Scanner reader = new Scanner(file);
        String line;

        rows = 0;
        while (reader.hasNextLine()){
            line = reader.nextLine();
            cols = line.length();
            rows++;
        }

        map = new String[rows];
        reader = new Scanner(file);
        int i = 0;
        while (reader.hasNextLine()){
            line = reader.nextLine();
            map[i] = line;
            i++;
        }
    }

    public String go(String direction) {
//        System.out.println(direction + " " + (steps + 1) + "* " + robotRow + " " + robotCol);

        if (!direction.equals("UP") &&
                !direction.equals("DOWN") &&
                !direction.equals("LEFT") &&
                !direction.equals("RIGHT")) {
            // invalid direction
            steps++;
            return "false";
        }
        int currentRow = robotRow;
        int currentCol = robotCol;
        if (direction.equals("UP")) {
            currentRow--;
        } else if (direction.equals("DOWN")) {
            currentRow++;
        } else if (direction.equals("LEFT")) {
            currentCol--;
        } else {
            currentCol++;
        }

        // check the next position
        if (map[currentRow].charAt(currentCol) == 'X') {
            // Exit gate
            steps++;
            System.out.println("Steps to reach the Exit gate " + steps);
            return "win";
        } else if (map[currentRow].charAt(currentCol) == '.') {
            // Wall
            steps++;
            return "false";
        } else {
            // Space => update robot location
            steps++;
            robotRow = currentRow;
            robotCol = currentCol;
            return "true";
        }
    }

    //testing site


    public static void main(String[] args) throws FileNotFoundException {
        //timing for performance measuring
        long startTime = System.currentTimeMillis();

        (new Robot()).navigate();

        long endTime = System.currentTimeMillis();
        System.out.println("\n=== Measurement ===");
        System.out.println("Program running time: " + (endTime - startTime) + " ms");
    }
}

