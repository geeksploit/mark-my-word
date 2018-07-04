package me.geeksploit.markmyword.model.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.geeksploit.markmyword.model.entity.ParseProgress;
import me.geeksploit.markmyword.model.entity.WordModel;
import me.geeksploit.markmyword.model.repository.WordsRepository;

public class TxtParser implements IParser{
    private File file;
    private int count;

    @Inject
    WordsRepository repository;


    public TxtParser(String path) {
        this.file = new File(path);
    }

    @Override
    public Observable<ParseProgress> startParse() {
        return Observable.create( emit ->{
            repository.insertNewBook(file.getName(), file.getPath());
            Map<WordModel, Integer> words = new HashMap<>();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))){
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null){
                    sb.append(line);
                    sb.append("\n");
                    line = br.readLine();
                }

                String allText = sb.toString();
                String[] wordsArr = allText.split("\\W");
                for (String s : wordsArr) {
                    if (s.length() > 1) {
                        WordModel wm = new WordModel(s, "");
                        if (words.containsKey(wm)) {
                            words.put(wm, words.get(wm) + 1);
                        } else {
                            words.put(wm, 1);
                            repository.insertWord(wm);
                        }
                        count++;
                        emit.onNext(new ParseProgress(file.getName(), count, words.size()));
                    }
                }
                emit.onComplete();
            }
        });
    }
}
