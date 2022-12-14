public class Robot {
    //virutal field
    boolean[][] visit;

    //direction
    int[] dRow, dCol;
    int rowMax, colMax;
    String[] traverse, reverse;

    //maze
    Maze maze;
    String status;

    //storing tools for path finding
    LinkedListStack<String> pathStore ;
    LinkedListStack<Integer> dirStore;


    public void navigate() {
        //virutal field
        rowMax = 1005; colMax = 1005; //max size of the maze
        visit = new boolean[rowMax * 2][colMax * 2]; //twice as maximum size of maze

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
        findPath();

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


    void findPath(){
        int curRow = rowMax, curCol = colMax;
        visit[curRow][curCol] = true;

        //intialize first 4 direction
        for (int i = 0 ; i < 4; i++){
            int newRow = curRow + dRow[i],
                newCol = curCol + dCol[i];
            visit[newRow][newCol] = true;
            dirStore.push(i);
        }

        //dfs algorithm
        while (!dirStore.isEmpty()){
            //get latest instruction
            int dirIdx = dirStore.peek();
            dirStore.pop();

            if (dirIdx >= 4){
                //travel fail, lets go back
                dirIdx %= 4;
                maze.go(reverse[dirIdx]);
                curRow -= dRow[dirIdx];
                curCol -= dCol[dirIdx];
                pathStore.pop();
            }
            else{
                //this cell is visited for first time
                status = maze.go(traverse[dirIdx]);

                if (status.equals("true")){
                    //can discover further from this cell
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
                    return;
                }
            }
        }
    }
}