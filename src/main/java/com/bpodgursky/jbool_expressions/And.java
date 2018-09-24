package com.bpodgursky.jbool_expressions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;



import static com.bpodgursky.jbool_expressions.Seeds.AND_SEED;

public class And<K> extends NExpression<K> {
  public static final String EXPR_TYPE = "and";
  private String cachedStringRepresentation = null;

  public static <K> And<K> of(Expression<K>[] children, Comparator<Expression> comparator) {
    return new And<K>(children, comparator);
  }

  private And(Expression<K>[] children, Comparator<Expression> comparator) {
    super(children, AND_SEED, comparator);
  }

  @Override
  public NExpression<K> create(Expression<K>[] children, Comparator<Expression> comparator) {
    return of(children, comparator);
  }

  public String toString() {
    if (cachedStringRepresentation == null) {
      StringBuilder builder = new StringBuilder();
      builder.append('(');
      for (int i=0;i<expressions.length;i++) {
        Expression<K> expression = expressions[i];
        builder.append(expression.toString());
        if(i != expressions.length-1){
          builder.append(" & ");
        }
      }
      builder.append(')');
      cachedStringRepresentation = builder.toString();
    }
    return cachedStringRepresentation;
  }

  public static <K> And<K> of(Expression<K> child1, Expression<K> child2, Expression<K> child3) {
    return of(ExprUtil.<K>list(child1, child2, child3));
  }

  public static <K> And<K> of(Expression<K> child1, Expression<K> child2) {
    return of(ExprUtil.<K>list(child1, child2));
  }

  @SafeVarargs
  public static <K> And<K> of(Expression<K>... children) {
    return new And<>(children, HASH_COMPARATOR);
  }

  public static <K> And<K> of(Expression<K> child1) {
    return of(ExprUtil.<K>list(child1));
  }

  public static <K> And<K> of(List<? extends Expression<K>> children) {
    return new And<K>(children.toArray(new Expression[children.size()]), HASH_COMPARATOR);
  }

  @Override
  public String getExprType() {
    return EXPR_TYPE;
  }

  public Expression<K> replaceVars(Map<K, Expression<K>> m) {
    Expression<K>[] children = new Expression[this.expressions.length];
    for (int i = 0; i < this.expressions.length; i++) {
      children[i] = this.expressions[i].replaceVars(m);
    }
    return of(children);
  }
}
