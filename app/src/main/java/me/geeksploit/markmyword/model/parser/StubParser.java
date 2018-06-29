package me.geeksploit.markmyword.model.parser;

public class StubParser {
    public static IParser getParser(String path){
        String ext = path.substring(path.lastIndexOf(".") + 1);
        String fixedPath = path.substring(path.lastIndexOf(":") + 1);

        switch (ext){
            case "txt": return new StubTxtParser(fixedPath);
            case "pdf": throw new RuntimeException("Not realized yet");
            default: throw new RuntimeException("No competent parser in fabric");
        }
    }
}
