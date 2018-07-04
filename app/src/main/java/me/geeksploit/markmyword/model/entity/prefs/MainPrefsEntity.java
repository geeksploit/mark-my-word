package me.geeksploit.markmyword.model.entity.prefs;

public class MainPrefsEntity implements PrefsMarker {
    private boolean isList;
    private boolean isImageDisplayed;
    private String bookTitle;

    public MainPrefsEntity(boolean isList, boolean isImageDisplayed, String bookTitle) {
        this.isList = isList;
        this.isImageDisplayed = isImageDisplayed;
        this.bookTitle = bookTitle;
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

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}
