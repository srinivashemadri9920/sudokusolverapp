package com.srinivas.sudoku.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DuplicateValues {

    private int row1;
    private int col1;
    private int row2;
    private int col2;

}
