package com.mes.workshop.domain;

/**
 * MES工厂下拉选项
 *
 * @author bin
 * @date 2026-07-12
 */
public class MesFactoryOption
{
    private Long factoryId;

    private String factoryCode;

    private String factoryName;

    public Long getFactoryId()
    {
        return factoryId;
    }

    public void setFactoryId(Long factoryId)
    {
        this.factoryId = factoryId;
    }

    public String getFactoryCode()
    {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode)
    {
        this.factoryCode = factoryCode;
    }

    public String getFactoryName()
    {
        return factoryName;
    }

    public void setFactoryName(String factoryName)
    {
        this.factoryName = factoryName;
    }
}
