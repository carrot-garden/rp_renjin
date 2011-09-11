package r.util;

import org.apache.commons.math.linear.AbstractRealMatrix;
import org.apache.commons.math.linear.MatrixIndexException;
import org.apache.commons.math.linear.RealMatrix;

import r.lang.DoubleVector;
import r.lang.IntVector;
import r.lang.Null;
import r.lang.Symbol;
import r.lang.Vector;

/**
 * Bridge between Renjin and the Commons Math APIs
 *
 */
public class CommonsMath {

  private CommonsMath() {}
  
  /**
   * Returns a view of a R matrix as a Commons Math {@code RealMatrix}.
   * 
   * @param vector an R vector
   * @return a view of the R matrix as a Commons Math {@code RealMatrix}
   * @throws IllegalArgumentException if the vector does not have a {@code dim} attribute, or if the vector
   * does not have exactly 2 dimensions
   */
  public static RealMatrix asRealMatrix(Vector vector) {
    return new MatrixAdapter(vector);
  }
  
  /**
   * Creates a copy of a Commons Math {@code RealMatrix} as an
   * {@code DoubleVector} with an appropriate {@code dim} attribute.
   * 
   * @param matrix
   * @return a {@code DoubleVector}
   */
  public static Vector asDoubleVector(RealMatrix matrix) {
    int nrows = matrix.getRowDimension();
    int ncols = matrix.getColumnDimension();
    
    DoubleVector.Builder vector = new DoubleVector.Builder(nrows * ncols);
    vector.setAttribute(Symbol.DIM, new IntVector(nrows, ncols));
    
    int vector_i = 0;
    for(int i=0;i!=nrows;++i) {
      for(int j=0;j!=ncols;++j) {
        vector.set(vector_i++, matrix.getEntry(i, j));
      }
    }
    return vector.build();
  }
  
  
  private static class MatrixAdapter extends AbstractRealMatrix {
    private int nrows;
    private int ncols;
    private Vector vector;
    
    public MatrixAdapter(Vector vector) {
      this.vector = vector;
      
      Vector dim = (Vector) vector.getAttribute(Symbol.DIM);
      if(dim == Null.INSTANCE) {
        throw new IllegalArgumentException("the vector has no 'dim' attribute");
      }
      if(dim.length() != 2) {
        throw new IllegalArgumentException("the vector has is not a matrix; it has " + dim.length() + " dimension(s).");
      } 
      nrows = dim.getElementAsInt(0);
      ncols = dim.getElementAsInt(1);
    }
    
    
    @Override
    public RealMatrix createMatrix(int rowDimension, int columnDimension)
        throws IllegalArgumentException {
      throw new UnsupportedOperationException();
    }

    @Override
    public RealMatrix copy() {
      throw new UnsupportedOperationException();
    }

    @Override
    public double getEntry(int row, int column) throws MatrixIndexException {
      return vector.getElementAsDouble(row * ncols + column);
    }

    @Override
    public void setEntry(int row, int column, double value)
        throws MatrixIndexException {
      throw new UnsupportedOperationException();    
    }

    @Override
    public void addToEntry(int row, int column, double increment)
        throws MatrixIndexException {
      throw new UnsupportedOperationException();
    }

    @Override
    public void multiplyEntry(int row, int column, double factor)
        throws MatrixIndexException {
      throw new UnsupportedOperationException();
    }

    @Override
    public int getRowDimension() {
      return nrows;
    }

    @Override
    public int getColumnDimension() {
      return ncols;
    }
  }
}