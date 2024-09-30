import axios from "axios";
import { axiosClient } from './axios';

/* Interface 명시 */

/* Login API 구현 */
// 1. create
export const createUser = (email: Record<string, string>) => {
    return axiosClient.post(`/users/create`, email);
}
// 2. regist
export const registUser = (newMember: Record<string, unknown>) => {
    return axiosClient.post(`/api/users/regist`, newMember);
}

// exam
export const directoryDelete = (id : number) => {
    return axiosClient.delete(`/directory/delete/${id}`);
}

/* JWT 코드 */