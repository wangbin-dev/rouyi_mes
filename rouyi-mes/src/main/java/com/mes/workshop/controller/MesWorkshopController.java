package com.mes.workshop.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.mes.workshop.domain.MesWorkshop;
import com.mes.workshop.service.IMesWorkshopService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * MES车间Controller
 *
 * @author bin
 * @date 2026-07-12
 */
@RestController
@RequestMapping("/mes/workshop")
public class MesWorkshopController extends BaseController
{
    @Autowired
    private IMesWorkshopService mesWorkshopService;

    @PreAuthorize("@ss.hasPermi('mes:workshop:list')")
    @GetMapping("/list")
    public TableDataInfo list(MesWorkshop mesWorkshop)
    {
        startPage();
        List<MesWorkshop> list = mesWorkshopService.selectMesWorkshopList(mesWorkshop);
        return getDataTable(list);
    }

    @GetMapping("/factoryOptions")
    public AjaxResult factoryOptions()
    {
        return success(mesWorkshopService.selectEnabledFactoryOptions());
    }

    @PreAuthorize("@ss.hasPermi('mes:workshop:export')")
    @Log(title = "MES车间", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MesWorkshop mesWorkshop)
    {
        List<MesWorkshop> list = mesWorkshopService.selectMesWorkshopList(mesWorkshop);
        ExcelUtil<MesWorkshop> util = new ExcelUtil<MesWorkshop>(MesWorkshop.class);
        util.exportExcel(response, list, "MES车间数据");
    }

    @PreAuthorize("@ss.hasPermi('mes:workshop:import')")
    @Log(title = "MES车间", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<MesWorkshop> util = new ExcelUtil<MesWorkshop>(MesWorkshop.class);
        List<MesWorkshop> workshopList = util.importExcel(file.getInputStream());
        String message = mesWorkshopService.importWorkshop(workshopList, updateSupport, getUsername());
        return success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<MesWorkshop> util = new ExcelUtil<MesWorkshop>(MesWorkshop.class);
        util.importTemplateExcel(response, "MES车间数据");
    }

    @PreAuthorize("@ss.hasPermi('mes:workshop:query')")
    @GetMapping(value = "/{workshopId}")
    public AjaxResult getInfo(@PathVariable("workshopId") Long workshopId)
    {
        return success(mesWorkshopService.selectMesWorkshopByWorkshopId(workshopId));
    }

    @PreAuthorize("@ss.hasPermi('mes:workshop:add')")
    @Log(title = "MES车间", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody MesWorkshop mesWorkshop)
    {
        if (!mesWorkshopService.checkWorkshopCodeUnique(mesWorkshop))
        {
            return error("新增车间'" + mesWorkshop.getWorkshopName() + "'失败，车间编码已存在");
        }
        mesWorkshop.setCreateBy(getUsername());
        return toAjax(mesWorkshopService.insertMesWorkshop(mesWorkshop));
    }

    @PreAuthorize("@ss.hasPermi('mes:workshop:edit')")
    @Log(title = "MES车间", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody MesWorkshop mesWorkshop)
    {
        if (!mesWorkshopService.checkWorkshopCodeUnique(mesWorkshop))
        {
            return error("修改车间'" + mesWorkshop.getWorkshopName() + "'失败，车间编码已存在");
        }
        mesWorkshop.setUpdateBy(getUsername());
        return toAjax(mesWorkshopService.updateMesWorkshop(mesWorkshop));
    }

    @PreAuthorize("@ss.hasPermi('mes:workshop:edit')")
    @Log(title = "MES车间", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody MesWorkshop mesWorkshop)
    {
        mesWorkshop.setUpdateBy(getUsername());
        return toAjax(mesWorkshopService.updateMesWorkshop(mesWorkshop));
    }

    @PreAuthorize("@ss.hasPermi('mes:workshop:remove')")
    @Log(title = "MES车间", businessType = BusinessType.DELETE)
    @DeleteMapping("/{workshopIds}")
    public AjaxResult remove(@PathVariable Long[] workshopIds)
    {
        return toAjax(mesWorkshopService.deleteMesWorkshopByWorkshopIds(workshopIds));
    }
}
