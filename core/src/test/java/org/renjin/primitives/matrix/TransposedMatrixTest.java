package org.renjin.primitives.matrix;

import org.junit.Test;
import org.renjin.primitives.sequence.DoubleSequence;
import org.renjin.sexp.DoubleVector;
import org.renjin.sexp.IntArrayVector;
import org.renjin.sexp.Symbols;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


public class TransposedMatrixTest {

@Test
  public void transposed() {
    DoubleVector x = new DoubleSequence(1,1,12);
    x = (DoubleVector)x.setAttribute(Symbols.DIM, new IntArrayVector(4,3));

    DoubleVector y = new TransposedMatrix(x);
    assertThat(y.getElementAsDouble(9), equalTo(4d));
    assertThat(y.getElementAsDouble(4), equalTo(6d));
  }
}
