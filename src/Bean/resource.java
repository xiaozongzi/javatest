package Bean;

/**
 * Created by ${zhangzz} on 2015/8/26.
 */
public class resource {
    private String identifier;
    private String type;
    private String href;
    private String scormtype;
    private file file;

    public resource() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getScormtype() {
        return scormtype;
    }

    public void setScormtype(String scormtype) {
        this.scormtype = scormtype;
    }

    public file getFile() {
        return file;
    }

    public void setFile(file file) {
        this.file = file;
    }
}
