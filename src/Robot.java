public class Robot {
    boolean[][] visit;
    int[] dx, dy;
    String[] traverse, reverse;
    Maze maze;
    String result;
    LinkedListStack<String> pathStore ;

    public void navigate() {
        System.out.println("* Navigation program *");

        //virtual field
        int heightMax = 1005, widthMax = 1005;
        visit = new boolean[heightMax * 2][widthMax * 2]; //twice as maximum size of maze

        //values for travelling direction
        dx = new int[]{-1,0,1,0};
        dy = new int[]{0,1,0,-1};
        traverse = new String[]{"UP", "RIGHT", "DOWN", "LEFT"};
        reverse = new String[]{"DOWN", "LEFT", "UP", "RIGHT"};

        //status from maze's traveling function response
        result = "";
        maze = new Maze();
        pathStore = new LinkedListStack<String>();

        //start visiting
        visit[heightMax][widthMax] = true;
        findPath(heightMax , widthMax);

        //after visit, get path
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


    void findPath(int curX, int curY){
        //travel 4 direction from current cell
        for (int i = 0 ; i < 4; i++) {
            //new position if going to direction
            int newX, newY;
            newX = curX + dx[i];
            newY = curY + dy[i];

            if (visit[newX][newY] == false) {
                visit[newX][newY] = true;

                result = maze.go(traverse[i]);

                if (result.equals("true")) {
                    //can explore more
                    pathStore.push(traverse[i]);
                    findPath(newX, newY);

                    //after explore, ends if win, otherwise return to current place
                    if (result.equals("win")) {
                        return;
                    }

                    //always able to return to current place
                    maze.go(reverse[i]);
                    pathStore.pop();
                }

                if (result.equals("win")) {
                    //record last step then terminate
                    pathStore.push(traverse[i]);
                    return;
                }
            }

        }
    }
}
