/**
 * Created by tzb.
 */
import axios from 'axios'
import store from "../vuex/store";
import {Message} from 'element-ui';
import {bus} from '../bus.js'

axios.defaults.withCredentials = true;

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

    if (resCode == '402') {
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


/**
 * api请求(注意：delete|get不需要传requestType，默认值只能是json)
 * @param url 请求地址
 * @param method 请求方式：post|get|put|delete|patch
 * @param params 请求参数-路径传参(form传参)-一般用于get请求
 * @param data 请求参数-body传参-一般用于post请求
 * @param callBack 成功回调
 * @param errCallBack 失败回调
 * @returns {Promise<void>}
 */
export default async function httpClient (url, {method, params, data,}, callBack, errCallBack) {
  url = base+url;
  let config = {headers: {}};
  if (!callBack)
    callBack = (res)=>{console.log(res)};
  if (!errCallBack)
    errCallBack = (err)=>{console.log(err)};
  //默认post方式请求
  if (!method)
    method = 'post';
  axios({method, url, data, params, config}).then(result => callBack(result.data)).catch(errCallBack)
}
