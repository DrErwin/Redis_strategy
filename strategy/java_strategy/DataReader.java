package java_strategy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataReader {
    String CYCLIC_VISIT_PATH;
    ArrayList<String> cyclicVisitData = new ArrayList<String>();

    public DataReader(String path) {
        this.CYCLIC_VISIT_PATH = path;
        this.readFile();
    }
    
    private void readFile(){
        try (FileReader reader = new FileReader(this.CYCLIC_VISIT_PATH);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                // System.out.println(line);
                cyclicVisitData.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getData(){
        return (String[])this.cyclicVisitData.toArray(new String[this.cyclicVisitData.size()]);
    }
}
