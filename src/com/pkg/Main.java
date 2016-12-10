package com.pkg;
import java.util.*;

import edu.princeton.cs.introcs.*;
import static edu.princeton.cs.introcs.StdDraw.filledCircle;
import static edu.princeton.cs.introcs.StdDraw.filledSquare;
import static edu.princeton.cs.introcs.StdDraw.setPenColor;



/**
 * Created by Ahmed on 11/27/2016.
 */
public class Main {

    private int n;                 // dimension of maze
    private int end1,end2 ;
    private int start1,start2 ;
    private boolean[][] northwall;     // is there a wall to north of cell i, j
    private boolean[][] eastwall;
    private boolean[][] southwall;
    private boolean[][] west;
    private boolean[][] visited;
    private boolean done = false;
    //private boolean[][] block;
    private int currentx,currenty; //to use in the second algorithm
    private int[]keepx;
    private int[]keepy;




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

       // block = new boolean [n+2][n+2];
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
                double r = StdRandom.uniform(4);
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
            int x = 1 + StdRandom.uniform(n-1);
            int y = 1 + StdRandom.uniform(n-1);
            northwall[x][y] = southwall[x][y+1] = false;

        }

        // add some random walls
        for (int i = 0; i < 10; i++) {
            int x = n/2 + StdRandom.uniform(n/2);
            int y = n/2 + StdRandom.uniform(n/2);
            eastwall[x][y] = west[x+1][y] = true;
        }

        start1=1+StdRandom.uniform(n-1);
        start2=1+StdRandom.uniform(n-1);
        end1= 1+StdRandom.uniform(n-1);
        end2= 1+StdRandom.uniform(n-1);

    }


    // solve the maze using depth-first search
    private boolean DFS(int x, int y) {
        if (x == 0 || y == 0 || x == n + 1 || y == n + 1) return false;
        if ( visited[x][y]) return false;
        if(done) return true;
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


/*
void fill(int i,int j){
}
    //the second algorithm (End Filling)
    boolean EndFilling(int x, int y ){
        for(int i =0 ;i< n ;i++){
            for(int j=0;j< n ;j++){
                if(southwall[i][j] == true && eastwall[i][j]== true && west [i][j]== true) {
                    block[i][j] = true;
                    fill(i,j);
                }
            }
        }
        return false ;
    }
*/




boolean Amer(){

    StdDraw.setPenColor(StdDraw.BLUE);
    currentx =start1;
    currenty = start2;
    keepx = new int [n*n];
    keepy = new int [n*n];
    int a=0;

    while(true) {


        int c = 0; //count there is how many junction
        if (!eastwall[currentx][currenty]&& !visited[currentx + 1][currenty]) c++;
        if (!west[currentx][currenty] && !visited[currentx-1][currenty]) c++;
        if (!southwall[currentx][currenty] && !visited[currentx ][currenty-1]) c++;
        if (!northwall[currentx][currenty]&& !visited[currentx][currenty+1]) c++;


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
                filledSquare(currentx + 0.5, currenty + 0.5, 0.25);
                StdDraw.show();
                StdDraw.pause(30);
                if (currentx == end1 && currenty == end2) break;
            }
            else if (!northwall[currentx][currenty] && !visited[currentx][currenty+1]) {
                currenty++;
                visited[currentx][currenty] = true;
                filledSquare(currentx + 0.5, currenty + 0.5, 0.25);
                StdDraw.show();
                StdDraw.pause(30);
                if (currentx == end1 && currenty == end2) break;
            }
            else if (!southwall[currentx][currenty] && !visited[currentx ][currenty-1]) {
                currenty--;
                visited[currentx][currenty] = true;
                filledSquare(currentx + 0.5, currenty + 0.5, 0.25);
                StdDraw.show();
                StdDraw.pause(30);
                if (currentx == end1 && currenty == end2) break;
            }
            else if (!west[currentx][currenty] && !visited[currentx-1][currenty]) {
                currentx--;
                visited[currentx][currenty] = true;
                filledSquare(currentx + 0.5, currenty + 0.5, 0.25);
                StdDraw.show();
                StdDraw.pause(30);
                if (currentx == end1 && currenty == end2) break;
            }
            else {

                if (currentx == end1 && currenty == end2) break;
                if(a==0)break;
                currentx = keepx[a]; //return to the nearest junction
                currenty= keepy[a];
                a--;

            }
        }

    if(currentx == end1 && currenty == end2)return true;
    return false;
    }







    //to make each element unvested again
    void restart(){
        for (int x = 1; x < n; x++)
            for (int y = 1; y < n; y++)
                visited[x][y] = false;
        done = false;
    }



    // draw the maze
    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledSquare(start1+0.5, start2+0.5, 0.375);

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

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int size = input.nextInt();
        Main maze = new Main(size);
        StdDraw.enableDoubleBuffering();
        maze.draw();
      //  maze.solve();
        System.out.println("choose the algoritm 1,2,3,4");
        int algo = input.nextInt();
        maze.restart();
        if (algo == 1) {
            boolean issolvable ;
            issolvable = maze.DFS(maze.start1, maze.start2);
            System.out.println(issolvable);
        }


       maze.restart();
        if (algo == 2) {
            boolean issolvable ;
            issolvable = maze.Amer();
            System.out.println(issolvable);
        }




       // else if (algo == 3)maiz.solve3(maze.start1,maze.start2);
       // else if (algo == 4)maiz.solve4(maze.start1,maze.start2);


    }


}
