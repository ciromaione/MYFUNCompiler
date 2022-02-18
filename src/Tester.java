import ast.ProgramOp;
import scopingtable.ScopingTable;
import visitor.CTranspilerVisitor;
import visitor.SemanticAnalyzerVisitor;
import visitor.XMLGenVisitor;

import java.io.FileReader;
import java.io.Reader;

public class Tester {
    public static void main(String[] args) {
        try {
            Reader inFile = new FileReader(args[0]);
            Lexer lexer = new Lexer(inFile);
            parser par = new parser(lexer);

            String [] pathNames = args[0].split("\\.")[0].split("/");
            String fileName = pathNames[pathNames.length - 1];

            ProgramOp astRoot = (ProgramOp) par.parse().value;

            XMLGenVisitor xmlGenVisitor = new XMLGenVisitor();
            astRoot.accept(xmlGenVisitor);
            xmlGenVisitor.printToFile("test_files/xml_out/" + fileName + ".xml");

            ScopingTable scopingTable = new ScopingTable();
            SemanticAnalyzerVisitor scopingVisitor = new SemanticAnalyzerVisitor(scopingTable);
            astRoot.accept(scopingVisitor);

            CTranspilerVisitor transpilerVisitor = new CTranspilerVisitor();
            astRoot.accept(transpilerVisitor);


            transpilerVisitor.printToFile("test_files/c_out/" + fileName + ".c");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
