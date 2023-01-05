import axios from "axios";

const HttpService = axios.create({

    baseURL: 'http://localhost:8080/api',
});

export default HttpService;