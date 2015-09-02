package xml;

/**
 * Created by ${zhangzz} on 2015/8/25.
 */
public class organization {
    private String identifier;
    private String structure;
    private item item;

    public organization() {
    }

    public item getItem() {
        return item;
    }

    public void setItem(item item) {
        this.item = item;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }
}
