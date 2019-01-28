import axios from 'axios';

let instance = axios.create({
  baseURL: 'http://localhost:8081/yummy',
  timeout: 1000,
  withCredentials: true
});

const ajax = (url, params) => {
  return new Promise((resolve, reject) => {
    instance({
      url: url,
      method: 'post',
      data: params
    }).then(res => {
      console.log(res);
      resolve(res.data);
    }).catch(err => {
      console.error(err);
      reject(err);
    });
  });
};

export default ajax;
