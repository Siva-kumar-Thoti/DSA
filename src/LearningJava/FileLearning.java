package LearningJava;

import java.io.*;

public class FileLearning {

  public static void main(String[] args) throws IOException {
    File file = new File("src/LearningJava/Siva.txt");
    if(file.exists()){
      System.out.println("file already exists");
    }
    if (file.createNewFile()) {
      System.out.println("File created");
    } else {
      System.out.println("file already created");
    }

    if(file.exists()){
      if(file.isFile()){
        System.out.println("it is a file");
      }
    }
  }
}
