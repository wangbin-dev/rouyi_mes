package com.mes.workshop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mes.workshop.domain.MesFactoryOption;
import com.mes.workshop.domain.MesWorkshop;
import com.mes.workshop.mapper.MesWorkshopMapper;
import com.mes.workshop.service.IMesWorkshopService;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;

/**
 * MES车间Service业务层处理
 *
 * @author bin
 * @date 2026-07-12
 */
@Service
public class MesWorkshopServiceImpl implements IMesWorkshopService
{
    @Autowired
    private MesWorkshopMapper mesWorkshopMapper;

    @Override
    public MesWorkshop selectMesWorkshopByWorkshopId(Long workshopId)
    {
        return mesWorkshopMapper.selectMesWorkshopByWorkshopId(workshopId);
    }

    @Override
    public List<MesWorkshop> selectMesWorkshopList(MesWorkshop mesWorkshop)
    {
        return mesWorkshopMapper.selectMesWorkshopList(mesWorkshop);
    }

    @Override
    public boolean checkWorkshopCodeUnique(MesWorkshop mesWorkshop)
    {
        Long workshopId = StringUtils.isNull(mesWorkshop.getWorkshopId()) ? -1L : mesWorkshop.getWorkshopId();
        MesWorkshop info = mesWorkshopMapper.checkWorkshopCodeUnique(mesWorkshop.getWorkshopCode());
        if (StringUtils.isNotNull(info) && info.getWorkshopId().longValue() != workshopId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public List<MesFactoryOption> selectEnabledFactoryOptions()
    {
        return mesWorkshopMapper.selectEnabledFactoryOptions();
    }

    @Override
    public String importWorkshop(List<MesWorkshop> workshopList, Boolean updateSupport, String operName)
    {
        if (StringUtils.isNull(workshopList) || workshopList.isEmpty())
        {
            throw new ServiceException("导入车间数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (MesWorkshop workshop : workshopList)
        {
            try
            {
                if (StringUtils.isEmpty(workshop.getFactoryCode()))
                {
                    throw new ServiceException("工厂编码不能为空");
                }
                if (StringUtils.isEmpty(workshop.getWorkshopCode()))
                {
                    throw new ServiceException("车间编码不能为空");
                }
                if (StringUtils.isEmpty(workshop.getWorkshopName()))
                {
                    throw new ServiceException("车间名称不能为空");
                }
                MesFactoryOption factory = mesWorkshopMapper.selectFactoryByFactoryCode(workshop.getFactoryCode());
                if (StringUtils.isNull(factory))
                {
                    throw new ServiceException("工厂编码不存在或工厂已停用");
                }
                workshop.setFactoryId(factory.getFactoryId());
                MesWorkshop existWorkshop = mesWorkshopMapper.checkWorkshopCodeUnique(workshop.getWorkshopCode());
                if (StringUtils.isNull(existWorkshop))
                {
                    workshop.setCreateBy(operName);
                    insertMesWorkshop(workshop);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、车间 ")
                            .append(workshop.getWorkshopCode()).append(" 导入成功");
                }
                else if (updateSupport)
                {
                    workshop.setWorkshopId(existWorkshop.getWorkshopId());
                    workshop.setUpdateBy(operName);
                    updateMesWorkshop(workshop);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、车间 ")
                            .append(workshop.getWorkshopCode()).append(" 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、车间 ")
                            .append(workshop.getWorkshopCode()).append(" 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、车间 " + workshop.getWorkshopCode() + " 导入失败：";
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

    @Override
    public int insertMesWorkshop(MesWorkshop mesWorkshop)
    {
        validateFactoryEnabled(mesWorkshop.getFactoryId());
        mesWorkshop.setCreateTime(DateUtils.getNowDate());
        if (StringUtils.isEmpty(mesWorkshop.getStatus()))
        {
            mesWorkshop.setStatus("0");
        }
        if (StringUtils.isEmpty(mesWorkshop.getVersionNo()))
        {
            mesWorkshop.setVersionNo("1.0");
        }
        if (StringUtils.isNull(mesWorkshop.getRevision()))
        {
            mesWorkshop.setRevision(0L);
        }
        if (StringUtils.isEmpty(mesWorkshop.getDelFlag()))
        {
            mesWorkshop.setDelFlag("0");
        }
        return mesWorkshopMapper.insertMesWorkshop(mesWorkshop);
    }

    @Override
    public int updateMesWorkshop(MesWorkshop mesWorkshop)
    {
        if (StringUtils.isNotNull(mesWorkshop.getFactoryId()))
        {
            validateFactoryEnabled(mesWorkshop.getFactoryId());
        }
        mesWorkshop.setUpdateTime(DateUtils.getNowDate());
        return mesWorkshopMapper.updateMesWorkshop(mesWorkshop);
    }

    @Override
    public int deleteMesWorkshopByWorkshopIds(Long[] workshopIds)
    {
        return mesWorkshopMapper.deleteMesWorkshopByWorkshopIds(workshopIds);
    }

    @Override
    public int deleteMesWorkshopByWorkshopId(Long workshopId)
    {
        return mesWorkshopMapper.deleteMesWorkshopByWorkshopId(workshopId);
    }

    private void validateFactoryEnabled(Long factoryId)
    {
        MesFactoryOption factory = mesWorkshopMapper.selectFactoryByFactoryId(factoryId);
        if (StringUtils.isNull(factory))
        {
            throw new ServiceException("所属工厂不存在或已停用");
        }
    }
}
