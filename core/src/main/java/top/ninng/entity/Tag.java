package top.ninng.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * 标签
 *
 * @author OhmLaw
 * @TableName tag
 */
public class Tag implements Serializable {

    private static final long serialVersionUID = -2806251614764202221L;

    /**
     * 标签id
     */
    @JSONField(serialize = false)
    private Integer id;

    /**
     * 混淆标签 id
     */
    @JSONField(name = "id")
    private String obfuscatorId;

    /**
     * 标签名
     */
    private String name;

    /**
     * 创建时间
     */
    @JSONField(serialize = false)
    private Date createTime;

    /**
     * 修改时间
     */
    @JSONField(serialize = false)
    private Date updateTime;

    /**
     * 删除状态
     */
    @JSONField(serialize = false)
    private Boolean deleteStatus;

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 删除状态
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 删除状态
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    /**
     * 标签id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 标签id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 标签名
     */
    public String getName() {
        return name;
    }

    /**
     * 标签名
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getObfuscatorId() {
        return obfuscatorId;
    }

    public void setObfuscatorId(String obfuscatorId) {
        this.obfuscatorId = obfuscatorId;
    }

    /**
     * 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleteStatus() == null) ? 0 : getDeleteStatus().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Tag other = (Tag) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null :
                this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null :
                this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getDeleteStatus() == null ? other.getDeleteStatus() == null :
                this.getDeleteStatus().equals(other.getDeleteStatus()));
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", obfuscatorId='" + obfuscatorId + '\'' +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteStatus=" + deleteStatus +
                '}';
    }
}
