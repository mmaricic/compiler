// generated with ast extension for cup
// version 0.8
// 5/1/2018 19:6:8


package rs.ac.bg.etf.pp1.ast;

public class FactorVar extends Factor {

    private ElemDesignator ElemDesignator;

    public FactorVar (ElemDesignator ElemDesignator) {
        this.ElemDesignator=ElemDesignator;
        if(ElemDesignator!=null) ElemDesignator.setParent(this);
    }

    public ElemDesignator getElemDesignator() {
        return ElemDesignator;
    }

    public void setElemDesignator(ElemDesignator ElemDesignator) {
        this.ElemDesignator=ElemDesignator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ElemDesignator!=null) ElemDesignator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ElemDesignator!=null) ElemDesignator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ElemDesignator!=null) ElemDesignator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorVar(\n");

        if(ElemDesignator!=null)
            buffer.append(ElemDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorVar]");
        return buffer.toString();
    }
}
