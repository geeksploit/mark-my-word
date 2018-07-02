package me.geeksploit.markmyword.model.parser;

import io.reactivex.Observable;

public interface IParser {
    Observable<Integer> startParse();
}
