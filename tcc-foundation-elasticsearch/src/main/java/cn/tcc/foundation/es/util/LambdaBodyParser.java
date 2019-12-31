package cn.tcc.foundation.es.util;

import cn.tcc.foundation.es.search.EsCallback;
import com.trigersoft.jaque.expression.Expression;
import com.trigersoft.jaque.expression.LambdaExpression;
import com.trigersoft.jaque.expression.MemberExpression;
import com.trigersoft.jaque.expression.UnaryExpression;

import java.lang.reflect.Member;

public class LambdaBodyParser {

    public static <T> String getMethodName(EsCallback<T> c) {
        LambdaExpression<EsCallback<T>> parsed = LambdaExpression.parse(c);
        Expression method = parsed.getBody();

        while (method instanceof UnaryExpression) {
            method = ((UnaryExpression) method).getFirst();
        }
        Member member = ((MemberExpression) method).getMember();
        return member.getName().toLowerCase();
    }
}
