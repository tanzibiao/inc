/**
 * Created by inc.
 * 角色相关api
 */
import * as API from './'

export default {

  //查询获取role列表(通过page分页)
  findList: params => {
    return API.GET('/role', params)
  },

  //查询获取一条role信息
  findById: id => {
    return API.GET('/role/userId/'+id)
  },

  add: params => {
    return API.POST(`/role`, params)
  },
  update: (id, params) => {
    return API.PUT('/role', params)
  },

  //单个删除role
  remove: params => {
    return API.DELETE('/role',params)
  },

  //批量删除，传ids数组
  removeBatch: (ids) => {
    return API.DELETE(`/api/v1/roles/batch/${ids}`)
  }

}
