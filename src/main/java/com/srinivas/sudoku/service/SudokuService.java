package com.srinivas.sudoku.service;

import com.srinivas.sudoku.model.SudokuRequestModel;
import com.srinivas.sudoku.model.SudokuResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class SudokuService {

    private int[][] matrix;

    int currRow;
    int currColumn;
    int currVal;

    private boolean areCluesOk(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if( matrix[i][j]!=0){
                    if(!isSafe(i,j, matrix[i][j])) {
                        currRow = i;
                        currColumn = j;
                        currVal = matrix[i][j];
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isSafe(int row,int col,int num){

        for(int i=0;i<9;i++){
            if(i != row){
                if(num == matrix[i][col])
                    return false;
            }
        }
        for(int i=0;i<9;i++){
            if(i!=col){
                if(num == matrix[row][i])
                    return false;
            }
        }

        int xrow = row - row%3;
        int xcol = col - col%3;

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(!(i+xrow == row && j+xcol == col)){
                    if(num == matrix[i+ xrow][j+xcol])
                        return false;
                }
            }
        }
        return true;
    }


    private boolean doSudoku(int row,int col){

        if(row == 8 && col == 9)
            return true;

        if(col > 8){
            row = row + 1;
            col = 0;
        }

        if(matrix[row][col] > 0)
            return doSudoku(row, col+1);

        for(int i=1;i<=9;i++){
            if(isSafe(row, col, i)){
                matrix[row][col] = i;
                if(doSudoku(row, col+1))
                    return true;
            }
            matrix[row][col] = 0;
        }
        return false;
    }


    public ResponseEntity<SudokuResponseModel> solveSudoku(SudokuRequestModel sudokuRequestModel){

        SudokuResponseModel sudokuResponseModel = new SudokuResponseModel();
        try{
            this.matrix = sudokuRequestModel.getUnsolvedMatrix();
            if(!areCluesOk()){
                throw new Exception("Initial clues are wrong row = " + (currRow+1) + " column = " + (currColumn+1)+ " value= "+ currVal  );
            }
            if(doSudoku(0,0)){
                sudokuResponseModel.setSolvedMatrix(this.matrix);
                sudokuResponseModel.setHasError(false);
                sudokuResponseModel.setErrorMessage(null);
                return new ResponseEntity<>(sudokuResponseModel, HttpStatus.OK);
            }
            else{
                throw new Exception("No valid solution for given sudoku");
            }
        }
        catch (Exception e){
            sudokuResponseModel.setSolvedMatrix(null);
            sudokuResponseModel.setErrorMessage(e.getMessage());
            sudokuResponseModel.setHasError(true);
            return new ResponseEntity<>(sudokuResponseModel, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
