package com.ganesh.recallnotes.FileHandling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WriteFile {

    private boolean append;
    private File file;

    public WriteFile() {
        this.append = false;
    }
    public WriteFile(File file){
        this.file = file;
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

    public WriteFile(String fileName){
        this.file = new File(fileName);
    }

    public void writeTask(String title, String description, String priority) throws IOException {

        Path path = Paths.get("tasks.txt");
        List<String> tasks = Files.readAllLines(path);

        String newTask = priority + "=>" + title + "::"+description;
        int insertIndex = tasks.size();

        for(int i = 0; i < tasks.size(); i++){
            String line = tasks.get(i);
            if(line.startsWith(priority+"=>")){
                insertIndex = i + 1;
            }
        }

        tasks.add(insertIndex, newTask);
        Files.write(path, tasks);

    }
}
