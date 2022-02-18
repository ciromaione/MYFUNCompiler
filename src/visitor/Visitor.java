package visitor;

import ast.*;
import ast.expr.*;
import ast.stms.*;

public interface Visitor {
    Object visit(ProgramOp programOp);

    Object visit(FunOp funOp);

    Object visit(BodyOp bodyOp);

    Object visit(ParDeclOp parDeclOp);

    Object visit(VarDeclOp varDeclOp);

    Object visit(ReadOp readOp);

    Object visit(WriteOp writeOp);

    Object visit(AssignOp assignOp);

    Object visit(CallFunOp callFunOp);

    Object visit(ParamOp paramOp);

    Object visit(WhileOp whileOp);

    Object visit(IfStatOp ifStatOp);

    Object visit(AddOp addOp);

    Object visit(MulOp mulOp);

    Object visit(DiffOp diffOp);

    Object visit(DivOp divOp);

    Object visit(DivIntOp divIntOp);

    Object visit(PowOp powOp);

    Object visit(StrCatOp strCatOp);

    Object visit(AndOp andOp);

    Object visit(OrOp orOp);

    Object visit(GEOp geOp);

    Object visit(GTOp gtOp);

    Object visit(EQOp eqOp);

    Object visit(LEOp leOp);

    Object visit(LTOp ltOp);

    Object visit(NEOp neOp);

    Object visit(UminusOp uminusOp);

    Object visit(NotOp notOp);

    Object visit(IntConst intConst);

    Object visit(RealConst realConst);

    Object visit(StringConst stringConst);

    Object visit(BoolConst boolConst);

    Object visit(Identifier identifier);

    Object visit(IdInitOp idInitOp);

    Object visit(ReturnOp returnOp);

    Object visit(ElifOp elifOp);
}
