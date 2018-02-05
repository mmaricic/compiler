// generated with ast extension for cup
// version 0.8
// 5/1/2018 19:6:8


package rs.ac.bg.etf.pp1.ast;

public class PostOperation extends DesignatorStatement {

    private PostOperand PostOperand;
    private PostOperator PostOperator;

    public PostOperation (PostOperand PostOperand, PostOperator PostOperator) {
        this.PostOperand=PostOperand;
        if(PostOperand!=null) PostOperand.setParent(this);
        this.PostOperator=PostOperator;
        if(PostOperator!=null) PostOperator.setParent(this);
    }

    public PostOperand getPostOperand() {
        return PostOperand;
    }

    public void setPostOperand(PostOperand PostOperand) {
        this.PostOperand=PostOperand;
    }

    public PostOperator getPostOperator() {
        return PostOperator;
    }

    public void setPostOperator(PostOperator PostOperator) {
        this.PostOperator=PostOperator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(PostOperand!=null) PostOperand.accept(visitor);
        if(PostOperator!=null) PostOperator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(PostOperand!=null) PostOperand.traverseTopDown(visitor);
        if(PostOperator!=null) PostOperator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(PostOperand!=null) PostOperand.traverseBottomUp(visitor);
        if(PostOperator!=null) PostOperator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PostOperation(\n");

        if(PostOperand!=null)
            buffer.append(PostOperand.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(PostOperator!=null)
            buffer.append(PostOperator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PostOperation]");
        return buffer.toString();
    }
}
