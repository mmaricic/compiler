package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {
	Logger log = Logger.getLogger(getClass());
	Struct boolType = new Struct(Struct.Bool);

	boolean errorDetected = false;
	Obj currentMethod = null;
	boolean returnFound = false;
	Struct currentType = Tab.noType;
	int constantValue;
	int loop = 0;
	boolean foundMain = false;
	Struct currentClass = null;
	ArrayList<Obj> funcCall = new ArrayList<Obj>();
	int formParsCount = 0;
	ArrayList<Obj> oldParams = null;
	public int nVars;
	boolean invalidMethod = false;
	HashMap<Struct, Struct> arrayTypes = new HashMap<Struct, Struct>(); 

	public SemanticAnalyzer() {
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder("Semanticka greska");
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		msg.append(message);

		log.error(msg.toString());
		System.err.println(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
		System.out.println(msg.toString());
	}

	@Override
	public void visit(ProgName ProgName) {
		ProgName.obj = Tab.insert(Obj.Prog, ProgName.getProgName(), Tab.noType);
		Tab.openScope();
	}

	@Override
	public void visit(Program Program) {
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(Program.getProgName().obj);
		Tab.closeScope();
		if (!foundMain)
			report_error("Program mora imati metodu main!", Program);
	}

	@Override
	public void visit(MethodVarEnd MethodVarEnd) {
		if (currentMethod != null) {
			Tab.chainLocalSymbols(currentMethod);
		}
	};

	@Override
	public void visit(MethodDecl MethodDecl) {
		if (currentMethod != null) {
			if (!returnFound && currentMethod.getType() != Tab.noType) {
				report_error(": funkcija " + currentMethod.getName() + " nema return iskaz!", MethodDecl);
			}

			if (currentMethod.getName().equals("main")) {
				if (currentMethod.getLevel() != 0)
					report_error(": Metoda main ne sme imati argumente!", MethodDecl);
				else if (currentMethod.getType() != Tab.noType)
					report_error(": Povratni tip metode main mora biti tipa void!", MethodDecl);
				foundMain = true;
			}
		}
		Tab.closeScope();
		returnFound = false;
		currentMethod = null;
		currentType = Tab.noType;
		oldParams = null;
		invalidMethod = false;
	}

	@Override
	public void visit(TypeVoid typeVoid) {
		typeVoid.struct = Tab.noType;
	}

	@Override
	public void visit(SomeType someType) {
		someType.struct = someType.getType().struct;
	}

	@Override
	public void visit(MethodTypeName methodTypeName) {
		Obj method = Tab.currentScope.findSymbol(methodTypeName.getMethodName());
		if (currentClass != null && method != null) {
			if (method.getType().equals(methodTypeName.getTypeDef().struct)) {
				currentMethod = method;
				oldParams = new ArrayList<Obj>(method.getLocalSymbols());
				oldParams.remove(0);
				method.setLocals(null);
				methodTypeName.obj = method;
			} else {
				report_error(
						": prilikom redefinicije metode potpis mora ostati isti - tip povratne vrednosti se ne sme menjati!",
						methodTypeName);
				currentMethod = new Obj(Obj.Meth, methodTypeName.getMethodName(), new Struct(Struct.None));
			}
		} else if (method != null) {
			report_error(": ime " + methodTypeName.getMethodName() + " je vec deklarisano!", methodTypeName);
			currentMethod = new Obj(Obj.Meth, methodTypeName.getMethodName(), new Struct(Struct.None));
		} else {
			currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethodName(), methodTypeName.getTypeDef().struct);
			methodTypeName.obj = currentMethod;
		}
		Tab.openScope();
		if (currentClass != null) {
			Obj ths = Tab.insert(Obj.Var, "this", currentClass);
			ths.setFpPos(1);
			formParsCount++;
		}
	}

	@Override
	public void visit(FormParameters formPars) {
		if (currentMethod != null) {
			if (oldParams != null) {
				if (invalidMethod || currentMethod.getLevel() != formParsCount) {
					if (currentMethod.getLevel() != formParsCount)
						report_error(": broj argumenata redefinisane metode ne sme biti manji od one koju redefinise!",
								formPars);
					Scope temp = new Scope(null);
					for (Obj l : oldParams)
						temp.addToLocals(l);
					currentMethod.setLocals(temp.getLocals());
					return;
				}
			}
			currentMethod.setLevel(Tab.currentScope.getnVars());
		}
		formParsCount = 0;
	}

	@Override
	public void visit(NoFormPars noForm) {
		if (currentMethod != null) {
			if (oldParams != null)
				if (invalidMethod || currentMethod.getLevel() > 1) {
					if (currentMethod.getLevel() != formParsCount)
						report_error(": broj argumenata redefinisane metode ne sme biti manji od one koju redefinise!",
								noForm.getParent());
					Scope temp = new Scope(null);
					for (Obj l : oldParams)
						temp.addToLocals(l);
					currentMethod.setLocals(temp.getLocals());
					return;
				}
			currentMethod.setLevel(Tab.currentScope.getnVars());

		}
		formParsCount = 0;
	}

	@Override
	public void visit(ReturnExpr returnExpr) {
		if (currentMethod == null)
			report_error(": RETURN ne sme postojati van metode ili funkcije!", returnExpr);
		else {
			returnFound = true;
			Struct currMethType = currentMethod.getType();
			if (!currMethType.compatibleWith(currentType)) {
				report_error(" : tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije "
						+ currentMethod.getName(), returnExpr);
			}
		}
	}

	@Override
	public void visit(Return ret) {
		if (currentMethod == null)
			report_error(": RETURN ne sme postojati van metode ili funkcije!", ret);
		else {
			returnFound = true;
			Struct currMethType = currentMethod.getType();
			if (currMethType.getKind() != Struct.None) {
				report_error(" : prazna return naredba moze postojati samo u metodama kojima je povratni tip void! ",
						ret);
			}
		}
	}

	@Override
	public void visit(FormalParameter fp) {
		if(fp.getBrackets() instanceof ArrayBracket) {
			if(arrayTypes.get(fp.getType().struct) == null)
				arrayTypes.put(fp.getType().struct, new Struct(Struct.Array, fp.getType().struct));
			fp.getType().struct = arrayTypes.get(fp.getType().struct);
		}
		if (oldParams != null && !invalidMethod) {
			if (oldParams.size() < (formParsCount - 1)) {
				report_error(
						": broj argumenata prilikom redefinisanja metode ne moze biti veci od broj argumenata u originalnoj metodi!",
						fp);
				invalidMethod = true;
				return;
			} else if (!oldParams.get(formParsCount - 1).getType().equals(fp.getType().struct)) {
				report_error(
						": prilikom redefinisanja metode potpis mora ostati isti - tipovi argumenata se ne smeju menjati!",
						fp);
				invalidMethod = true;
				return;
			}
		}
		formParsCount++;
		Obj newFp;
		Obj obj = Tab.currentScope.findSymbol(fp.getFormalParameterName());
		if (obj != Tab.noObj && obj != null) {
			report_error(": nije dozvoljeno definisati formalne parametre sa istim imenom!", fp);
			return;
		}
		if (fp.getBrackets() instanceof NotArray)
			newFp = Tab.insert(Obj.Var, fp.getFormalParameterName(), fp.getType().struct);
		else {
			
			newFp = Tab.insert(Obj.Var, fp.getFormalParameterName(), fp.getType().struct);
		}
		newFp.setFpPos(formParsCount);
	}

	@Override
	public void visit(ClassName ClassName) {

		ClassName.struct = new Struct(Struct.Class);
		if (ClassName.getExtending() instanceof Extends)
			ClassName.struct.setElementType(((Extends) ClassName.getExtending()).getType().struct);
		Tab.insert(Obj.Type, ClassName.getClassName(), ClassName.struct);
		Tab.openScope();
		Tab.insert(Obj.Fld, "1vft", Tab.intType);
		currentClass = ClassName.struct;

		if (ClassName.getExtending() instanceof Extends && ClassName.getExtending().obj != Tab.noObj) {

			for (Obj sym : ((Extends) ClassName.getExtending()).obj.getType().getMembers())
				if (sym.getKind() == Obj.Fld) {
					Obj newSym = new Obj(sym.getKind(), sym.getName(), sym.getType(), sym.getAdr(), sym.getLevel());
					Tab.currentScope.addToLocals(newSym);
				}
		}
	}

	@Override
	public void visit(ClassVars classVars) {
		if (currentClass != null)
			Tab.chainLocalSymbols(currentClass);
		Extending e = ((ClassDecl) classVars.getParent()).getClassName().getExtending();
		if (e instanceof Extends && e.obj != Tab.noObj) {
			for (Obj sym : e.obj.getType().getMembers())
				if (sym.getKind() == Obj.Meth) {
					Obj newSym = new Obj(sym.getKind(), sym.getName(), sym.getType(), sym.getAdr(), sym.getLevel());
					newSym.setFpPos(sym.getFpPos());
					Collection<Obj> locals = sym.getLocalSymbols();
					if (!locals.isEmpty()) {
						Scope temp = new Scope(null);
						for (Obj l : locals) {
							Obj newl = new Obj(l.getKind(), l.getName(), l.getType(), l.getAdr(), l.getLevel());
							temp.addToLocals(newl);
						}
						newSym.setLocals(temp.getLocals());
					}
					Tab.currentScope.addToLocals(newSym);
				}
		}
	}

	@Override
	public void visit(ClassDecl ClassDecl) {

		Tab.closeScope();
		currentClass = null;
	}

	@Override
	public void visit(Type Type) {
		Obj typeNode = Tab.find(Type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error(": zadati tip podataka " + Type.getTypeName() + " nije pronadjen u tabeli simbola!", Type);
			currentType = Tab.noType;
		} else if (Obj.Type == typeNode.getKind()) {
			currentType = typeNode.getType();
		} else {
			report_error(": Ime " + Type.getTypeName() + " ne predstavlja tip!", Type);
			currentType = Tab.noType;
		}
		Type.struct = currentType;
	}

	@Override
	public void visit(VarNameDecl VarNameDecl) {
		int kind = Obj.Var;
		if (currentClass != null && currentMethod == null)
			kind = Obj.Fld;
		if (Tab.currentScope.findSymbol(VarNameDecl.getVarName()) != null)
			report_error(": " + VarNameDecl.getVarName() + " je vec deklarisano!", VarNameDecl);
		else {
			if (VarNameDecl.getBrackets() instanceof NotArray) {
				Tab.insert(kind, VarNameDecl.getVarName(), currentType);

			} else {
				if(arrayTypes.get(currentType) == null) {
					arrayTypes.put(currentType, new Struct(Struct.Array, currentType)); 
				}
				Tab.insert(kind, VarNameDecl.getVarName(), arrayTypes.get(currentType));
			}
		}
	}

	@Override
	public void visit(VarDeclGood varDeclGood) {
		currentType = Tab.noType;
	}

	@Override
	public void visit(ValueAssign valueAssign) {
		if (!currentType.equals(valueAssign.getConstant().struct)) {
			report_error(": neslaganje u tipu konstante i dodeljene vrednosti!", valueAssign);
		} else if (Tab.currentScope.findSymbol(valueAssign.getConstName()) != null)
			report_error(": " + valueAssign.getConstName() + " je vec deklarisano!", valueAssign);
		else {
			Obj constant = Tab.insert(Obj.Con, valueAssign.getConstName(), currentType);
			constant.setAdr(constantValue);
		}

	}

	@Override
	public void visit(ConstDecl constDecl) {
		currentType = Tab.noType;
	}

	@Override
	public void visit(ConstInt constInt) {
		constInt.struct = Tab.intType;
		constantValue = constInt.getConstant();
	}

	@Override
	public void visit(ConstChar constChar) {
		constChar.struct = Tab.charType;
		constantValue = constChar.getConstant();
	}

	@Override
	public void visit(ConstBool constBool) {
		constBool.struct = boolType;
		constantValue = constBool.getConstant() ? 1 : 0;
	}

	@Override
	public void visit(Assignment assignment) {
		int res = validObjType(assignment.getAssignDesignator().getDesignator());
		Obj desObj = assignment.getAssignDesignator().getDesignator().obj;
		if(res == 1 && assignment.getExpr().struct.getKind() == Struct.Class && desObj.getType().getKind() == Struct.Class) {
			Struct left = desObj.getType();
			Struct right = assignment.getExpr().struct;
			if(right == Tab.nullType)
				return;
			while(right != null && left != right)
				right = right.getElemType();
			if(right == null)
				report_error(" : nekompatibilni tipovi u dodeli vrednosti ", assignment);
		}
		else if(res == 1 /*&& (assignment.getExpr().struct.getKind() == Struct.Array && desObj.getType().getKind() == Struct.Array*/
				&& assignment.getExpr().struct != desObj.getType() /*||!assignment.getExpr().struct.assignableTo(desObj.getType()))*/)
				report_error(" : nekompatibilni tipovi u dodeli vrednosti ", assignment);
		else if (res == -1)
			report_error(" : leva strana dodele vrednosti mora biti promenljiva, element niza ili polje klase ",
					assignment);
	}

	@Override
	public void visit(PostOperation post) {
		Obj obj = post.getPostOperand().getDesignator().obj;
		int res = validObjType(post.getPostOperand().getDesignator());
		if (res == -1)
			report_error(" : moguce je primeniti post operator samo promenljivu, element niza ili polje klase", post);
		else {
			if (obj.getKind() == Obj.Var && obj.getType().getKind() == Struct.Array
					|| obj.getType().getKind() == Struct.Class) {
				report_error(" : vrednost se ne moze dodeliti nizu vec elementu niza!", post);
				return;
			}
			Struct struct = post.getPostOperand().getDesignator().obj.getType();
			if (!(struct.getKind() == Struct.Int))
				report_error(": post operator moze biti primenjen samo na tip int!", post);
		}
	}

	@Override
	public void visit(Read read) {

		int res = validObjType(read.getDesignator());
		if (res == -1)
			report_error(": moguce je primeniti operaciju read na promenljivu, element niza ili polje klase!", read);
		if (res == 1) {
			Obj obj = read.getDesignator().obj;
			if (obj.getKind() == Obj.Var && obj.getType().getKind() == Struct.Array
					|| obj.getType().getKind() == Struct.Class) {
				report_error(" : vrednost se ne moze dodeliti nizu vec elementu niza!", read);
				return;
			}
			int structKind = read.getDesignator().obj.getType().getKind();
			if (structKind == Struct.Array || structKind == Struct.Class || structKind == Struct.None)
				report_error(": simbol u opearciji read mora biti tipa int, bool ili char!", read);
		}
	}

	@Override
	public void visit(PrintExpr print) {
		int type = print.getExpr().struct.getKind();
		if (type == Struct.Array || type == Struct.Class || type == Struct.None)
			report_error(": simbol u opearciji print mora biti tipa int, bool ili char!", print);
	}

	@Override
	public void visit(DesignatorOptMethodCall DesignatorOptMethodCall) {
		funcCall.remove(funcCall.size() - 1);
	}

	@Override
	public void visit(FactorFuncCall functionCall) {

		functionCall.struct = functionCall.getFuncCallName().getDesignator().obj.getType();
		funcCall.remove(funcCall.size() - 1);
	}

	@Override
	public void visit(LoopBegin loopBegin) {
		loop++;
	}

	@Override
	public void visit(DoWhile doWhile) {
		loop--;
	}

	@Override
	public void visit(Break brk) {
		if (loop <= 0)
			report_error(": kljucna rec BREAK se sme koristiti samo u okviru do-while petlje!", brk);

	}

	@Override
	public void visit(Continue cnt) {
		if (loop <= 0)
			report_error(": kljucna rec CONTINUE se sme koristiti samo u okviru do-while petlje!", cnt);

	}

	@Override
	public void visit(Extends ext) {
		Obj obj = Tab.find(ext.getType().getTypeName());
		if (obj == Tab.noObj)
			report_error(": Ne postoji tip zadat u Extend!", ext);
		else if (obj.getKind() != Obj.Type || obj.getType().getKind() != Struct.Class)
			report_error(": Tip koriscen za extend mora biti neka korisnicki definisana klasa!", ext);

		ext.obj = obj;
	}

	@Override
	public void visit(FuncCallName fcn) {
		Designator d = fcn.getDesignator();
		if (d instanceof ArrayElem) {
			report_error(" : element niza ne predstavlja ime funkcije ili metode klase!", fcn);
			funcCall.add(null);
		}

		else if (d.obj.getKind() != Obj.Meth) {
			report_error(" : "
					+ (d instanceof ClassAccess ? ((ClassAccess) d).getClassElemName() : ((DesignatorName) d).getName())
					+ " ne predstavlja ime funkcije ili metode klase!", fcn);
			funcCall.add(null);
		} else {
			funcCall.add(d.obj);
		}
	}

	@Override
	public void visit(NoPars noPars) {
		if (funcCall.isEmpty() || funcCall.get(funcCall.size() - 1) == null) {
			report_error(": Nevalidan naziv metode!", noPars.getParent());
			return;
		}

		if (funcCall.get(funcCall.size() - 1).getLevel() > 0) {
			ArrayList<Obj> formPars = new ArrayList<Obj>(funcCall.get(funcCall.size() - 1).getLocalSymbols());
			if (funcCall.get(funcCall.size() - 1).getLevel() == 1 && formPars.get(0).getName() == "this"
					&& formPars.get(0).getType().getKind() == Struct.Class)
				return;
			report_error(": Funkcija " + funcCall.get(funcCall.size() - 1).getName()
					+ " mora imati zadate stvarne argumente prilikom poziva!", noPars.getParent());
		}

	}

	@Override
	public void visit(ActPars pars) {
		if (funcCall.isEmpty() || funcCall.get(funcCall.size() - 1) == null) {
			report_error(": Nevalidan naziv metode!", pars);
			return;
		}
		int i = 1;
		boolean removedThis = false;
		ArrayList<Obj> formPars = new ArrayList<Obj>(funcCall.get(funcCall.size() - 1).getLocalSymbols());
		if (formPars.get(0).getName().equals("this") && formPars.get(0).getType().getKind() == Struct.Class) {
			formPars.remove(0);
			removedThis = true;
		}

		String funcName = funcCall.get(funcCall.size() - 1).getName();

		if (funcName.equals("len")) {
			if (pars.getExprList() instanceof ExpressionList)
				report_error(": funkcija len moze imati samo 1 argument!", pars);
			else if (pars.getExpr().struct.getKind() != Struct.Array)
				report_error(": argument funkcije len mora biti niz!", pars);
			return;
		}

		if (formPars.isEmpty())
			report_error(": funkcija " + funcName + " ne moze imati parametre!", pars);
		else {
			if (!pars.getExpr().struct.compatibleWith(formPars.get(0).getType())) {
				report_error(": nevalidan tip parametra pri pozivu funkcije " + funcName + "!", pars);
				return;
			}
			ExprList exprList = pars.getExprList();
			while (exprList instanceof ExpressionList) {
				if (i >= funcCall.get(funcCall.size() - 1).getLevel()) {
					report_error(": previse argumenata pri pozivu metode " + funcName + "!", pars);
					return;
				}
				Expr e = ((ExpressionList) exprList).getExpr();
				exprList = ((ExpressionList) exprList).getExprList();
				if (!e.struct.compatibleWith(formPars.get(i++).getType())) {
					report_error(": nevalidan tip parametra pri pozivu funkcije " + funcName + "!", pars);
					return;
				}

			}

			if ((!removedThis && i < funcCall.get(funcCall.size() - 1).getLevel())
					|| (removedThis && i + 1 < funcCall.get(funcCall.size() - 1).getLevel()))
				report_error(": nedovoljan broj argumenata pri pozivu funkcije " + funcName + "!", pars);
		}
	}

	@Override
	public void visit(NoCondTermList nct) {
		nct.struct = boolType;
	}

	@Override
	public void visit(ConditionTermList condTermList) {

		if (condTermList.getCondTerm().struct.getKind() != Struct.Bool
				|| condTermList.getCondTermList().struct.getKind() != Struct.Bool)
			condTermList.struct = Tab.noType;
		else
			condTermList.struct = boolType;
	}

	@Override
	public void visit(CondTerm condTerm) {
		if (condTerm.getCondFact().struct.getKind() != Struct.Bool
				|| condTerm.getCondFactList().struct.getKind() != Struct.Bool)
			condTerm.struct = Tab.noType;
		else
			condTerm.struct = boolType;
	}

	@Override
	public void visit(NoCondFactList ncd) {
		ncd.struct = boolType;
	}

	@Override
	public void visit(ConditionFactList condFactList) {

		if (condFactList.getCondFact().struct.getKind() != Struct.Bool
				|| condFactList.getCondFactList().struct.getKind() != Struct.Bool)
			condFactList.struct = Tab.noType;
		else
			condFactList.struct = boolType;
	}

	@Override
	public void visit(CondFactExpr factExpr) {
		factExpr.struct = factExpr.getExpr().struct;
	}

	@Override
	public void visit(CondFactWithComparision factExprCmp) {
		if (factExprCmp.getExpr().struct.getKind() == Struct.Bool
				|| factExprCmp.getExpr1().struct.getKind() == Struct.Bool)
			report_error(": nije moguce primeniti relacione operatore na promenljive tipa bool!", factExprCmp);

		if (!factExprCmp.getExpr().struct.compatibleWith(factExprCmp.getExpr1().struct))
			report_error(": nije moguce porediti objekte nekompatibilnih tipova!", factExprCmp);

		if ((factExprCmp.getExpr().struct.isRefType() || factExprCmp.getExpr1().struct.isRefType())
				&& (!(factExprCmp.getRelop() instanceof Equal) && !(factExprCmp.getRelop() instanceof Diff)))
			report_error(": nizovne i klasne promenljive se mogu samo porediti na jednakost/razlicitost!", factExprCmp);

		factExprCmp.struct = boolType;
	}

	@Override
	public void visit(ValidCondition cond) {
		if (cond.getCondTerm().struct.getKind() != Struct.Bool
				|| cond.getCondTermList().struct.getKind() != Struct.Bool)
			report_error(": uslov mora biti boolean tipa!", cond);
	}

	@Override
	public void visit(ExprTerm expr) {
		expr.struct = expr.getTerm().struct;
		currentType = expr.getTerm().struct;
	}

	@Override
	public void visit(ExprMinusTerm expr) {
		expr.struct = expr.getTerm().struct;
		currentType = expr.getTerm().struct;
		if (expr.getTerm().struct.getKind() != Struct.Int)
			report_error(": negacija se moze primeniti samo na int!", expr);
	}

	@Override
	public void visit(ExprAddop al) {
		Struct ts1 = al.getTerm().struct;
		Struct ts2 = al.getExpr().struct;

		if (ts1.compatibleWith(ts2) && ts1.equals(Tab.intType))
			al.struct = ts1;
		else {
			report_error(" : nekompatibilni tipovi u izrazu za sabiranje.", al);
			al.struct = Tab.noType;
		}
	}

	@Override
	public void visit(TermFactor term) {
		term.struct = term.getFactor().struct;
		currentType = term.struct;
	}

	@Override
	public void visit(TermMulop mpl) {
		Struct ts1 = mpl.getFactor().struct;
		Struct ts2 = mpl.getTerm().struct;
		if (ts1 == Tab.intType && ts2 == Tab.intType)
			mpl.struct = ts1;
		else {
			report_error(" : moguce je mnoziti samo 2 simbola tipa int.", mpl);
			mpl.struct = Tab.noType;
		}

	}

	@Override
	public void visit(FactorVar fv) {
		fv.struct = fv.getElemDesignator().obj.getType();
	}

	@Override
	public void visit(Const cnst) {
		cnst.struct = cnst.getConstant().struct;
	}

	@Override
	public void visit(NewArray na) {
		if (na.getExpr().struct != Tab.intType) {
			report_error(": velicina niza mora biti int!", na);
			na.struct = Tab.noType;
		} else {
			report_info("Detektovano pravljenje niza tipa " + na.getType().getTypeName() + " na liniji " + na.getLine(),
					null);
			if(arrayTypes.get(na.getType().struct) == null) {
				arrayTypes.put(na.getType().struct, new Struct(Struct.Array, na.getType().struct)); 
			}
			na.struct =arrayTypes.get(na.getType().struct);
		}
	}

	@Override
	public void visit(FactorParenExpr fpe) {
		fpe.struct = fpe.getExpr().struct;
	}

	@Override
	public void visit(NewClass nc) {
		if (nc.getType().struct.getKind() != Struct.Class) {
			report_error(": Greska prilikom pravljenja objekta klase - zadati tip " + nc.getType().getTypeName()
					+ " ne predstavlja ime klase!", nc);
			nc.struct = Tab.noType;
		} else {
			report_info("Detektovana upotreba unutrasnje klase " + nc.getType().getTypeName()
					+ "(pravljenje objekta) na liniji " + nc.getLine() + ": "
					+ printNode(Tab.find(nc.getType().getTypeName()), -1), null);
			nc.struct = nc.getType().struct;
		}
	}

	@Override
	public void visit(DesignatorName dn) {
		Obj obj = Tab.noObj;

		obj = Tab.find(dn.getName());
		if (obj == Tab.noObj) {
			report_error(" : ime " + dn.getName() + " nije deklarisano! ", dn);
			dn.obj = Tab.noObj;
		} else {
			dn.obj = obj;
			reportDetection(dn);

		}
	}

	private void reportDetection(DesignatorName dn) {
		switch (dn.obj.getKind()) {
		case Obj.Con:
			report_info("Detektovana upotreba globalne konstante " + dn.getName() + " na liniji " + dn.getLine() + ": "
					+ printNode(dn.obj, -1), null);
			break;
		case Obj.Meth:
			if (currentClass != null && currentClass.getMembersTable().searchKey(dn.getName()) != null)
				report_info("Detektovan poziv metode " + dn.getName() + " unutrasnje klase " + " na liniji "
						+ dn.getLine() + ": " + printNode(dn.obj, -1), null);
			else
				report_info("Detektovan poziv globalne funkcije " + dn.getName() + " na liniji " + dn.getLine() + ": "
						+ printNode(dn.obj, -1), null);
			break;
		case Obj.Var:
			if (dn.obj.getFpPos() > 0)
				report_info(
						"Detektovana upotreba formalnog parametra " + dn.getName() + " metode "
								+ currentMethod.getName() + " na liniji " + dn.getLine() + ": " + printNode(dn.obj, -1),
								null);
			else if (currentMethod != null && currentMethod.getLocalSymbols().contains(dn.obj))
				report_info(
						"Detektovana upotreba lokalne promenljive " + dn.getName() + " metode "
								+ currentMethod.getName() + " na liniji " + dn.getLine() + ": " + printNode(dn.obj, -1),
								null);
			else
				report_info("Detektovana upotreba globalne promenljive " + dn.getName() + " na liniji " + dn.getLine()
				+ ": " + printNode(dn.obj, -1), null);
			break;
		}
	}

	@Override
	public void visit(ClassAccess ca) {
		if(ca.getElemDesignator().obj != Tab.noObj) {
			Struct str = ca.getElemDesignator().obj.getType();

			/*
			 * if(ca.getDesignator() instanceof ArrayElem){
			 * report_error(": ne postoje polja niza!", ca); ca.obj = Tab.noObj; return; }
			 */

			if (str.getKind() != Struct.Class) {
				report_error(": ne postoje polja kod promenljivih koje nisu klasnog tipa!", ca);
				ca.obj = Tab.noObj;
			}
			else {
				Obj elem = str.getMembersTable().searchKey(ca.getClassElemName());
				if (elem == null) {
					report_error(": ne postoji polje u klasi sa imenom " + ca.getClassElemName() + "!", ca);
					ca.obj = Tab.noObj;
				} else {
					ca.obj = elem;
					if (elem.getKind() == Obj.Meth)
						report_info("Detektovan poziv metode " + elem.getName() + " unutrasnje klase " + " na liniji "
								+ ca.getLine() + ": " + printNode(elem, -1), null);
					else
						report_info("Detektovan pristup polju " + elem.getName() + " unutrasnje klase" + " na liniji "
								+ ca.getLine() + ": " + printNode(elem, -1), null);
				}
			}
		}else
			ca.obj = Tab.noObj;
	}

	@Override
	public void visit(ElemDesignator ElemDesignator) {
		ElemDesignator.obj = ElemDesignator.getDesignator().obj;
	}

	@Override
	public void visit(ArrayElem ae) {
		Obj obj = ae.getElemDesignator().obj;
		if(obj != Tab.noObj) {
			if (ae.getElemDesignator().getDesignator() instanceof ArrayElem) {
				report_error(": postoje samo jednodimenzionalni nizovi!", ae);
				ae.obj = Tab.noObj;
				return;
			}

			String name = (ae.getElemDesignator().getDesignator() instanceof DesignatorName)
					? ((DesignatorName) ae.getElemDesignator().getDesignator()).getName()
							: ((ClassAccess) ae.getElemDesignator().getDesignator()).getClassElemName();

					if (obj.getType().getKind() != Struct.Array) {
						report_error(": Promenljiva " + name + " nije nizovnog tipa!", ae);
						ae.obj = Tab.noObj;
					} else if (ae.getExpr().struct.getKind() != Struct.Int) {
						report_error(": Za pristup elementu niza mora se koristiti int!", ae);
						ae.obj = Tab.noObj;
					} else {
						ae.obj = new Obj(Obj.Elem, name, obj.getType().getElemType());
						report_info("Detektovan pristup elementu niza " + obj.getName() + " na liniji " + ae.getLine() + ": "
								+ printNode(ae.obj, -1), null);
					}
		}else
			ae.obj = Tab.noObj;
	}

	public boolean passed() {
		return (!errorDetected && foundMain);
	}

	private int validObjType(Designator des) {
		Obj obj = des.obj;
		if (obj == Tab.noObj)
			return 0;

		if (obj.getKind() == Obj.Fld || obj.getKind() == Obj.Elem)
			return 1;
		if (obj.getKind() == Obj.Var && obj.getType().getKind() != Struct.None) {

			return 1;
		}
		return -1;
	}

	private String printNode(Obj objToVisit, int kind) {
		StringBuilder output = new StringBuilder();
		switch (objToVisit.getKind()) {
		case Obj.Con:
			output.append("Con ");
			break;
		case Obj.Var:
			output.append("Var ");
			break;
		case Obj.Type:
			output.append("Type ");
			break;
		case Obj.Meth:
			output.append("Meth ");
			break;
		case Obj.Fld:
			output.append("Fld ");
			break;
		case Obj.Prog:
			output.append("Prog ");
			break;
		}

		output.append(objToVisit.getName());
		output.append(": ");

		if ((Obj.Var == objToVisit.getKind()) && "this".equalsIgnoreCase(objToVisit.getName())
				|| objToVisit.getKind() == kind)
			output.append("");
		else {
			Struct structToVisit = objToVisit.getType();
			switch (structToVisit.getKind()) {
			case Struct.None:
				output.append("NoType ");
				break;
			case Struct.Int:
				output.append("Int ");
				break;
			case Struct.Char:
				output.append("Char ");
				break;
			case Struct.Bool:
				output.append("Bool ");
				break;
			case Struct.Class:
				output.append("Class ");
				if (objToVisit.getKind() == Obj.Type) {
					output.append("[");
					for (Obj obj : structToVisit.getMembers()) {
						printNode(obj, structToVisit.getKind());
					}
					output.append("]");
				}
				break;
			case Struct.Array:
				output.append("Arr of ");

				switch (structToVisit.getElemType().getKind()) {
				case Struct.None:
					output.append("notype");
					break;
				case Struct.Int:
					output.append("int");
					break;
				case Struct.Char:
					output.append("char");
					break;
				case Struct.Bool:
					output.append("bool");
					break;
				case Struct.Class:
					output.append("Class");
					break;
				}
				break;
			}

			output.append(", ");
			output.append(objToVisit.getAdr());
			output.append(", ");
			output.append(objToVisit.getLevel() + " ");

		}
		return output.toString();
	}
}