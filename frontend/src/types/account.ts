
export interface account {
    accountId: number,
    userKey: string, // 필요한가?
    bankId: number, // 필요한가?
    
    accountNo: string,
    accountName: string,
    accountBalance: number,
    dailyTransferLimit: number,
    oneTimeTransferLimit: number,
    currency: string,

    accountCreatedDate: string,
    accountExperityDate: string,
    lastTranscationDate: string,
}

// < 추가 구현 > - 계좌에서 돈 계산하는 함수