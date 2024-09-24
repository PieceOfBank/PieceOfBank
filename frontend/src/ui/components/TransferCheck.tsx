import { View, Text, Button, ImageBackground, Alert, TextInput, TouchableOpacity, ScrollView, StyleSheet, SafeAreaView } from 'react-native';
import { useRouter, Link } from 'expo-router';
import React, { useState } from 'react';


const TransferCheck = ({ onChange }: {onChange:(value:string) => void}) => {

    const secondCheck = () =>{
        onChange('')
    }

     return(
        <View className='flex-1 flex-row h-5/6'>
            <View className='flex-1 justfiy-center items-center basis-3/5 m-2 p-2 bg-gray-300'>
                <Text className='text-2xl m-2 text-center my-4'>거래 확인</Text>
                <Text className='text-2xl m-2 text-center my-4'>님에게</Text>
                <Text className='text-2xl m-2 text-center my-4'>{onChange.name}원을 보내겠습니까?</Text>
                <Button title="확인" onPress={secondCheck}></Button>
            </View>
        </View>
    )

}

export default TransferCheck
