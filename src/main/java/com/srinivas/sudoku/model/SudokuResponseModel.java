package com.srinivas.sudoku.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SudokuResponseModel {

    private int[][] solvedMatrix;
    private boolean hasError;
    private String errorMessage;

}
