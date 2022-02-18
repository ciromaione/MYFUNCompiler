package visitor;

import ast.*;
import ast.expr.*;
import ast.stms.*;
import errors.*;
import scopingtable.ScopingItem;
import scopingtable.ScopingTable;
import typesystem.TypeSystem;

public class SemanticAnalyzerVisitor implements Visitor {
    private ScopingTable scopingTable;

    public SemanticAnalyzerVisitor(ScopingTable scopingTable) {
        this.scopingTable = scopingTable;
    }

    @Override
    public Object visit(ProgramOp programOp) {
        scopingTable.enterScope();
        if(programOp.varDeclOps != null) {
            for(VarDeclOp vd : programOp.varDeclOps) {
                vd.accept(this);
            }
        }
        if(programOp.funOps != null) {
            for (FunOp fo : programOp.funOps) {
                fo.accept(this);
            }
        }
        scopingTable.enterScope();
        programOp.bodyOp.accept(this);
        return null;
    }

    @Override
    public Object visit(FunOp funOp) {
        if(scopingTable.probe(funOp.id.idName))
            throw new IdAlreadyDeclaredError(funOp.id.name);
        scopingTable.addId(new ScopingItem(funOp.id.idName, funOp.parDeclOps, funOp.type));
        scopingTable.enterScope();
        if(funOp.parDeclOps != null) {
            for (ParDeclOp pd : funOp.parDeclOps) {
                pd.accept(this);
            }
        }
        funOp.bodyOp.functionIdName = funOp.id.idName;
        funOp.bodyOp.accept(this);

        if(funOp.type != null && !scopingTable.lookup(funOp.id.idName).getHasReturn())
            throw new RuntimeException("Function " + funOp.id.idName + " must return a " + funOp.type.toString() + "!");

        return null;
    }

    @Override
    public Object visit(BodyOp bodyOp) {
        if(bodyOp.varDeclOps != null) {
            for(VarDeclOp vd : bodyOp.varDeclOps) {
                vd.accept(this);
            }
        }
        if(bodyOp.statOps != null) {
            for (StatNode stat : bodyOp.statOps) {
                ((Node) stat).functionIdName = bodyOp.functionIdName;
                stat.accept(this);
            }
        }
        scopingTable.exitScope();

        return null;
    }

    @Override
    public Object visit(ParDeclOp parDeclOp) {
        if(scopingTable.probe(parDeclOp.id.idName))
            throw new IdAlreadyDeclaredError(parDeclOp.id.name);
        scopingTable.addId(new ScopingItem(parDeclOp.id.idName, parDeclOp.type));

        return null;
    }

    @Override
    public Object visit(VarDeclOp varDeclOp) {
        if(varDeclOp.type == Type.VAR) {
            for(IdInitOp initOp : varDeclOp.idInitOps) {
                if(scopingTable.probe(initOp.id.idName))
                    throw new IdAlreadyDeclaredError(initOp.id.idName);
                scopingTable.addId(new ScopingItem(initOp.id.idName, typeFromConst(initOp.expr)));
                initOp.accept(this);
            }
        } else {
            for(IdInitOp initOp : varDeclOp.idInitOps) {
                if(scopingTable.probe(initOp.id.idName))
                    throw new IdAlreadyDeclaredError(initOp.id.idName);
                scopingTable.addId(new ScopingItem(initOp.id.idName, varDeclOp.type));
                initOp.accept(this);
            }
        }

        return null;
    }

    @Override
    public Object visit(ReadOp readOp) {
        for(Identifier id : readOp.ids) {
            id.accept(this);
        }
        if(readOp.expr != null) {
            readOp.expr.accept(this);

            if(readOp.expr.getNodeType() != Type.STRING)
                throw new RuntimeException("The Read op has a " + readOp.expr.getNodeType().toString() + " type print argument, must be a string!");
        }

        return null;
    }

    @Override
    public Object visit(WriteOp writeOp) {
        writeOp.expr.accept(this);

        if(writeOp.expr.getNodeType() != Type.STRING)
            throw new RuntimeException("The Write op has a " + writeOp.expr.getNodeType().toString() + " type print argument, must be a string!");
        return null;
    }

