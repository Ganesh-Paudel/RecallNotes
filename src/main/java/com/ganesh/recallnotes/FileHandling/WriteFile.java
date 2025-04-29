package com.ganesh.recallnotes.FileHandling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class handles everything related to writing into the file
 */
public class WriteFile {

    /* to keep track if the user wanted to append the data or not */
    private boolean append;
    /* to store the file object that will be passed on with creation of instance */
    private File file;

    /**
     * default constructor makes the append property to befalse
     */
    public WriteFile() {
        this.append = false;
    }

//    public WriteFile(File file){
//        this.file = file;
//    }


    /**
     * constructor when passed a boolean sets the value of appedn to that
     * @param append add end of file or overwrite the file
     */
    public WriteFile(boolean append){
        this.append = append;
    }

    /**
     * constructor when provided a filename creates a file on that and then assigns the file variable to that
     * @param fileName the name of the file
     */
    public WriteFile(String fileName){
        this.file = new File(fileName);
    }

    /**
     * Designed for the writing of note into the file. Formats the string and then adds it to the file
     * based on the preference of the user
     * @param file the file where the data is to be stored
     * @param title the title of the note
     * @param content the content of the note
     */
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


    /**
     * Desgined to write the task in to-do-list. It formats the string and then adds into the file
     * We go through the list and as long as the priority is smaller than the current line we are on we keep going
     * and as soon as we get priority that's lesser than current one then insert the new task on that index
     *
     * @param title the title of the task
     * @param description the description of the task
     * @param priority the priority of the task
     * @throws IOException if the file doesnot exist then it will throw the io exception
     */
    public void writeTask(String title, String description, String priority) throws IOException {

        File file = new File("tasks.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        Path path = Paths.get("tasks.txt");
        List<String> tasks = Files.readAllLines(path);

        String newTask = priority + "=>" + title + "::" + description;
        int insertIndex = 0;

        for (int i = 0; i < tasks.size(); i++) {
            String line = tasks.get(i);
            String existingPriority = line.split("=>")[0];

            if (comparePriority(priority, existingPriority) < 0) {
                insertIndex = i;
                break;
            }
            insertIndex = i + 1;
        }

        tasks.add(insertIndex, newTask);
        Files.write(path, tasks);

    }

    /**
     * This method is used to compare the priority between two tasks. It checks the two strings and return 0 if same
     * if not same returns -1 if the first one is small and 1 if the second one is small
     * @param p1 priority one
     * @param p2 priority two
     * @return 0 if same, -1 if p1 < p2, 1 if p1 > p2
     */
    private int comparePriority(String p1, String p2) {
        List<String> priorityOrder = Arrays.asList("H", "M", "L");

        return Integer.compare(
                priorityOrder.indexOf(p1),
                priorityOrder.indexOf(p2)
        );
    }

    /**
     * This is for the appending the tasks when the user changes the task in runtime. It receives list and just
     * writes them into the file. the file being received should not consist of the task that is removed or deleted
     * @param taskComponents the list of tasks without the deleting task
     * @throws IOException if the tasks file doesnot exist
     */
    public void writeTask(ArrayList<String[]> taskComponents) throws IOException {

        Path path = Paths.get("tasks.txt");
        List<String> tasks = new ArrayList<>();

        for(int i = 0; i < taskComponents.size(); i++){
            String newTask = taskComponents.get(i)[0] + "=>" + taskComponents.get(i)[1] + "::"+taskComponents.get(i)[2];
            tasks.add(newTask);
        }
        Files.write(path, tasks);

    }
}
