package com.mes.factory.service;

import java.util.List;
import com.mes.factory.domain.MesFactory;

/**
 * MES工厂Service接口
 *
 * @author bin
 * @date 2026-07-11
 */
public interface IMesFactoryService
{
    /**
     * 查询MES工厂
     *
     * @param factoryId MES工厂主键
     * @return MES工厂
     */
    public MesFactory selectMesFactoryByFactoryId(Long factoryId);

    /**
     * 查询MES工厂列表
     *
     * @param mesFactory MES工厂
     * @return MES工厂集合
     */
    public List<MesFactory> selectMesFactoryList(MesFactory mesFactory);

    /**
     * 校验工厂编码是否唯一
     *
     * @param mesFactory MES工厂
     * @return 结果
     */
    public boolean checkFactoryCodeUnique(MesFactory mesFactory);

    /**
     * 导入MES工厂数据
     *
     * @param factoryList 工厂数据列表
     * @param updateSupport 是否更新已存在数据
     * @param operName 操作人
     * @return 导入结果
     */
    public String importFactory(List<MesFactory> factoryList, Boolean updateSupport, String operName);

    /**
     * 新增MES工厂
     *
     * @param mesFactory MES工厂
     * @return 结果
     */
    public int insertMesFactory(MesFactory mesFactory);

    /**
     * 修改MES工厂
     *
     * @param mesFactory MES工厂
     * @return 结果
     */
    public int updateMesFactory(MesFactory mesFactory);

    /**
     * 批量删除MES工厂
     *
     * @param factoryIds 需要删除的MES工厂主键集合
     * @return 结果
     */
    public int deleteMesFactoryByFactoryIds(Long[] factoryIds);

    /**
     * 删除MES工厂信息
     *
     * @param factoryId MES工厂主键
     * @return 结果
     */
    public int deleteMesFactoryByFactoryId(Long factoryId);
}
