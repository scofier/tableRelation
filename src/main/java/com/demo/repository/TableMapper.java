package com.demo.repository;

import com.demo.dto.ColumnDto;
import com.demo.dto.TableDto;
import com.demo.dto.TableRelationDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface TableMapper {

    @Select("select TABLE_NAME name,TABLE_COMMENT `desc` from information_schema.TABLES where TABLE_SCHEMA=#{arg1}")
    List<TableDto> getAllTable(String db);

    @Select("select COLUMN_NAME name,COLUMN_COMMENT `desc`,COLUMN_TYPE type,TABLE_NAME tb from information_schema.COLUMNS where TABLE_SCHEMA=#{db} and TABLE_NAME in(${tb})")
    List<ColumnDto> getTableInfo(@Param("db") String db, @Param("tb")String tb);

    @Select("select `from`,`to`,`text`,to_text toText from table_relation where db=#{db}")
    List<TableRelationDto> getRelateTable(String db);

    default List<ColumnDto> getTableInfos(@Param("db") String db, @Param("tb")String[] tbs) {
        return getTableInfo(db, String.format("'%s'", String.join("','", tbs)));
    }

    default Map<String,List<ColumnDto>> getAllTableInfo(String db) {
        List<TableDto> tables = getAllTable(db);
        List<ColumnDto> columnDtoList = getTableInfos(db, tables.stream().map(TableDto::getName).toArray(String[]::new));

        return columnDtoList.stream().collect(Collectors.groupingBy(ColumnDto::getTb));
    }
}
