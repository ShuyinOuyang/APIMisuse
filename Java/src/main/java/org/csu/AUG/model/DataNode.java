package org.csu.AUG.model;

import java.util.Optional;

public interface DataNode extends Node{
    /**
     *
     * @return 返回数据的名称
     */
    String getName();

    /**
     *
     * @return 返回数据的值
     */
    String getValue();

    /**
     *
     * @return 返回数据类型名称
     */
    String getType();

    //过滤基本数据类型
    @Override
    default Optional<String> getAPI(){
        String dataType = getType();
        switch (dataType){
            case "int":
            case "long":
            case "float":
            case "double":
            case "short":
            case "boolean":
            case "null":
                return Optional.empty();
            default:
                if(dataType.endsWith("[]")){
                    return Optional.empty();
                }
                else {
                    return Optional.of(getType());
                }
        }
    }
}
