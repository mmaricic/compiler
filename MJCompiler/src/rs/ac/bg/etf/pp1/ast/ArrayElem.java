// generated with ast extension for cup
// version 0.8
// 5/1/2018 19:6:8


package rs.ac.bg.etf.pp1.ast;

public class ArrayElem extends Designator {

    private ElemDesignator ElemDesignator;
    private Expr Expr;

    public ArrayElem (ElemDesignator ElemDesignator, Expr Expr) {
        this.ElemDesignator=ElemDesignator;
        if(ElemDesignator!=null) ElemDesignator.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public ElemDesignator getElemDesignator() {
        return ElemDesignator;
    }

    public void setElemDesignator(ElemDesignator ElemDesignator) {
        this.ElemDesignator=ElemDesignator;
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
        if(ElemDesignator!=null) ElemDesignator.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ElemDesignator!=null) ElemDesignator.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ElemDesignator!=null) ElemDesignator.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayElem(\n");

        if(ElemDesignator!=null)
            buffer.append(ElemDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayElem]");
        return buffer.toString();
    }
}
