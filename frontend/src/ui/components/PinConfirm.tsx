import { View, Text, Button, ImageBackground, Alert, TextInput, TouchableOpacity, ScrollView, StyleSheet, SafeAreaView } from 'react-native';
import { useRouter, Link } from 'expo-router';
import React, { useState } from 'react';

const PinConfirm = ({ onChange }: {onChange:(value:string) => void}) => {
    const router = useRouter();

    const [numCheck, setNumCheck] = useState(0); // 1차 입력된 핀번호 개수
    const [pinCheck, setPinCheck] = useState<string>('') // 1차 6자리 핀번호
  
  
    // 핀번호 입력 시 상태 변경 (초기화, 삭제, 추가)
    const pinChecking = (value: string) => {
        if (value=='정정') {
          setNumCheck(0)
          setPinCheck(prevNum => prevNum = '')
        } else if (value=='←'){
          if (numCheck>0) {
            setNumCheck(prevNum => prevNum - 1)
            setPinCheck(prevNum => prevNum.slice(0,-1))
          }
        } else {
          if (numCheck<6){
            setNumCheck(prevNum => prevNum + 1)
            setPinCheck(prevNum => prevNum + value.toString())
          } 
        }
      }
  
    // 1차 핀번호 점검
    const thirdCheck = () => {
      if (numCheck == 6) {
        onChange('')
      } else {
        Alert.alert('핀번호 확인', '번호를 총 6자리 입력해주세요!')
      }
    }


  
    // 1차 숫자 키패드 배열
    const touchNumber = [['1', '2', '3'], ['4', '5', '6'], ['7', '8', '9'], ['정정', '0', '←']] 
    // 1차 동그라미 6자리 배열
    const circleNumber = [1, 2, 3, 4, 5, 6]
    // 1차 입력된 핀번호 개수에 맞춰 동그라미 색 변경
    const touchCircle = circleNumber.map((number) => 
      <View className={`${number<=numCheck ? 'bg-blue-500' : 'bg-white'} w-8 h-8 rounded-full m-1`} key={number}></View>)

     return(
        <View className='flex-1 flex-row h-5/6'>
            <View className='flex-1 justfiy-center items-center basis-2/5 m-2 p-2 bg-gray-300 h-72'>
                <Text className='text-2xl'>비밀번호를</Text>
                <Text className='text-2xl'>입력해주세요</Text>
                <View className='m-2'>
                    <View className='flex-row'>
                        {touchCircle}
                    </View>
                </View>
            </View>

            <View className='flex-1 justfiy-center items-center basis-2/5 m-2 h-72'>

            <View>
                {touchNumber.map((list, index) => 
                <View className='flex-row' key={index}>
                    {list.map((number, indexs)=> 
                    <TouchableOpacity 
                    className='w-14 h-14 border justify-center items-center m-1 bg-white' 
                    onPress={() => pinChecking(number)} 
                    key={indexs}>
                    <Text className='text-center text-2xl'>{number}</Text>
                    </TouchableOpacity>
                    )}
                </View>
                )}
            <Button title="확인" onPress={thirdCheck}></Button>

            </View>

            </View>
        </View>

    )



}

export default PinConfirm