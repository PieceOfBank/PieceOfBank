import { Link, useRouter } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, Alert } from 'react-native';
import React, { useState, useEffect } from 'react';
import * as ScreenOrientation from 'expo-screen-orientation';
import TransferObject from "../../src/ui/components/TransferObject";
import TransferCheck from "../../src/ui/components/TransferCheck";
import PinConfirm from "../../src/ui/components/PinConfirm";
import TransferOk from "../../src/ui/components/TransferOk";

const careTransfer = () => {

    // 화면 가로고정
    useEffect(() => {
        const screenChange = async() => {
            await ScreenOrientation.lockAsync(ScreenOrientation.OrientationLock.LANDSCAPE);
        };
        screenChange();
        return () => {
            ScreenOrientation.unlockAsync()
        }
        },[]);

    const router = useRouter();

    const [step, setStep] = useState('1'); // 송금 절차 화면 (1-1차, 2-2차, 3-3차, 4-4차)
      
    const [accountNumber, setAccountNumber] = useState<string>(''); // 계좌번호

    const [bank, setBank] = useState<string>(''); // 은행  

    const [account, setAccount] = useState<string>(''); // 금액

    const [inputPin, setInputPin] = useState<string>(''); // 핀번호    


    const firstChange = (num:string, bank:string, account:string) => {
      setAccountNumber(num)
      setBank(bank)
      setAccount(account)
      setStep('2')
    }

    const secondChange = () => {
      setStep('3')
    }

    const thirdChange = () => {
      setStep('4')
    }

    // 1차 송금 화면
    if (step=='1') {
      return (
        <View className='flex-1 justify-center items-center'>
          <View className='flex-1 flex-row justify-center items-center'>
            <TransferObject onChange={firstChange} />
          </View>
        </View>
      );
    }

    // 2차 송금 화면
    else if (step=='2') {
      return (
        <View className='flex-1 justify-center items-center'>
          <View className='flex-1 flex-row justify-center items-center'>
            <TransferCheck onChange={secondChange} account={account} />
          </View>
        </View>
      );
    }

    else if (step=='3'){
      return(
        <View>
          <View className='flex-1 justify-center items-center'>
            <View className='flex-1 flex-row justify-center items-center'>
              <PinConfirm onChange={thirdChange}/>
            </View>
          </View>
        </View>
      )
    }

    // 금액 한도 초과일 경우 보호자에게 알림
    // 금액 이하일 경우 송금 완료 
    else if (step=='4'){
      return(
        <View>
          <View className='flex-1 justify-center items-center'>
            <View className='flex-1 flex-row justify-center items-center'>
              <TransferOk />
            </View>
          </View>
        </View>
      )
    }

}

export default careTransfer;