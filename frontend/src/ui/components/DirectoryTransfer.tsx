import { View, Text, Button, ImageBackground, Alert, TextInput, TouchableOpacity, ScrollView, StyleSheet, SafeAreaView, KeyboardAvoidingView } from 'react-native';
import { useRouter, Link, useLocalSearchParams } from 'expo-router';
import React, { useState } from 'react';

interface existInfo {
    onChange: (inputBalance:string) => void;
    name?: string
}

const DirectoryTransfer = ({ onChange, name } : existInfo) => { 

    const [inputBalance, setInputBalance] = useState<string>(''); // 금액



    const existCheck = () =>{
        onChange(inputBalance)
    }

     return(
        <View className='flex-1 flex-row h-5/6'>
        <View className='flex-1 justfiy-center items-center basis-2/5 m-2 p-2 bg-gray-300'>
            <Text className='text-2xl m-2 text-center mt-4'>{name}님에게</Text>
            <Text className='text-2xl m-2 text-center mt-4'>얼마를 보낼까요?</Text>

        </View>

        <View className='flex-1 justfiy-center items-center basis-2/5 m-2 p-2 bg-gray-300'>
            <Text className='text-2xl m-2 text-center my-4'>보낼 금액</Text>
            <SafeAreaView>
                <View className='flex-row justify-center items-center'>
                    <TextInput className='my-1 w-64 h-12 bg-white' keyboardType='numeric' onChangeText={(balance) => setInputBalance(balance)} />
                    <Text className='my-2 text-2xl'>   원</Text>
                </View>
            </SafeAreaView>
            <Button title="확인" onPress={existCheck}></Button>
        </View>
        </View>

    )



}

export default DirectoryTransfer
