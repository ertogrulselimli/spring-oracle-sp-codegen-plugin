package com.ertogrul.plugins.procedure;



public class Parameter {

    private String paramName;
    private String paramType;
    private Direction direction;

    public Parameter(String paramName, String paramType, Direction direction) {
        this.paramName = paramName;
        this.paramType = paramType;
        this.direction = direction;
    }

    public Parameter() {

    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
