// generated with ast extension for cup
// version 0.8
// 5/1/2018 19:6:8


package rs.ac.bg.etf.pp1.ast;

public class FactorFuncCall extends Factor {

    private FuncCallName FuncCallName;
    private OptionParameters OptionParameters;

    public FactorFuncCall (FuncCallName FuncCallName, OptionParameters OptionParameters) {
        this.FuncCallName=FuncCallName;
        if(FuncCallName!=null) FuncCallName.setParent(this);
        this.OptionParameters=OptionParameters;
        if(OptionParameters!=null) OptionParameters.setParent(this);
    }

    public FuncCallName getFuncCallName() {
        return FuncCallName;
    }

    public void setFuncCallName(FuncCallName FuncCallName) {
        this.FuncCallName=FuncCallName;
    }

    public OptionParameters getOptionParameters() {
        return OptionParameters;
    }

    public void setOptionParameters(OptionParameters OptionParameters) {
        this.OptionParameters=OptionParameters;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FuncCallName!=null) FuncCallName.accept(visitor);
        if(OptionParameters!=null) OptionParameters.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FuncCallName!=null) FuncCallName.traverseTopDown(visitor);
        if(OptionParameters!=null) OptionParameters.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FuncCallName!=null) FuncCallName.traverseBottomUp(visitor);
        if(OptionParameters!=null) OptionParameters.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorFuncCall(\n");

        if(FuncCallName!=null)
            buffer.append(FuncCallName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionParameters!=null)
            buffer.append(OptionParameters.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorFuncCall]");
        return buffer.toString();
    }
}
