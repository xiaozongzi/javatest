package xml;

import Bean.resources;

/**
 * Created by ${zhangzz} on 2015/8/25.
 */
public class manifest {
    private organizations organizations;
    private resources resoures;

    public manifest() {
    }

    public resources getResoures() {
        return resoures;
    }

    public void setResoures(resources resoures) {
        this.resoures = resoures;
    }

    public organizations getOrganizations() {
        return organizations;
    }

    public void setOrganizations(organizations organizations) {
        this.organizations = organizations;
    }
}
