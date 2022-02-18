package visitor;

import ast.*;
import ast.expr.*;
import ast.stms.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CTranspilerVisitor implements Visitor {
    private StringBuffer program;
    private int currentIdIndex;
    private Map<String, String> idMap;
    private final int bufferSize = 256;
    private int currentStringIndex;
    private Map<String, String> functionBeforeReturnOps;

    public CTranspilerVisitor() {
        this.program = new StringBuffer();
        this.idMap = new HashMap<>();
        this.currentIdIndex = 0;
        this.currentStringIndex = 0;
        this.functionBeforeReturnOps = new HashMap<>();
    }

    public void printToFile(String path) throws IOException {
        BufferedWriter bwr = new BufferedWriter(new FileWriter(path));
        bwr.write(program.toString());
        bwr.flush();
        bwr.close();
    }

    @Override
    public Object visit(ProgramOp programOp) {
        StringBuffer programOpBuff = new StringBuffer();
        if(programOp.varDeclOps != null) {
            for(VarDeclOp vd : programOp.varDeclOps) {
                String decl = (String) vd.accept(this);
                programOpBuff.append(decl);
            }
        }

        if(programOp.funOps != null) {
            for(FunOp fo : programOp.funOps) {
                String fun = (String) fo.accept(this);
                programOpBuff.append(fun);
            }
        }

        String body = (String) programOp.bodyOp.accept(this);
        programOpBuff.append("int main() {\n" + body + "return 0;\n}");

        program.append(customOps());
        program.append(programOpBuff);
        String libs = "#include <stdio.h>\n#include <string.h>\n#include <math.h>\n";
        program.insert(0, libs);

        return null;
    }

    @Override
    public Object visit(FunOp funOp) {
        StringBuffer function = new StringBuffer();
        function.append(getCTypeParam(funOp.type) + " " + getCId(funOp.id.idName) + "(");

        StringBuffer initOutPars = new StringBuffer();
        StringBuffer assignOutPars = new StringBuffer();
        if(funOp.parDeclOps != null) {
            ArrayList<String> parDecls = new ArrayList<>();
            for(ParDeclOp po : funOp.parDeclOps) {
                String decl = (String) po.accept(this);
                parDecls.add(decl);
                //uso delle variabili temporanee per i parametri di tipo out (le stringhe in C sono già passate per riferimento)
                if(po.mode == ParDeclMode.OUT && po.type != Type.STRING) {
                    initOutPars.append(getDeclString(po.type, po.id.idName))
                            .append("=*").append(getCId(po.id.idName)).append("_out;\n");
                    assignOutPars.append("*").append(getCId(po.id.idName)).append("_out")
                            .append("=").append(getCId(po.id.idName)).append(";\n");
                }
            }
            function.append(String.join(", ", parDecls));
        }

        // metto in questa mappa le operazioni da fare prima di ogni return
        functionBeforeReturnOps.put(funOp.id.idName, assignOutPars.toString());

        String body = (String) funOp.bodyOp.accept(this);

        function.append(") {\n")
                .append(initOutPars)
                .append(body)
                .append(assignOutPars)
                .append("}\n");
        return function.toString();
    }

    @Override
    public Object visit(BodyOp bodyOp) {
        StringBuffer body = new StringBuffer();
        if(bodyOp.varDeclOps != null) {
            for(VarDeclOp vd : bodyOp.varDeclOps) {
                String decl = (String) vd.accept(this);
                body.append(decl);
            }
        }

        if(bodyOp.statOps != null) {
            for(StatNode so : bodyOp.statOps) {
                String stat = (String) so.accept(this);
                body.append(stat);
                if(so instanceof CallFunOp) // se call fun è chiamata non all'interno di una espressione devo aggiungere il punto e virgola
                    body.append(";\n");
            }
        }
        return body.toString();
    }

    @Override
    public Object visit(ParDeclOp parDeclOp) {
        String mode = " ";
        String out = "";
        if(parDeclOp.type != Type.STRING && parDeclOp.mode == ParDeclMode.OUT) {
            mode = " *"; //puntatore
            out = "_out"; //per creare una variabile temporanea
        }
        return getCTypeParam(parDeclOp.type) + mode + getCId(parDeclOp.id.idName) + out;
    }

    @Override
    public Object visit(VarDeclOp varDeclOp) {
        StringBuffer decls = new StringBuffer();
        if(varDeclOp.idInitOps != null) {
            for(IdInitOp init : varDeclOp.idInitOps) {
                String decl = (String) init.accept(this);
                decls.append(decl);
            }
        }
        return decls.toString();
    }

    @Override
    public Object visit(ReadOp readOp) {
        StringBuffer readBuffer = new StringBuffer();
        if(readOp.expr != null) {
            String message = (String) readOp.expr.accept(this);
            readBuffer.append("printf(\"%s\"," + message + ");\n");
        }
        ArrayList<String> typeFormats = new ArrayList<>();
        ArrayList<String> idsStrings = new ArrayList<>();
        for(Identifier id : readOp.ids) {
            typeFormats.add(getPrintfTypeFormat(id.getNodeType()));
            String ampersand = id.getNodeType() == Type.STRING ? "" : "&";
            idsStrings.add(ampersand + getCId(id.idName));
        }
        readBuffer.append("scanf(\"")
                .append(String.join("", typeFormats))
                .append("\",")
                .append(String.join(",", idsStrings))
                .append(");\n");
        return readBuffer.toString();
    }

    @Override
    public Object visit(WriteOp writeOp) {
        StringBuffer writeBuffer = new StringBuffer();
        String appendChar = switch(writeOp.writeType) {
            case WRITE -> "";
            case WRITELN -> "\\r\\n";
            case WRITEB -> " ";
            case WRITET -> "\\t";
        };
        String message = (String) writeOp.expr.accept(this);
        writeBuffer.append("printf(\"%s")
                .append(appendChar)
                .append("\",")
                .append(message)
                .append(");\n");
        return writeBuffer.toString();
    }

    @Override
    public Object visit(AssignOp assignOp) {
        String expr = (String) assignOp.expr.accept(this);
        if(assignOp.expr.getNodeType() == Type.STRING)
            return "strcpy(" + getCId(assignOp.id.idName) + ", " + expr + ");\n";
        return getCId(assignOp.id.idName) + "=" + expr + ";\n";
    }

    @Override
    public Object visit(CallFunOp callFunOp) {
        StringBuffer callFunBuff = new StringBuffer();
        callFunBuff.append(getCId(callFunOp.id.idName)).append("(");
        if(callFunOp.paramOps != null) {
            ArrayList<String> params = new ArrayList<>();
            for(ParamOp po : callFunOp.paramOps) {
                String param = (String) po.accept(this);
                params.add(param);
            }
            callFunBuff.append(String.join(",", params));
        }
        callFunBuff.append(")");
        return callFunBuff.toString();
    }

    @Override
    public Object visit(ParamOp paramOp) {
        String param = (String) paramOp.expr.accept(this);
        String ampersand = "";
        if(paramOp.mode == ParDeclMode.OUT && paramOp.expr.getNodeType() != Type.STRING)
            ampersand = "&";
        return ampersand + param;
    }

    @Override
    public Object visit(WhileOp whileOp) {
        StringBuffer whileBuff = new StringBuffer();
        whileBuff.append("while(");
        String cond = (String) whileOp.condition.accept(this);
        whileBuff.append(cond).append(") {\n");
        String body = (String) whileOp.bodyOp.accept(this);
        whileBuff.append(body).append("\n}\n");
        return whileBuff.toString();
    }

    @Override
    public Object visit(IfStatOp ifStatOp) {
        StringBuffer ifBuff = new StringBuffer();
        ifBuff.append("if(");
        String cond = (String) ifStatOp.condition.accept(this);
        ifBuff.append(cond).append(") {\n");
        String bodyIf = (String) ifStatOp.ifBodyOp.accept(this);
        ifBuff.append(bodyIf).append("\n}\n");

        if(ifStatOp.elifOpList != null){
            for (ElifOp elif : ifStatOp.elifOpList) {
                ifBuff.append((String) elif.accept(this));
            }
        }

        if(ifStatOp.elseBodyOp != null) {
            String bodyElse = (String) ifStatOp.elseBodyOp.accept(this);
            ifBuff.append("else {\n").append(bodyElse).append("\n}\n");
        }
        return ifBuff.toString();
    }

    @Override
    public Object visit(AddOp addOp) {
        return getCClassicBynaryOp(addOp, "+");
    }

    @Override
    public Object visit(MulOp mulOp) {
        return getCClassicBynaryOp(mulOp, "*");
    }

    @Override
    public Object visit(DiffOp diffOp) {
        return getCClassicBynaryOp(diffOp, "-");
    }

    @Override
    public Object visit(DivOp divOp) {
        return getCClassicBynaryOp(divOp, "/");
    }

    @Override
    public Object visit(DivIntOp divIntOp) {
        String leftExpr = (String) divIntOp.left.accept(this);
        String rightExpr = (String) divIntOp.right.accept(this);
        return "((int) " + leftExpr + "/" + rightExpr + ")";
    }

    @Override
    public Object visit(PowOp powOp) {
        String leftExpr = (String) powOp.left.accept(this);
        String rightExpr = (String) powOp.right.accept(this);
        String cast = "";
        if(powOp.left.getNodeType() == Type.INTEGER && powOp.right.getNodeType() == Type.INTEGER)
            cast = "(int) ";
        return "(" + cast + "pow(" + leftExpr + "," + rightExpr + "))";
    }

    @Override
    public Object visit(StrCatOp strCatOp) {
        String leftExpr = (String) strCatOp.left.accept(this);
        String rightExpr = (String) strCatOp.right.accept(this);

        String s1 = exprToString(leftExpr, strCatOp.left.getNodeType());
        String s2 = exprToString(rightExpr, strCatOp.right.getNodeType());

        String newString = createNewString();

        return strConcatName + "(" + s1 + "," + s2 + "," + newString + ")";
    }

    @Override
    public Object visit(AndOp andOp) {
        String leftExpr = (String) andOp.left.accept(this);
        String rightExpr = (String) andOp.right.accept(this);
        return "(" + leftExpr + "&&" + rightExpr + ")";
    }

    @Override
    public Object visit(OrOp orOp) {
        String leftExpr = (String) orOp.left.accept(this);
        String rightExpr = (String) orOp.right.accept(this);
        return "(" + leftExpr + "||" + rightExpr + ")";
    }

    @Override
    public Object visit(GEOp geOp) {
        String leftExpr = (String) geOp.left.accept(this);
        String rightExpr = (String) geOp.right.accept(this);
        if(geOp.left.getNodeType() == Type.STRING && geOp.right.getNodeType() == Type.STRING)
            return "(strcmp(" + leftExpr + ", " + rightExpr + ") >= 0)";

        return "(" + leftExpr + ">=" + rightExpr + ")";
    }

    @Override
    public Object visit(GTOp gtOp) {
        String leftExpr = (String) gtOp.left.accept(this);
        String rightExpr = (String) gtOp.right.accept(this);
        if(gtOp.left.getNodeType() == Type.STRING && gtOp.right.getNodeType() == Type.STRING)
            return "(strcmp(" + leftExpr + ", " + rightExpr + ") > 0)";

        return "(" + leftExpr + ">" + rightExpr + ")";
    }

    @Override
    public Object visit(EQOp eqOp) {
        String leftExpr = (String) eqOp.left.accept(this);
        String rightExpr = (String) eqOp.right.accept(this);
        if(eqOp.left.getNodeType() == Type.STRING && eqOp.right.getNodeType() == Type.STRING)
            return "(strcmp(" + leftExpr + ", " + rightExpr + ") == 0)";

        return "(" + leftExpr + "==" + rightExpr + ")";
    }

    @Override
    public Object visit(LEOp leOp) {
        String leftExpr = (String) leOp.left.accept(this);
        String rightExpr = (String) leOp.right.accept(this);
        if(leOp.left.getNodeType() == Type.STRING && leOp.right.getNodeType() == Type.STRING)
            return "(strcmp(" + leftExpr + ", " + rightExpr + ") <= 0)";

        return "(" + leftExpr + "<=" + rightExpr + ")";
    }

    @Override
    public Object visit(LTOp ltOp) {
        String leftExpr = (String) ltOp.left.accept(this);
        String rightExpr = (String) ltOp.right.accept(this);
        if(ltOp.left.getNodeType() == Type.STRING && ltOp.right.getNodeType() == Type.STRING)
            return "(strcmp(" + leftExpr + ", " + rightExpr + ") < 0)";

        return "(" + leftExpr + "<" + rightExpr + ")";
    }

    @Override
    public Object visit(NEOp neOp) {
        String leftExpr = (String) neOp.left.accept(this);
        String rightExpr = (String) neOp.right.accept(this);
        if(neOp.left.getNodeType() == Type.STRING && neOp.right.getNodeType() == Type.STRING)
            return "(strcmp(" + leftExpr + ", " + rightExpr + ") != 0)";

        return "(" + leftExpr + "!=" + rightExpr + ")";
    }

    @Override
    public Object visit(UminusOp uminusOp) {
        String arg = (String) uminusOp.right.accept(this);
        return "(- " + arg + ")";
    }

    @Override
    public Object visit(NotOp notOp) {
        String arg = (String) notOp.right.accept(this);
        return "(! " + arg + ")";
    }

    @Override
    public Object visit(IntConst intConst) {
        return String.valueOf(intConst.value);
    }

    @Override
    public Object visit(RealConst realConst) {
        return String.valueOf(realConst.value);
    }

    @Override
    public Object visit(StringConst stringConst) {
        return "\"" + stringConst.value + "\"";
    }

    @Override
    public Object visit(BoolConst boolConst) {
        if(boolConst.value)
            return String.valueOf(1);
        return String.valueOf(0);
    }

    @Override
    public Object visit(Identifier identifier) {
        return getCId(identifier.idName);
    }

    @Override
    public Object visit(IdInitOp idInitOp) {
        if(idInitOp.getIdType() == Type.STRING) {
            String string = createNewString();
            idMap.put(idInitOp.id.idName, string);
            String init = "";
            if(idInitOp.expr != null) {
                String expr = (String) idInitOp.expr.accept(this);
                init = "strcpy(" + string + "," + expr + ");\n";
            }
            return init;
        }
        String init = "";
        if(idInitOp.expr != null) {
            String expr = (String) idInitOp.expr.accept(this);
            init = "=" + expr;
        }
        return getDeclString(idInitOp.getIdType(), idInitOp.id.idName) + init + ";\n";
    }

    @Override
    public Object visit(ReturnOp returnOp) {
        String expr = (String) returnOp.expr.accept(this);
        String ops = "";
        if(functionBeforeReturnOps.containsKey(returnOp.functionIdName)) {
            ops = functionBeforeReturnOps.get(returnOp.functionIdName);
        }
        return ops + "return " + expr + ";\n";
    }

    @Override
    public Object visit(ElifOp elifOp) {
        StringBuffer elif = new StringBuffer();
        elif.append("else if(")
                .append((String) elifOp.cond.accept(this))
                .append(") {\n")
                .append((String) elifOp.body.accept(this))
                .append("\n}\n");
        return elif.toString();
    }

    private String getCId(String id) {
        if(idMap.containsKey(id))
            return idMap.get(id);
        String newId = "id_" + currentIdIndex;
        currentIdIndex++;
        idMap.put(id, newId);
        return newId;
    }

    private String getCTypeParam(Type t) {
        if(t == null) return "void";
        return switch(t) {
            case INTEGER, BOOL -> "int";
            case REAL -> "float";
            case STRING -> "char*";
            default -> throw new RuntimeException("Unexpected c type conversion!");
        };
    }

    private String getDeclString(Type t, String id) {
        return switch(t) {
            case INTEGER, BOOL -> "int " + getCId(id);
            case REAL -> "float " + getCId(id);
            case STRING -> "char " + getCId(id) + "[" + bufferSize + "]";
            default -> throw new RuntimeException("Unexpected c type declaration!");
        };
    }

    private String getPrintfTypeFormat(Type t) {
        return switch(t) {
            case INTEGER, BOOL -> "%d";
            case REAL -> "%f";
            case STRING -> "%s";
            default -> throw new RuntimeException("Unexpected c type conversion for formatted string!");
        };
    }

    private String getCClassicBynaryOp(BinaryOpNode node, String op) {
        String leftExpr = (String) node.left.accept(this);
        String rightExpr = (String) node.right.accept(this);
        return "(" + leftExpr + op + rightExpr + ")";
    }

    private String createNewString() {
        String id = "_string_" + currentStringIndex;
        currentStringIndex++;
        program.append("char " + id + "[" + bufferSize + "];\n");
        return id;
    }

    private final String strConcatName = "str_concat";
    private final String intToStringName = "int_to_string";
    private final String realToStringName = "real_to_string";
    private final String boolToStringName = "bool_to_string";
    private String customOps() {
        // return the content of the file custom_ops.c as string
        return "char* " + strConcatName + "(char *s1, char *s2, char *dest) {\n" +
                "    char _s1[" + bufferSize + "];\n" +
                "    char _s2[" + bufferSize + "];\n" +
                "    strcpy(_s1, s1);\n" +
                "    strcpy(_s2, s2);\n" +
                "    strcat(_s1, _s2);\n" +
                "    strcpy(dest, _s1);\n" +
                "    return dest;\n" +
                "}\n" +
                "\n" +
                "char* " + intToStringName + "(int a, char *dest) {\n" +
                "    sprintf(dest, \"%d\", a);\n" +
                "    return dest;\n" +
                "}\n" +
                "\n" +
                "char* " + realToStringName + "(float a, char *dest) {\n" +
                "    sprintf(dest, \"%f\", a);\n" +
                "    return dest;\n" +
                "}\n" +
                "\n" +
                "char* " + boolToStringName + "(int a) {\n" +
                "    if(a) return \"true\";\n" +
                "    return \"false\";\n" +
                "}";
    }

    private String exprToString(String expr, Type type) {
        String id;
        switch (type) {
            case REAL:
                id = createNewString();
                return realToStringName + "(" + expr + "," + id + ")";
            case INTEGER:
                id = createNewString();
                return intToStringName + "(" + expr + "," + id + ")";
            case BOOL:
                return boolToStringName + "(" + expr + ")";
            case STRING:
                return expr;
            default:
                throw new RuntimeException("Unespected type to string conversion!");
        }
    }
}
