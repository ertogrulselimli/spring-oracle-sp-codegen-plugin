package com.ertogrul.plugins.codegenerate;

import com.ertogrul.plugins.procedure.Direction;
import com.ertogrul.plugins.procedure.Parameter;
import com.ertogrul.plugins.procedure.Procedure;

import java.util.ArrayList;
import java.util.List;



public class GenerateFile {


    private  static void generateSingleMethod(StringBuilder source, Procedure procedure){

        source.append("public Object /*write your function name */ "+procedure.getProcedureName()+"() throws ParameterTypeException, ProcedureException {");
        source.append("\n");
        source.append("List<Parameter> parameters = new ArrayList<>();");
        source.append("\n\n\n");
        List<Parameter> outParams=new ArrayList<>();
        procedure.getParameters().stream().forEach(t->{
            String type="";
            if(t.getParamType().equalsIgnoreCase("VARCHAR2")){
                type="Types.VARCHAR";
            }else if(t.getParamType().equalsIgnoreCase("NUMBER")){
                type="Types.NUMERIC";
            }else if(t.getParamType().equalsIgnoreCase("REF CURSOR")){
                type="Types.REF";
            }else {

            }
            source.append("parameters.add(new ParameterBuilder(\""+t.getParamName()+"\","+type+")");
            if(t.getDirection()== Direction.OUT){

                source.append(".withDirection(Direction.OUT)");
                if(type.equals("Types.REF")){
                    source.append(".withRowMapper(/* specify mapper*/ null)");
                }
                outParams.add(t);

            }
            source.append(".createParameter());");
            source.append("\n");

        });
        source.append("\n");
        source.append("Map<String, Object> result = new StoredProcedure().\n" +
                "                    procedureName(\""+procedure.getProcedureName()+"\").\n" +
                "                    packageName(\""+procedure.getPackageName()+"\").\n" +
                "                    schemaName(\""+procedure.getSchemaName()+"\").jdbcTemplate(jdbcTemplate).parameters(parameters).execute();");
        source.append("\n\n");

        outParams.stream().forEach(c->{

            source.append("    final  Object "+c.getParamName()+" =  result.get(\""+c.getParamName()+"\");");
            source.append("\n");
        });

        source.append("return null;");
        source.append("\n");
        source.append("}");

    }


    public static StringBuilder  generateDaoClass(String className, List<Procedure> procedures){
        StringBuilder source=new StringBuilder();
        source.append("import com.gnisoft.storedprocedure.exceptions.ParameterTypeException;\n");
        source.append("import com.gnisoft.storedprocedure.exceptions.ProcedureException; \n");
        source.append("import com.gnisoft.storedprocedure.procedures.Direction;\n" +
                "import com.gnisoft.storedprocedure.procedures.Parameter;\n" +
                "import com.gnisoft.storedprocedure.procedures.ParameterBuilder;\n" +
                "import com.gnisoft.storedprocedure.procedures.StoredProcedure;");
        source.append("\n");
        source.append("import org.springframework.stereotype.Component;\n");
        source.append("import org.springframework.beans.factory.annotation.Autowired;\n");
        source.append("import org.springframework.jdbc.core.JdbcTemplate;\n\n");
        source.append("import java.util.Map;\n");
        source.append("import java.sql.Types;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n\n")  ;

        source.append("\n");
        source.append("@Component");
        source.append("\n");
        source.append("public class "+className+" "+"{"+"\n");
        source.append("\n\n");
        source.append("    @Autowired\n" +
                "    JdbcTemplate jdbcTemplate;");
        source.append("\n");
        source.append("\n\n");

        for(Procedure p:procedures){

            generateSingleMethod(source,p);
            source.append("\n\n\n") ;
        }
        source.append("\n");
        source.append("}");
        return source;
    }



}
