package net.h2so;

/**
 * @Description
 * @Auther mikicomo
 * @Date 2019-06-04 16:09
 */
public class ObjectParam {
    private String id;
    private String name;

    private boolean ok;

    public ObjectParam(String id, String name, boolean isOk) {
        this.id = id;
        this.name = name;
        this.ok = isOk;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
