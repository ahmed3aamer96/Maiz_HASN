package com.pkg;
import java.util.*;
import java.io.*;

import edu.princeton.cs.introcs.*;
import static edu.princeton.cs.introcs.StdDraw.filledCircle;
import static edu.princeton.cs.introcs.StdDraw.filledSquare;
import static edu.princeton.cs.introcs.StdDraw.setPenColor;



/**
 * Created by Ahmed on 11/27/2016.
 */
public class Main {

    private int n;                 // dimension of maze
    private int end1, end2;
    private int start1, start2;
    private boolean[][] northwall;     // is there a wall to north of cell i, j
    private boolean[][] eastwall;
    private boolean[][] southwall;
    private boolean[][] west;
    private boolean[][] visited;
    private boolean done = false;
    private int currentx, currenty; //to use in the second algorithm
    private int[] keepx;
    private int[] keepy;
    private int[][] child; //to use in 3rd algorithm


    public Main(int n) {
        this.n = n;
        StdDraw.setXscale(0, n + 2);
        StdDraw.setYscale(0, n + 2);
        init();
        generate();
    }

    private void init() {
        // initialize border cells as already visited
        visited = new boolean[n + 2][n + 2];
        for (int x = 0; x < n + 2; x++) {
            visited[x][0] = true;
            visited[x][n + 1] = true;
        }
        for (int y = 0; y < n + 2; y++) {
            visited[0][y] = true;
            visited[n + 1][y] = true;
        }


        // initialze all walls as present
        northwall = new boolean[n + 2][n + 2];
        eastwall = new boolean[n + 2][n + 2];
        southwall = new boolean[n + 2][n + 2];
        west = new boolean[n + 2][n + 2];

        child = new int[n + 2][n + 2]; //initialize the size of child

        for (int x = 0; x < n + 2; x++) {
            for (int y = 0; y < n + 2; y++) {
                northwall[x][y] = true;
                eastwall[x][y] = true;
                southwall[x][y] = true;
                west[x][y] = true;
            }
        }
    }


    // generate the maze
    private void generate(int x, int y) {
        visited[x][y] = true;

        // while there is an unvisited neighbor
        while (!visited[x][y + 1] || !visited[x + 1][y] || !visited[x][y - 1] || !visited[x - 1][y]) {

            // pick random neighbor
            while (true) {
                double r = (int) (Math.random() * (4));
                //   double r = StdRandom.uniform(4);
                if (r == 0 && !visited[x][y + 1]) {
                    northwall[x][y] = false;
                    southwall[x][y + 1] = false;


                    generate(x, y + 1);
                    break;
                } else if (r == 1 && !visited[x + 1][y]) {
                    eastwall[x][y] = false;
                    west[x + 1][y] = false;
                    generate(x + 1, y);
                    break;
                } else if (r == 2 && !visited[x][y - 1]) {
                    southwall[x][y] = false;
                    northwall[x][y - 1] = false;
                    generate(x, y - 1);
                    break;
                } else if (r == 3 && !visited[x - 1][y]) {
                    west[x][y] = false;
                    eastwall[x - 1][y] = false;
                    generate(x - 1, y);
                    break;
                }
            }
        }

    }

    // generate the maze starting from lower left
    private void generate() {
        generate(1, 1);


        // delete some random walls
        for (int i = 0; i < n; i++) {

            int x = 1 + (int) (Math.random() * (n - 1));
            int y = 1 + (int) (Math.random() * (n - 1));
            northwall[x][y] = southwall[x][y + 1] = false;

        }

        // add some random walls
        for (int i = 0; i < 10; i++) {
            int x = n / 2 + StdRandom.uniform(n / 2);
            int y = n / 2 + StdRandom.uniform(n / 2);
            eastwall[x][y] = west[x + 1][y] = true;
        }
        start1 = 1 + (int) (Math.random() * (n - 1));
        start2 = 1 + (int) (Math.random() * (n - 1));
        end1 = 1 + (int) (Math.random() * (n - 1));
        end2 = 1 + (int) (Math.random() * (n - 1));

    }


