package util;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.Serializable;

public class Params implements Serializable {

    private static final long serialVersionUID = 1L;

    @Parameter(names = "-t", description = "Type of the command. Can be 'set', 'get', 'delete', 'exit'")
    private String type;

    @Parameter(names = "-k", description = "key of the cell")
    private String key;

    @Parameter(names = "-v", description = "Data of the cell")
    private String value;

    public Params(){};

    public Params(String type, String i) {
        this.type = type;
        this.key = i;
    }

    public Params(String type, String i, String data) {
        this.type = type;
        this.key = i;
        this.value = data;
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
        return  checkField(type) + " " + checkField(key) + " " + checkField(value);
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public static void main(String[]args) {
    }
}
