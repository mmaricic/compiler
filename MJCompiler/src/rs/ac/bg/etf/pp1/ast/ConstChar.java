// generated with ast extension for cup
// version 0.8
// 5/1/2018 19:6:8


package rs.ac.bg.etf.pp1.ast;

public class ConstChar extends Constant {

    private Character constant;

    public ConstChar (Character constant) {
        this.constant=constant;
    }

    public Character getConstant() {
        return constant;
    }

    public void setConstant(Character constant) {
        this.constant=constant;
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
        buffer.append("ConstChar(\n");

        buffer.append(" "+tab+constant);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstChar]");
        return buffer.toString();
    }
}