    // solve the maze using depth-first search
    private boolean DFS(int x, int y) {
        if (x == 0 || y == 0 || x == n + 1 || y == n + 1) return false;
        if (visited[x][y]) return false;
        if (done) return true;
        visited[x][y] = true;
        StdDraw.setPenColor(StdDraw.BLUE);
        filledSquare(x + 0.5, y + 0.5, 0.25);
        StdDraw.show();
        StdDraw.pause(30);


        // reached to end
        if (x == end1 && y == end2) done = true;

        if (!northwall[x][y]) DFS(x, y + 1);
        if (!eastwall[x][y]) DFS(x + 1, y);
        if (!southwall[x][y]) DFS(x, y - 1);
        if (!west[x][y]) DFS(x - 1, y);

        if (done) return true;

        StdDraw.setPenColor(StdDraw.GRAY);
        filledSquare(x + 0.5, y + 0.5, 0.25);
        StdDraw.show();
        StdDraw.pause(30);
        return false;
    }





    boolean DFS_withoutrecursion() {

        StdDraw.setPenColor(StdDraw.BLUE);
        currentx = start1;
        currenty = start2;
        keepx = new int[n * n];
        keepy = new int[n * n];
        int a = 0;

        while (true) {


            int c = 0; //count there is how many junction
            if (!eastwall[currentx][currenty] && !visited[currentx + 1][currenty]) c++;
            if (!west[currentx][currenty] && !visited[currentx - 1][currenty]) c++;
            if (!southwall[currentx][currenty] && !visited[currentx][currenty - 1]) c++;
            if (!northwall[currentx][currenty] && !visited[currentx][currenty + 1]) c++;


            while (c > 1) {
                a++;
                keepx[a] = currentx;
                keepy[a] = currenty;
                c--;
            }


            //if more than one way finish them one by one

            //start looking at right first
            if (!eastwall[currentx][currenty] && !visited[currentx + 1][currenty]) {

                currentx++;
                visited[currentx][currenty] = true;
                filledCircle(currentx + 0.5, currenty + 0.5, 0.25);
                StdDraw.show();
                StdDraw.pause(30);
                if (currentx == end1 && currenty == end2) break;
            } else if (!northwall[currentx][currenty] && !visited[currentx][currenty + 1]) {
                currenty++;
                visited[currentx][currenty] = true;
                filledCircle(currentx + 0.5, currenty + 0.5, 0.25);
                StdDraw.show();
                StdDraw.pause(30);
                if (currentx == end1 && currenty == end2) break;
            } else if (!southwall[currentx][currenty] && !visited[currentx][currenty - 1]) {
                currenty--;
                visited[currentx][currenty] = true;
                filledCircle(currentx + 0.5, currenty + 0.5, 0.25);
                StdDraw.show();
                StdDraw.pause(30);
                if (currentx == end1 && currenty == end2) break;
            } else if (!west[currentx][currenty] && !visited[currentx - 1][currenty]) {
                currentx--;
                visited[currentx][currenty] = true;
                filledCircle(currentx + 0.5, currenty + 0.5, 0.25);
                StdDraw.show();
                StdDraw.pause(30);
                if (currentx == end1 && currenty == end2) break;
            } else {

                if (currentx == end1 && currenty == end2) break;
                if (a == 0) break;
                currentx = keepx[a]; //return to the nearest junction
                currenty = keepy[a];
                a--;

            }
        }

        if (currentx == end1 && currenty == end2) return true;
        return false;
    }


