// @formatter:off
 /*******************************************************************************
 *
 * This file is part of JMad.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package cern.accsoft.steering.util;

import java.util.ArrayList;

public class TMatrix<T> {

    private ArrayList<ArrayList<T>> matrix;
    private int rows;
    private int columns;

    public TMatrix(int rows, int columns, T initValue) {
        this.rows = rows;
        this.columns = columns;
        matrix = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            ArrayList<T> rowContent = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                rowContent.add(initValue);
            }
            matrix.add(rowContent);
        }
    }

    public void set(int row, int col, T value) {
        matrix.get(row).set(col, value);
    }

    public T get(int row, int col) {
        return matrix.get(row).get(col);
    }

    public int getRowDimension() {
        return rows;
    }

    public int getColumnDimension() {
        return columns;
    }
}
