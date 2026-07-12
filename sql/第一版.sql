CREATE TABLE IF NOT EXISTS mes_factory (
    factory_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '工厂ID',
    factory_code VARCHAR(64) NOT NULL COMMENT '工厂编码',
    factory_name VARCHAR(100) NOT NULL COMMENT '工厂名称',
    manager VARCHAR(64) DEFAULT NULL COMMENT '负责人',
    status CHAR(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    version_no VARCHAR(32) DEFAULT '1.0' COMMENT '版本号',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT NULL COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT NULL COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    revision INT NOT NULL DEFAULT 0 COMMENT '乐观锁版本',
    del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0存在 2删除）',
    PRIMARY KEY (factory_id),
    UNIQUE KEY uk_mes_factory_code (factory_code)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES工厂';

CREATE TABLE IF NOT EXISTS mes_workshop (
    workshop_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '车间ID',
    factory_id BIGINT NOT NULL COMMENT '所属工厂ID',
    workshop_code VARCHAR(64) NOT NULL COMMENT '车间编码',
    workshop_name VARCHAR(100) NOT NULL COMMENT '车间名称',
    status CHAR(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    version_no VARCHAR(32) DEFAULT '1.0' COMMENT '版本号',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (workshop_id),
    UNIQUE KEY uk_mes_workshop_code (workshop_code),
    KEY idx_mes_workshop_factory_id (factory_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES车间';

CREATE TABLE IF NOT EXISTS mes_production_line (
    line_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '产线ID',
    workshop_id BIGINT NOT NULL COMMENT '所属车间ID',
    line_code VARCHAR(64) NOT NULL COMMENT '产线编码',
    line_name VARCHAR(100) NOT NULL COMMENT '产线名称',
    status CHAR(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    version_no VARCHAR(32) DEFAULT '1.0',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (line_id),
    UNIQUE KEY uk_mes_line_code (line_code),
    KEY idx_mes_line_workshop_id (workshop_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES产线';

CREATE TABLE IF NOT EXISTS mes_work_center (
    work_center_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '工作中心ID',
    workshop_id BIGINT NOT NULL COMMENT '所属车间ID',
    work_center_code VARCHAR(64) NOT NULL COMMENT '工作中心编码',
    work_center_name VARCHAR(100) NOT NULL COMMENT '工作中心名称',
    capacity DECIMAL(18,6) DEFAULT NULL COMMENT '生产能力',
    status CHAR(1) NOT NULL DEFAULT '0',
    version_no VARCHAR(32) DEFAULT '1.0',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (work_center_id),
    UNIQUE KEY uk_mes_work_center_code (work_center_code),
    KEY idx_mes_work_center_workshop_id (workshop_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES工作中心';

CREATE TABLE IF NOT EXISTS mes_workstation (
    workstation_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '工位ID',
    line_id BIGINT DEFAULT NULL COMMENT '所属产线ID',
    work_center_id BIGINT DEFAULT NULL COMMENT '所属工作中心ID',
    workstation_code VARCHAR(64) NOT NULL COMMENT '工位编码',
    workstation_name VARCHAR(100) NOT NULL COMMENT '工位名称',
    workstation_type VARCHAR(32) DEFAULT NULL COMMENT '工位类型',
    status CHAR(1) NOT NULL DEFAULT '0',
    version_no VARCHAR(32) DEFAULT '1.0',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (workstation_id),
    UNIQUE KEY uk_mes_workstation_code (workstation_code),
    KEY idx_mes_workstation_line_id (line_id),
    KEY idx_mes_workstation_work_center_id (work_center_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES工位';

CREATE TABLE IF NOT EXISTS mes_team (
    team_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '班组ID',
    workshop_id BIGINT NOT NULL COMMENT '所属车间ID',
    team_code VARCHAR(64) NOT NULL COMMENT '班组编码',
    team_name VARCHAR(100) NOT NULL COMMENT '班组名称',
    leader VARCHAR(64) DEFAULT NULL COMMENT '班组负责人',
    status CHAR(1) NOT NULL DEFAULT '0',
    version_no VARCHAR(32) DEFAULT '1.0',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (team_id),
    UNIQUE KEY uk_mes_team_code (team_code),
    KEY idx_mes_team_workshop_id (workshop_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES班组';

CREATE TABLE IF NOT EXISTS mes_shift (
    shift_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '班次ID',
    shift_code VARCHAR(64) NOT NULL COMMENT '班次编码',
    shift_name VARCHAR(100) NOT NULL COMMENT '班次名称',
    start_time TIME NOT NULL COMMENT '上班时间',
    end_time TIME NOT NULL COMMENT '下班时间',
    cross_day CHAR(1) NOT NULL DEFAULT '0' COMMENT '跨天标识（0否 1是）',
    status CHAR(1) NOT NULL DEFAULT '0',
    version_no VARCHAR(32) DEFAULT '1.0',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (shift_id),
    UNIQUE KEY uk_mes_shift_code (shift_code)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES班次';

CREATE TABLE IF NOT EXISTS mes_equipment (
    equipment_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '设备ID',
    workstation_id BIGINT DEFAULT NULL COMMENT '所属工位ID',
    equipment_code VARCHAR(64) NOT NULL COMMENT '设备编码',
    equipment_name VARCHAR(100) NOT NULL COMMENT '设备名称',
    equipment_type VARCHAR(32) DEFAULT NULL COMMENT '设备类型',
    status CHAR(1) NOT NULL DEFAULT '0',
    version_no VARCHAR(32) DEFAULT '1.0',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (equipment_id),
    UNIQUE KEY uk_mes_equipment_code (equipment_code),
    KEY idx_mes_equipment_workstation_id (workstation_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES设备';

CREATE TABLE IF NOT EXISTS mes_unit (
    unit_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '计量单位ID',
    unit_code VARCHAR(64) NOT NULL COMMENT '单位编码',
    unit_name VARCHAR(100) NOT NULL COMMENT '单位名称',
    precision_num INT NOT NULL DEFAULT 0 COMMENT '精度',
    status CHAR(1) NOT NULL DEFAULT '0',
    version_no VARCHAR(32) DEFAULT '1.0',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (unit_id),
    UNIQUE KEY uk_mes_unit_code (unit_code)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES计量单位';

CREATE TABLE IF NOT EXISTS mes_material (
    material_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '物料ID',
    material_code VARCHAR(64) NOT NULL COMMENT '物料编码',
    material_name VARCHAR(100) NOT NULL COMMENT '物料名称',
    specification VARCHAR(255) DEFAULT NULL COMMENT '规格型号',
    material_type VARCHAR(32) NOT NULL COMMENT '物料类型（product成品 semi半成品 raw原材料）',
    unit_id BIGINT NOT NULL COMMENT '计量单位ID',
    batch_manage_type VARCHAR(32) DEFAULT NULL COMMENT '批次管理方式',
    status CHAR(1) NOT NULL DEFAULT '0',
    version_no VARCHAR(32) DEFAULT '1.0',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (material_id),
    UNIQUE KEY uk_mes_material_code (material_code),
    KEY idx_mes_material_unit_id (unit_id),
    KEY idx_mes_material_type (material_type)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES物料主数据';

CREATE TABLE IF NOT EXISTS mes_process (
    process_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '工序ID',
    process_code VARCHAR(64) NOT NULL COMMENT '工序编码',
    process_name VARCHAR(100) NOT NULL COMMENT '工序名称',
    inspect_required CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否需要检验（0否 1是）',
    status CHAR(1) NOT NULL DEFAULT '0',
    version_no VARCHAR(32) DEFAULT '1.0',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (process_id),
    UNIQUE KEY uk_mes_process_code (process_code)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES工序';

CREATE TABLE IF NOT EXISTS mes_bom (
    bom_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'BOM ID',
    bom_code VARCHAR(64) NOT NULL COMMENT 'BOM编码',
    product_id BIGINT NOT NULL COMMENT '产品ID',
    bom_version VARCHAR(32) NOT NULL COMMENT 'BOM版本',
    status CHAR(1) NOT NULL DEFAULT '0' COMMENT '状态（0草稿 1发布 2停用）',
    effective_time DATETIME DEFAULT NULL COMMENT '生效时间',
    version_no VARCHAR(32) DEFAULT '1.0',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (bom_id),
    UNIQUE KEY uk_mes_bom_code (bom_code),
    UNIQUE KEY uk_mes_bom_product_version (product_id, bom_version),
    KEY idx_mes_bom_product_id (product_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES BOM主表';

CREATE TABLE IF NOT EXISTS mes_bom_detail (
    detail_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'BOM明细ID',
    bom_id BIGINT NOT NULL COMMENT 'BOM ID',
    material_id BIGINT NOT NULL COMMENT '子物料ID',
    quantity DECIMAL(18,6) NOT NULL COMMENT '需求数量',
    loss_rate DECIMAL(10,4) NOT NULL DEFAULT 0 COMMENT '损耗率',
    sort_no INT NOT NULL DEFAULT 1 COMMENT '排序',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (detail_id),
    UNIQUE KEY uk_mes_bom_detail_material (bom_id, material_id),
    KEY idx_mes_bom_detail_bom_id (bom_id),
    KEY idx_mes_bom_detail_material_id (material_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES BOM明细';

CREATE TABLE IF NOT EXISTS mes_route (
    route_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '工艺路线ID',
    route_code VARCHAR(64) NOT NULL COMMENT '工艺路线编码',
    product_id BIGINT NOT NULL COMMENT '产品ID',
    route_version VARCHAR(32) NOT NULL COMMENT '工艺路线版本',
    status CHAR(1) NOT NULL DEFAULT '0' COMMENT '状态（0草稿 1发布 2停用）',
    version_no VARCHAR(32) DEFAULT '1.0',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (route_id),
    UNIQUE KEY uk_mes_route_code (route_code),
    UNIQUE KEY uk_mes_route_product_version (product_id, route_version),
    KEY idx_mes_route_product_id (product_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES工艺路线主表';

CREATE TABLE IF NOT EXISTS mes_route_detail (
    detail_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '工艺路线明细ID',
    route_id BIGINT NOT NULL COMMENT '工艺路线ID',
    process_id BIGINT NOT NULL COMMENT '工序ID',
    sequence_no INT NOT NULL COMMENT '工序顺序',
    work_center_id BIGINT DEFAULT NULL COMMENT '工作中心ID',
    inspect_required CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否检验（0否 1是）',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (detail_id),
    UNIQUE KEY uk_mes_route_sequence (route_id, sequence_no),
    UNIQUE KEY uk_mes_route_process (route_id, process_id),
    KEY idx_mes_route_detail_route_id (route_id),
    KEY idx_mes_route_detail_process_id (process_id),
    KEY idx_mes_route_detail_work_center_id (work_center_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES工艺路线明细';

CREATE TABLE IF NOT EXISTS mes_defect_type (
    defect_type_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '缺陷类型ID',
    defect_code VARCHAR(64) NOT NULL COMMENT '缺陷编码',
    defect_name VARCHAR(100) NOT NULL COMMENT '缺陷名称',
    status CHAR(1) NOT NULL DEFAULT '0',
    version_no VARCHAR(32) DEFAULT '1.0',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (defect_type_id),
    UNIQUE KEY uk_mes_defect_code (defect_code)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES缺陷类型';

CREATE TABLE IF NOT EXISTS mes_exception_type (
    exception_type_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '异常类型ID',
    exception_code VARCHAR(64) NOT NULL COMMENT '异常编码',
    exception_name VARCHAR(100) NOT NULL COMMENT '异常名称',
    status CHAR(1) NOT NULL DEFAULT '0',
    version_no VARCHAR(32) DEFAULT '1.0',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (exception_type_id),
    UNIQUE KEY uk_mes_exception_code (exception_code)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES异常类型';

CREATE TABLE IF NOT EXISTS mes_downtime_reason (
    reason_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '停机原因ID',
    reason_code VARCHAR(64) NOT NULL COMMENT '停机原因编码',
    reason_name VARCHAR(100) NOT NULL COMMENT '停机原因名称',
    status CHAR(1) NOT NULL DEFAULT '0',
    version_no VARCHAR(32) DEFAULT '1.0',
    create_by VARCHAR(64) DEFAULT '',
    create_time DATETIME DEFAULT NULL,
    update_by VARCHAR(64) DEFAULT '',
    update_time DATETIME DEFAULT NULL,
    remark VARCHAR(500) DEFAULT NULL,
    revision INT NOT NULL DEFAULT 0,
    del_flag CHAR(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (reason_id),
    UNIQUE KEY uk_mes_downtime_reason_code (reason_code)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='MES停机原因';