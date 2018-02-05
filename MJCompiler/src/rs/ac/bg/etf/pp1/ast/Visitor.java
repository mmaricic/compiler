// generated with ast extension for cup
// version 0.8
// 5/1/2018 19:6:9


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Extending Extending);
    public void visit(DeclarationList DeclarationList);
    public void visit(Parameter Parameter);
    public void visit(Mulop Mulop);
    public void visit(ConstantList ConstantList);
    public void visit(Constant Constant);
    public void visit(Relop Relop);
    public void visit(CondTermList CondTermList);
    public void visit(VarName VarName);
    public void visit(PostOperator PostOperator);
    public void visit(StatementList StatementList);
    public void visit(Addop Addop);
    public void visit(Factor Factor);
    public void visit(CondFactList CondFactList);
    public void visit(Designator Designator);
    public void visit(Term Term);
    public void visit(FormParsList FormParsList);
    public void visit(Condition Condition);
    public void visit(IdentList IdentList);
    public void visit(TypeDef TypeDef);
    public void visit(Methods Methods);
    public void visit(Brackets Brackets);
    public void visit(ExprList ExprList);
    public void visit(VarDeclList VarDeclList);
    public void visit(Expr Expr);
    public void visit(OptionParameters OptionParameters);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(Decl Decl);
    public void visit(Statement Statement);
    public void visit(VarDecl VarDecl);
    public void visit(CondFact CondFact);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(FormParsDecl FormParsDecl);
    public void visit(PrintAdditional PrintAdditional);
    public void visit(Mod Mod);
    public void visit(Div Div);
    public void visit(Mul Mul);
    public void visit(Sub Sub);
    public void visit(Add Add);
    public void visit(Lte Lte);
    public void visit(Lt Lt);
    public void visit(Gte Gte);
    public void visit(Gt Gt);
    public void visit(Diff Diff);
    public void visit(Equal Equal);
    public void visit(ElemDesignator ElemDesignator);
    public void visit(ArrayElem ArrayElem);
    public void visit(ClassAccess ClassAccess);
    public void visit(DesignatorName DesignatorName);
    public void visit(ConstBool ConstBool);
    public void visit(ConstChar ConstChar);
    public void visit(ConstInt ConstInt);
    public void visit(FactorParenExpr FactorParenExpr);
    public void visit(NewArray NewArray);
    public void visit(NewClass NewClass);
    public void visit(Const Const);
    public void visit(FactorFuncCall FactorFuncCall);
    public void visit(FactorVar FactorVar);
    public void visit(TermMulop TermMulop);
    public void visit(TermFactor TermFactor);
    public void visit(ExprAddop ExprAddop);
    public void visit(ExprMinusTerm ExprMinusTerm);
    public void visit(ExprTerm ExprTerm);
    public void visit(CondFactWithComparision CondFactWithComparision);
    public void visit(CondFactExpr CondFactExpr);
    public void visit(NoCondFactList NoCondFactList);
    public void visit(ConditionFactList ConditionFactList);
    public void visit(CondTerm CondTerm);
    public void visit(Or Or);
    public void visit(NoCondTermList NoCondTermList);
    public void visit(ConditionTermList ConditionTermList);
    public void visit(CondError CondError);
    public void visit(CondEmptyError CondEmptyError);
    public void visit(ValidCondition ValidCondition);
    public void visit(NoExprList NoExprList);
    public void visit(ExpressionList ExpressionList);
    public void visit(ActPars ActPars);
    public void visit(Dec Dec);
    public void visit(Inc Inc);
    public void visit(NoPars NoPars);
    public void visit(HasPars HasPars);
    public void visit(FuncCallName FuncCallName);
    public void visit(PostOperand PostOperand);
    public void visit(AssignDesignator AssignDesignator);
    public void visit(PostOperation PostOperation);
    public void visit(DesignatorOptMethodCall DesignatorOptMethodCall);
    public void visit(AssignError AssignError);
    public void visit(Assignment Assignment);
    public void visit(NoAdditionalPrint NoAdditionalPrint);
    public void visit(AdditionalPrint AdditionalPrint);
    public void visit(NoStatement NoStatement);
    public void visit(Statements Statements);
    public void visit(LoopBegin LoopBegin);
    public void visit(IfCondition IfCondition);
    public void visit(Else Else);
    public void visit(While While);
    public void visit(MultipleStatements MultipleStatements);
    public void visit(PrintExpr PrintExpr);
    public void visit(Read Read);
    public void visit(ReturnExpr ReturnExpr);
    public void visit(Return Return);
    public void visit(Continue Continue);
    public void visit(Break Break);
    public void visit(DoWhile DoWhile);
    public void visit(IfElse IfElse);
    public void visit(If If);
    public void visit(OperationStatement OperationStatement);
    public void visit(Type Type);
    public void visit(ParameterError ParameterError);
    public void visit(FormalParameter FormalParameter);
    public void visit(NoFormParsList NoFormParsList);
    public void visit(FormalParameterList FormalParameterList);
    public void visit(NotArray NotArray);
    public void visit(ArrayBracket ArrayBracket);
    public void visit(FormPars FormPars);
    public void visit(NoFormPars NoFormPars);
    public void visit(FormParameters FormParameters);
    public void visit(TypeVoid TypeVoid);
    public void visit(SomeType SomeType);
    public void visit(NoVarDecl NoVarDecl);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(MethodTypeName MethodTypeName);
    public void visit(MethodVarEnd MethodVarEnd);
    public void visit(MethodDecl MethodDecl);
    public void visit(NoMethodList NoMethodList);
    public void visit(MethodList MethodList);
    public void visit(NoClassMethods NoClassMethods);
    public void visit(ClassHasMethods ClassHasMethods);
    public void visit(ExtendError ExtendError);
    public void visit(NoExtend NoExtend);
    public void visit(Extends Extends);
    public void visit(ClassName ClassName);
    public void visit(ClassStart ClassStart);
    public void visit(ClassVars ClassVars);
    public void visit(ClassDecl ClassDecl);
    public void visit(VarOne VarOne);
    public void visit(VarNameList VarNameList);
    public void visit(NoName NoName);
    public void visit(InvalidName InvalidName);
    public void visit(VarNameDecl VarNameDecl);
    public void visit(VarDeclError VarDeclError);
    public void visit(VarDeclGood VarDeclGood);
    public void visit(ValueAssign ValueAssign);
    public void visit(NoConstList NoConstList);
    public void visit(ConstList ConstList);
    public void visit(ConstDecl ConstDecl);
    public void visit(DeclDerived3 DeclDerived3);
    public void visit(DeclDerived2 DeclDerived2);
    public void visit(DeclDerived1 DeclDerived1);
    public void visit(DeclarationListEmpty DeclarationListEmpty);
    public void visit(DeclList DeclList);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
