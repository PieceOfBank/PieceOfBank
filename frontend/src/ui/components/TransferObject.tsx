import { View, Text, Button, ImageBackground, Alert, TextInput, TouchableOpacity, ScrollView, StyleSheet, SafeAreaView, KeyboardAvoidingView } from 'react-native';
import { useRouter, Link } from 'expo-router';
import React, { useState } from 'react';

interface firstInfo {
    onChange: (inputNum:string, inputBank:string, inputAccount:string) => void;
}

const TransferObject = ({ onChange } : firstInfo) => {

    const [inputNum, setInputNum] = useState<string>(''); // 계좌번호

    const [inputBank, setInputBank] = useState<string>(''); // 은행  

    const [inputAccount, setInputAccount] = useState<string>(''); // 금액


    const firstCheck = () =>{
        onChange(inputNum, inputBank, inputAccount)
    }

     return(
        <KeyboardAvoidingView className='flex-1 flex-row h-5/6'>
        <View className='flex-1 justfiy-center items-center basis-2/5 m-2 p-2 bg-gray-300'>
            <Text className='text-2xl m-2 text-center mt-4'>보낼사람</Text>
            <SafeAreaView>
                <View>
                    <Text className='mb-2'>계좌번호</Text>
                    <TextInput className='my-1 w-80 h-12 bg-white' onChangeText={(num) => setInputNum(num)}/>
                </View>
                <View>
                    <Text className='mb-2'>은행</Text>
                    <TextInput className='my-1 w-80 h-12 bg-white' onChangeText={(bank) => setInputBank(bank)}/>
                </View>
            </SafeAreaView>
        </View>

        <View className='flex-1 justfiy-center items-center basis-2/5 m-2 p-2 bg-gray-300'>
            <Text className='text-2xl m-2 text-center my-4'>보낼 금액</Text>
            <SafeAreaView>
                <View className='flex-row justify-center items-center'>
                    <TextInput className='my-1 w-64 h-12 bg-white' keyboardType='numeric' onChangeText={(account) => setInputAccount(account)} />
                    <Text className='my-2 text-2xl'>   원</Text>
                </View>
            </SafeAreaView>
            <Button title="확인" onPress={firstCheck}></Button>
        </View>
        </KeyboardAvoidingView>

    )



}

export default TransferObject
