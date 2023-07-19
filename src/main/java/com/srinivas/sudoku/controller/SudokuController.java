package com.srinivas.sudoku.controller;

import com.srinivas.sudoku.model.SudokuRequestModel;
import com.srinivas.sudoku.model.SudokuResponseModel;
import com.srinivas.sudoku.service.SudokuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solveSudoku")
@CrossOrigin( origins = "http://localhost:3000")
public class SudokuController {

    @Autowired
    SudokuService sudokuService;

    @GetMapping("/health")
    ResponseEntity<String> healthCheck(){
        return new ResponseEntity<>("OK!!", HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<SudokuResponseModel> solveSudoku(@RequestBody SudokuRequestModel sudokuRequestModel){
        return sudokuService.solveSudoku(sudokuRequestModel);
    }
}
