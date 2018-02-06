package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.custom.VirtualTableGenerator;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	Struct insideOfClass = null;
	Obj currentMethod = null;
	ArrayList<Integer> fixupAdr = new ArrayList<Integer>();
	ArrayList<Integer> fixupTrue = new ArrayList<Integer>();
	ArrayList<Integer> doWhileStart = new ArrayList<Integer>();
	ArrayList<ArrayList<Integer>> fixupJmps = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> fixupCondJumps = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> fixupContinue = new ArrayList<ArrayList<Integer>>();
	int count;
	VirtualTableGenerator vtg = new VirtualTableGenerator();
	HashMap<String, Integer> vtadressMap = new HashMap<String, Integer>();  



	@Override
	public void visit(LoopBegin Do) {
		doWhileStart.add(Code.pc);
		fixupContinue.add(new ArrayList<Integer>());
	}

	@Override
	public void visit(While While) {
		ArrayList<Integer> cont = fixupContinue.remove(fixupContinue.size()-1);
		while(!cont.isEmpty())
			Code.fixup(cont.remove(0));
	}

	@Override
	public void visit(Extends ext) {
		insideOfClass = ((ClassName)ext.getParent()).struct;
		Struct parent = ext.obj.getType();
		for(Obj elem : parent.getMembers())
			if(elem.getKind() == Obj.Meth) {
				Obj o = insideOfClass.getMembersTable().searchKey(elem.getName());
				o.setAdr(elem.getAdr());
			}
	}

	@Override
	public void visit(ClassName ClassName) {
		if(insideOfClass == null)
			insideOfClass = ClassName.struct;
	
		vtadressMap.put(ClassName.getClassName(), Code.dataSize);
		
	}
	
	@Override
	public void visit(ClassDecl classDecl) {
		Collection<Obj> obj = classDecl.getClassName().struct.getMembers();
		insideOfClass = null;
		for(Obj o: obj)
			if(o.getKind() == Obj.Meth) 
				vtg.addFunctionEntry (o.getName(), o.getAdr());
		vtg.addTableTerminator();
		
	}

	@Override
	public void visit(DoWhile doWhile) {
		int adr = doWhileStart.remove(doWhileStart.size()-1);
		Code.putJump(adr);
		ArrayList<Integer> fixup = fixupCondJumps.remove(fixupCondJumps.size()-1);
		while(!fixup.isEmpty())
			Code.fixup(fixup.remove(0));
		fixup = fixupJmps.remove(fixupJmps.size()-1);
		while(!fixup.isEmpty())
			Code.put2(fixup.get(0), adr - fixup.remove(0)+1);
	}

	@Override
	public void visit(TermMulop termMulop){
		Mulop op = termMulop.getMulop();
		if(op instanceof Mul)
			Code.put(Code.mul);
		else if(op instanceof Div)
			Code.put(Code.div);
		else if(op instanceof Mod)
			Code.put(Code.rem);
	}

	@Override
	public void visit(ExprAddop exprAddop){
		Addop op = exprAddop.getAddop();
		if(op instanceof Add)
			Code.put(Code.add);
		else if(op instanceof Sub)
			Code.put(Code.sub);
	}

	@Override
	public void visit(ExprMinusTerm exprNegation){
		Code.put(Code.neg);
	}

	@Override
	public void visit(Const cnst){
		Constant constant = cnst.getConstant();
		if(constant instanceof ConstBool)
			Code.loadConst(((ConstBool) constant).getConstant()?1:0);
		else if (constant instanceof ConstInt)
			Code.loadConst(((ConstInt) constant).getConstant());
		else
			Code.loadConst(((ConstChar) constant).getConstant());

	}

	@Override
	public void visit(AdditionalPrint printNum){
		Code.loadConst(printNum.getN1());
	}

	@Override
	public void visit(NoAdditionalPrint NoAdditionalPrint) {
		Code.put(Code.const_5);
	}

	/*@Override
	public void visit(FactorVar var) {
		Code.load(var.getElemDesignator().getDesignator().obj);
	}*/

	@Override
	public void visit(NewArray newArray) {
		Code.put(Code.newarray);
		if(newArray.getType().struct.getKind() == Struct.Char)
			Code.put(0);
		else
			Code.put(1);
	}

	@Override
	public void visit(NewClass newClass) {
		Code.put(Code.new_);
		Code.put2((newClass.struct.getNumberOfFields())*4);
		Code.put(Code.dup);
		Code.loadConst(vtadressMap.get(newClass.getType().getTypeName()));
		Code.put(Code.putfield);
		Code.put2(0);

	}

	@Override
	public void visit(FuncCallName FuncCallName) {
		Designator des = FuncCallName.getDesignator();
		if(!(des instanceof ClassAccess) && insideOfClass != null && currentMethod != null 
				&& !currentMethod.getLocalSymbols().contains(des.obj) && insideOfClass.getMembers().contains(des.obj)) {
			Code.put(Code.load);
			Code.put(0);
		}
	}

	@Override
	public void visit(FactorFuncCall funcCall) {
		callMethod(funcCall.getFuncCallName().getDesignator());
	}

	@Override
	public void visit(DesignatorOptMethodCall funcCall) {
		callMethod(funcCall.getFuncCallName().getDesignator());
	}

	private void callMethod(Designator des) {
		Obj functionObj = des.obj;
		if(functionObj.getName().equals("ord") || functionObj.getName().equals("chr"))
			return;
		if(functionObj.getName().equals("len"))
			Code.put(Code.arraylength);
		else{
			if(des instanceof ClassAccess) {
			
				des.traverseBottomUp(this);
				Code.put(Code.getfield);
				Code.put2(0);
				Code.put(Code.invokevirtual);
				String name = des.obj.getName();
				for(int i = 0; i < name.length(); i++) {
					Code.put4(name.charAt(i));
				}
				Code.put4(-1);
			}else if(insideOfClass != null && insideOfClass.getMembers().contains(des.obj)) {
					Code.put(Code.load_n);
					Code.put(Code.getfield);
					Code.put2(0);
					Code.put(Code.invokevirtual);
					String name = des.obj.getName();
					for(int i = 0; i < name.length(); i++) {
						Code.put4(name.charAt(i));
					}
					Code.put4(-1);
			}
			else {
				int offset = functionObj.getAdr() - Code.pc; 
				Code.put(Code.call);
				Code.put2(offset);
			}
		}
	}

	@Override
	public void visit(PostOperation postOp) {
		Obj design = postOp.getPostOperand().getDesignator().obj;
		Code.store(design);

	}

	@Override
	public void visit(PostOperand operand){
		Code.load(operand.getDesignator().obj);
	}

	@Override
	public void visit(Inc postOp){
		Code.put(Code.const_1);
		Code.put(Code.add);
	}

	@Override
	public void visit(Dec postOp){
		Code.put(Code.const_1);
		Code.put(Code.sub);

	}
	@Override
	public void visit(Assignment Assignment) {

		Code.store(Assignment.getAssignDesignator().getDesignator().obj);
	}


	@Override
	public void visit(Read Read) {
		if(Read.getDesignator().obj.getType().getKind() == Struct.Char)
			Code.put(Code.bread);
		else
			Code.put(Code.read);
		Code.store(Read.getDesignator().obj);
	}

	@Override
	public void visit(PrintExpr PrintExpr) {
		if(PrintExpr.getExpr().struct.getKind() == Struct.Char)
			Code.put(Code.bprint);
		else
			Code.put(Code.print);
	}

	@Override
	public void visit(MethodDecl methodDecl){
		Code.put(Code.exit);
		Code.put(Code.return_);
		currentMethod = null;
	}

	@Override
	public void visit(ReturnExpr ReturnExpr) {

		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(Return Return) {

		Code.put(Code.exit);
		Code.put(Code.return_);
	}


	@Override
	public void visit(MethodTypeName MethodTypeName) {
		if ("main".equalsIgnoreCase(MethodTypeName.getMethodName())) {
			Code.mainPc = Code.pc;
			
		}
		MethodTypeName.obj.setAdr(Code.pc);

		Code.put(Code.enter);
		Code.put(MethodTypeName.obj.getLevel());
		Code.put(MethodTypeName.obj.getLocalSymbols().size());
		currentMethod = MethodTypeName.obj;
		if("main".equalsIgnoreCase(MethodTypeName.getMethodName())) {
			vtg.copyTable();
		}
	}

	@Override
	public void visit(AssignDesignator AssignDesignator) {
		Designator des = AssignDesignator.getDesignator();
		if(!(des instanceof ClassAccess) && insideOfClass != null && currentMethod != null 
				&& !currentMethod.getLocalSymbols().contains(des.obj) && insideOfClass.getMembers().contains(des.obj)) {
			Code.put(Code.load);
			Code.put(0);
		}
	}

	@Override
	public void visit(ElemDesignator elem){
		Designator des = elem.getDesignator();
		if(!(des instanceof ClassAccess) && insideOfClass != null && currentMethod != null 
				&& !currentMethod.getLocalSymbols().contains(des.obj) 
				&& insideOfClass.getMembers().contains(des.obj)) {
			Code.put(Code.load);
			Code.put(0);
		}
		Code.load(elem.getDesignator().obj);

	}

	@Override
	public void visit(CondFactExpr condFactExpr) {
		Code.put(Code.const_n);
		Code.putFalseJump(Code.ne, 0);
		fixupAdr.add(Code.pc-2);
		count++;
	}

	@Override
	public void visit(CondFactWithComparision compare) {
		int jmp;
		if(compare.getRelop() instanceof Equal)
			jmp = Code.eq;
		else if(compare.getRelop() instanceof Diff)
			jmp = Code.ne;
		else if(compare.getRelop() instanceof Gt)
			jmp = Code.gt;	
		else if(compare.getRelop() instanceof Gte)
			jmp = Code.ge;
		else if(compare.getRelop() instanceof Lt)
			jmp = Code.lt;
		else
			jmp = Code.le;

		Code.putFalseJump(jmp, 0);
		fixupAdr.add(Code.pc-2);
		count++;
	}

	@Override
	public void visit(Or or) {
		Code.putJump(0);
		fixupTrue.add(Code.pc - 2);
		while(count > 0) {
			count--;
			Code.fixup(fixupAdr.remove(0));
		}
	}

	@Override
	public void visit(ValidCondition cond){
		fixupJmps.add(fixupTrue);
		fixupTrue = new ArrayList<Integer>();

		fixupCondJumps.add(fixupAdr);
		fixupAdr = new ArrayList<Integer>();
		count = 0;
	}

	@Override
	public void visit(IfCondition ifCond) {
		ArrayList<Integer> fixup = fixupJmps.get(fixupJmps.size() - 1);
		while(!fixup.isEmpty())
			Code.fixup(fixup.remove(0));
	}

	@Override
	public void visit(If If) {
		ArrayList<Integer> fixup = fixupCondJumps.remove(fixupCondJumps.size() - 1);
		while(!fixup.isEmpty())
			Code.fixup(fixup.remove(0));
		fixupJmps.remove(fixupJmps.size()-1);
	}

	@Override
	public void visit(Else Else) {
		Code.putJump(0);
		fixupJmps.get(fixupJmps.size()-1).add(Code.pc - 2);
		ArrayList<Integer> fixup = fixupCondJumps.get(fixupCondJumps.size() - 1);
		while(!fixup.isEmpty())
			Code.fixup(fixup.remove(0));

	}

	@Override
	public void visit(IfElse ifElse) {
		ArrayList<Integer> fixup = fixupJmps.remove(fixupJmps.size() - 1);
		while(!fixup.isEmpty())
			Code.fixup(fixup.remove(0));
		fixupCondJumps.remove(fixupCondJumps.size()-1);
	}

	@Override
	public void visit(Continue cont){
		Code.putJump(0);
		fixupContinue.get(fixupContinue.size()-1).add(Code.pc-2);
	}

	@Override
	public void visit(Break brk){
		Code.putJump(0);
		fixupAdr.add(Code.pc-2);
	}
}
