class ActivityService {
    updateActivity(activity, jwt) {
        return axios.put('/activities', activity, { headers: { Authorization: 'Bearer ' + jwt } });
    }

    removeActivity(idActivity, jwt) {
        let config = {
            headers: { 'Authorization': 'Bearer ' + jwt },
            params: { idActivity: idActivity },
        }
        return axios.delete('/activities', config);
    }

    addActivity(activity, jwt) {
        return axios.post('/activities', activity, { headers: { Authorization: 'Bearer ' + jwt } });
    }
}

export const activityService = new ActivityService();
