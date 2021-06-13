/**
 * Created by inc.
 */
import axios from 'axios'
import store from "../vuex/store";
import { Message } from 'element-ui';
import {
  bus
} from '../bus.js'

axios.defaults.withCredentials = true;
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
//  axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';//配置请求头


//添加一个请求拦截器
axios.interceptors.request.use(
  config => {
    if (window.localStorage.getItem('access-token')) {
      config.headers.Authorization = window.localStorage.getItem('access-token');
    }
    store.state.loading = true
    return config
  },
  error => {
    return Promise.reject(error)
  }
);
// 添加一个响应拦截器
axios.interceptors.response.use(function (response) {
  store.state.loading = false
  if (response.data && response.data.code) {
    const resCode = parseInt(response.data.code);
    if (resCode === 401) {
      //未登录
      bus.$emit('goto', '/login')
    }

    if (resCode === '402') {
      //登录过期
      Message.error({
        showClose: true,
        message: '登录过期，请重新登录',
        duration: 2000
      });

      bus.$emit('goto', '/login')
    }
  }

  return response;
}, function (error) {
  store.state.loading = false
  // Do something with response error
  return Promise.reject(error);
});

//基地址
let base = process.env.API_ROOT


//通用方法
export const POST = (url, params) => {
  return axios.post(`${base}${url}`, params).then(res => res.data)
}

//表单方法
export const POSTFORM = (url, params) => {
  const config ={headers:{"Content-Type":"multipart/form-data"}}
  return axios.post(`${base}${url}`, params, config).then(res => res.data)
}

export const GET = (url, params) => {
  return axios.get(`${base}${url}`, {
    params: params
  }).then(res => res.data)
}

export const PUT = (url, params) => {
  return axios.put(`${base}${url}`, params).then(res => res.data)
}

export const DELETE = (url, params) => {
  return axios.delete(`${base}${url}`, {
    params: params
  }).then(res => res.data)
}

export const PATCH = (url, params) => {
  return axios.patch(`${base}${url}`, params).then(res => res.data)
}
