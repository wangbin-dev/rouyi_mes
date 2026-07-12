package com.mes.workshop.mapper;

import java.util.List;
import com.mes.workshop.domain.MesFactoryOption;
import com.mes.workshop.domain.MesWorkshop;

/**
 * MES车间Mapper接口
 *
 * @author bin
 * @date 2026-07-12
 */
public interface MesWorkshopMapper
{
    public MesWorkshop selectMesWorkshopByWorkshopId(Long workshopId);

    public List<MesWorkshop> selectMesWorkshopList(MesWorkshop mesWorkshop);

    public MesWorkshop checkWorkshopCodeUnique(String workshopCode);

    public MesFactoryOption selectFactoryByFactoryId(Long factoryId);

    public MesFactoryOption selectFactoryByFactoryCode(String factoryCode);

    public List<MesFactoryOption> selectEnabledFactoryOptions();

    public int insertMesWorkshop(MesWorkshop mesWorkshop);

    public int updateMesWorkshop(MesWorkshop mesWorkshop);

    public int deleteMesWorkshopByWorkshopId(Long workshopId);

    public int deleteMesWorkshopByWorkshopIds(Long[] workshopIds);
}
