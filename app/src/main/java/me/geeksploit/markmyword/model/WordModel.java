package ru.supernacho.markmywordtemp.model;

import java.net.URI;

public class WordModel {
    private String word;
    private String translate;
    private String description;
    private String imgUri;

    public WordModel(String word, String translate) {
        this.word = word;
        this.translate = translate;
        this.description = "";
    }

    public WordModel(String word, String translate, String description, String imgUri) {
        this.word = word;
        this.translate = translate;
        this.description = description;
        this.imgUri = imgUri;
}

    public String getWord() {
        return word;
    }

    public String getTranslate() {
        return translate;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUri() {
        return imgUri;
    }
}
