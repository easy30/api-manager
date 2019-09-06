// 引入axios，并加到原型链中
import axios from 'axios'
import qs from 'qs'

var ajax = {};
export {axios, ajax};
//axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
//axios.defaults.headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
ajax.postFormItem = function (url, data) {
   return axios.post(url, qs.stringify(data));
}

ajax.postForm = function (url, data, succesCallback,failCallback) {
    ajax.postFormItem(url, data)
        .then((response) => {
            doResponse(response, succesCallback,failCallback);
        })
        .catch((error) => {
            if(failCallback) failCallback(error);
            else ajax.showError(error);

        })

}



ajax.postFormSync = async function (url, data, silent) {
    try {
        let response = await ajax.postFormItem(url, data);
        return doResponseSync(response, silent);
    }
    catch (error) {

        return doErrorSync(error,silent);

    }


}

ajax.postJsonItem = function (url, data) {
    return axios.post(url, data)
}
ajax.postJson = function (url, data, succesCallback) {
    ajax.postJsonItem(url, data)
        .then((response) => {
            doResponse(response, succesCallback);
        })
        .catch((error) => {

            ajax.showError(error);

        })

}


ajax.postJsonSync = async function (url, data, silent) {
    try {
        let response = await ajax.postJsonItem(url, qs.stringify(data));
        return doResponseSync(response, silent);
    }
    catch (error) {

        return doErrorSync(error,silent);

    }


}
ajax.getItem = function (url, data) {
    var d=data?{params:data}:null;
    return axios.get(url, d);
}
ajax.get = function (url, p2, p3) {
    var data = p3 ? p2 : null;
    var callback = p3 ? p3 : p2;
    ajax.getItem(url, data)
        .then((response) => {
            doResponse(response, callback);
        })
        .catch((error) => {

            ajax.showError(error);

        })


}

ajax.getSync = async function (url, p2, p3) {
    try {
        var data = p3 ? p2 : null;
        var silent = p3 ? p3 : p2;
        let response = await ajax.getItem(url, data);
        return doResponseSync(response, silent);
    } catch (error) {
        return doErrorSync(error,silent);

    }

}



ajax.all = function (requestItems, callback) {

    axios.all(requestItems).then(axios.spread(callback))
        .catch(error=>{  ajax.showError(error);});
     
}
ajax.allSync = async function (requestItems, silent) {
    try {
    var responses=await axios.all(requestItems);
    for(var i=0;i<responses.length;i++){
        doResponseSync(responses[i],silent);
    }
    return responses;

    }catch (error) {
        return doErrorSync(error,silent);

    }
}

ajax.error = function (callback) {
    ajax.showError = callback;
}

function getCode(response) {
    var code = 0;
    if (response.data && response.data.code) code = response.data.code;
    return code;
}

function doResponse(response, callback,failCallback) {
    //if(response.status==320){}
    console("response",response);
    var code=getCode(response);
    if (code == 0) {

        if (callback) callback(response);
    }
    else {
        var e=responseToError(response);
        if(failCallback) failCallback(e);
        else ajax.showError(e);
    }
}

function responseToError(response){
    var message=response.data && response.data.msg?response.data.msg:"";
    var e=new Error(message);
    e.code=getCode(response);
    e.response=response;
    return e;
}

function doResponseSync(response, silent) {
    if (getCode(response) == 0) {
        response.success=true;
        //if (callback) callback(response);
    }
    else {
        response.success=false;
        if(!silent){
            ajax.showError(responseToError(response));
        }
    }
    return response;
}
function doErrorSync(error,silent) {
    var response={};
    response.success=false;
    response.error=error;
    if(!silent) ajax.showError(error);
    return response;

}

