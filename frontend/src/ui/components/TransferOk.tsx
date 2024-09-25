import { View, Text, Button, ImageBackground, Alert, TextInput, TouchableOpacity, ScrollView, StyleSheet } from 'react-native';
import { useRouter, Link } from 'expo-router';
import React, { useState } from 'react';

const TransferOk = () => {

    
     return(
        <View className='flex-1 flex-row h-5/6'>
            <View className='flex-1 justfiy-center items-center basis-4/5 m-2 p-2 bg-gray-300 h-72'>
                    <Text className='text-2xl'>송금이 완료되었습니다</Text>
                    <Link className='my-2 bg-blue-500 text-white rounded-3xl m-3 p-4' href={'/ward/main'}>메인으로</Link>
            </View>
        </View>
    )



}

export default TransferOk