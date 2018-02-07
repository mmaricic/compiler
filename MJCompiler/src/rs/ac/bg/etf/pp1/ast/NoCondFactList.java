// generated with ast extension for cup
// version 0.8
// 7/1/2018 0:55:59


package rs.ac.bg.etf.pp1.ast;

public class NoCondFactList extends CondFactList {

    public NoCondFactList () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoCondFactList(\n");

        buffer.append(tab);
        buffer.append(") [NoCondFactList]");
        return buffer.toString();
    }
}
