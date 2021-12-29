package com.example.eval.simple.provider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.eval.api.Eval;
import org.osgi.service.component.annotations.Component;
import org.apache.felix.service.command.CommandProcessor;


@Component(property = {
        CommandProcessor.COMMAND_SCOPE + "=simple",
        CommandProcessor.COMMAND_FUNCTION + "=eval"
    },
    service=EvalImpl.class
)
public class EvalImpl implements Eval {
	
    Pattern EXPR = Pattern.compile( "\\s*(?<left>\\d+)\\s*(?<op>\\+|-)\\s*(?<right>\\d+)\\s*");

    @Override
    public double eval(String expression) throws Exception {
        Matcher m = EXPR.matcher(expression);
        if ( !m.matches())
            return Double.NaN;
        double left = Double.valueOf( m.group("left"));
        double right = Double.valueOf( m.group("right"));
        switch( m.group("op")) {
          case "+": return left + right;
          case "-": return left - right;
        }
        return Double.NaN;

	}
}
