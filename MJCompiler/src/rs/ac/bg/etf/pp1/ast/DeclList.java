// generated with ast extension for cup
// version 0.8
// 5/1/2018 19:6:6


package rs.ac.bg.etf.pp1.ast;

public class DeclList extends DeclarationList {

    private DeclarationList DeclarationList;
    private Decl Decl;

    public DeclList (DeclarationList DeclarationList, Decl Decl) {
        this.DeclarationList=DeclarationList;
        if(DeclarationList!=null) DeclarationList.setParent(this);
        this.Decl=Decl;
        if(Decl!=null) Decl.setParent(this);
    }

    public DeclarationList getDeclarationList() {
        return DeclarationList;
    }

    public void setDeclarationList(DeclarationList DeclarationList) {
        this.DeclarationList=DeclarationList;
    }

    public Decl getDecl() {
        return Decl;
    }

    public void setDecl(Decl Decl) {
        this.Decl=Decl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DeclarationList!=null) DeclarationList.accept(visitor);
        if(Decl!=null) Decl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DeclarationList!=null) DeclarationList.traverseTopDown(visitor);
        if(Decl!=null) Decl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DeclarationList!=null) DeclarationList.traverseBottomUp(visitor);
        if(Decl!=null) Decl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclList(\n");

        if(DeclarationList!=null)
            buffer.append(DeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Decl!=null)
            buffer.append(Decl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclList]");
        return buffer.toString();
    }
}
