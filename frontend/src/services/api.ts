import axios from "axios";
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
// 1. create [완료]
export const createUser = (email: Record<string, string>) => {
    return axiosClient.post(`/users/create`, email);
}
// 2. regist // 다시 확인해보기
export const registUser = (newMember: Record<string, unknown>) => {
    // const abcd : any = Session-Token
    return axiosClient.post(`/users/regist`, newMember,{
        headers:{cookie:'e9d72da8-e2f2-4239-8eb4-ae5eec0796e8'},
        // createUser 응답값에 들어있는 userKey도 같이 보내야 함
    });
    
}


// 3. login
export const loginUser = (email: Record<string, string>) => {
    return axiosClient.post(`/auth/login`, email);
}


/* JWT 코드 */




/* Directory API */
// 1. 계좌 저장소 수정 - PUT
export const updateDirectory = (id: number) => {
    return axiosClient.patch(`/directory/update/${id}`,{
        headers:{id:id}
    })
}
// 2. 계좌 저장소 등록 - POST
export const createDirectory = (data: Record<string, string>) => {
    return axiosClient.post(`/directory/create`,)
}
// 3. 계좌 저장소 조회 - GET
export const getDirectory = (data: Record<string, string>) => {
    return axiosClient.get(`/directory/find`, {
        headers:{userKey:"ecef4aeb-27f7-4c5c-9e70-3d7fbfc9a1ad"}
    }
        
    )
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
// 1. 계좌 생성 - POST @@@
export const createAccount = (data: Record<string, string>) => {
    return axiosClient.post(`/account/client/createDemandDepositAccount`, data,{
        headers:{userKey:"1cf012ec-c2bc-4d96-96c8-f311c883a3e7"}
    })
}
// 2. 계좌 목록 조회 ★★ - 빈 객체 넣어줘야 요청 잘 들어감
export const getAccountList = () => {
    return axiosClient.post(`/account/client/inquireDemandDepositAccountList`, {}, {
        headers:{userKey:'ecef4aeb-27f7-4c5c-9e70-3d7fbfc9a1ad'}
    })
}

// 3. 계좌 조회(단건) @@@
export const getAccount = (data: Record<string, string>) => {
    return axiosClient.post(`/account/client/inquireDemandDepositAccount`, data, {
        headers:{userKey:"ecef4aeb-27f7-4c5c-9e70-3d7fbfc9a1ad"}
    })
}
// 4. 계좌 이체 
export const accountTransfer = (data: Record<string, unknown>) => {
    return axiosClient.post(`/account/client/updateDemandDepositAccountTransfer`, data, {
        headers:{userKey:'1bf84ad8-160e-4b31-b6ae-73ea4064cfad'}
    } )
}
// 5. 거래 내역 조회
export const getHistoryList = (data: Record<string, unknown>) => {
    return axiosClient.post(`/account/client/inquireTransactionHistoryList`, data, {
        headers:{userKey:'1bf84ad8-160e-4b31-b6ae-73ea4064cfad'}
    })
}
// 6. 거래 내역 조회(단건)
export const getHistory = (data:oneHistory, key:number) => {
    return axiosClient.post(`/account/client/inquireTransactionHistory`, data, {
        headers:{userKey:key}
    })
}
// 입금??
export const addMoney = (data: Record<string, unknown>) => {
    return axiosClient.post(`/account/client/updateDemandDepositAccountDeposit`, data, {
        headers:{userKey:'1bf84ad8-160e-4b31-b6ae-73ea4064cfad'}
    })
}


/* Media API */
// 1. 미디어 등록 - POST
export const mediaPost = (transNo:number, type:string, content:string, data:Record<string, unknown>) => {
    return axiosClient.post(`/media/upload`, data, {
        headers:{
            transactionUniqueNo:transNo, 
            type:type, 
            content:content}
    })
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
// 1. 알림 생성 !!
export const notifyPost = (data:Record<string, string>) => {
    return axiosClient.post(`/notification/send`, data)
}
// 2. 알림 조회(전체) !!
export const notifyList = (data:Record<string, string>) => {
    return axiosClient.get(`/notification`, {params: data}
)
}
// 3. 알림 조회(단건)
export const notifyGet = (notificationId:string) => {
    return axiosClient.get(`api/notification/${notificationId}`,)
}
// 4. 알림 업데이트 (읽음 처리)
export const notifyUpdate = (notificationId:string) => {
    return axiosClient.patch(`api/notification/${notificationId}`,)
}
// 5. 알림 업데이트 (삭제)
export const notifyDelete = (notificationId:string) => {
    return axiosClient.patch(`api/notification/${notificationId}`,)
}
// 6. 거래 한도 이상일 때 자식에게 알림 (pending History 저장됐을 때)
export const notifyLimitRequest = () => {
    return axiosClient.post(`api/notification/transfers/request`,)
}
// 7. 부모 거래 승인 처리
export const transferApproval = () => {
    return axiosClient.patch(`api/notification/transfers/approval`,)
}
// 8. 부모 거래 거부 처리
export const transferRefusal= () => {
    return axiosClient.patch(`api/notification/transfers/refusal`,)
}
// 9. 부모 거래 만료 처리
export const transferExpiry= () => {
    return axiosClient.patch(`api/notification/transfers/expiry`,)
}

/* Subscription API */

// 1. 보호 관계 요청 (보호자 → 피보호자)
export const subscriptionPost = (data:Record<string,string>) => {
    return axiosClient.post(`/notification/subscriptions/request`, 
        {params: data})
}

// 2. 보호 관계 수락 (피보호자 → 보호자)
export const subscriptionApproval = (data:Record<string,string>) => {
    return axiosClient.post(`/notification/subscriptions/approval`, 
        {params: data})
}
// 3. 보호 관계 거절 (피보호자 → 보호자)
export const subscriptionRefusal= (data:Record<string,string>) => {
    return axiosClient.post(`/notification/subscriptions/refusal`, 
        {params: data})
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
