package top.ninng.entity;

/**
 * @Author OhmLaw
 * @Date 2023/1/11 16:00
 * @Version 1.0
 */
public class AboutResult {

    private String info;
    private String email;
    private String headPortrait;

    public AboutResult(String info, String email, String headPortrait) {
        this.info = info;
        this.email = email;
        this.headPortrait = headPortrait;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
