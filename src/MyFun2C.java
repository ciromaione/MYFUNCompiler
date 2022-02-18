import ast.ProgramOp;
import scopingtable.ScopingTable;
import visitor.CTranspilerVisitor;
import visitor.SemanticAnalyzerVisitor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Scanner;

public class MyFun2C {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.err.println("You must specify a .fun file as argument!");
            return;
        }
        File infile = new File(args[0]);
        String fileName = infile.getName();
        String ext = fileName.split("\\.")[fileName.split("\\.").length - 1];
        if(!ext.equals("fun")) {
            System.err.println("You must specify a .fun file as argument!");
            return;
        }
        String dirPath = infile.getParent();
        String name = fileName.split("\\.")[0];
        String cName = name + ".c";
        String outName = name + ".exe";

        try {
            Reader infileReader = new FileReader(infile);
            Lexer lexer = new Lexer(infileReader);
            parser par = new parser(lexer);

            ProgramOp astRoot = (ProgramOp) par.parse().value;

            ScopingTable scopingTable = new ScopingTable();
            SemanticAnalyzerVisitor scopingVisitor = new SemanticAnalyzerVisitor(scopingTable);
            astRoot.accept(scopingVisitor);

            CTranspilerVisitor transpilerVisitor = new CTranspilerVisitor();
            astRoot.accept(transpilerVisitor);

            transpilerVisitor.printToFile(Path.of(dirPath, cName).toString());

            ProcessBuilder builder = new ProcessBuilder("gcc", "-o", outName, cName);
            builder.directory(new File(dirPath));
            builder.redirectErrorStream(true);
            Process p = builder.start();
            Scanner sc = new Scanner(p.getInputStream());
            if (sc.hasNextLine()) {
                System.out.println("C compiler output...\n");
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
