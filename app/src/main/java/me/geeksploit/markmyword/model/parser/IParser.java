package me.geeksploit.markmyword.model.parser;

import io.reactivex.Observable;
import me.geeksploit.markmyword.model.entity.ParseProgress;

public interface IParser {
    Observable<ParseProgress> startParse();
}
