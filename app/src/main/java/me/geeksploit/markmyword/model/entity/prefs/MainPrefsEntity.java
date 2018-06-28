package me.geeksploit.markmyword.model.entity.prefs;

public class MainPrefsEntity implements PrefsMarker {
    private boolean isList;
    private boolean isImageDisplayed;

    public MainPrefsEntity(boolean isList, boolean isImageDisplayed) {
        this.isList = isList;
        this.isImageDisplayed = isImageDisplayed;
    }

    public boolean isList() {
        return isList;
    }

    public void setList(boolean list) {
        isList = list;
    }

    public boolean isImageDisplayed() {
        return isImageDisplayed;
    }

    public void setImageDisplayed(boolean imageDisplayed) {
        isImageDisplayed = imageDisplayed;
    }
}
