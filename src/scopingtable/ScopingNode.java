package scopingtable;

import ast.Identifier;

import java.util.HashMap;

public class ScopingNode extends HashMap<String, ScopingItem> {
    private ScopingNode father;

    public ScopingNode(ScopingNode father) {
        super();
        this.father = father;
    }

    public ScopingNode getFather() {
        return father;
    }
}
