import { View, Text, Button, ImageBackground, Alert, TextInput, TouchableOpacity, ScrollView, StyleSheet, SafeAreaView } from 'react-native';
import { useRouter, Link } from 'expo-router';
import React, { useState } from 'react';

interface mediaCheck {
  onChange: () => void;
}

const MediaConfirm = ({ onChange }: mediaCheck) => {
    const router = useRouter();

    const thirdCheck = () => {
        onChange()
    }

     return(
        <View className='flex-1 justify-center items-center'>
                <View className='bg-gray-300 rounded-xl w-3/4 p-2'>
                  <Text className='text-2xl text-center font-bold '>송금이 완료되었습니다</Text>
                  <Text className='text-2xl text-center font-bold'>미디어 송금을 할까요?</Text>
                </View>
                <View className='m-5'>
                </View>
            <Button title="네" onPress={thirdCheck}></Button>

        </View>

    )



}

export default MediaConfirm