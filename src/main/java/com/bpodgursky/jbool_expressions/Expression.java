package com.bpodgursky.jbool_expressions;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bpodgursky.jbool_expressions.rules.Rule;

public abstract class Expression<K> implements Serializable {

  public static final Comparator<Expression> HASH_COMPARATOR = new HashComparator();

  public static final Comparator<Expression> LEXICOGRAPHIC_COMPARATOR = new LexicographicComparator();

  public static class HashComparator implements Comparator<Expression>  {
    @Override
    public int compare(Expression o1, Expression o2) {

      if(o1 == o2){
        return 0;
      }

      int compare = Integer.compare(o1.hashCode(), o2.hashCode());
      if(compare == 0 && !o1.equals(o2)) {
        // If hashcode matches and expressions are not equal then we may have a hash collision.
        // This is very unlikely to happen but if it does then go for string comparison (slow).
        return LEXICOGRAPHIC_COMPARATOR.compare(o1, o2);
      }
      return compare;
    }
  }

  public static class LexicographicComparator implements Comparator<Expression> {
    @Override
    public int compare(Expression o1, Expression o2) {
      return o1.toString().compareTo(o2.toString());
    }
  }

  public abstract Expression<K> apply(List<Rule<?, K>> rules);

  public abstract String getExprType();

  public abstract Expression<K> sort(Comparator<Expression> comparator);

  public String toLexicographicString(){
    return sort(LEXICOGRAPHIC_COMPARATOR).toString();
  }

  public Set<K> getAllK(){
    Set<K> variables =new HashSet<>();
    collectK(variables, Integer.MAX_VALUE);
    return variables;
  }

  public abstract void collectK(Set<K> set, int limit);

  public abstract Expression<K> replaceVars(Map<K,Expression<K>> m);
}
