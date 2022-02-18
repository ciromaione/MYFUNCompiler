package visitor;

import ast.*;
import ast.expr.*;
import ast.stms.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLGenVisitor implements Visitor {
    private Document document;

    public XMLGenVisitor() throws ParserConfigurationException {
        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    }

    public void printToFile(String path) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(new File(path)));
    }

    @Override
    public Object visit(ProgramOp programOp) {
        // <ProgramOp>
        Element programOpTag = document.createElement(programOp.name);
        // <VarDeclOp> list
        if(programOp.varDeclOps != null) {
            for(VarDeclOp vdo : programOp.varDeclOps) {
                programOpTag.appendChild((Element) vdo.accept(this));
            }
        }
        // <FunOp> list
        if(programOp.funOps != null) {
            for(FunOp fo : programOp.funOps) {
                programOpTag.appendChild((Element) fo.accept(this));
            }
        }
        // <BodyOp>
        programOpTag.appendChild((Node) programOp.bodyOp.accept(this));

        document.appendChild(programOpTag);
        return programOpTag;
    }

    @Override
    public Object visit(FunOp funOp) {
        // <FunOp>
        Element funOpTag = document.createElement(funOp.name);
        // identifier
        funOpTag.appendChild((Node) funOp.id.accept(this));
        // <ParDeclOp>
        if(funOp.parDeclOps != null) {
            for (ParDeclOp pdo : funOp.parDeclOps) {
                funOpTag.appendChild((Node) pdo.accept(this));
            }
        }
        // <TypeOp>
        if(funOp.type != null) {
            funOpTag.appendChild(getTypeTag(funOp.type));
        }
        // <BodyOp>
        funOpTag.appendChild((Node) funOp.bodyOp.accept(this));

        return funOpTag;
    }

    @Override
    public Object visit(BodyOp bodyOp) {
        Element bodyOpTag = document.createElement(bodyOp.name);
        // <VarDeclOp>
        if(bodyOp.varDeclOps != null) {
            for (VarDeclOp vdo : bodyOp.varDeclOps) {
                bodyOpTag.appendChild((Node) vdo.accept(this));
            }
        }
        // Stats
        if(bodyOp.statOps != null) {
            for (StatNode s : bodyOp.statOps) {
                bodyOpTag.appendChild((Node) s.accept(this));
            }
        }
        return bodyOpTag;
    }

    @Override
    public Object visit(ParDeclOp parDeclOp) {
        Element parDeclOpTag = document.createElement(parDeclOp.name);
        // <ModeOp>
        parDeclOpTag.appendChild(getParDeclModeTag(parDeclOp.mode));
        // <TypeOp>
        parDeclOpTag.appendChild(getTypeTag(parDeclOp.type));
        // identifier
        parDeclOpTag.appendChild((Node) parDeclOp.id.accept(this));

        return parDeclOpTag;
    }

    @Override
    public Object visit(VarDeclOp varDeclOp) {
        // <VarDeclOp>
        Element varDeclOpTag = document.createElement(varDeclOp.name);
        // <TypeOp>
        varDeclOpTag.appendChild(getTypeTag(varDeclOp.type));
        // <IdInitOp>
        if(varDeclOp.idInitOps != null) {
            for(IdInitOp initOp: varDeclOp.idInitOps) {
                varDeclOpTag.appendChild((Node) initOp.accept(this));
            }
        }

        return varDeclOpTag;
    }

    @Override
    public Object visit(ReadOp readOp) {
        Element readOpTag = document.createElement(readOp.name);
        // identifiers
        for(Identifier id : readOp.ids) {
            readOpTag.appendChild((Node) id.accept(this));
        }
        // expr
        if(readOp.expr != null) {
            readOpTag.appendChild((Node) readOp.expr.accept(this));
        }
        return readOpTag;
    }

    @Override
    public Object visit(WriteOp writeOp) {
        Element writeOpTag = document.createElement(writeOp.name);
        // write mode
        writeOpTag.appendChild(document.createTextNode("(" + writeOp.writeType.toString() + ")"));
        // expr
        writeOpTag.appendChild((Node) writeOp.expr.accept(this));

        return writeOpTag;
    }

    @Override
    public Object visit(AssignOp assignOp) {
        Element assignOpTag = document.createElement(assignOp.name);
        // Identifier
        assignOpTag.appendChild((Node) assignOp.id.accept(this));
        // expr
        assignOpTag.appendChild((Node) assignOp.expr.accept(this));

        return assignOpTag;
    }

    @Override
    public Object visit(CallFunOp callFunOp) {
        Element callFunOpTag = document.createElement(callFunOp.name);
        //id
        callFunOpTag.appendChild((Node) callFunOp.id.accept(this));
        // <ParamOp>
        if(callFunOp.paramOps != null) {
            for (ParamOp p : callFunOp.paramOps) {
                callFunOpTag.appendChild((Node) p.accept(this));
            }
        }

        return callFunOpTag;
    }

    @Override
    public Object visit(ParamOp paramOp) {
        Element paramOpTag = document.createElement(paramOp.name);
        // mode
        paramOpTag.appendChild(getParDeclModeTag(paramOp.mode));
        // expr
        paramOpTag.appendChild((Node) paramOp.expr.accept(this));

        return paramOpTag;
    }

    @Override
    public Object visit(WhileOp whileOp) {
        Element whileOpTag = document.createElement(whileOp.name);
        // condition
        whileOpTag.appendChild((Node) whileOp.condition.accept(this));
        // body
        whileOpTag.appendChild((Node) whileOp.bodyOp.accept(this));

        return whileOpTag;
    }

    @Override
    public Object visit(IfStatOp ifStatOp) {
        Element ifStatOpTag = document.createElement(ifStatOp.name);
        // condition
        ifStatOpTag.appendChild((Node) ifStatOp.condition.accept(this));
        // if body
        ifStatOpTag.appendChild((Node) ifStatOp.ifBodyOp.accept(this));
        // elif
        if(ifStatOp.elifOpList != null) {
            for(ElifOp elif : ifStatOp.elifOpList) {
                ifStatOpTag.appendChild((Element) elif.accept(this));
            }
        }
        // else body
        if(ifStatOp.elseBodyOp != null) {
            ifStatOpTag.appendChild((Node) ifStatOp.elseBodyOp.accept(this));
        }

        return ifStatOpTag;
    }

    @Override
    public Object visit(AddOp addOp) {
        return createBinaryOpTag(addOp);
    }

    @Override
    public Object visit(MulOp mulOp) {
        return createBinaryOpTag(mulOp);
    }

    @Override
    public Object visit(DiffOp diffOp) {
        return createBinaryOpTag(diffOp);
    }

    @Override
    public Object visit(DivOp divOp) {
        return createBinaryOpTag(divOp);
    }

    @Override
    public Object visit(DivIntOp divIntOp) {
        return createBinaryOpTag(divIntOp);
    }

    @Override
    public Object visit(PowOp powOp) {
        return createBinaryOpTag(powOp);
    }

    @Override
    public Object visit(StrCatOp strCatOp) {
        return createBinaryOpTag(strCatOp);
    }

    @Override
    public Object visit(AndOp andOp) {
        return createBinaryOpTag(andOp);
    }

    @Override
    public Object visit(OrOp orOp) {
        return createBinaryOpTag(orOp);
    }

    @Override
    public Object visit(GEOp geOp) {
        return createBinaryOpTag(geOp);
    }

    @Override
    public Object visit(GTOp gtOp) {
        return createBinaryOpTag(gtOp);
    }

    @Override
    public Object visit(EQOp eqOp) {
        return createBinaryOpTag(eqOp);
    }

    @Override
    public Object visit(LEOp leOp) {
        return createBinaryOpTag(leOp);
    }

    @Override
    public Object visit(LTOp ltOp) {
        return createBinaryOpTag(ltOp);
    }

    @Override
    public Object visit(NEOp neOp) {
        return createBinaryOpTag(neOp);
    }

    @Override
    public Object visit(UminusOp uminusOp) {
        Element uminusOpTag = document.createElement(uminusOp.name);
        uminusOpTag.appendChild((Node) uminusOp.right.accept(this));
        return uminusOpTag;
    }

    @Override
    public Object visit(NotOp notOp) {
        Element nonOpTag = document.createElement(notOp.name);
        nonOpTag.appendChild((Node) notOp.right.accept(this));
        return nonOpTag;
    }

    @Override
    public Object visit(IntConst intConst) {
        return document.createTextNode("(" + intConst.name + ", " + intConst.value + ")");
    }

    @Override
    public Object visit(RealConst realConst) {
        return document.createTextNode("(" + realConst.name + ", " + realConst.value + ")");
    }

    @Override
    public Object visit(StringConst stringConst) {
        return document.createTextNode("(" + stringConst.name + ", \"" + stringConst.value + "\")");
    }

    @Override
    public Object visit(BoolConst boolConst) {
        return document.createTextNode("(" + boolConst.name + ", " + boolConst.value + ")");
    }

    @Override
    public Object visit(Identifier identifier) {
        return document.createTextNode(identifier.toString());
    }

    @Override
    public Object visit(IdInitOp idInitOp) {
        if(idInitOp.expr != null) {
            Element idInitOpTag = document.createElement(idInitOp.name);
            // id
            idInitOpTag.appendChild((Node) idInitOp.id.accept(this));
            // expr
            idInitOpTag.appendChild((Node) idInitOp.expr.accept(this));
            return idInitOpTag;
        }
        else {
            return idInitOp.id.accept(this);
        }
    }

    @Override
    public Object visit(ReturnOp returnOp) {
        Element returnOpTag = document.createElement(returnOp.name);
        returnOpTag.appendChild((Node) returnOp.expr.accept(this));

        return returnOpTag;
    }

    @Override
    public Object visit(ElifOp elifOp) {
        Element elifTag = document.createElement(elifOp.name);
        elifTag.appendChild((Node) elifOp.cond.accept(this));
        elifTag.appendChild((Node) elifOp.body.accept(this));

        return elifTag;
    }

    private Element getTypeTag(Type t) {
        // <TypeOp>
        Element typeOpTag = document.createElement("TypeOp");
        typeOpTag.appendChild(document.createTextNode(t.toString()));
        return typeOpTag;
    }

    private Element getParDeclModeTag(ParDeclMode m) {
        // <ModeOp>
        Element modeTag = document.createElement("ModeOp");
        modeTag.appendChild(document.createTextNode(m.toString()));
        return modeTag;
    }

    private Object createBinaryOpTag(BinaryOpNode binaryOp) {
        Element binaryOpTag = document.createElement(binaryOp.name);
        binaryOpTag.appendChild((Node) binaryOp.left.accept(this));
        binaryOpTag.appendChild((Node) binaryOp.right.accept(this));

        return binaryOpTag;
    }
}
