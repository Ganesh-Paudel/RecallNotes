package com.ganesh.recallnotes.FileHandling;

import java.io.*;
import java.util.ArrayList;

/**
 * This class handles everything related to reading content from a file
 */
public class ReadFile {
    /* buffered reader for each instance to read */
    private BufferedReader reader;

    /**
     * constructor, takes file object and creates a reader for that file
     * @param file the file object that is going to be used in that instance
     * @throws FileNotFoundException if file does not exist
     */
    public ReadFile(File file) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(file));
    }

    /**
     * returns section of note. It's specifically for notes reads the file and parses them and just returns one section
     * section is title and description. goes through the file parses it and once the content and title is read
     * returns an array of those two
     * @return array of strings of title and content of the section
     */
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

    /**
     * close the reader
     * @throws IOException if the file does not exist io exception will be given
     */
    public void close() throws IOException {
        this.reader.close();
    }

    /**
     * this method is designed to return tasks after reading it as an array
     * @return arraylist of tasks containing an array of string which is [priority, title, description]
     * @throws IOException if the current file associated with the object is or does not exist
     */
    public ArrayList<String[]> getTasks() throws IOException {
        String line;
        ArrayList<String[]> allTasks = new ArrayList<>();
        while((line = reader.readLine()) != null){
            String[] task = line.split("=>|::");
            allTasks.add(task);

        }
        return allTasks;
    }

    /**
     * Returns the topmost task which is the prioritizes task
     * @return a string with the non parsed task (priority=>title::description)
     * @throws IOException object's fiel does not exist
     */
    public String getPriorityTask() throws IOException {
        String task = reader.readLine();
        if(task == null){
            return "";
        }
        return task.trim();
    }
}