    // the Third Algoritm
    private void initialize(Queue<Integer> q1, int[][] child) {
        q1.clear();

        child[0][0] = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++, count++) {
                child[i][j] += count;  //gives every element a number
            }
        }

        return;
    }

    private void enqueue(Queue<Integer> q, int w) {
        q.add(w);
    }

    private int dequeue(Queue<Integer> q) {
        return q.remove();
    }

    private void fill(int x, int y) {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledSquare(x + 0.5, y + 0.5, 0.25);
        StdDraw.show();
        StdDraw.pause(30);
    }

    private void ifWall(int x, int y, int[][] pred) { // to check if on the element there is wall on north,south, east and west from the element
        if (x == 0 || y == 0 || x == n + 1 || y == n + 1) return;

        if (!northwall[x][y] && !visited[x][y + 1]) {
            child[x][y] = -1;
            visited[x][y + 1] = true;
            pred[x][y + 1] = child[x][y];
            fill(x, y);

            enqueue(Q, child[x][y + 1]);


        }
        if (!eastwall[x][y] && !visited[x + 1][y]) {
            child[x][y] = -1;
            visited[x + 1][y] = true;
            pred[x + 1][y] = child[x][y];
            fill(x, y);
            enqueue(Q, child[x + 1][y]);


        }
        if (!southwall[x][y] && !visited[x][y - 1]) {
            child[x][y] = -1;
            visited[x][y - 1] = true;
            pred[x][y - 1] = child[x][y];
            fill(x, y);
            enqueue(Q, child[x][y - 1]);


        }
        if (!west[x][y] && !visited[x - 1][y]) {
            child[x][y] = -1;
            visited[x - 1][y] = true;
            pred[x - 1][y] = child[x][y];
            fill(x, y);
            enqueue(Q, child[x - 1][y]);
        }
        if (northwall[x][y] || southwall[x][y] || eastwall[x][y] || west[x][y] || visited[x][y]) {
            fill(x, y);
        }
    }

    void Path(int[][] pred, int x, int y) {  //supposedly to find the shortest path
        int w = pred[x][y];
        if (w != -1) {

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (child[i][j] == w) {
                        Path(pred, i, j);

                        StdDraw.setPenColor(StdDraw.GRAY);
                        StdDraw.filledSquare(i + 0.5, j + 0.5, 0.25);
                        StdDraw.show();
                        StdDraw.pause(30);
                    }
                }

            }

        }

    }

    private static Queue<Integer> Q = new LinkedList<Integer>(); // creates a que

    private boolean BFS(int x, int y) {
        int[][] pred = new int[n + 2][n + 2];
        int[][] dist = new int[n + 2][n + 2];
        enqueue(Q, child[x][y]);  //to enqueue the start element
        visited[x][y] = true;  //makes the start element visited
        pred[x][y] = -1;
        dist[x][y] = 0;
        fill(x, y);
        int w = 0;
        while (!Q.isEmpty() && !done) {
            int u = dequeue(Q);

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    w = child[i][j];
                    if (u == w) { //checks if the number assigned to child is correct to after dequeue
                        x = i;
                        y = j;
                        ifWall(x, y, pred);
                        break;
                    }

                }

                if (u == w) break;
            }
            if (x == end1 && y == end2) done = true;
        }

        if (done) {

            Path(pred, x, y);
            return true;
        }


        return false;
    }


    //to make each element unvested again
    void restart() {
        for (int x = 1; x < n; x++)
            for (int y = 1; y < n; y++)
                visited[x][y] = false;

        initialize(Q, child); //for algorithm 3 -> to set each child's number and to make que empty
        done = false;
    }


    // draw the maze
    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledSquare(start1 + 0.5, start2 + 0.5, 0.375);

        StdDraw.setPenColor(StdDraw.YELLOW);
        filledSquare(end1 + 0.5, end2 + 0.5, 0.375);

        StdDraw.setPenColor(StdDraw.BLACK);
        for (int x = 1; x < n; x++) {
            for (int y = 1; y < n; y++) {
                if (southwall[x][y]) StdDraw.line(x, y, x + 1, y);
                if (northwall[x][y]) StdDraw.line(x, y + 1, x + 1, y + 1);
                if (west[x][y]) StdDraw.line(x, y, x, y + 1);
                if (eastwall[x][y]) StdDraw.line(x + 1, y, x + 1, y + 1);
            }
        }
        StdDraw.show();
        StdDraw.pause(1000);

    }


    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        int algo = 5, i = 0;
        Main[] maze = new Main[100]; // creates an array of object to save the maze each time it is run
        int size = input.nextInt();


        do {
            maze[i] = new Main(size);
            StdDraw.enableDoubleBuffering();
            maze[i].draw();


            System.out.println("choose the algorithm 1,2,3,4 (0 to exit)");
            algo = input.nextInt();


            if (algo == 1) {
                maze[i].restart();
                boolean issolvable;
                issolvable = maze[i].DFS(maze[i].start1, maze[i].start2);
                if(issolvable)System.out.println("it can be solve");
                else System.out.println("it can't be solve");
            }


            if (algo == 2) {
                maze[i].restart();
                boolean issolvable;
                issolvable = maze[i].DFS_withoutrecursion();
                if(issolvable)System.out.println("it can be solve");
                else System.out.println("it can't be solve");
            }

            if (algo == 3) {
                maze[i].restart();
                boolean issolvable;
                issolvable = maze[i].BFS(maze[i].start1, maze[i].start2);
                if(issolvable)System.out.println("it can be solve");
                else System.out.println("it can't be solve");
            }

            i++;

            System.out.println("\nPress Enter to continue...");
            System.in.read();//Waits for user to enter Enter
            StdDraw.clear();//Clears the maze's screen
        } while (algo != 0); // if it is 0, it will exit
    }

}
