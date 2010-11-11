/*
 * R : A Computer Language for Statistical Data Analysis
 * Copyright (C) 1995, 1996  Robert Gentleman and Ross Ihaka
 * Copyright (C) 1997--2008  The R Development Core Team
 * Copyright (C) 2003, 2004  The R Foundation
 * Copyright (C) 2010 bedatadriven
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package r.lang.primitive;

import r.lang.*;
import r.lang.exception.EvalException;
import r.lang.primitive.annotations.AlwaysNull;
import r.lang.primitive.annotations.Environment;
import r.parser.ParseUtil;

/**
 * Primitive type inspection and coercion functions
 */
public class Types {

  public static boolean isNull(SEXP exp) {
    return exp == NullExp.INSTANCE;
  }

  public static boolean isLogical(SEXP exp) {
    return exp instanceof LogicalExp;
  }

  public static boolean isInteger(SEXP exp) {
    return exp instanceof IntExp;
  }

  public static boolean isReal(SEXP exp) {
    return exp instanceof DoubleExp;
  }

  public static boolean isDouble(SEXP exp) {
    return exp instanceof DoubleExp;
  }

  public static boolean isComplex(SEXP exp) {
    return exp instanceof ComplexExp;
  }

  public static boolean isCharacter(SEXP exp) {
    return exp instanceof StringExp;
  }

  public static boolean isSymbol(SEXP exp) {
    return exp instanceof SymbolExp;
  }

  public static boolean isEnvironment(SEXP exp) {
    return exp instanceof EnvExp;
  }

  public static boolean isExpression(SEXP exp) {
    return exp instanceof EnvExp;
  }

  public static boolean isList(SEXP exp) {
    return exp instanceof ListExp ||
           exp.getClass() == PairListExp.class;
  }

  public static boolean isPairList(SEXP exp) {
    return exp instanceof PairList;
  }

  public static boolean isAtomic(SEXP exp) {
    return exp instanceof AtomicExp;
  }

  public static boolean isRecursive(SEXP exp) {
    return exp instanceof RecursiveExp;
  }

  public static boolean isNumeric(SEXP exp) {
    return (exp instanceof IntExp && !exp.inherits("factor")) ||
            exp instanceof LogicalExp ||
            exp instanceof DoubleExp;


  }

  public static boolean isCall(SEXP exp) {
    return exp instanceof LangExp;
  }

  public static boolean isLanguage(SEXP exp) {
    return exp instanceof SymbolExp ||
            exp instanceof LangExp ||
            exp instanceof ExpExp;

  }

  public static boolean isFunction(SEXP exp) {
    return exp instanceof FunExp;
  }

  public static boolean isSingle(SEXP exp) {
    throw new EvalException("type \"single\" unimplemented in R");
  }

  public static boolean isNA(double value) {
    return DoubleExp.isNA(value);
  }

  public static boolean isNaN(double value) {
    return DoubleExp.isNaN(value);
  }

  public static boolean isFinite(double value) {
    return !Double.isInfinite(value);
  }

  public static boolean isInfinite(double value) {
    return Double.isInfinite(value);
  }

  public static String asCharacter(String value) {
    return value;
  }

  public static DoubleExp asDouble(DoubleExp exp) {
    return exp;
  }

  public static double asDouble(int value) {
    return (double)value;
  }

  public static double asDouble(String value) {
    return ParseUtil.parseDouble(value);
  }

  public static int asInteger(double x) {
    return (int) x;
  }

  public static IntExp asInteger(IntExp exp) {
    return exp;
  }

  public static int asInteger(String x) {
    return (int)ParseUtil.parseDouble(x);
  }


  public static ListExp list(PairList arguments) {

    int n = arguments.length();
    SEXP values[] = new SEXP[n];
    String names[] = new String[n];

    int index=0;
    boolean haveNames = false;

    for(PairListExp arg : arguments.listNodes()) {
      values[index] = arg.getValue();
      if(arg.hasTag()) {
        names[index] = arg.getTag().getPrintName();
        haveNames = true;
      } else {
        names[index] = "";
      }
      index++;
    }

    if(haveNames) {
      return new ListExp(values, PairListExp.buildList(SymbolExp.NAMES, new StringExp(names)).list());
    } else {
      return new ListExp(values);
    }
  }

  public static SEXP subset$(ListExp list, SymbolExp symbol) {
      return list.get(symbol.getPrintName());
  }

  public static SEXP subset$(EnvExp environment, SymbolExp symbol) {
      return environment.getVariable(symbol);
  }

  @AlwaysNull
  public static SEXP subset$(NullExp nil) {
    return NullExp.INSTANCE;
  }


  public static EnvExp environment(@Environment EnvExp rho) {
    return rho.getGlobalContext().getGlobalEnvironment();
  }

  public static EnvExp environment(ClosureExp arg) {
    return arg.getEnclosingEnvironment();
  }

  public static NullExp environment(SEXP exp) {
    return NullExp.INSTANCE;
  }

  public static EnvExp parentFrame(@Environment EnvExp rho, int n) {
    EnvExp parent = rho;
    while(n > 0 && rho != rho.getGlobalContext().getGlobalEnvironment()) {
      parent = rho.getParent();
      --n;
    }
    return parent;
  }

  public static EnvExp newEnv(boolean hash, EnvExp parent, int size) {
    return new EnvExp(parent);      
  }

  public static EnvExp baseEnv(@Environment EnvExp rho) {
    return rho.getGlobalContext().getBaseEnvironment();
  }

  public static int length(SEXP exp) {
    return exp.length();
  }
}