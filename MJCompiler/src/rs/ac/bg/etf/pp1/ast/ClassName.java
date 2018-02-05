// generated with ast extension for cup
// version 0.8
// 5/1/2018 19:6:7


package rs.ac.bg.etf.pp1.ast;

public class ClassName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private String className;
    private Extending Extending;

    public ClassName (String className, Extending Extending) {
        this.className=className;
        this.Extending=Extending;
        if(Extending!=null) Extending.setParent(this);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className=className;
    }

    public Extending getExtending() {
        return Extending;
    }

    public void setExtending(Extending Extending) {
        this.Extending=Extending;
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
        if(Extending!=null) Extending.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Extending!=null) Extending.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Extending!=null) Extending.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassName(\n");

        buffer.append(" "+tab+className);
        buffer.append("\n");

        if(Extending!=null)
            buffer.append(Extending.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassName]");
        return buffer.toString();
    }
}
