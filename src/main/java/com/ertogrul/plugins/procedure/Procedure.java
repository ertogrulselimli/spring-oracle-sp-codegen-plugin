package com.ertogrul.plugins.procedure;

import java.util.ArrayList;
import java.util.List;

public class Procedure {

    private String schemaName;

    private String packageName;

    private String procedureName;


    private List<Parameter> parameters=new ArrayList<Parameter>();

    public Procedure() {

    }


    public Procedure(String schemaName, String packageName, String procedureName) {
        this.schemaName = schemaName;
        this.packageName = packageName;
        this.procedureName = procedureName;
    }


    public List<Parameter> getParameters() {
        return parameters;
    }


    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }




}
