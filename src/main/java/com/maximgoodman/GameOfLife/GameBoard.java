package com.maximgoodman.GameOfLife;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class GameBoard
{
   int squareLength;
   byte[][]board;
   byte[][]holdBoard;
   

    public GameBoard(int squareLength)
    {
        this.squareLength =squareLength;

        byte[][]board = new byte[squareLength][squareLength];
        this.board=board;

        populateBoard();
                
    }

    private void populateBoard() 
    {
               
        for (int i=0;i<squareLength;i++) 
        {
        
            for(int j=0;j<squareLength;j++)
            {
                int pop = (int) (Math.random()*100);
                if(pop%2 ==0)
                {
                    board[i][j]=0;
                }
                else
                {
                    board[i][j]=1;
                }
                
            }
        }

        //printBoard(board);
    }

    public void printBoard(byte[][] board)
    {

        for (int i=0;i<board.length;i++) 
        {
            
            List list = new ArrayList<>();
            
            for(int j=0;j<board[i].length;j++)
            {
               list.add(String.valueOf(board[i][j]));
            }
            
            System.out.println(Arrays.toString(list.toArray()));
            
        }
        System.out.println("--------");
    }

    public void nextIteration()
    {
        holdBoard = new byte[squareLength][];
        for(int i=0; i<squareLength; i++)
        {
            holdBoard[i] = board[i].clone();
        }
      

        for(int i =0; i<squareLength;i++)
        {
            for(int j=0; j<squareLength;j++)
            {
                if(board[i][j] == 1)
                {
                    livingCellRules(i,j);
                }
                else
                {
                    deadCellRules(i,j);
                }
            }
        }

	}

    private void deadCellRules(int i, int j) {

        int countLiving = countLiving(i,j);

        if(countLiving==3)
        {
            board[i][j]=1;
        }
  
    }

    private void livingCellRules(int i, int j)
    {

        int countLiving = countLiving(i,j);

        if(countLiving<2||countLiving>3)
        {
            board[i][j] =0; 
        }
       

    }

    private int countLiving(int i, int j){

        int countLiving =0;

        for(int row=i-1;row<=i+1;row++)
        {
            if(row<0||row==squareLength)
            {

            }
            else
            {
                for(int col = j-1;col<=j+1;col++)
                {
                    if(col<0||col==squareLength||row==i&&col==j)
                    {}
                    else
                    {
                        if(holdBoard[row][col]==1)
                        {
                            countLiving++;
                        }
                    }
                }
            }
        }

        return countLiving;
}

    public byte[][] getBoard()
    {
        return board;
    }
}