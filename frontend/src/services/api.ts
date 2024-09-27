import axios from "axios";
import { axiosClient } from './axios';

/* Interface 명시 */

/* Login API 구현 */
// 1. create
export const createUser = (email: Record<string, string>) => {
    return axiosClient.post(`/api/users/create`, email);
}
// 2. regist
export const registUser = (newMember: Record<string, unknown>) => {
    return axiosClient.post(`/api/users/regist`, newMember);
}

/* JWT 코드 */