    @Override
    public Object visit(AssignOp assignOp) {
        ScopingItem scopingItem = scopingTable.lookup(assignOp.id.idName);
        if(scopingItem == null)
            throw new VariableNotDeclaredError(assignOp.id.idName);
        assignOp.expr.accept(this);

        Type varType = scopingItem.getVariableType();
        Type exprType = assignOp.expr.getNodeType();
        if(varType != exprType && !(varType == Type.REAL && exprType == Type.INTEGER))
            throw new TypeAssignError(assignOp.id.idName, varType, exprType);
        return null;
    }

    @Override
    public Object visit(CallFunOp callFunOp) {
        ScopingItem scopingItem = scopingTable.lookup(callFunOp.id.idName);
        if(scopingItem == null)
            throw new VariableNotDeclaredError(callFunOp.id.idName);

        if(scopingItem.getReturnType() != null)
            callFunOp.setNodeType(scopingItem.getReturnType());

        if(callFunOp.paramOps != null) {
            for(ParamOp po : callFunOp.paramOps) {
                po.accept(this);
            }

            if(scopingItem.getParams() == null)
                throw new FunctionNotDeclaredError(callFunOp.paramOps, callFunOp.id.idName);
            if(scopingItem.getParams().size() != callFunOp.paramOps.size())
                throw new FunctionNotDeclaredError(callFunOp.paramOps, callFunOp.id.idName);
            for(int i=0; i<callFunOp.paramOps.size(); ++i) {
                if(callFunOp.paramOps.get(i).expr.getNodeType() != scopingItem.getParams().get(i).type)
                    throw new FunctionNotDeclaredError(callFunOp.paramOps, callFunOp.id.idName);
                if(callFunOp.paramOps.get(i).mode != scopingItem.getParams().get(i).mode)
                    throw new FunctionNotDeclaredError(callFunOp.paramOps, callFunOp.id.idName);
            }
        }
        else if(scopingItem.getParams() != null)
            throw new FunctionNotDeclaredError(callFunOp.paramOps, callFunOp.id.idName);
        return null;
    }

    @Override
    public Object visit(ParamOp paramOp) {
        paramOp.expr.accept(this);

        return null;
    }

    @Override
    public Object visit(WhileOp whileOp) {
        whileOp.bodyOp.functionIdName = whileOp.functionIdName;

        whileOp.condition.accept(this);

        if(whileOp.condition.getNodeType() != Type.BOOL)
            throw new ConditionNotBoolError("WHILE", whileOp.condition.getNodeType());

        scopingTable.enterScope();
        whileOp.bodyOp.accept(this);

        return null;
    }

