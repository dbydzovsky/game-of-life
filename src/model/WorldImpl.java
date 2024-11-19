package model;

import exception.MismatchedSizeException;
import lombok.var;
import model.shapes.Shape;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WorldImpl implements World {
    public WorldImpl(int rows, int columns) {
    }

    @Override
    public boolean getCell(int row, int col) {
        return false;
    }

    @Override
    public void setCell(int row, int col, boolean status) {

    }

    @Override
    public int getRows() {
        return 0;
    }

    @Override
    public int getColumns() {
        return 0;
    }

    @Override
    public void randomize() {

    }

    @Override
    public void clear() {

    }

    @Override
    public void next() {

    }

    @Override
    public void draw(Shape shape) {

    }

    public void save(File selectedFile) throws IOException {
        try (var dos = new DataOutputStream(new FileOutputStream(selectedFile))) {
            dos.writeInt(getRows());
            dos.writeInt(getColumns());
            for (int row = 0; row < getRows(); row++) {
                for (int col = 0; col < getColumns(); col++) {
                    dos.writeBoolean(getCell(row, col));
                }
            }
        }
    }

    public void load(File selectedFile) throws IOException, MismatchedSizeException {
        try (var dis = new DataInputStream(new FileInputStream(selectedFile))) {
            int fileRows = dis.readInt();
            int fileCols = dis.readInt();

            for (int row = 0; row < fileRows; row++) {
                for (int col = 0; col < fileCols; col++) {
                    boolean status = dis.readBoolean();
                    if (row >= getRows() | col >= getColumns()) {
                        continue;
                    }
                    setCell(row, col, status);
                }
            }

            if (fileRows != this.getRows() || fileCols != this.getColumns()) {
                throw new MismatchedSizeException();
            }
        }
    }
}
