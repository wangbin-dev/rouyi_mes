import request from '@/utils/request'

// 查询MES工厂列表
export function listFactory(query) {
  return request({
    url: '/mes/factory/list',
    method: 'get',
    params: query
  })
}

// 查询MES工厂详细
export function getFactory(factoryId) {
  return request({
    url: '/mes/factory/' + factoryId,
    method: 'get'
  })
}

// 新增MES工厂
export function addFactory(data) {
  return request({
    url: '/mes/factory',
    method: 'post',
    data: data
  })
}

// 修改MES工厂
export function updateFactory(data) {
  return request({
    url: '/mes/factory',
    method: 'put',
    data: data
  })
}

// 修改MES工厂状态
export function changeFactoryStatus(factoryId, status) {
  return request({
    url: '/mes/factory/changeStatus',
    method: 'put',
    data: {
      factoryId,
      status
    }
  })
}

// 删除MES工厂
export function delFactory(factoryId) {
  return request({
    url: '/mes/factory/' + factoryId,
    method: 'delete'
  })
}
