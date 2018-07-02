package me.geeksploit.markmyword.model.parser;

public class ParserFactory {
    public static IParser getParser(String path){
        String ext = path.substring(path.lastIndexOf(".") + 1);
        String fixedPath = path.substring(path.lastIndexOf(":") + 1);

        switch (ext){
            case "txt": return new TxtParser(fixedPath);
            case "pdf": throw new RuntimeException("Not realized yet");
            default: throw new RuntimeException("No competent parser in fabric");
        }
    }
}
