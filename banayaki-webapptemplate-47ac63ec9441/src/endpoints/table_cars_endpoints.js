import backend from '../plugins/axios'

const endpoint_url = '/cars'

export function eeGetAll() {
    return backend.get(`${endpoint_url}/`)
}

export function eeGetById(id) {
    return backend.get(`${endpoint_url}/${id}`)
}

export function eeAddNewItem(data) {
    const formData = new URLSearchParams()
    for (const key in data) {
        formData.append(key, data[key])
    }

    return backend.post(`${endpoint_url}/`, formData, {headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
}

export function eeUpdateItem(data) {
    return backend.put(`${endpoint_url}/`, data)
}

export function eeDeleteItem(id) {
    return backend.delete(`${endpoint_url}/${id}`)
}

export function springGetAll() {
    return backend.get(`${endpoint_url}/`)
}

export function springGetById(id) {
    return backend.get(`${endpoint_url}/${id}`)
}

export function springAddNewItem(params, body) {
    return backend.post(`${endpoint_url}/`, JSON.stringify(body), {
        params: params,
        headers: {'content-type': 'application/json'}
    })
}

export function springUpdateItem(id, params, body) {
    return backend.put(`${endpoint_url}/${id}`, JSON.stringify(body), {
        params: params,
        headers: {'content-type': 'application/json'}
    })
}

export function springDeleteItem(id) {
    return backend.delete(`${endpoint_url}/${id}`)
}