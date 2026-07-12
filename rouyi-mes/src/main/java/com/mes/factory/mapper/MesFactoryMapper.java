package com.mes.factory.mapper;

import java.util.List;
import com.mes.factory.domain.MesFactory;

/**
 * MES工厂Mapper接口
 *
 * @author bin
 * @date 2026-07-11
 */
public interface MesFactoryMapper
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
     * @param factoryCode 工厂编码
     * @return MES工厂
     */
    public MesFactory checkFactoryCodeUnique(String factoryCode);

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
     * 删除MES工厂
     *
     * @param factoryId MES工厂主键
     * @return 结果
     */
    public int deleteMesFactoryByFactoryId(Long factoryId);

    /**
     * 批量删除MES工厂
     *
     * @param factoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMesFactoryByFactoryIds(Long[] factoryIds);
}
