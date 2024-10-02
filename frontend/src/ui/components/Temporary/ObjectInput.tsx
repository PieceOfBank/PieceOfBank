import { View, Text, Button, ImageBackground, Alert, TextInput, TouchableOpacity, ScrollView, StyleSheet, SafeAreaView, KeyboardAvoidingView } from 'react-native';
import { useRouter, Link, useLocalSearchParams } from 'expo-router';
import React, { useState } from 'react';
import CancelButton from '../CancelButton';

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
            onPress={() => router.push('/family copy/DirectoryList')} 
            >
            <Text className='text-white text-center font-bold'>설정 완료</Text></TouchableOpacity>
            <CancelButton />
        </View>

      </View>

    )



}

export default ObjectInput
