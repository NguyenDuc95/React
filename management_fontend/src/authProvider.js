
import decodeJwt from 'jwt-decode';

const authoProvicer = {
    // called when the user attempts to log in
    login: ({ username, password }) => {
        localStorage.setItem('username', username);
        var myHeaders = new Headers();
        myHeaders.append("Authorization", "Basic Og==");
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Cookie", "JSESSIONID=FA535B7A1CC1EAC50CFD7373352E95B8");
        var raw = JSON.stringify({
        "userName": username,
        "password": password
        });
        var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
        };
        return fetch("http://localhost:8081/api/login", requestOptions)
        .then(response => {
            console.log('response status: ' + response.status);
            if(!response.ok) { throw response }
            return response.json();
        })
        .then((token) => {
            console.log(token);
             const decodedToken = decodeJwt(token.token);
            console.log("save token");
            localStorage.setItem('token',JSON.stringify(token));
            console.log(decodedToken.sub);
            localStorage.setItem('permissions', decodedToken.sub);
        })
        .catch(error => {
            console.log('error:', error);
            throw new Error('Network error');
        });
    },
    logout: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('permissions');
        return Promise.resolve();
    },
    checkError: ({ status }) => {
        if (status === 401 || status === 403) {
            localStorage.removeItem('token');
            localStorage.removeItem('permissions');
            return Promise.reject();
        }
        return Promise.resolve();
    },
    checkAuth: () => {
        return localStorage.getItem('token')
            ? Promise.resolve()
            : Promise.reject({redirectTo:'http://localhost:3000/#/login'});
    },
    getPermissions: () => {
        const role = localStorage.getItem('permissions');
        return role ? Promise.resolve(role) : Promise.reject();
    }
};

export default authoProvicer;