package com.mes.factory.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * MES工厂对象 mes_factory
 *
 * @author bin
 * @date 2026-07-11
 */
public class MesFactory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工厂ID */
    private Long factoryId;

    /** 工厂编码 */
    @Excel(name = "工厂编码")
    private String factoryCode;

    /** 工厂名称 */
    @Excel(name = "工厂名称")
    private String factoryName;

    /** 负责人 */
    @Excel(name = "负责人")
    private String manager;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 版本号 */
    private String versionNo;

    /** 乐观锁版本 */
    private Long revision;

    /** 删除标识（0存在 2删除） */
    private String delFlag;

    public Long getFactoryId()
    {
        return factoryId;
    }

    public void setFactoryId(Long factoryId)
    {
        this.factoryId = factoryId;
    }

    @NotBlank(message = "工厂编码不能为空")
    @Size(max = 64, message = "工厂编码长度不能超过64个字符")
    public String getFactoryCode()
    {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode)
    {
        this.factoryCode = factoryCode;
    }

    @NotBlank(message = "工厂名称不能为空")
    @Size(max = 100, message = "工厂名称长度不能超过100个字符")
    public String getFactoryName()
    {
        return factoryName;
    }

    public void setFactoryName(String factoryName)
    {
        this.factoryName = factoryName;
    }

    @Size(max = 64, message = "负责人长度不能超过64个字符")
    public String getManager()
    {
        return manager;
    }

    public void setManager(String manager)
    {
        this.manager = manager;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getVersionNo()
    {
        return versionNo;
    }

    public void setVersionNo(String versionNo)
    {
        this.versionNo = versionNo;
    }

    public Long getRevision()
    {
        return revision;
    }

    public void setRevision(Long revision)
    {
        this.revision = revision;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("factoryId", getFactoryId())
                .append("factoryCode", getFactoryCode())
                .append("factoryName", getFactoryName())
                .append("manager", getManager())
                .append("status", getStatus())
                .append("versionNo", getVersionNo())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("revision", getRevision())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
