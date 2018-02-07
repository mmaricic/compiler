// generated with ast extension for cup
// version 0.8
// 7/1/2018 0:55:56


package rs.ac.bg.etf.pp1.ast;

public class ConstList extends ConstantList {

    private ConstantList ConstantList;
    private ValueAssign ValueAssign;

    public ConstList (ConstantList ConstantList, ValueAssign ValueAssign) {
        this.ConstantList=ConstantList;
        if(ConstantList!=null) ConstantList.setParent(this);
        this.ValueAssign=ValueAssign;
        if(ValueAssign!=null) ValueAssign.setParent(this);
    }

    public ConstantList getConstantList() {
        return ConstantList;
    }

    public void setConstantList(ConstantList ConstantList) {
        this.ConstantList=ConstantList;
    }

    public ValueAssign getValueAssign() {
        return ValueAssign;
    }

    public void setValueAssign(ValueAssign ValueAssign) {
        this.ValueAssign=ValueAssign;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstantList!=null) ConstantList.accept(visitor);
        if(ValueAssign!=null) ValueAssign.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstantList!=null) ConstantList.traverseTopDown(visitor);
        if(ValueAssign!=null) ValueAssign.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstantList!=null) ConstantList.traverseBottomUp(visitor);
        if(ValueAssign!=null) ValueAssign.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstList(\n");

        if(ConstantList!=null)
            buffer.append(ConstantList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ValueAssign!=null)
            buffer.append(ValueAssign.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstList]");
        return buffer.toString();
    }
}
