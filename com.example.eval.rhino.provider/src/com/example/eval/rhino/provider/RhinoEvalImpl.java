package com.example.eval.rhino.provider;


import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.osgi.service.component.annotations.Component;

import com.example.eval.api.Eval;
import org.apache.felix.service.command.CommandProcessor;

@Component(property = {
        CommandProcessor.COMMAND_SCOPE + "=rhino",
        CommandProcessor.COMMAND_FUNCTION + "=eval"
    },
    service=RhinoEvalImpl.class
)
public class RhinoEvalImpl implements Eval{

	@Override
	public double eval(String expression) throws Exception {
		Context cx = Context.enter();

		try {
			Scriptable scope = cx.initStandardObjects();
			Object result = cx.evaluateString(scope, expression, "?", 1, null);
			
			if(result instanceof Number) {
				return ((Number) result).doubleValue();
			}
			return Double.NaN;
		} finally {
			Context.exit();
		}
	}
}
