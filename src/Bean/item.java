package Bean;

/**
 * Created by ${zhangzz} on 2015/8/25.
 */
public class item {
    private String identifier;
    private String isvisible;
    private String identifierref;
    private title title;
    public item() {
    }

    public title getTitle() {
        return title;
    }

    public void setTitle(title title) {
        this.title = title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIsvisible() {
        return isvisible;
    }

    public void setIsvisible(String isvisible) {
        this.isvisible = isvisible;
    }

    public String getIdentifierref() {
        return identifierref;
    }

    public void setIdentifierref(String identifierref) {
        this.identifierref = identifierref;
    }
}
