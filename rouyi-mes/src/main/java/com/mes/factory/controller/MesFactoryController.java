package com.mes.factory.controller;

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
import com.mes.factory.domain.MesFactory;
import com.mes.factory.service.IMesFactoryService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * MES工厂Controller
 *
 * @author bin
 * @date 2026-07-11
 */
@RestController
@RequestMapping("/mes/factory")
public class MesFactoryController extends BaseController
{
    @Autowired
    private IMesFactoryService mesFactoryService;

    /**
     * 查询MES工厂列表
     */
    @PreAuthorize("@ss.hasPermi('mes:factory:list')")
    @GetMapping("/list")
    public TableDataInfo list(MesFactory mesFactory)
    {
        startPage();
        List<MesFactory> list = mesFactoryService.selectMesFactoryList(mesFactory);
        return getDataTable(list);
    }

    /**
     * 导出MES工厂列表
     */
    @PreAuthorize("@ss.hasPermi('mes:factory:export')")
    @Log(title = "MES工厂", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MesFactory mesFactory)
    {
        List<MesFactory> list = mesFactoryService.selectMesFactoryList(mesFactory);
        ExcelUtil<MesFactory> util = new ExcelUtil<MesFactory>(MesFactory.class);
        util.exportExcel(response, list, "MES工厂数据");
    }

    /**
     * 导入MES工厂数据
     */
    @PreAuthorize("@ss.hasPermi('mes:factory:import')")
    @Log(title = "MES工厂", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<MesFactory> util = new ExcelUtil<MesFactory>(MesFactory.class);
        List<MesFactory> factoryList = util.importExcel(file.getInputStream());
        String message = mesFactoryService.importFactory(factoryList, updateSupport, getUsername());
        return success(message);
    }

    /**
     * 下载MES工厂导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<MesFactory> util = new ExcelUtil<MesFactory>(MesFactory.class);
        util.importTemplateExcel(response, "MES工厂数据");
    }

    /**
     * 获取MES工厂详细信息
     */
    @PreAuthorize("@ss.hasPermi('mes:factory:query')")
    @GetMapping(value = "/{factoryId}")
    public AjaxResult getInfo(@PathVariable("factoryId") Long factoryId)
    {
        return success(mesFactoryService.selectMesFactoryByFactoryId(factoryId));
    }

    /**
     * 新增MES工厂
     */
    @PreAuthorize("@ss.hasPermi('mes:factory:add')")
    @Log(title = "MES工厂", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody MesFactory mesFactory)
    {
        if (!mesFactoryService.checkFactoryCodeUnique(mesFactory))
        {
            return error("新增工厂'" + mesFactory.getFactoryName() + "'失败，工厂编码已存在");
        }
        mesFactory.setCreateBy(getUsername());
        return toAjax(mesFactoryService.insertMesFactory(mesFactory));
    }

    /**
     * 修改MES工厂
     */
    @PreAuthorize("@ss.hasPermi('mes:factory:edit')")
    @Log(title = "MES工厂", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody MesFactory mesFactory)
    {
        if (!mesFactoryService.checkFactoryCodeUnique(mesFactory))
        {
            return error("修改工厂'" + mesFactory.getFactoryName() + "'失败，工厂编码已存在");
        }
        mesFactory.setUpdateBy(getUsername());
        return toAjax(mesFactoryService.updateMesFactory(mesFactory));
    }

    /**
     * 修改MES工厂状态
     */
    @PreAuthorize("@ss.hasPermi('mes:factory:edit')")
    @Log(title = "MES工厂", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody MesFactory mesFactory)
    {
        mesFactory.setUpdateBy(getUsername());
        return toAjax(mesFactoryService.updateMesFactory(mesFactory));
    }

    /**
     * 删除MES工厂
     */
    @PreAuthorize("@ss.hasPermi('mes:factory:remove')")
    @Log(title = "MES工厂", businessType = BusinessType.DELETE)
    @DeleteMapping("/{factoryIds}")
    public AjaxResult remove(@PathVariable Long[] factoryIds)
    {
        return toAjax(mesFactoryService.deleteMesFactoryByFactoryIds(factoryIds));
    }
}
