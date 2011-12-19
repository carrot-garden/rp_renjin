package r.compiler.ir.tac.functions;

import r.compiler.ir.exception.InvalidSyntaxException;
import r.compiler.ir.tac.IRScopeBuilder;
import r.compiler.ir.tac.expressions.Constant;
import r.compiler.ir.tac.expressions.Expression;
import r.compiler.ir.tac.statements.GotoStatement;
import r.lang.FunctionCall;
import r.lang.Null;
import r.lang.Symbol;

public class BreakTranslator extends FunctionCallTranslator {

  @Override
  public Symbol getName() {
    return Symbol.get("break");
  }

  @Override
  public Expression translateToExpression(IRScopeBuilder builder,
      TranslationContext context, FunctionCall call) {

    addStatement(builder, context, call);
    return new Constant(Null.INSTANCE); 
  }

  @Override
  public void addStatement(IRScopeBuilder builder, TranslationContext context,
      FunctionCall call) {

    if(!(context instanceof LoopContext)) {
      throw new InvalidSyntaxException("`break` cannot be used outside of a loop");
    }
    LoopContext loopContext = (LoopContext)context;
    builder.addStatement( new GotoStatement(loopContext.getExitLabel()));
  }

}