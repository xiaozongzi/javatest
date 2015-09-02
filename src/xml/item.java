package xml;

import Bean.title;

import java.util.List;

/**
 * Created by ${zhangzz} on 2015/8/25.
 */

public class item {
  private Bean.title title;
  private List<Bean.item> item;
 /*   private item item;

    public item getItem() {
        return item;
    }

    public void setItem(item item) {
        this.item = item;
    }*/

    public item() {
    }

    public Bean.title getTitle() {
        return title;
    }

    public void setTitle(Bean.title title) {
        this.title = title;
    }

    public List<Bean.item> getItem() {
        return item;
    }

    public void setItem(List<Bean.item> item) {
        this.item = item;
    }
}
