## Final Projects
----

 
----

**Team Name:**
Data Unstructured

**Project Name:**
Maze_HASN

**Section:**
3

**Members:**

1.	AHMED AMER SHAFIE 1432285
2.	HAMZA SAMEH FARAG 1517003
3.	MUHAMMAD NAZHAN BIN MOHAMAD ABDUL NASIR 1517975
4.	SHAKIF BIN SUHAIMI 1514263

----

### Report

> Your Project Report Goes Here

----
Introduction
We all know what a MAZE is. Its something mysterious and scary. None of us wants to get stuck in it as the way out is not easy to find. Finding the only way out is extremely hard as it requires a lot of ground work and it needs a complete layout of the maze. 
 
3 algorithms are used in this project, that is Depth-first Search(DFS), Breadth-first Search (BFS), DFS without recursion. The maze is changeable in size as well as the starting point and the end point. 

Algorithm 1: Depth-first Search
This approach is one of the simplest ways to generate the maze. Yet, a maze must be considered to be a large grid of cells and the computer then selects a random neighboring cell that has not yet been visited from the starting cell. The process will continue until every cells have been visited. In other words, it involves a deep recursion and are rearranged into a loop by storing backtracking information in the maze itself. DFS() method is created in this project receiving two integers(x and y) and a boolean return value. If x and y are both 0 or both n+1, it will return false. If the node have been visited, return false. If the node has done visited, return true until it reaches to the end of the path, return false.

Algorithm 2: Breadth-first Search
This approach may not be the fastest but it covers all node at once, example if there is any junction it will go through that junction. BFS() method will accept the start node’s coordinate of x and y. Then, it will queue the start node after assigning each node in the maze with a unique number from 0 to n. The BFS uses Queue. This Queue will accept the starting unique number of the node. After Enqueued, the starting node’s flag will be made as true(it is visited). Next, a while loop is called with a condition that the Queue is not empty and the maze is not done yet. If its true, Dequeue the element inside the Queue once and then checks if the Dequeued number is the same number in child[i][j]. If the number is same, then assign x = i; and y = j; and call function ifWall() that will check if the node has a wall north, south, east or west way. If there is no wall, Enqueue the node to the direction that has no wall into the Queue and repeat the process until the maze is completed or cannot find any other way.

Algorithm 3: DFS without recursion
This approach is basically a DFS but it is not using any recursion as recursions waste the storage and delay the function and has many cons. So it uses loops and a heap to keep the coordinate of every junction. When the path reach to a dead end, it jumps to the nearest junction and take an another path which is unvisited. The only problem in this approach is that it will not give us the shortest path but it is much more faster than a regular DFS. 
Major functions/classes and what they do
- public class Main{}   -->   Declaring the dimension of the maze and the wall north, east, south 							  and west of the cell i, j;
- private void init()   -->   to initialize border cells as already visited
                  -->  to initialize the size of arrays used
  -->    to initialize all walls as present
  -->   to make block = new boolean[n+2][n+2]
- private void generate(int x, int y)   -->   to generate the maze by picking a random neighbor
- private void generate()   -->   to generate the maze starting from the lower left
-->   to delete random walls
-->   to add random walls
- private boolean DFS(int x, int y)   -->   to solve the maze using depth-first search
- boolean Amer()   -->   to solve the maze by:
- count how many junctions
- if more than one way finish them one by one
- start looking at the right first
- return to the nearest junction
- private boolean BFS(int x, int y)  --> to solve maze by;
- goes to each and every junction available
- uses ques and while loop
- void restart()   -->   to make each element unvisited again
- public void draw   -->   to draw the maze
- public static void main( String[] args)   -->   main method
Maze generator scheme
In the init() function, we use it to define where the borders are and set the walls into true. Firstly, we visit the generate function without parameters and through it we call the generate function with parameters. In the generate function without parameters, we use recursion to delete random walls and add random walls to ensure randomization. In the generate with parameters, we check each cell one by one and look at its neighboring boxes and it choose randomly one neighbor which is not visited and delete the wall between them. Then recursionly recall the generate function with the new cell. Then, keep going recursionly until the end.

Extra features
- an array of object in the main so that each time we run the maze, it will be stored in the object. 
- a do-while loop that runs the maze how many times the user wishes until the user press 0 to exit.

