package com.ganesh.recallnotes.FileHandling;

import java.io.*;

public class ReadFile {
    private BufferedReader reader;
    private static int lineCounter = 0;

    public ReadFile(File file) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(file));
    }

    public String[] getSection(){
        String line;
        String title = null;
        String content = null;
        try{
            while((line = reader.readLine()) != null){
                line = line.trim();

                if(line.startsWith("#") && line.endsWith("#")) {
                    title = line.substring(1, line.length() - 2).trim();
                } else if(line.startsWith("@@") && line.endsWith("@@") && title != null) {
                    content = line.substring(2, line.length() - 3).trim();
                    return new String[]{title, content};
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private void skipLines(BufferedReader reader, int lineCounter) throws IOException {
        for(int i = 0; i < lineCounter; i++){
            reader.readLine();
        }
    }

    public void close() throws IOException {
        this.reader.close();
    }
}
