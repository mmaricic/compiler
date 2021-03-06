// generated with ast extension for cup
// version 0.8
// 7/1/2018 0:55:59


package rs.ac.bg.etf.pp1.ast;

public class ClassAccess extends Designator {

    private ElemDesignator ElemDesignator;
    private String classElemName;

    public ClassAccess (ElemDesignator ElemDesignator, String classElemName) {
        this.ElemDesignator=ElemDesignator;
        if(ElemDesignator!=null) ElemDesignator.setParent(this);
        this.classElemName=classElemName;
    }

    public ElemDesignator getElemDesignator() {
        return ElemDesignator;
    }

    public void setElemDesignator(ElemDesignator ElemDesignator) {
        this.ElemDesignator=ElemDesignator;
    }

    public String getClassElemName() {
        return classElemName;
    }

    public void setClassElemName(String classElemName) {
        this.classElemName=classElemName;
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
        buffer.append("ClassAccess(\n");

        if(ElemDesignator!=null)
            buffer.append(ElemDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+classElemName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassAccess]");
        return buffer.toString();
    }
}
