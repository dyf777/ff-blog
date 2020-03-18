import axios from "axios"
import {Message} from "element-ui";
import router from "../router";

axios.interceptors.response.use(success => {
    if (success.status == 200 && success.data.status == 500) {
        Message.error(success.data.msg);
        return;
    }
    if(success.data.msg){
        Message.success({message:success.data.msg});
    }
    return success.data;
}, error => {
    if (error.response.status == 504 || error.response.status == 404) {
        Message.error("未找到服务器");
    } else if (error.response.status == 401) {
        Message.error("尚未登录，请登录");
        router.replace("/");
    } else if (error.response.status == 403) {
        Message.error("权限不足，请联系管理员");
    } else {
        if (error.response.data) {
            Message.error(error.response.data.msg);
        } else {
            Message.error("未知错误");
        }
    }
    return;
});

let base = '';
export const postKeyValueRequest = (url, param) => {
    return axios({
        method: "post",
        url: `${base}${url}`,
        data: param,
        transformRequest: [function (data) {
            let ret = '';
            for (let i in data) {
                ret += encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + '&';
            }
            return ret;
        }],
        headers:{
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
};

export const getRequest=(url,param)=>{
    axios({
        method: 'post',
        url: `${base}${url}`,
        data: param
    })
};
export const postRequest=(url,param)=>{
    axios({
        method: 'post',
        url: `${base}${url}`,
        data: param
    })
};
export const putRequest=(url,param)=>{
    axios({
        method: 'post',
        url: `${base}${url}`,
        data: param
    })
};
export const deleteRequest=(url,param)=>{
    axios({
        method: 'post',
        url: `${base}${url}`,
        data: param
    })
};

