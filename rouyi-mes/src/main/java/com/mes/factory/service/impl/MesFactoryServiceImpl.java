package com.mes.factory.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mes.factory.domain.MesFactory;
import com.mes.factory.mapper.MesFactoryMapper;
import com.mes.factory.service.IMesFactoryService;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;

/**
 * MES工厂Service业务层处理
 *
 * @author bin
 * @date 2026-07-11
 */
@Service
public class MesFactoryServiceImpl implements IMesFactoryService
{
    @Autowired
    private MesFactoryMapper mesFactoryMapper;

    /**
     * 查询MES工厂
     *
     * @param factoryId MES工厂主键
     * @return MES工厂
     */
    @Override
    public MesFactory selectMesFactoryByFactoryId(Long factoryId)
    {
        return mesFactoryMapper.selectMesFactoryByFactoryId(factoryId);
    }

    /**
     * 查询MES工厂列表
     *
     * @param mesFactory MES工厂
     * @return MES工厂
     */
    @Override
    public List<MesFactory> selectMesFactoryList(MesFactory mesFactory)
    {
        return mesFactoryMapper.selectMesFactoryList(mesFactory);
    }

    /**
     * 校验工厂编码是否唯一
     *
     * @param mesFactory MES工厂
     * @return 结果
     */
    @Override
    public boolean checkFactoryCodeUnique(MesFactory mesFactory)
    {
        Long factoryId = StringUtils.isNull(mesFactory.getFactoryId()) ? -1L : mesFactory.getFactoryId();
        MesFactory info = mesFactoryMapper.checkFactoryCodeUnique(mesFactory.getFactoryCode());
        if (StringUtils.isNotNull(info) && info.getFactoryId().longValue() != factoryId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 导入MES工厂数据
     *
     * @param factoryList 工厂数据列表
     * @param updateSupport 是否更新已存在数据
     * @param operName 操作人
     * @return 导入结果
     */
    @Override
    public String importFactory(List<MesFactory> factoryList, Boolean updateSupport, String operName)
    {
        if (StringUtils.isNull(factoryList) || factoryList.isEmpty())
        {
            throw new ServiceException("导入工厂数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (MesFactory factory : factoryList)
        {
            try
            {
                if (StringUtils.isEmpty(factory.getFactoryCode()))
                {
                    throw new ServiceException("工厂编码不能为空");
                }
                if (StringUtils.isEmpty(factory.getFactoryName()))
                {
                    throw new ServiceException("工厂名称不能为空");
                }
                MesFactory existFactory = mesFactoryMapper.checkFactoryCodeUnique(factory.getFactoryCode());
                if (StringUtils.isNull(existFactory))
                {
                    factory.setCreateBy(operName);
                    insertMesFactory(factory);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、工厂 ")
                            .append(factory.getFactoryCode()).append(" 导入成功");
                }
                else if (updateSupport)
                {
                    factory.setFactoryId(existFactory.getFactoryId());
                    factory.setUpdateBy(operName);
                    updateMesFactory(factory);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、工厂 ")
                            .append(factory.getFactoryCode()).append(" 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、工厂 ")
                            .append(factory.getFactoryCode()).append(" 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、工厂 " + factory.getFactoryCode() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        return successMsg.toString();
    }

    /**
     * 新增MES工厂
     *
     * @param mesFactory MES工厂
     * @return 结果
     */
    @Override
    public int insertMesFactory(MesFactory mesFactory)
    {
        mesFactory.setCreateTime(DateUtils.getNowDate());
        if (StringUtils.isEmpty(mesFactory.getStatus()))
        {
            mesFactory.setStatus("0");
        }
        if (StringUtils.isEmpty(mesFactory.getVersionNo()))
        {
            mesFactory.setVersionNo("1.0");
        }
        if (StringUtils.isNull(mesFactory.getRevision()))
        {
            mesFactory.setRevision(0L);
        }
        if (StringUtils.isEmpty(mesFactory.getDelFlag()))
        {
            mesFactory.setDelFlag("0");
        }
        return mesFactoryMapper.insertMesFactory(mesFactory);
    }

    /**
     * 修改MES工厂
     *
     * @param mesFactory MES工厂
     * @return 结果
     */
    @Override
    public int updateMesFactory(MesFactory mesFactory)
    {
        mesFactory.setUpdateTime(DateUtils.getNowDate());
        return mesFactoryMapper.updateMesFactory(mesFactory);
    }

    /**
     * 批量删除MES工厂
     *
     * @param factoryIds 需要删除的MES工厂主键
     * @return 结果
     */
    @Override
    public int deleteMesFactoryByFactoryIds(Long[] factoryIds)
    {
        return mesFactoryMapper.deleteMesFactoryByFactoryIds(factoryIds);
    }

    /**
     * 删除MES工厂信息
     *
     * @param factoryId MES工厂主键
     * @return 结果
     */
    @Override
    public int deleteMesFactoryByFactoryId(Long factoryId)
    {
        return mesFactoryMapper.deleteMesFactoryByFactoryId(factoryId);
    }
}
