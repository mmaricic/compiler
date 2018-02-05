package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.custom.CustomTableDumpVisitor;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

public class Compiler {
	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {

		Logger log = Logger.getLogger(MJParserTest.class);
		BufferedReader br = null;
		
		
		if (args.length < 2) {
			log.error("Nedovoljno argumenata! Koriscenje: <source-file> <obj-file> ");
			return;
		}

		
			File sourceCode = new File(args[0]);
			if (!sourceCode.exists()) {
			log.error("Izvorni fajl [" + sourceCode.getAbsolutePath() + "] nije pronadjen!");
			return;
		}
			
			try{
			br = new BufferedReader(new FileReader(sourceCode));
			
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();
	        Program prog = (Program)(s.value);
		        System.out.println(prog.toString(""));
		        
		        Tab.init(); // Universe scope
				SemanticAnalyzer semanticCheck = new SemanticAnalyzer();
				prog.traverseBottomUp(semanticCheck);
				Tab.dump(new CustomTableDumpVisitor());
	       
	        if (!p.errorDetected && semanticCheck.passed()) {
	        	File objFile = new File(args[1]);
	        	log.info("Generating bytecode file: " + objFile.getAbsolutePath());
	        	if (objFile.exists())
	        		objFile.delete();
	        	Code.dataSize = semanticCheck.nVars;
	        	CodeGenerator codeGenerator = new CodeGenerator();
	        	prog.traverseBottomUp(codeGenerator);
	        	Code.write(new FileOutputStream(objFile));
	        	log.info("Parsiranje uspesno zavrseno!");
	        }
	        else {
	        	log.error("Parsiranje NIJE uspesno zavrseno!");
	        }
	        
		}finally{
			if(br != null) br.close();
		}
	}
	
}
