// generated with ast extension for cup
// version 0.8
// 5/1/2018 19:6:8


package rs.ac.bg.etf.pp1.ast;

public class ConstInt extends Constant {

    private Integer constant;

    public ConstInt (Integer constant) {
        this.constant=constant;
    }

    public Integer getConstant() {
        return constant;
    }

    public void setConstant(Integer constant) {
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
        buffer.append("ConstInt(\n");

        buffer.append(" "+tab+constant);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstInt]");
        return buffer.toString();
    }
}
