package scopingtable;

import ast.ParDeclOp;
import ast.Type;

import java.util.List;

public class ScopingItem {
    public enum ItemType { FUNCTION, VARIABLE }

    private ItemType itemType;
    private String idName;
    private List<ParDeclOp> params;
    private Type returnType;
    private Type variableType;
    private boolean hasReturn;

    // Constructor for variables
    public ScopingItem(String idName, Type variableType) {
        this.itemType = ItemType.VARIABLE;
        this.idName = idName;
        this.variableType = variableType;
        this.params = null;
        this.returnType = null;
    }

    // Constructor for functions
    public ScopingItem(String idName, List<ParDeclOp> params, Type returnType) {
        this.itemType = ItemType.FUNCTION;
        this.idName = idName;
        this.variableType = null;
        this.params = params;
        this.returnType = returnType;
        this.hasReturn = false;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public String getIdName() {
        return idName;
    }

    public Type getVariableType() {
        return variableType;
    }

    public List<ParDeclOp> getParams() {
        return params;
    }

    public Type getReturnType() {
        return returnType;
    }

    public boolean getHasReturn() {
        return hasReturn;
    }

    public void setHasReturn(boolean hasReturn) {
        this.hasReturn = hasReturn;
    }
}
