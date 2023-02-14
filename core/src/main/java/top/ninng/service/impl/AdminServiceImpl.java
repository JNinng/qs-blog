package top.ninng.service.impl;

import org.springframework.stereotype.Service;
import top.ninng.entity.Config;
import top.ninng.entity.UnifyResponse;
import top.ninng.mapper.ConfigMapper;
import top.ninng.service.IAdminService;
import top.ninng.utils.GetConfig;
import top.ninng.utils.IdObfuscator;

import java.util.ArrayList;

/**
 * 管理业务
 *
 * @Author OhmLaw
 * @Date 2023/2/13 16:55
 * @Version 1.0
 */
@Service
public class AdminServiceImpl implements IAdminService {

    GetConfig getConfig;
    IdObfuscator idObfuscator;
    ConfigMapper configMapper;

    public AdminServiceImpl(GetConfig getConfig, IdObfuscator idObfuscator, ConfigMapper configMapper) {
        this.getConfig = getConfig;
        this.idObfuscator = idObfuscator;
        this.configMapper = configMapper;
    }

    @Override
    public UnifyResponse<ArrayList<Config>> getAllConfig() {
        return UnifyResponse.ok(getConfig.getConfigArrayList());
    }

    @Override
    public UnifyResponse<String> updateConfig(long id, String key, String value, String info, String defaultValue) {
        int result = configMapper.updateByPrimaryKeySelective(new Config(Math.toIntExact(id), key, value, info,
                defaultValue));
        getConfig.updateData();
        return UnifyResponse.ok(result > 0 ? "更新成功！" : "更新失败！", null);
    }
}