    @Override
    public Object visit(IfStatOp ifStatOp) {
        ifStatOp.ifBodyOp.functionIdName = ifStatOp.functionIdName;

        ifStatOp.condition.accept(this);

        if(ifStatOp.condition.getNodeType() != Type.BOOL)
            throw new ConditionNotBoolError("IF", ifStatOp.condition.getNodeType());

        scopingTable.enterScope();
        ifStatOp.ifBodyOp.accept(this);

        if(ifStatOp.elifOpList != null) {
            for(ElifOp elif : ifStatOp.elifOpList) {
                elif.accept(this);
            }
        }

        if(ifStatOp.elseBodyOp != null) {
            ifStatOp.elseBodyOp.functionIdName = ifStatOp.functionIdName;

            scopingTable.enterScope();
            ifStatOp.elseBodyOp.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(AddOp addOp) {
        visitBinaryOp(addOp);
        return null;
    }

    @Override
    public Object visit(MulOp mulOp) {
        visitBinaryOp(mulOp);
        return null;
    }

    @Override
    public Object visit(DiffOp diffOp) {
        visitBinaryOp(diffOp);
        return null;
    }

    @Override
    public Object visit(DivOp divOp) {
        visitBinaryOp(divOp);
        return null;
    }

    @Override
    public Object visit(DivIntOp divIntOp) {
        visitBinaryOp(divIntOp);
        return null;
    }

    @Override
    public Object visit(PowOp powOp) {
        visitBinaryOp(powOp);
        return null;
    }

    @Override
    public Object visit(StrCatOp strCatOp) {
        visitBinaryOp(strCatOp);
        return null;
    }

    @Override
    public Object visit(AndOp andOp) {
        visitBinaryOp(andOp);
        return null;
    }

    @Override
    public Object visit(OrOp orOp) {
        visitBinaryOp(orOp);
        return null;
    }

    @Override
    public Object visit(GEOp geOp) {
        visitBinaryOp(geOp);
        return null;
    }

    @Override
    public Object visit(GTOp gtOp) {
        visitBinaryOp(gtOp);
        return null;
    }

    @Override
    public Object visit(EQOp eqOp) {
        visitBinaryOp(eqOp);
        return null;
    }

    @Override
    public Object visit(LEOp leOp) {
        visitBinaryOp(leOp);
        return null;
    }

    @Override
    public Object visit(LTOp ltOp) {
        visitBinaryOp(ltOp);
        return null;
    }

    @Override
    public Object visit(NEOp neOp) {
        visitBinaryOp(neOp);
        return null;
    }

    @Override
    public Object visit(UminusOp uminusOp) {
        uminusOp.right.accept(this);
        Type type = TypeSystem.getTypeForUminusOp(uminusOp);
        if(type == null)
            throw new RuntimeException("The argument of UminusOp is " + uminusOp.right.getNodeType().toString() + ", must be real or integer!");
        uminusOp.setNodeType(type);
        return null;
    }

    @Override
    public Object visit(NotOp notOp) {
        notOp.right.accept(this);
        Type type = TypeSystem.getTypeForNotOp(notOp);
        if(type == null)
            throw new RuntimeException("The argument of NotOp is " + notOp.right.getNodeType().toString() + ", must be bool!");
        notOp.setNodeType(type);
        return null;
    }

    @Override
    public Object visit(IntConst intConst) {
        intConst.setNodeType(Type.INTEGER);
        return null;
    }

    @Override
    public Object visit(RealConst realConst) {
        realConst.setNodeType(Type.REAL);
        return null;
    }

    @Override
    public Object visit(StringConst stringConst) {
        stringConst.setNodeType(Type.STRING);
        return null;
    }

    @Override
    public Object visit(BoolConst boolConst) {
        boolConst.setNodeType(Type.BOOL);
        return null;
    }

    @Override
    public Object visit(Identifier identifier) {
        ScopingItem scopingItem = scopingTable.lookup(identifier.idName);
        if(scopingItem == null)
            throw new VariableNotDeclaredError(identifier.idName);

        identifier.setNodeType(scopingItem.getVariableType());
        return null;
    }

    @Override
    public Object visit(IdInitOp idInitOp) {
        ScopingItem item = scopingTable.lookup(idInitOp.id.idName);
        idInitOp.setIdType(item.getVariableType());
        if(idInitOp.expr != null) {
            idInitOp.expr.accept(this);

            if(item == null)
                throw new VariableNotDeclaredError(idInitOp.id.idName);
            if(idInitOp.expr.getNodeType() != item.getVariableType())
                throw new TypeAssignError(idInitOp.id.idName, item.getVariableType(), idInitOp.expr.getNodeType());
        }
        return null;
    }

    @Override
    public Object visit(ReturnOp returnOp) {
        returnOp.expr.accept(this);

        ScopingItem scopingItem = scopingTable.lookup(returnOp.functionIdName);
        if(scopingItem == null)
            throw new RuntimeException("Return op must be in a function body!");

        if(scopingItem.getReturnType() != returnOp.expr.getNodeType())
            throw new RuntimeException("Return op in function '" + scopingItem.getIdName() + "' has a " + returnOp.expr.getNodeType().toString() + " argument, must be a " + scopingItem.getReturnType().toString() + "!");

        scopingItem.setHasReturn(true);
        return null;
    }

    @Override
    public Object visit(ElifOp elifOp) {
        elifOp.cond.accept(this);
        if(elifOp.cond.getNodeType() != Type.BOOL)
            throw new ConditionNotBoolError("elif", elifOp.cond.getNodeType());
        scopingTable.enterScope();
        elifOp.body.accept(this);
        return null;
    }

    private Type typeFromConst(ExprNode expr) {
        if(expr instanceof IntConst) return Type.INTEGER;
        else if(expr instanceof RealConst) return Type.REAL;
        else if(expr instanceof BoolConst) return Type.BOOL;
        else if(expr instanceof StringConst) return Type.STRING;
        else throw new RuntimeException("A VAR declaration must have an assignment to a constant value!");
    }

    private void visitBinaryOp(BinaryOpNode binaryOpNode) {
        binaryOpNode.right.accept(this);
        binaryOpNode.left.accept(this);

        Type type = TypeSystem.getTypeFromBinaryOp(binaryOpNode);
        if(type == null)
            throw new InvalidTypeForBinaryOpError(binaryOpNode.name, binaryOpNode.left.getNodeType(), binaryOpNode.right.getNodeType());
        binaryOpNode.setNodeType(type);
    }
}
