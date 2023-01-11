package top.ninng.utils;

import org.springframework.stereotype.Component;
import top.ninng.entity.Config;
import top.ninng.mapper.ConfigMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author OhmLaw
 * @Date 2023/1/10 19:58
 * @Version 1.0
 */
@Component
public class GetConfig {

    ConfigMapper configMapper;
    ArrayList<Config> configArrayList;
    Map<String, String> map;

    public GetConfig(ConfigMapper configMapper) {
        this.configMapper = configMapper;
    }

    public Map<String, String> map() {
        if (EmptyCheck.isEmpty(map)) {
            this.configArrayList = configMapper.selectAll();
        }
        if (EmptyCheck.isEmpty(map)) {
            map = new HashMap<>(configArrayList.size());
            for (Config config : configArrayList) {
                map.put(config.getKey(), config.getValue());
            }
        }
        return map;
    }
}
