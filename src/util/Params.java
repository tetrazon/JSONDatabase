package util;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.Serializable;

public class Params implements Serializable {

    private static final long serialVersionUID = 1L;

    @Parameter(names = "-t", description = "Type of the command. Can be 'set', 'get', 'delete', 'exit'")
    private String type;

    @Parameter(names = "-i", description = "Index of the cell(1-1000)")
    private String index;

    @Parameter(names = "-m", description = "Data of the cell")
    private String data;

    public Params(){};

    public Params(String type, String i) {
        this.type = type;
        this.index = i;
    }

    public Params(String type, String i, String data) {
        this.type = type;
        this.index = i;
        this.data = data;
    }

    public static Params getInstance(String[] args){
        Params params = new Params();
        JCommander.newBuilder()
                .addObject(params)
                .build()
                .parse(args);
        return params;
    }

    @Override
    public String toString() {
        return  checkField(type) + " " + checkField(index) + " " + checkField(data);
    }

    private String checkField(String field){
        return field != null? field : "";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public static void main(String[]args) {
    }
}
