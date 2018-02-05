// generated with ast extension for cup
// version 0.8
// 5/1/2018 19:6:7


package rs.ac.bg.etf.pp1.ast;

public class Assignment extends DesignatorStatement {

    private AssignDesignator AssignDesignator;
    private Expr Expr;

    public Assignment (AssignDesignator AssignDesignator, Expr Expr) {
        this.AssignDesignator=AssignDesignator;
        if(AssignDesignator!=null) AssignDesignator.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public AssignDesignator getAssignDesignator() {
        return AssignDesignator;
    }

    public void setAssignDesignator(AssignDesignator AssignDesignator) {
        this.AssignDesignator=AssignDesignator;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AssignDesignator!=null) AssignDesignator.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AssignDesignator!=null) AssignDesignator.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AssignDesignator!=null) AssignDesignator.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Assignment(\n");

        if(AssignDesignator!=null)
            buffer.append(AssignDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Assignment]");
        return buffer.toString();
    }
}
