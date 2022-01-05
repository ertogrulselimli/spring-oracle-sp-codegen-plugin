package com.ertogrul.plugins;


import com.ertogrul.plugins.codegenerate.GenerateFile;
import com.ertogrul.plugins.procedure.Procedure;
import com.ertogrul.plugins.procedure.ProcedureBuilder;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


@Mojo(name = "generate")
public class GenerateProcedureMojo extends AbstractMojo{


    @Parameter(property = "generate.connectionUrl")
    private String connectionUrl;


    @Parameter(property = "generate.username")
    private String username;


    @Parameter(property = "generate.password")
    private String password;


    @Parameter(property = "generate.packageName")
    private String packageName;



/*
    @Parameter(property = "generate.procedureName")
    private String procedureName;*/


    @Parameter
    private List<String> procedures;


    @Parameter(property = "generate.className")
    private String className;


    @Parameter(property = "generate.outputDirectory")
    private File outputDirectory;



    public void execute() throws MojoExecutionException, MojoFailureException {

        try {
            ProcedureBuilder builder = new ProcedureBuilder(connectionUrl, username, password, packageName, procedures);
            final List<Procedure> procedureList = builder.buildProcedure();
            StringBuilder classContent = GenerateFile.generateDaoClass(className, procedureList);

            File f = outputDirectory;
            if (!f.exists()) {
                f.mkdirs();
            }
            File touch = new File(f, className + ".java");

            FileWriter w = null;
            try {
                w = new FileWriter(touch);
                w.write(classContent.toString());
            } catch (IOException e) {
                throw new MojoExecutionException("Error creating file " + touch, e);
            } finally {
                if (w != null) {
                    try {
                        w.close();
                    } catch (IOException e) {
                        getLog().error(e);

                    }
                }
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            throw new MojoExecutionException(ex.getMessage());

        }
    }

}
