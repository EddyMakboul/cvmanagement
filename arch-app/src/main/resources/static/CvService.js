

class CvService {

    getCvByUserId(id) {
        return axios.get('/users/' + id)
    }

    postLogin(email, password) {
        return axios.post('/users/signin?email=' + email + '&password=' + password)
    }

    isConnected(jwt) {
        return axios.get('/users/isconnected', { headers: { Authorization: 'Bearer ' + jwt } })
    }

    logout(jwt) {
        return axios.get('/users/logout', { headers: { Authorization: 'Bearer ' + jwt } })
    }

    searchCvByCriteria(criteria, pageNo, pageSize) {
        return axios.get('/users/search', { params: { criteria: criteria, pageNo: pageNo, pageSize: pageSize } })
    }

    size(criteria) {
        return axios.get('/users/size', { params: { criteria: criteria } })
    }

    updateUser(userdto, jwt) {
        return axios.put('/users', userdto, { headers: { Authorization: 'Bearer ' + jwt } });
    }
    createUser(user, jwt) {
        return axios.post('/users', user, { headers: { Authorization: 'Bearer ' + jwt } })
    }
    isGoodUser(userId, jwt) {
        let config = {
            headers: { 'Authorization': 'Bearer ' + jwt },
            params: { userId: userId },
        }
        return axios.get('/users/isGoodUser', config)
    }
}


export const cvService = new CvService();