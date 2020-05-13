package com.inqube.aamarmedic.model.baseModel;

public class BaseModelClass {
    public String id;
    public String role;
    public String selected;

    public BaseModelClass(String id, String role) {
        this.id = id;
        this.role = role;
    }

    public BaseModelClass(String id, String role, String selected) {
        this.id = id;
        this.role = role;
        this.selected = selected;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int ascii=0;
        for(int i=0;i<this.getId().length();i++){
            ascii+=(int)this.getId().charAt(i);
        }
        return ascii;
    }

    @Override
    public String toString() {
        return this.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseModelClass other = (BaseModelClass) obj;
        if (!this.getId().equalsIgnoreCase(other.getId()))
            return false;
        return true;
    }
}
