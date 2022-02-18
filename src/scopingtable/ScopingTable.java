package scopingtable;

public class ScopingTable {
    private ScopingNode activeScope = null;

    public void enterScope() {
        ScopingNode table = new ScopingNode(activeScope);
        activeScope = table;
    }

    public void exitScope() {
        if(activeScope != null && activeScope.getFather() != null) {
            activeScope = activeScope.getFather();
        }
    }

    public ScopingItem lookup(String idName) {
        ScopingNode currentLooking = activeScope;
        while(currentLooking != null) {
            if(currentLooking.containsKey(idName))
                return currentLooking.get(idName);
            currentLooking = currentLooking.getFather();
        }
        return null;
    }

    public boolean probe(String idName) {
        return activeScope.containsKey(idName);
    }

    public void addId(ScopingItem item) {
        if(!activeScope.containsKey(item.getIdName())) {
            activeScope.put(item.getIdName(), item);
        }
    }
}
