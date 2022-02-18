package errors;

import ast.ParDeclMode;
import ast.stms.ParamOp;

import java.util.List;

public class FunctionNotDeclaredError extends RuntimeException {
    public FunctionNotDeclaredError(List<ParamOp> paramOps, String id) {
        super(FunctionNotDeclaredError.createMessage(paramOps, id));
    }

    private static String createMessage(List<ParamOp> paramOps, String id) {
        if(paramOps == null)
            return "The function '" + id + "', with params () is not declared in this scope!";
        String [] pars = new String[paramOps.size()];
        for(int i=0; i<paramOps.size(); ++i) {
            String mode = "";
            if(paramOps.get(i).mode == ParDeclMode.OUT) mode = "out ";
            pars[i] = mode + paramOps.get(i).expr.getNodeType().toString();
        }
        return "The function '" + id + "', with params (" + String.join(", ", pars) + ") is not declared in this scope!";
    }
}
