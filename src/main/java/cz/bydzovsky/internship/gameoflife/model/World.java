package cz.bydzovsky.internship.gameoflife.model;

import cz.bydzovsky.internship.gameoflife.model.shapes.Shape;

public interface World {

    boolean getCell(int row, int col);

    void setCell(int row, int col, boolean status);

    int getRows();

    int getColumns();

    void randomize();

    void clear();

    void next();

    void draw(Shape shape);
}
