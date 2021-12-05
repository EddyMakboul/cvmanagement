class ActivityService {
    updateActivity(activity) {
        return axios.put('/activities', activity);
    }

    removeActivity(idActivity) {
        return axios.delete('/activities', { params: { idActivity: idActivity } });
    }

    addActivity(activity) {
        return axios.post('/activities', activity);
    }
}

export const activityService = new ActivityService();
