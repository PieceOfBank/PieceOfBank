import axios from "axios";
import AsyncStorage from '@react-native-async-storage/async-storage';
import { axiosClient } from './axios';
import { account } from "../types/account";

/* Interface 명시 */

/* Account API */

// 1. 계좌 생성
interface accountTypeNo{
    accountTypeUniqueNo: string
}

// 2. 계좌 조회 (단건)
interface accountNumber{
    accountNo:string
}

// 3. 계좌 이체
interface balanceTransfer{
    depositAccountNo: string,
    transactionBalance: number,
    withdrawalAccountNo: string
}

// 4. 거래 내역 조회
interface historyList{
    accountNo: string,
    startDate: string,
    endDate: string,
    transactionType: string,
    orderByType: string
}

// 5. 거래 내역 조회 (단건)
interface oneHistory{
    accountNo: string,
    transactionUniqueNo: string
}

// // 계좌 이체 한도 변경
// interface transferLimit{
//     accountNo: string, 
//     oneTimeTransferLimit: string, 
//     dailyTransferLimit: string
// }

/* Directory API */

// 연락처 생성
interface makeDirectory{
    directoryId: number,
    userKey: string,
    accountNo: string,
    institutionCode: number,
    name: string
}


/* Login API 구현 */
// 1. create
export const createUser = (email: Record<string, string>) => {
    return axiosClient.post(`/users/create`, email);
}
// 2. regist
export const registUser = (newMember: Record<string, unknown>) => {
    return axiosClient.post(`/users/regist`, newMember);
}


/* JWT 코드 */
// Interceptor - JWT 로직 (AccessToken & RefreshToken)
axiosClient.interceptors.request.use(
    async (config) => {
        const user = await AsyncStorage.getItem("userId");

        if (!user) return config;

        const accessToken = await AsyncStorage.getItem("accessToken");

        if (accessToken) {
            config.headers.Authorization = `${accessToken}`;
        }

        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

axiosClient.interceptors.response.use(
    (response) => {
        return response;
    },
    async (error) => {
        const originalRequest = error.config;

        if (error.response && error.response.status === 403 && !originalRequest._retry) {
            originalRequest._retry = true;

            try {
                const refreshToken = await AsyncStorage.getItem("refreshToken");
                const response = await axiosClient.post('/auth/reissue', {}, {
                    headers: { 'refresh': refreshToken }
                });

                const newAccessToken = response.headers['authorization'];
                console.log(response.headers);

                originalRequest.headers.Authorization = `${newAccessToken}`;
                
                // 토큰을 다시 저장
                await AsyncStorage.setItem("accessToken", newAccessToken);
                // await AsyncStorage.setItem("refreshToken", newRefreshToken);  // 필요 시

                return axiosClient(originalRequest);

            } catch (error) {
                // 에러 발생 시 저장된 정보 삭제
                await AsyncStorage.removeItem('userId');
                await AsyncStorage.removeItem('accessToken');
                await AsyncStorage.removeItem('refreshToken');
            }

            return Promise.reject(error);
        }
        return Promise.reject(error);
    }
);
        

/* Directory API */
// 1. 계좌 저장소 수정 - PUT
export const updateDirectory = (id: number) => {
    return axiosClient.patch(`/directory/update/${id}`,{
        headers:{id:id}
    })
}
// 2. 계좌 저장소 등록 - POST
export const createDirectory = (data: makeDirectory) => {
    return axiosClient.post(`/directory/create`, data)
}
// 3. 계좌 저장소 조회 - GET
export const getDirectory = () => {
    return axiosClient.get(`/directory/find`)
}
// 4. 계좌 저장소 삭제 - DELETE
export const deleteDirectory = (id: number) => {
    return axiosClient.delete(`/directory/delete/${id}`,{
        headers:{id:id}
    })
}


/* user API */
// 1. 로그아웃 - POST

// 2. 보호자 / 피보호자 조회 - GET

// 3. 대표 계좌 등록 - POST

// 4. PIN 등록 - POST

// 5. PIN 조회 - GET



/* Account API */
// 1. 계좌 생성 - POST
export const createAccount = (data: Record<string, string>) => {
    return axiosClient.post(`/account/client/createDemandDepositAccount`, data)
}
// 2. 계좌 목록 조회
export const getAccountList = (key:number) => {
    return axiosClient.get(`/api/account/client/inquireDemandDepositAccountList`, {
        headers:{userKey:key}
    })
}
// 3. 계좌 조회(단건)
export const getAccount = (data:accountNumber, key:number) => {
    return axiosClient.post(`/api/account/client/inquireDemandDepositAccount`, data, {
        headers:{userKey:key}
    })
}
// 4. 계좌 이체
export const accountTransfer = (data:balanceTransfer, key:number) => {
    return axiosClient.post(`/api/account/client/updateDemandDepositAccountTransfer`, data, {
        headers:{userKey:key}
    })
}
// 5. 거래 내역 조회
export const getHistoryList = (data:historyList, key:number) => {
    return axiosClient.post(`/api/account/client/inquireTransactionHistoryList`, data, {
        headers:{userKey:key}
    })
}
// 6. 거래 내역 조회(단건)
export const getHistory = (data:oneHistory, key:number) => {
    return axiosClient.post(`/api/account/client/inquireTransactionHistory`, data, {
        headers:{userKey:key}
    })
}
// // 계좌 이체 한도 변경
// export const updateLimit = () => {
//     return axiosClient.patch(`/api/account/client/updateTransferLimit`)
// }

/* Media API */
// 1. 미디어 등록 - POST
export const mediaPost = () => {
    return axiosClient.get(`/media/upload`,)
}

// 2. 미디어 조회 - GET
export const mediaGet = () => {
    return axiosClient.get(`/media/find`,)
}


/* Pending API */
// 1. 이체 대기 등록 - POST
export const pendingPost = () => {

}

// 2. 이체 대기 내역 조회 - GET
export const pendingGet = () => {
    
}

// 3. 이체 대기 취소 - DELETE
export const pendingDelete = () => {
    
}

/* Notification API */
// 1. 알림 생성 - POST
export const notifyPost = () => {
    return axiosClient.get(`/media/upload`,)
}
// 2. 알림 조회(전체) - GET
export const notifyList = () => {
    
}
// 3. 알림 조회(단건) - GET
export const notifyGet = () => {
    
}
// 4. 알림 삭제 - DELETE
export const notifyDelete = () => {
    
}

/* Subscription API */
// 1. 보호 관계 등록 - POST
export const subscriptionPost = () => {
    
}
// 2. 보호 관계 순서 수정 - PUT
export const subscriptionOrderUpdate = () => {
    
}
// 3. 보호 관계 삭제 - DELETE
export const subscriptionDelete = () => {
    
}
// 4. 보호 관계 수정 - PUT
export const subscriptionUpdate = () => {
    
}
// 5. 보호 관계 조회 - GET
export const subscriptionGet = () => {

}
