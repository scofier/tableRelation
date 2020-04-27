package com.demo.web;

import com.demo.dto.ColumnDto;
import com.demo.repository.TableMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sk
 */
@RequestMapping("/api")
@RestController
public class ApiController {

    @Resource
    TableMapper tableMapper;

    @GetMapping("/tables")
    public Object tables(String db) {
        return tableMapper.getAllTable(db);
    }

    @GetMapping("/tableInfo")
    public Object tableInfo(String db, String tb) {
        return tableMapper.getTableInfos(db, tb.split(","));
    }

    @GetMapping("/getAllTableInfo")
    public Object getAllTableInfo(String db) {
        Map<String, List<ColumnDto>> datas =  tableMapper.getAllTableInfo(db);
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for(Map.Entry<String,List<ColumnDto>> en : datas.entrySet()) {
            Map<String,Object> map = new HashMap<>();
            map.put("key", en.getKey());
            // format items
            en.getValue().forEach(e-> e.setName(e.getName() + "" + e.getDesc()));
            map.put("items", en.getValue());
            list.add(map);
        }
        return list;
    }

    @GetMapping("/getRelateTable")
    public Object getRelateTable(String db) {
        return tableMapper.getRelateTable(db);
    }


}
