// generated with ast extension for cup
// version 0.8
// 5/1/2018 19:6:7


package rs.ac.bg.etf.pp1.ast;

public class DoWhile extends Statement {

    private LoopBegin LoopBegin;
    private Statement Statement;
    private While While;
    private Condition Condition;

    public DoWhile (LoopBegin LoopBegin, Statement Statement, While While, Condition Condition) {
        this.LoopBegin=LoopBegin;
        if(LoopBegin!=null) LoopBegin.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.While=While;
        if(While!=null) While.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
    }

    public LoopBegin getLoopBegin() {
        return LoopBegin;
    }

    public void setLoopBegin(LoopBegin LoopBegin) {
        this.LoopBegin=LoopBegin;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public While getWhile() {
        return While;
    }

    public void setWhile(While While) {
        this.While=While;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LoopBegin!=null) LoopBegin.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(While!=null) While.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LoopBegin!=null) LoopBegin.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(While!=null) While.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LoopBegin!=null) LoopBegin.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(While!=null) While.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoWhile(\n");

        if(LoopBegin!=null)
            buffer.append(LoopBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(While!=null)
            buffer.append(While.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DoWhile]");
        return buffer.toString();
    }
}
