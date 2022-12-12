import java.io.FileNotFoundException;

public class Robot {
    //order: UP, RIGHT, DOWN, LEFT
    boolean[][] visit;
    int[] dRow, dCol;
    String[] traverse, reverse;
    Maze maze;
    String status;
    LinkedListStack<String> pathStore ;
    LinkedListStack<Integer> dirStore;

    public void navigate() throws FileNotFoundException{
        //virutal field
        int rowMax = 1005, widthMax = 1005;
        visit = new boolean[rowMax * 2][widthMax * 2]; //twice as maximum size of maze

        //values for travelling direction
        dRow = new int[]{-1,0,1,0};
        dCol = new int[]{0,1,0,-1};
        traverse = new String[]{"UP", "RIGHT", "DOWN", "LEFT"};
        reverse = new String[]{"DOWN", "LEFT", "UP", "RIGHT"};

        //processor for handling travel
        status = "";
        maze = new Maze();
        pathStore = new LinkedListStack<String>();
        dirStore = new LinkedListStack<Integer>();

        //path finding
        int curRow = rowMax, curCol = widthMax;
        visit[curRow][curCol] = true;

        //intialize
        for (int i = 0 ; i < 4; i++){
            dirStore.push(i);
        }

        while (!dirStore.isEmpty()){
            //debug
//            System.out.println("--");
//            LinkedListStack.Node<Integer> s = dirStore.head;
//            while (s != null){
//                if (s.data >= 4) System.out.print(reverse[s.data % 4] + "* ");
//                else System.out.print(traverse[s.data] + " ");
//                s = s.next;
//            }
//            System.out.println("");

            int dirIdx = dirStore.peek();
            dirStore.pop();

            if (dirIdx >= 4){
                //travel fail, lets go back
                maze.go(reverse[dirIdx % 4]);
                curRow -= dRow[dirIdx % 4];
                curCol -= dCol[dirIdx % 4];
                pathStore.pop();
            }
            else{
                status = maze.go(traverse[dirIdx]);
                if (status.equals("true")){
                    //can discover from this cell
                    curRow += dRow[dirIdx];
                    curCol += dCol[dirIdx];
                    pathStore.push(traverse[dirIdx]);

                    //add adjacent cell to stack
                    dirStore.push(dirIdx + 4); //In case fail after travel
                    for (int i = 0 ; i < 4; i++){
                        int newRow = curRow + dRow[i],
                            newCol = curCol + dCol[i];
                        if (!visit[newRow][newCol]){
                            visit[newRow][newCol] = true;
                            dirStore.push(i);
                        }
                    }
                }
                else if (status.equals("win")){
                    //exit gate found, save last step then terminate
                    pathStore.push(traverse[dirIdx]);
                    break;
                }
            }
        }

        //get path
        getPath();
    }


    void getPath(){
        System.out.println("=== Path ===");

        //since the stack value is taken from last value to the first
        //the order should be retrieved from the last element to the start
        String[] path = new String[pathStore.size()];
        for (int i = path.length - 1 ; i >= 0 ; i--){
            path[i] = pathStore.peek();
            pathStore.pop();
        }

        //print out path result
        for (String direction : path){
            System.out.print(direction + " ");
        }
    }

}