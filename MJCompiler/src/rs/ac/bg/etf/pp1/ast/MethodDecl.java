// generated with ast extension for cup
// version 0.8
// 5/1/2018 19:6:7


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private MethodTypeName MethodTypeName;
    private FormParsDecl FormParsDecl;
    private VarDeclList VarDeclList;
    private MethodVarEnd MethodVarEnd;
    private StatementList StatementList;

    public MethodDecl (MethodTypeName MethodTypeName, FormParsDecl FormParsDecl, VarDeclList VarDeclList, MethodVarEnd MethodVarEnd, StatementList StatementList) {
        this.MethodTypeName=MethodTypeName;
        if(MethodTypeName!=null) MethodTypeName.setParent(this);
        this.FormParsDecl=FormParsDecl;
        if(FormParsDecl!=null) FormParsDecl.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.MethodVarEnd=MethodVarEnd;
        if(MethodVarEnd!=null) MethodVarEnd.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public MethodTypeName getMethodTypeName() {
        return MethodTypeName;
    }

    public void setMethodTypeName(MethodTypeName MethodTypeName) {
        this.MethodTypeName=MethodTypeName;
    }

    public FormParsDecl getFormParsDecl() {
        return FormParsDecl;
    }

    public void setFormParsDecl(FormParsDecl FormParsDecl) {
        this.FormParsDecl=FormParsDecl;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public MethodVarEnd getMethodVarEnd() {
        return MethodVarEnd;
    }

    public void setMethodVarEnd(MethodVarEnd MethodVarEnd) {
        this.MethodVarEnd=MethodVarEnd;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodTypeName!=null) MethodTypeName.accept(visitor);
        if(FormParsDecl!=null) FormParsDecl.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(MethodVarEnd!=null) MethodVarEnd.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodTypeName!=null) MethodTypeName.traverseTopDown(visitor);
        if(FormParsDecl!=null) FormParsDecl.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(MethodVarEnd!=null) MethodVarEnd.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodTypeName!=null) MethodTypeName.traverseBottomUp(visitor);
        if(FormParsDecl!=null) FormParsDecl.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(MethodVarEnd!=null) MethodVarEnd.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(MethodTypeName!=null)
            buffer.append(MethodTypeName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsDecl!=null)
            buffer.append(FormParsDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodVarEnd!=null)
            buffer.append(MethodVarEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
