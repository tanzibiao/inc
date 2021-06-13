/**
 * 菜单相关的api
 * @author inc 1992lcg@163.com
 */
import * as API from './'

export default {

  menus: params=>{
    return API.GET('/menu',params)
  },
  editMenu: params=>{
    return API.PUT('/menu',params)
  },
  menuIdsByRoleId: params=>{
    return API.GET('/menu/roleId',params)
  },
  add: params=>{
    return API.POST('/menu',params)
  },
  remove: params=>{
    return API.DELETE('/menu',params)
  }
}
