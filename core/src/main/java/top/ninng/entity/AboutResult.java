package top.ninng.entity;

import java.io.Serializable;

/**
 * “关于”信息响应实体
 *
 * @Author OhmLaw
 * @Date 2023/1/11 16:00
 * @Version 1.0
 */
public class AboutResult implements Serializable {

    private static final long serialVersionUID = 2279499493621489517L;

    private String info;
    private String email;
    private String headPortrait;
    private String id;

    public AboutResult(String info, String email, String headPortrait, String id) {
        this.info = info;
        this.email = email;
        this.headPortrait = headPortrait;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
