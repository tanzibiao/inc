/**
 * Created by inc.
 * 用户相关api
 */
import * as API from './'

export default {
  //登录
  login: params => {
    return API.POST('/login', params)
  },
  //注册
  register: params => {
    return API.POST('/user/register', params)
  },
  //登出
  logout: params => {
    return API.GET('/logout', params)
  },
  tokenUser: params =>{
    return API.GET('/user/currentUser',params)
  },
  //修改个人信息
  changeProfile: params => {
    return API.POST('/user/profile', params)
  },
  //修改个人信息
  updatePwd: params => {
    return API.POST('/user/updatePwd', params)
  },

  //查询获取user列表(通过page分页)
  findList: params => {
    return API.GET('/user', params)
  },

  //增加用户
  addUser:params =>{
    return API.POST('/user',params)
  },
  //修改用户
  editUser:params =>{
    return API.PUT('/user',params)
  },
  resetPwd:params =>{
    return API.POST('/user/resetPwd',params)
  },
  //删除用户
  removeUser:params =>{
    return API.DELETE('/user',params)
  }
}
