package me.geeksploit.markmyword.model.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

//ParserFactory
public class TxtParser implements IParser{
    private File file;
    private int count;


    public TxtParser(String path) {
        this.file = new File(path);
    }

    @Override
    public Observable<Integer> startParse() {
        return Observable.create( emit ->{
            Map<String, Integer> words = new HashMap<>();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))){
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null){
                    sb.append(line);
                    sb.append("\n");
                    line = br.readLine();
                }

                String allText = sb.toString();
                String[] wordsArr = allText.split("\\s*(\\s|,|!|\\?|\\.)\\s*");
                for (String s : wordsArr) {
                    if (words.containsKey(s)) {
                        words.put(s, words.get(s) + 1);
                    } else {
                        words.put(s, 1);
                    }
                    count++;
                    emit.onNext(count);
                }
                emit.onComplete();
            }
        });
    }
}
