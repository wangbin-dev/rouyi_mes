package com.mes.workshop.service;

import java.util.List;
import com.mes.workshop.domain.MesFactoryOption;
import com.mes.workshop.domain.MesWorkshop;

/**
 * MES车间Service接口
 *
 * @author bin
 * @date 2026-07-12
 */
public interface IMesWorkshopService
{
    public MesWorkshop selectMesWorkshopByWorkshopId(Long workshopId);

    public List<MesWorkshop> selectMesWorkshopList(MesWorkshop mesWorkshop);

    public boolean checkWorkshopCodeUnique(MesWorkshop mesWorkshop);

    public List<MesFactoryOption> selectEnabledFactoryOptions();

    public String importWorkshop(List<MesWorkshop> workshopList, Boolean updateSupport, String operName);

    public int insertMesWorkshop(MesWorkshop mesWorkshop);

    public int updateMesWorkshop(MesWorkshop mesWorkshop);

    public int deleteMesWorkshopByWorkshopIds(Long[] workshopIds);

    public int deleteMesWorkshopByWorkshopId(Long workshopId);
}
