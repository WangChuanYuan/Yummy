import axios from 'axios';

let instance = axios.create({
  baseURL: 'http://localhost:8081/yummy',
  timeout: 100000, /* 时间长一些，后端邮件发送时间过短会导致无法响应而取消，影响session的初次设置 */
  withCredentials: true
});

const api = {};

api.post = (url, data) => {
  return new Promise((resolve, reject) => {
    instance({
      url: url,
      method: 'post',
      data: data
    }).then(res => {
      console.log(res);
      resolve(res.data);
    }).catch(err => {
      console.error(err);
      reject(err);
    });
  });
};

api.get = (url, params) => {
  return new Promise((resolve, reject) => {
    instance({
      url: url,
      method: 'get',
      params: params
    }).then(res => {
      console.log(res);
      resolve(res.data);
    }).catch(err => {
      console.error(err);
      reject(err);
    });
  });
};

/** 使用post即可，当data为formData时，会自动识别为multipart/form-data */
// api.multipart = (url, formData) => {
//   return new Promise((resolve, reject) => {
//     instance({
//       url: url,
//       method: 'post',
//       data: formData,
//       headers: {
//         'content-type': `multipart/form-data; boundary=${formData._boundary}`
//       }
//     }).then(res => {
//       console.log(res);
//       resolve(res.data);
//     }).catch(err => {
//       console.error(err);
//       reject(err);
//     });
//   });
// };

export default api;
