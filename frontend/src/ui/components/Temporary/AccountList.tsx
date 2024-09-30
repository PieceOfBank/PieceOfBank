import { Link } from "expo-router";
import { View, Text, ImageBackground, TextInput, SafeAreaView, TouchableOpacity, Image, FlatList, StyleSheet, ScrollView, Button } from 'react-native';
import React, { useState, useEffect } from 'react';
import { useRouter } from 'expo-router';
import Checkbox from 'expo-checkbox';
import Toast from "react-native-toast-message";

const FamilyAccountList = () => {

    const router = useRouter();

    // 임시 대표 계좌 정보
    const [mainAccount, setMainAccount] = useState('');
    // 임시 전체 계좌 정보
    interface AccountItem {
        id: string;
        bank: string;
        number: string;
      }
    // 임시 전체 계좌
    const accountList : AccountItem [] = [{id:'1', bank:'신한은행', number:'11111111111'}, {id:'2', bank:'하나은행', number:'2222222222'}, {id:'3', bank:'국민은행', number:'33333333333'}]

    // 체크박스 표시
    const [accountChecked, setAccountChecked] = useState(Array(accountList.length).fill(false));

    // 체크박스 변경 - 중복 없이 하나만 체크되도록 설정 (나머지는 체크박스 false로)
    const checkChange = (index:number) => {
        const updateCheck = [...accountChecked]
        updateCheck[index] = true
        for (let i = 0; i < accountList.length; i++){
        if (i == index){
            updateCheck[i] = true
            setMainAccount(accountList[i]['number'])
        }
        else {
            updateCheck[i] = false
        }
        }
        setAccountChecked(updateCheck)
    }

    const mainSelect = () => {
        console.log(mainAccount)
        console.log(accountChecked)
        Toast.show({
            type: 'success',
            text1: '대표 계좌 등록이 완료되었습니다',
            text2: ':happy:'
          })
        router.push('/family copy/familyMain')
    
    }

     return (
        <View className='flex-1 w-4/5'>
            <ScrollView className='flex-1'>
                <View className='justify-center items-center'>
                {accountList.map((list, index) => (
                    <View key={index} className='w-5/6 h-12 p-3 m-2 flex-row items-center bg-gray-300 justify-between rounded-2xl'>
                        <Text className='pl-4'>{list.bank}</Text>
                        <Text>{list.number}</Text>
                        <View className='mx-2'>
                            <TouchableOpacity 
                            className={`w-24 h-8 rounded-xl justify-center ${accountChecked[index] ? 'bg-sky-500' : 'bg-gray-500'}`}
                            onPress={() => checkChange(index)} 
                            >
                                <View className='flex-row justify-center'>
                                <Text className='text-white text-center font-bold'>대표 계좌</Text>
                                <Text className={`text-white text-center font-bold ${accountChecked[index] ? ' text-yellow-300' : 'text-gray-400'}`}> ★ </Text>
                                </View>
                            </TouchableOpacity>
                        </View>      
                    </View>
             
                ))}
                <View className='justify-center items-center'>
                    <Button title="대표 계좌 등록하기" onPress={mainSelect}></Button>
                </View>
                </View>

            </ScrollView>
        </View>

         
       );
}

export default FamilyAccountList