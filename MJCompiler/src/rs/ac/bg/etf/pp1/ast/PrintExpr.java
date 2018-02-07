// generated with ast extension for cup
// version 0.8
// 7/1/2018 0:55:58


package rs.ac.bg.etf.pp1.ast;

public class PrintExpr extends Statement {

    private Expr Expr;
    private PrintAdditional PrintAdditional;

    public PrintExpr (Expr Expr, PrintAdditional PrintAdditional) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.PrintAdditional=PrintAdditional;
        if(PrintAdditional!=null) PrintAdditional.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public PrintAdditional getPrintAdditional() {
        return PrintAdditional;
    }

    public void setPrintAdditional(PrintAdditional PrintAdditional) {
        this.PrintAdditional=PrintAdditional;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(PrintAdditional!=null) PrintAdditional.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(PrintAdditional!=null) PrintAdditional.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(PrintAdditional!=null) PrintAdditional.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PrintExpr(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(PrintAdditional!=null)
            buffer.append(PrintAdditional.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PrintExpr]");
        return buffer.toString();
    }
}
