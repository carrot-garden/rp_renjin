package org.renjin.util;

import org.junit.Assert;
import org.junit.Test;
import org.renjin.stats.internals.distributions.MersenneTwister;


public class HeapsortTandemTest {
  final int N = 1000000;
  MersenneTwister mt = new MersenneTwister(12345);

  @Test
  public void testHeapsortDescending() {
    int i = 0;
    final double[] values = new double[N];
    final int[] valuesOrder = new int[N];

    for (; i < N; i++) {
      values[i] = mt.nextDouble();
      valuesOrder[i] = i;
    }

    HeapsortTandem.heapsortDescending(values, valuesOrder, N);

    // Ensure the values are in descending order
    for (i = 0; i < N - 1; i++) {
      Assert.assertTrue(values[i] >= values[i + 1]);
    }
  }

  @Test
  public void testHeapsortAscending() {
    int i = 0;
    final double[] values = new double[N];
    final int[] valuesOrder = new int[N];

    for (; i < N; i++) {
      values[i] = mt.nextDouble();
      valuesOrder[i] = i;
    }

    HeapsortTandem.heapsortAscending(values, valuesOrder, N);

    // Ensure the values are in descending order
    for (i = 0; i < N - 1; i++) {
      Assert.assertTrue(values[i] <= values[i + 1]);
    }
  }

  @Test
  public void testPresortedArrayDescending() {
    int i = 0;
    final double[] values = new double[]{0.2356849, 0.2163148, 0.1985367, 0.1822197, 0.1672438};
    final int[] valuesOrder = new int[N];

    for (; i < values.length; i++) {
      valuesOrder[i] = i;
    }

    HeapsortTandem.heapsortDescending(values, valuesOrder, values.length);

    // Ensure the values are in descending order
    for (i = 0; i < values.length-1; i++) {
      Assert.assertTrue(values[i] >= values[i + 1]);
    }
  }

}
