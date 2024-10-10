import axios from "axios";
import { EXPO_BACKEND_ADDRESS } from '@env';

export const BASE_URL = EXPO_BACKEND_ADDRESS;

export const HEADERS = {
  "Access-Control-Allow-Origin": "*",
  "Content-Type": "application/json",
};

export const axiosClient = axios.create({
  baseURL: BASE_URL,
  headers: HEADERS,
});


export const MEDIAHEADERS = {
  "Access-Control-Allow-Origin": "*",
  "Content-Type": "multipart/form-data",
};

export const axiosMedia = axios.create({
  baseURL: BASE_URL,
  headers: HEADERS,
});
