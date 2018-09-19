package com.bpodgursky.jbool_expressions.rules;

import com.bpodgursky.jbool_expressions.Expression;
import com.bpodgursky.jbool_expressions.NExpression;

public class SimplifyNExpression<K> extends Rule<NExpression<K>, K> {

  @Override
  public Expression<K> applyInternal(NExpression<K> input, RuleSetCache<K> cache) {

    if(input.expressions.length == 1){
      return input.expressions[0];
    }

    return input;
  }

  @Override
  protected boolean isApply(Expression<K> input) {
    return input instanceof NExpression;
  }
}
