

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class Maze {
    int rows;
    int cols;
    String[] map;
    int robotRow;
    int robotCol;
    int steps;

    public Maze() throws FileNotFoundException {
        robotRow = 4;
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
//        System.out.println(direction + " " + (steps + 1));

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

        long startTime = System.currentTimeMillis();

        (new Robot()).navigate();

        long stopTime = System.currentTimeMillis();
        System.out.println("Runtime: " + (stopTime - startTime) + "ms");
    }
}


class Robot {

    final int ROW_MAX = 2000 + 10;
    final int COL_MAX = 2000 + 10;
    boolean visited[][] = new boolean[ROW_MAX][COL_MAX];
    int[] dx = {-1, 0, 1, 0};
    int[] dy = {0, 1, 0, -1};
    String[] directions = {"UP", "RIGHT", "DOWN", "LEFT"};
    String[] directions_traverse = {"DOWN", "LEFT", "UP", "RIGHT"};

    String result = "";
    Maze maze = new Maze();

    int currentRowRobot = 1005;
    int currentColRobot = 1005;

    Stack<Direction> paths = new Stack<>();
    Stack<Coordinate> coords = new Stack();

    Robot() throws FileNotFoundException {
    }

    public void navigate() {

        visited[currentRowRobot][currentColRobot] = true;

        coords.add(new Coordinate(currentRowRobot, currentColRobot));


        while (!coords.empty()) {

            if (result.equals("win")) {
                break;
            }
            Coordinate cur = coords.peek();
//            System.out.println(cur.Row + " " + cur.Col);
//            System.out.println(maze.robotRow + " " + maze.robotCol);
            boolean valid = false;
            for (int i = 0; i < 4; i++) {
                int new_row = cur.Row + dx[i];
                int new_col = cur.Col + dy[i];
                if (visited[new_row][new_col] == false) {
                    visited[new_row][new_col] = true;
                    System.out.println("traverse " + directions[i]);

                    result = maze.go(directions[i]);

                    if (result.equals("true")) {
                        paths.push(new Direction(directions[i], directions_traverse[i]));
                        coords.add(new Coordinate(new_row, new_col));
                        valid = true;
                        break;
                    }

                    if (result.equals("win")) {
                        paths.push(new Direction(directions[i], directions_traverse[i]));
                        break;
                    }

                }
            }
            if (result.equals("win")) {
                break;
            }
            if (valid == false) {
                Direction direct = paths.peek();
                paths.pop();
                coords.pop();
                System.out.println("reverse " + direct.reverse);
                result = maze.go(direct.reverse);
            }

        }

        //print out path
        ArrayList<String> valid_path = new ArrayList<>();
        while (!paths.empty()) {
            valid_path.add(paths.peek().traverse);
            paths.pop();
        }
        Collections.reverse(valid_path);
        System.out.println("====PATHS====");
        for (String str : valid_path) {
            System.out.println(str);
        }
    }

}

class Direction{
    String traverse;
    String reverse;

    public Direction(String traverse, String reverse) {
        this.traverse = traverse;
        this.reverse = reverse;
    }
}

class Coordinate {
    int Row, Col;

    public Coordinate(int row, int col) {
        Row = row;
        Col = col;
    }
}