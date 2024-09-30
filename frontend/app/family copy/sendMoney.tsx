import { Link, useRouter, useLocalSearchParams } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, Alert, Button, TouchableOpacity } from 'react-native';
import React, { useState, useEffect } from 'react';
import Toast from "react-native-toast-message";
import TransferInput from "../../src/ui/components/Temporary/TransferInput";
import PinInfo from "../../src/ui/components/Temporary/PinCheck";
import MediaConfirm from "../../src/ui/components/Temporary/MediaConfirm";


/*
일반 송금 후 미디어 송금 추가 요청 -> 송금 후 거래고유번호 받아와서 변수로 넘기기

*/
const sendMoney = () => {

    /* 요청 보낼 정보 */
    const [account, setAccount] = useState<string>(''); // 계좌번호
    const [bank, setBank] = useState<string>(''); // 은행  
    const [balance, setBalance] = useState<string>(''); // 금액

    // 현재 계정의 핀 번호 정보
    const [nowPin, setNowPin] = useState('')

      // 현재 계정의 핀 번호 요청
  const pinInfo = () => {
    try{
      /* ★ 핀 번호 요청 보내는 내용 추가하기 ★ */
      setNowPin('111111') // 임시 핀번호

    } catch(error){
      console.log(error)
    }
  }
const router = useRouter();
useEffect(() => {

    
    // 현재 계정의 핀번호 확인
    pinInfo()
    
}, [])
    
    const nowName = '임시이름'

    /* 1차 - 연락처 대상 송금 : 송금시 필요한 금액 입력 받는 화면 & 계좌, 은행은 전달받은 정보 활용 */
    const existChange = (balance:string) => {
        setBalance(balance)
        setStep('2')
    }

    const thirdChange = () => {
        setStep('4')
      }
    const [step, setStep] = useState('1'); // 송금 절차 화면 (1-1차, 2-2차, 3-3차, 4-4차)

    const secondChange = (inputPin:string) => {
        if (inputPin == nowPin){
  
          const balanceCheck = parseInt(balance) // 송금 금액 숫자 변환
            setStep('3')
            Toast.show({
              type: 'success',
              text1: '송금 완료',
              text2: '송금이 완료되었습니다'
            })}

          else {
            Toast.show({
              type: 'error',
              text1: '송금 실패!',
              text2: '비밀번호를 다시 입력해주세요'
            })
          }
  
        }



    // 1차 화면 - 보낼 금액 입력받는 화면
    if (step == '1') {
        return (
            <View className='flex-1 justify-center items-center'>
            <View className='flex-1 flex-row justify-center items-center'>
                {/* <TransferObject onChange={firstChange} />  */}
                <TransferInput onChange={existChange} name={nowName}/>
            </View>
            </View>
        )
    }

    // 2차 화면 - 핀번호 입력받는 화면
    else if (step == '2') {
        return(
            <View className='flex-1'>
                <PinInfo onChange={secondChange}/>
            </View>
          )
    }

    // 3차 화면 - 일반 송금 완료 후 미디어 보낼 지 확인하는 화면
    else if (step == '3'){
        return(
            <View className='flex-1'>
                <MediaConfirm onChange={thirdChange}/>
            </View>
        )
    }

    // 미디어 보낼 경우 media/selectMedia로 보내기
    else if (step == '4'){
        return(
            router.push('/family copy/media/selectMedia')
        )
    }

};

export default sendMoney;