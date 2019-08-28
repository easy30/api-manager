// 引入axios，并加到原型链中
import axios from 'axios'
import qs from 'qs'

var ajax = {};
export {axios, ajax};
//axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
//axios.defaults.headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
ajax.postForm = function (url, data, succesCallback) {
    axios.post(url, qs.stringify(data))
        .then((response) => {
            doResponse(response, succesCallback);
        })
        .catch((error) => {

            ajax.showError(error);

        })

}

ajax.postFormSync = async function (url, data, silent) {
    try {
        let response = await axios.post(url, qs.stringify(data));
        return doResponseSync(response, silent);
    }
    catch (error) {

        return doErrorsync(error,silent);

    }


}

ajax.postJson = function (url, data, succesCallback) {
    axios.post(url, data)
        .then((response) => {
            doResponse(response, succesCallback);
        })
        .catch((error) => {

            ajax.showError(error);

        })

}


ajax.postJsonSync = async function (url, data, silent) {
    try {
        let response = await axios.post(url, qs.stringify(data));
        return doResponseSync(response, silent);
    }
    catch (error) {

        return doErrorsync(error,silent);

    }


}

ajax.get = function (url, p2, p3) {
    var data = p3 ? {params:p2} : null;
    var callback = p3 ? p3 : p2;
    axios.get(url, data)
        .then((response) => {
            doResponse(response, callback);
        })
        .catch((error) => {

            ajax.showError(error);

        })


}

ajax.getSync = async function (url, p2, p3) {
    try {
        var data = p3 ? {params:p2} : null;
        var silent = p3 ? p3 : p2;
        let response = await axios.get(url, data);
        return doResponseSync(response, silent);
    } catch (error) {
        return doErrorsync(error,silent);

    }

}



ajax.all = function (reqeusts, callback) {

    axios.all(reqeusts).then(axios.spread(callback))
        .catch(error=>{  ajax.showError(error);});
     
}
ajax.allSync = async function (reqeusts, silent) {
    try {
    var responses=await axios.all(reqeusts);
    for(var i=0;i<responses.length;i++){
        doResponseSync(responses[i],silent);
    }
    return responses;

    }catch (error) {
        return doErrorsync(error,silent);

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

function doResponse(response, callback) {
    var code=getCode(response);
    if (code == 0) {

        if (callback) callback(response);
    }
    else {
        var message=response.data && response.data.msg?response.data.msg:"";
        var e=new Error(message);
        e.code=code;
        ajax.showError(e);
    }
}

function doResponseSync(response, silent) {
    if (getCode(response) == 0) {
        response.success=true;
        //if (callback) callback(response);
    }
    else {
        response.success=false;
        if(!silent)ajax.showError(response);
    }
    return response;
}
function doErrorsync(error,silent) {
    var response={};
    response.success=false;
    response.error=error;
    if(!silent) ajax.showError(error);
    return response;

}

