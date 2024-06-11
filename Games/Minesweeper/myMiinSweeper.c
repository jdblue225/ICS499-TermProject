/*
	written by master programmer Brandon Crass
	in CSCI 1111
	Assignment 8

	Take a shot at an explosive program
	
	Minesweeper
	
	finish minesweeper
	
	decide when the game is done
	1. blew up 
		or
	2. no blanks (congratulations)

*/


#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>


#define MAX_SIZE 40
#define BOMB 9
#define BLANK 10

void printGrid(const int grid[MAX_SIZE][MAX_SIZE], int n);
void blankGrid(int grid[MAX_SIZE][MAX_SIZE], int n);
void placeBombsGrid(int grid[MAX_SIZE][MAX_SIZE], int n, int numBombs);
int getRand(int first, int last);
int countSurroundingBombs(const int grid[MAX_SIZE][MAX_SIZE], int n, int row, int col);
void testCount(int grid[MAX_SIZE][MAX_SIZE], int n);
int takeMove(int grid[MAX_SIZE][MAX_SIZE], int n, int row, int col, int moves);
int movesLeft(int n, int bombCount, int movesTaken);
void revealBombs(const int grid[MAX_SIZE][MAX_SIZE], int n);

bool playAgain;

int main()
{	
	do{
		int mine_grid[MAX_SIZE][MAX_SIZE]= {0};
		int n = 10; //actual size
		int row, col;
		int bombCount = 10;
		int movesTaken = 0;

		
		blankGrid(mine_grid, n);
		printGrid(mine_grid, n);
//		printf("please enter your move, row and col\n");
//	   	scanf("%d%d", &row, &col);
		
		
		placeBombsGrid(mine_grid, n, bombCount);
		
		system("cls");		//clear the screen
		printGrid(mine_grid, n);
	 //   testCount(mine_grid, n);
	    
	    do{
	    	printf("please enter your move, row and col\n");
	    	scanf("%d%d", &row, &col);

			system("cls");		//clear the screen
	
			if(row >= 0 && col >= 0 && row < n && col < n && mine_grid[row][col] != 0){
			
		    	if (mine_grid[row][col] == BOMB){
	    			printf("BOOM! Game over\n");
	    				
				}else if(movesLeft(n, bombCount, movesTaken) == 0){
					printf("WINNER!\n");
					
				}else{
					movesTaken = takeMove(mine_grid,  n,  row, col, movesTaken);
					printGrid(mine_grid, n);
					printf("%d moves remaining.\n", movesLeft(n, bombCount, movesTaken));
				}
			}else{
				printGrid(mine_grid, n);
				printf("Please enter a valid move.\n");
			}
			
			
		}while(mine_grid[row][col] != BOMB && movesLeft(n, bombCount, movesTaken) != 0); //fix!!!!
		
	
		revealBombs(mine_grid, n);
		
		printf("PLAY AGAIN?\n'1' for yes\n'0' for no\n");//for debugging
		scanf("%d", &playAgain);
		printf("\n\n");		//make it look cleaner between games
		
	}while(playAgain == true);
  
	return 0;
}


	
void printGrid(const int grid[MAX_SIZE][MAX_SIZE], int n)
{
	int i,j;
	
	for(i = 0; i < n; ++i){
		for(j = 0; j < n; ++j){
			if (grid[i][j] == BLANK || grid[i][j] == BOMB)
						printf(" + ");
			else if (grid[i][j] == 0 )
						printf(" . ");
			else
						printf("%2d ", grid[i][j]);
	    }
	    printf("\n");
	}	
}
/*
	fill with blanks
*/
void blankGrid(int grid[MAX_SIZE][MAX_SIZE], int n)
{
	int i,j;
	
	for(i = 0; i < n; ++i){
		for(j = 0; j < n; ++j){
			grid[i][j] = BLANK;
	    }
	}	
}

void placeBombsGrid(int grid[MAX_SIZE][MAX_SIZE], int n, int numBombs)
{
    int i;
    int row, col;
    
 	for(i = 0; i < numBombs; i++)
 	{
 		 do{
 		 	row = getRand(0, n-1);
    	    col = getRand(0, n-1);
		}while(grid[row][col] == BOMB);
    	 //IF USER FIRST MOVE IS NOT HERE THEN PLACE BOMB
		grid[row][col] = BOMB;
	}
}


int getRand(int first, int last)
{
   static int firstTime = 1;
   int amountOfNumbers;
   if (firstTime == 1){
      /*first time in this function, seed the random number generator */
       firstTime = 0;
	   srand(time(NULL));
   }
   amountOfNumbers = last - first + 1;
   return(rand() % amountOfNumbers + first);
}


int countSurroundingBombs(const int grid[MAX_SIZE][MAX_SIZE], int n, int row, int col)
{
	int count = 0;
	int i, j, r, c;
	
	for (i = -1; i <= 1; i++){
		for (j = -1; j <= 1; j++){
			r = i + row;
			c = j + col;
			if (r >= 0 && r < n && c >= 0 && c < n && grid[r][c] == BOMB){
				count++;
			}
		}
	}
	
	return count;
}



void testCount(int grid[MAX_SIZE][MAX_SIZE], int n)
{
	int row, col;
	
	while(1){
		printf("Enter a row and col\n");
		scanf("%d%d", &row, &col);
		printf("%d\n", countSurroundingBombs(grid, n, row, col));
	}
}

int takeMove(int grid[MAX_SIZE][MAX_SIZE], int n, int row, int col, int moves)
{
	int count;
	count = countSurroundingBombs(grid, n, row, col);
	grid[row][col] = count;
	
	
	if (count == 0){
		int i, j, r, c;
	
		for (i = 1; i >= -1; i--){
			for (j = 1; j >= -1; j--){
				r = i + row;
				c = j + col;
				if (r >= 0 && r < n && c >= 0 && c < n && grid[r][c] == BLANK){
					 moves = takeMove(grid, n, r, c, moves);
//					 printf("%d\n", moves);//DEBUGGING
				}
			}//for j
		}//for i
	}//	if (count == 0){
	
	moves++;

	return moves;
}


int movesLeft(int n, int bombCount, int movesTaken){
	
	int totalSpaces = n * n;
	int movesLeft = totalSpaces - bombCount - movesTaken;
		
	//	printf("There are %d moves remaining.\n", movesLeft);
		
	return movesLeft;
}

void revealBombs(const int grid[MAX_SIZE][MAX_SIZE], int n)
{
	int i,j;
	
	for(i = 0; i < n; ++i){
		for(j = 0; j < n; ++j){
			if (grid[i][j] == BOMB)
						printf(" X ");
			else if (grid[i][j] == 0 || grid[i][j] == BLANK)
						printf(" . ");
			else
						printf("%2d ", grid[i][j]);
	    }
	    printf("\n");
	}	
}