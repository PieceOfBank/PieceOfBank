import { View, Text, Button, ImageBackground, Alert, TextInput, TouchableOpacity, ScrollView, StyleSheet, SafeAreaView, KeyboardAvoidingView } from 'react-native';
import { useRouter, Link, useLocalSearchParams } from 'expo-router';
import React, { useState } from 'react';
import CancelButton from '../CancelButton';
import { createDirectory } from '../../../services/api';

interface existsInfo {
    onChange: (inputBalance:string) => void;
    name?: string
}

const ObjectInput = () => { 

    const [name, setName] = useState('')
    const [phone, setPhone] = useState('')
    const [bank, setBank] = useState('')
    const [account, setAccount] = useState('')

    const router = useRouter()

    const directoryGo = async() => {
      try{
        // 나중에 userkey 어떻게 받아와서 넣어야 할까요? authorization 으로 되는지 확인하기
        
        // interface UserInfo {
        //   userKey: string,
        //   accountNo: string,
        //   institutionCode: number,
        //   name: string,
        //   url: string
        // }
        
        // interface UserDirectory {
        //   directory: UserInfo;
        //   file: string;
        // }

        const userInfo = {
          userKey: "aaf1e84e-ba2d-4263-a581-b9a7e5582982",
          accountNo: "0016893582615978",
          institutionCode: 0,
          name: "test",
          url: "test"
        }

        // const imageInfo = {

        // }
        const JsonData = {
          directory: {
            userKey: "aaf1e84e-ba2d-4263-a581-b9a7e558298",
            accountNo: "0019312432084644",
            institutionCode: 0,
            name: "qkqkqk",
            url: "test"
          },
          file: "string"
        }
        console.log(JsonData)
        const response = await createDirectory(JsonData);
        console.log(response)
        } catch(error){
            console.log(error)
        }
    }
    
     return(
        <View className='justify-center items-center p-4'>
          <Text className='mt-12 mb-8 bg-gray-300 px-3 py-2 w-48 rounded-3xl text-xl text-center font-semibold'>대상자 설정</Text>
        <SafeAreaView className='bg-gray-200 rounded-3xl px-6 py-4'>
          <Text className="my-2 ml-2 font-semibold">이름</Text>
          <View className='flex-row'>
            <TextInput className="my-1 w-56 bg-white rounded-3xl py-1" onChangeText={(name) => setName(name)}></TextInput>
          </View>

          <Text className="my-2 ml-2 font-semibold">연락처</Text>
          <View className='flex-row'>
          <TextInput
            className="my-1 w-56 bg-white rounded-3xl py-1"
            keyboardType='numeric'
            onChangeText={(phone) => setPhone(phone)}></TextInput>
          </View>

          <Text className="my-2 ml-2 font-semibold">은행</Text>
          <View className='flex-row'>
          <TextInput
            className="my-1 w-56 bg-white rounded-3xl py-1"
            onChangeText={(bank) => setBank(bank)}></TextInput>
          </View>

          <Text className="my-2 ml-2 font-semibold">계좌번호</Text>
          <View className='flex-row'>
          <TextInput
            className="my-1 w-56 bg-white rounded-3xl py-1"
            keyboardType='numeric'
            onChangeText={(account) => setAccount(account)}></TextInput>
          </View>

        </SafeAreaView>
        <View className='flex-row mt-2'>
          <TouchableOpacity 
            className='m-2 py-2 px-4 bg-red-400 rounded-3xl bg-sky-500'
            onPress={directoryGo} 
            >
            <Text className='text-white text-center font-bold'>설정 완료</Text></TouchableOpacity>
            <CancelButton />
        </View>

      </View>

    )



}

export default ObjectInput
