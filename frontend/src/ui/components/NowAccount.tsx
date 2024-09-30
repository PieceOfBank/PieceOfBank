import { View, Text, Button, ImageBackground, Alert, TextInput, TouchableOpacity, ScrollView, StyleSheet } from 'react-native';
import { useRouter, Link } from 'expo-router';
import React, { useState } from 'react';


const NowAccount = () => {
    interface NowAccount {
        id: string;
        bank: string;
        accountNo: string;
    }

    const router = useRouter()

    // 계좌 등록 여부 확인
    const [bankExist, setBankExist] = useState(false)

     // 임시 리스트 (은행 정보)
     const nowBank: NowAccount = {id:'1', accountNo:'123-456-789', bank:'하나은행'}

        return(
            <View>
                <Link className='text-xl' href={'/ward/accountList'}>
                    {(bankExist == false 
                    ?<Text>대표 계좌를 설정해주세요!</Text> 
                    :<Text>{nowBank.bank} {nowBank.accountNo}</Text>)}        
                </Link>
            </View>
        )



}

export default NowAccount