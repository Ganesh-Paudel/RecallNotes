package com.ganesh.recallnotes.FileHandling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {

    private boolean append;

    public WriteFile() {
        this.append = false;
    }

    public WriteFile(boolean append){
        this.append = append;
    }

    public void write(File file , String title, String content){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, this.append))) {
            String newTitle = "# " + title + " #";
            String newContent = "@@ " + content + " @@";
            writer.write(newTitle);
            writer.newLine();
            writer.write(newContent);
            writer.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } ;
    }
}
