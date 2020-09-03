package cn.xzxy.lewy.expression;


import org.junit.jupiter.api.Test;

public class ExpressionEvaluatorTest {

    @Test
    public void testParseExpression() {

        Object eval = ExpressionEvaluator.eval("1+2+3+4");
        System.out.println(eval.toString());
    }
}
