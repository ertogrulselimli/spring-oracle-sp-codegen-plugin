package com.ertogrul.plugins.procedure;


import org.apache.maven.plugin.MojoFailureException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProcedureBuilder {


    private String connectionUrl;

    private String username;

    private String password;

    private String packageName;

    private List<String> procedures;


    public ProcedureBuilder() {

    }

    public ProcedureBuilder(String connectionUrl,
                            String username,
                            String password,
                            String packageName,
                            List<String> procedures) {
        this.connectionUrl = connectionUrl;
        this.username = username;
        this.password = password;
        this.packageName = packageName;
        this.procedures = procedures;
    }


    private Procedure getSingleProcedure(Connection conn, DatabaseMetaData metaData,String procedureName) throws MojoFailureException{

        Procedure procedure=new Procedure();
        ResultSet rs=null;
        try{


            rs = metaData.getProcedureColumns(packageName,
                metaData.getUserName(),
                procedureName,
                "%");
          rs.next();
          procedure.setPackageName(rs.getString(1));
          procedure.setSchemaName(rs.getString(2));
          procedure.setProcedureName(rs.getString(3));

        do {
            String dbColumnName = rs.getString(4);
            short dbColumnReturn = rs.getShort(5);
            String dbColumnReturnTypeName = rs.getString(7);
            int dbColumnPrecision = rs.getInt(8);
            int dbColumnByteLength = rs.getInt(9);
            short dbColumnScale = rs.getShort(10);
            short dbColumnRadix = rs.getShort(11);
            String dbColumnRemarks = rs.getString(13);

            // Interpret the return type (readable for humans)
            Direction procReturn = null;

            switch (dbColumnReturn) {
                case DatabaseMetaData.procedureColumnIn:
                    procReturn = Direction.IN;
                    break;
                case DatabaseMetaData.procedureColumnOut:
                    procReturn = Direction.OUT;
                    break;
                case DatabaseMetaData.procedureColumnInOut:
                    procReturn = Direction.INOUT;
                    break;
                default:
                    procReturn = Direction.IN;
            }

            Parameter param = new Parameter();
            param.setDirection(procReturn);
            param.setParamName(dbColumnName);
            param.setParamType(dbColumnReturnTypeName);
            procedure.getParameters().add(param);

        } while (rs.next());


    } catch (SQLException e) {
        throw new MojoFailureException(e.getMessage());
    } finally {
        try {
            rs.close();
        } catch (SQLException e) {
            throw new MojoFailureException(e.getMessage());
        }
    }
        return procedure;


    }


    public List<Procedure> buildProcedure() throws MojoFailureException {
        Connection conn = null;
         List<Procedure> procedureList=new ArrayList<>();
        try {
            conn = DriverManager.getConnection(connectionUrl, username, password);
            DatabaseMetaData metaData = conn.getMetaData();
             for(String prcName :this.procedures){
                 final Procedure singleProcedure = getSingleProcedure(conn, metaData, prcName);
                 procedureList.add(singleProcedure);
             }

        } catch (SQLException e) {
            throw new MojoFailureException(e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new MojoFailureException(e.getMessage());
            }
        }
        return procedureList;
    }



    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }


    public List<String> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<String> procedures) {
        this.procedures = procedures;
    }
}
