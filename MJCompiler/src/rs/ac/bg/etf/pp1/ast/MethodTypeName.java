// generated with ast extension for cup
// version 0.8
// 5/1/2018 19:6:7


package rs.ac.bg.etf.pp1.ast;

public class MethodTypeName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private TypeDef TypeDef;
    private String methodName;

    public MethodTypeName (TypeDef TypeDef, String methodName) {
        this.TypeDef=TypeDef;
        if(TypeDef!=null) TypeDef.setParent(this);
        this.methodName=methodName;
    }

    public TypeDef getTypeDef() {
        return TypeDef;
    }

    public void setTypeDef(TypeDef TypeDef) {
        this.TypeDef=TypeDef;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName=methodName;
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
        if(TypeDef!=null) TypeDef.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TypeDef!=null) TypeDef.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TypeDef!=null) TypeDef.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodTypeName(\n");

        if(TypeDef!=null)
            buffer.append(TypeDef.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+methodName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodTypeName]");
        return buffer.toString();
    }
}
